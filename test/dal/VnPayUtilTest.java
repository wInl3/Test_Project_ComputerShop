package dal;

import static org.junit.Assert.*;

import config.VnPayConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class VnPayUtilTest {

    @BeforeClass
    public static void setup() {
        VnPayConfig.vnp_TmnCode = "TESTCODE";
        VnPayConfig.vnp_ReturnUrl = "http://localhost/return";
        VnPayConfig.vnp_HashSecret = "SECRETKEY";
        VnPayConfig.vnp_PayUrl = "http://sandbox.vnpay.vn/payment";
    }

    private static Map<String, String> parseQuery(String url) throws Exception {
        URI uri = new URI(url);
        String query = uri.getQuery();
        Map<String, String> map = new LinkedHashMap<>();
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8.name());
            String v = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name()) : "";
            map.put(k, v);
        }
        return map;
    }

    @Test
    public void testWithBankCode() throws Exception {
        String url = VnPayUtil.generateUrlInternal("TXN123", 1000, "NCB", "Test order");

        assertNotNull(url);
        assertTrue(url.startsWith("http://sandbox.vnpay.vn/payment?"));

        Map<String, String> q = parseQuery(url);
        assertEquals("100000", q.get("vnp_Amount")); // 1000*100
        assertEquals("NCB", q.get("vnp_BankCode"));
        assertEquals("Test order", q.get("vnp_OrderInfo"));
        assertEquals("TESTCODE", q.get("vnp_TmnCode"));
        assertNotNull(q.get("vnp_SecureHash"));
    }

    
    @Test
    public void testWithoutBankCode() throws Exception {
        String url = VnPayUtil.generateUrlInternal("TXN123", 500, "", "Another test");

        Map<String, String> q = parseQuery(url);
        assertEquals("50000", q.get("vnp_Amount"));
        assertEquals("Another test", q.get("vnp_OrderInfo"));
        assertFalse(q.containsKey("vnp_BankCode"));
    }

    
    @Test
    public void testEncodingSpecialCharacters() throws Exception {
        String url = VnPayUtil.generateUrlInternal("TXN123", 1, null, "Test order");

        Map<String, String> q = parseQuery(url);
        assertEquals("Test order", q.get("vnp_OrderInfo")); // Dấu cách phải giữ đúng
    }

    
    @Test
    public void testWithZeroAmount() throws Exception {
        String url = VnPayUtil.generateUrlInternal("TXN123", 0, "NCB", "Test order");

        Map<String, String> q = parseQuery(url);
        assertEquals("0", q.get("vnp_Amount"));  // amount * 100 = 0
        assertEquals("Test order", q.get("vnp_OrderInfo"));
    }
    
    
    @Test
    public void testWithNullOrderInfo() throws Exception {
        String url = VnPayUtil.generateUrlInternal("TXN123", 1000, "NCB", null);

        Map<String, String> q = parseQuery(url);
        assertEquals("100000", q.get("vnp_Amount")); // 100*100
        // Nếu orderInfo = null thì vnp_OrderInfo vẫn tồn tại nhưng giá trị = null
        assertTrue(q.containsKey("vnp_OrderInfo"));
        assertNull(q.get("vnp_OrderInfo"));
    }

    
    @Test(expected = NullPointerException.class)
    public void testWithNullTxnRef_shouldThrowException() throws Exception {
        VnPayUtil.generateUrlInternal(null, 1000, "NCB", "Test order");
    }
    
    
    @Test
    public void testWithBlankTxnRef_shouldStillReturnUrl() throws Exception {
        String url = VnPayUtil.generateUrlInternal("   ", 1000, "NCB", "Test order");

        Map<String, String> q = parseQuery(url);
        assertEquals("100000", q.get("vnp_Amount")); // 1000*100
        assertEquals("   ", q.get("vnp_TxnRef"));    // giữ nguyên chuỗi trắng
    }

    
}
