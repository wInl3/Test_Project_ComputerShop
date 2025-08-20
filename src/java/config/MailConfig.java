/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config; // đổi package nếu bạn để file ở nơi khác

import java.io.InputStream;
import java.util.Properties;

public class MailConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = MailConfig.class.getClassLoader()
                .getResourceAsStream("config/mail.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                System.err.println("⚠️ Không tìm thấy file mail.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Lỗi khi đọc file mail.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
