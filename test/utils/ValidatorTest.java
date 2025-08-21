package utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class ValidatorTest {

    // ---------- Test data hợp lệ ----------
    private final String validEmail = "duc@mail.com";
    private final String validName = "Minh Duc";
    private final String validNameVN = "Nguyễn Minh Đức";
    private final String validPhone = "0123456789";
    private final String validPass = "aaaaaaaa"; // 8 ký tự tối thiểu hợp lệ

    // ---------------- 1) Normal Assert Test ----------------
    @Test
    public void normalAssert_validInput_returnsTrue() {
        assertTrue(Validator.validateNewUser(validEmail, validName, validPhone, validPass));
    }

    // ---------------- 2) Test with Timeout ----------------
    @Test(timeout = 100) // ms
    public void timeout_validateRunsFast() {
        for (int i = 0; i < 200; i++) {
            assertTrue(Validator.validateNewUser(validEmail, validName, validPhone, validPass));
        }
    }

    // ---------------- 3) Test with Exception ----------------

    // 3.1 Email
    @Test
    public void exception_emailRequired_messageCheck() {
        try {
            Validator.validateNewUser("", validNameVN, validPhone, validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Email is required!", ex.getMessage());
        }
    }

    @Test
    public void exception_invalidEmailFormat_messageCheck() {
        try {
            Validator.validateNewUser("abc@", validName, validPhone, validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid email format!", ex.getMessage());
        }
    }

    // 3.2 Full name
    @Test
    public void exception_fullNameRequired_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, "   ", validPhone, validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Full name is required!", ex.getMessage());
        }
    }

    @Test
    public void exception_fullNameHasNumber_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, "Nguyen Van A1", validPhone, validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Full name must not contain numbers or special characters!", ex.getMessage());
        }
    }

    @Test
    public void exception_fullNameHasSpecialChar_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, "Nguyen@A", validPhone, validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Full name must not contain numbers or special characters!", ex.getMessage());
        }
    }

    // 3.3 Phone number
    @Test
    public void exception_phoneRequired_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, validName, "   ", validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Phone number is required!", ex.getMessage());
        }
    }

    @Test
    public void exception_invalidPhoneFormat_tooShort_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, validName, "01245", validPass); // không đủ 10 số
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid phone number format!", ex.getMessage());
        }
    }

    @Test
    public void exception_invalidPhoneFormat_containsLetter_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, validName, "012a451426", validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid phone number format!", ex.getMessage());
        }
    }

    @Test
    public void exception_invalidPhoneFormat_containsSpecial_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, validName, "012@451!26", validPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid phone number format!", ex.getMessage());
        }
    }

    @Test
    public void exception_invalidPhoneFormat_tooLong_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, validName, "01234543632", validPass); // 11 số
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid phone number format!", ex.getMessage());
        }
    }

    // 3.4 Password
    @Test
    public void exception_passwordRequired_messageCheck() {
        try {
            Validator.validateNewUser(validEmail, validName, validPhone, "   "); // password trống
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Password is required!", ex.getMessage());
        }
    }

    @Test
    public void exception_passwordTooShort_messageCheck() {
        String shortPass = "abc123"; // 6 ký tự < 8
        try {
            Validator.validateNewUser(validEmail, validName, validPhone, shortPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Password must be between 8 and 64 characters long!", ex.getMessage());
        }
    }

    @Test
    public void exception_passwordTooLong_messageCheck() {
        // tạo chuỗi dài 65 ký tự
        String longPass = new String(new char[65]).replace('\0', 'a');
        try {
            Validator.validateNewUser(validEmail, validName, validPhone, longPass);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("Password must be between 8 and 64 characters long!", ex.getMessage());
        }
    }

    @Test
    public void valid_passwordMinLength_messageCheck() {
        String validMinPass = "abcdefgh"; // đúng 8 ký tự
        assertTrue(Validator.validateNewUser(validEmail, validName, validPhone, validMinPass));
    }

    @Test
    public void valid_passwordMaxLength_messageCheck() {
        String validMaxPass = new String(new char[64]).replace('\0', 'b'); // đúng 64 ký tự
        assertTrue(Validator.validateNewUser(validEmail, validName, validPhone, validMaxPass));
    }
}
