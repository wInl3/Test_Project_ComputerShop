package dal;

import config.VnPayConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class VnPayUtil {

    public static String generateVnPayUrlWithoutOrderId(int amount, String bankCode, String note)
            throws UnsupportedEncodingException {

        String txnRef = "DEPOSIT" + System.currentTimeMillis();
        String orderInfo = (note != null && !note.trim().isEmpty()) ? note.trim() : "BuildPCDeposit";

        return generateUrlInternal(txnRef, amount, bankCode, orderInfo);
    }

    private static String generateUrlInternal(String txnRef, int amount, String bankCode, String orderInfo)
            throws UnsupportedEncodingException {

        Map<String, String> vnp_Params = new HashMap<>();
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VnPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", "billpayment");
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String name : fieldNames) {
            String value = vnp_Params.get(name);
            if (hashData.length() > 0) {
                hashData.append('&');
                query.append('&');
            }
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");

            hashData.append(name).append('=').append(encodedValue);
            query.append(name).append('=').append(encodedValue);
        }

        String secureHash = hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);
        return VnPayConfig.vnp_PayUrl + "?" + query.toString();
    }

    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(bytes).toLowerCase();
        } catch (Exception ex) {
            return null;
        }
    }
}
