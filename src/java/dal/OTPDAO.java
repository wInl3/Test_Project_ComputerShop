package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import models.OTP;

public class OTPDAO extends DBContext {

    // Thêm OTP mới
    public void insertOTP(String email, String otpCode, Date expirationTime) {
        String sql = "INSERT INTO OTP (Email, OTP_Code, ExpirationTime, CreatedAt, IsUsed)\n"
                + "VALUES (?, ?, ?, NOW(), 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otpCode);
            ps.setTimestamp(3, new Timestamp(expirationTime.getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy OTP hợp lệ theo email và mã OTP
    public OTP getValidOTP(String email, String otpCode) {
        String sql = "SELECT OTP_ID, Email, OTP_Code, ExpirationTime, CreatedAt, IsUsed\n"
                + "FROM OTP\n"
                + "WHERE Email = ? AND OTP_Code = ? AND IsUsed = 0 AND ExpirationTime > NOW()\n"
                + "ORDER BY CreatedAt DESC\n"
                + "LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otpCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OTP otp = new OTP();
                    otp.setOtpId(rs.getInt("OTP_ID"));
                    otp.setEmail(rs.getString("Email"));
                    otp.setOtpCode(rs.getString("OTP_Code"));
                    otp.setExpirationTime(rs.getTimestamp("ExpirationTime"));
                    otp.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    otp.setUsed(rs.getBoolean("IsUsed"));
                    return otp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy OTP mới nhất theo email và mã OTP
    public OTP getLatestOTP(String email, String otpCode) {
        String sql = "SELECT OTP_ID, Email, OTP_Code, ExpirationTime, CreatedAt, IsUsed\n"
                + "FROM OTP\n"
                + "WHERE Email = ? AND OTP_Code = ?\n"
                + "ORDER BY CreatedAt DESC\n"
                + "LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otpCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OTP otp = new OTP();
                    otp.setOtpId(rs.getInt("OTP_ID"));
                    otp.setEmail(rs.getString("Email"));
                    otp.setOtpCode(rs.getString("OTP_Code"));
                    otp.setExpirationTime(rs.getTimestamp("ExpirationTime"));
                    otp.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    otp.setUsed(rs.getBoolean("IsUsed"));
                    return otp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy OTP mới nhất theo email (không cần đúng mã OTP)
    public OTP getLatestOTPByEmail(String email) {
        String sql = "SELECT OTP_ID, Email, OTP_Code, ExpirationTime, CreatedAt, IsUsed\n"
                + "FROM OTP\n"
                + "WHERE Email = ?\n"
                + "ORDER BY CreatedAt DESC\n"
                + "LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OTP otp = new OTP();
                    otp.setOtpId(rs.getInt("OTP_ID"));
                    otp.setEmail(rs.getString("Email"));
                    otp.setOtpCode(rs.getString("OTP_Code"));
                    otp.setExpirationTime(rs.getTimestamp("ExpirationTime"));
                    otp.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    otp.setUsed(rs.getBoolean("IsUsed"));
                    return otp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đánh dấu OTP đã sử dụng
    public void setOTPUsed(int otpId) {
        String sql = "UPDATE OTP SET IsUsed = 1 WHERE OTP_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, otpId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa các OTP đã hết hạn hoặc đã dùng (tuỳ chọn)
    public void deleteExpiredOrUsedOTP() {
        String sql = "DELETE FROM OTP WHERE IsUsed = 1 OR ExpirationTime < NOW()";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
