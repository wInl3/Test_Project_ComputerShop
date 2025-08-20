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
        if (phoneNumber == null) return false;
        return phoneNumber.matches("0\\d{9}");
    }
    
    // Helper method to check if a String is null or empty (now static for reuse)
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Validates an email address using regex pattern
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        // Basic email pattern:
        // - At least one character before @
        // - At least one character between @ and .
        // - At least two characters after last .
        // - Can contain letters, digits, dots, underscores, and hyphens
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private Validator() {
        // Prevent instantiation
    }
}
