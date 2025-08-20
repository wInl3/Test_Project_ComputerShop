package dal;

import static org.junit.Assert.*;
import org.junit.Test;

import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class VnPayUtilTest {

    // Hàm tiện ích để parse query string trong URL thành Map
    private static Map<String, String> parseQuery(String url) throws Exception {
        URI uri = new URI(url);
        String query = uri.getQuery();
        Map<String, String> map = new LinkedHashMap<>();
        if (query == null || query.isEmpty()) {
            return map;
        }
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8.name());
            String v = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name()) : "";
            map.put(k, v);
        }
        return map;
    }

    @Test
    public void withBankCode_shouldContainBankCodeAndParams() throws Exception {
        int amount = 12345;
        String bankCode = "NCB";
        String note = " Test note ";

        String url = VnPayUtil.generateVnPayUrlWithoutOrderId(amount, bankCode, note);
        assertNotNull(url);
        assertTrue(url.contains("?")); // URL phải có query

        Map<String, String> q = parseQuery(url);
        assertEquals(String.valueOf(amount * 100), q.get("vnp_Amount"));
        assertEquals("NCB", q.get("vnp_BankCode"));
        assertEquals("Test note", q.get("vnp_OrderInfo")); // note được trim
        assertEquals("2.1.0", q.get("vnp_Version"));
        assertEquals("pay", q.get("vnp_Command"));
        assertNotNull(q.get("vnp_TxnRef"));
        assertNotNull(q.get("vnp_CreateDate"));
        assertNotNull(q.get("vnp_SecureHash")); // đã có chữ ký
    }

    @Test
    public void withoutBankCode_shouldOmitBankCodeAndUseDefaultOrderInfo() throws Exception {
        int amount = 5000;
        String bankCode = null;
        String note = "   "; // chỉ khoảng trắng

        String url = VnPayUtil.generateVnPayUrlWithoutOrderId(amount, bankCode, note);
        assertNotNull(url);

        Map<String, String> q = parseQuery(url);
        assertEquals(String.valueOf(amount * 100), q.get("vnp_Amount"));
        assertFalse("vnp_BankCode không nên có khi null/empty", q.containsKey("vnp_BankCode"));
        assertEquals("BuildPCDeposit", q.get("vnp_OrderInfo")); // mặc định khi note rỗng
        assertNotNull(q.get("vnp_SecureHash"));
    }
    
    @Test
    public void withZeroAmount_shouldGenerateCorrectUrl() throws Exception {
        int amount = 0;
        String bankCode = "NCB";
        String note = "Test note";

        String url = VnPayUtil.generateVnPayUrlWithoutOrderId(amount, bankCode, note);
        assertNotNull(url);

        Map<String, String> q = parseQuery(url);
        assertEquals("0", q.get("vnp_Amount")); // amount * 100 = 0
    }

    
    @Test
    public void hmacSHA512_shouldReturnExpectedHash() throws Exception {
        String key = "secret";
        String data = "message";

        // Tự tính expected bằng Java (giống hệt code trong VnPayUtil)
        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmac512.init(secretKey);
        byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        String expected = sb.toString();

        String actual = VnPayUtil.hmacSHA512(key, data);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void hmacSHA512_withNullKey_shouldReturnNull() {
        String actual = VnPayUtil.hmacSHA512(null, "message");
        assertNull(actual);
    }

    @Test
    public void withEmptyNote_shouldUseDefaultOrderInfo() throws Exception {
        int amount = 5000;
        String bankCode = "NCB";
        String note = ""; // Chuỗi rỗng

        String url = VnPayUtil.generateVnPayUrlWithoutOrderId(amount, bankCode, note);
        assertNotNull(url);

        Map<String, String> q = parseQuery(url);
        assertEquals("BuildPCDeposit", q.get("vnp_OrderInfo")); // Mặc định khi note rỗng
    }

    @Test
    public void withEmptyBankCode_shouldOmitBankCode() throws Exception {
        int amount = 5000;
        String bankCode = ""; // Chuỗi rỗng
        String note = "Test note";

        String url = VnPayUtil.generateVnPayUrlWithoutOrderId(amount, bankCode, note);
        assertNotNull(url);

        Map<String, String> q = parseQuery(url);
        assertFalse("vnp_BankCode không nên có khi empty", q.containsKey("vnp_BankCode"));
    }


}
