/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author PC ASUS
 */
public class Validator {

    // Validates a phone number (must be exactly 10 digits, starts with 0)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches("0\\d{9}");
    }

    // Helper method to check if a String is null or empty (now static for reuse)
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Validates an email address using regex pattern
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        // Basic email pattern:
        // - At least one character before @
        // - At least one character between @ and .
        // - At least two characters after last .
        // - Can contain letters, digits, dots, underscores, and hyphens
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean validateNewUser(
            String email,
            String fullName,
            String phoneNumber,
            String hashedPassword
    ) {
        // --- Kiểm tra Email ---
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required!");
        } else {
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Invalid email format!");
            }
        }

        // --- Kiểm tra Full Name ---
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required!");
        } else {
            // Chỉ cho phép chữ cái (có dấu) và khoảng trắng
            if (!fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) {
                throw new IllegalArgumentException("Full name must not contain numbers or special characters!");
            }
        }

        // --- Kiểm tra Phone Number ---
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required!");
        } else {
            if (!phoneNumber.matches("0\\d{9}")) {
                throw new IllegalArgumentException("Invalid phone number format!");
            }
        }

        // --- Kiểm tra Password Hash ---
        if (hashedPassword == null || hashedPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required!");
        } else {
            if (hashedPassword.length() < 8 || hashedPassword.length() > 64) {
                throw new IllegalArgumentException("Password must be between 8 and 64 characters long!");
            }
            // hợp lệ thì bỏ qua, không cần regex phức tạp nữa
        }

// Nếu không có lỗi gì, trả về true
        return true;

    }

    private Validator() {
        // Prevent instantiation
    }

}
