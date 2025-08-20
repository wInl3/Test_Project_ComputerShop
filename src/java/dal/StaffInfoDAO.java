package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import models.StaffInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffInfoDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(StaffInfoDAO.class.getName());

    public void setConnection(Connection conn) {
        this.connection = conn;
    }

    public StaffInfo getStaffInfoByUserId(int userId) {
        LOGGER.info("Getting staff info for UserID: " + userId);
        String sql = "SELECT * FROM StaffInfo WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                StaffInfo info = new StaffInfo(
                        rs.getInt("StaffInfoID"),
                        rs.getInt("UserID"),
                        rs.getString("StartedDate"),
                        rs.getString("EndDate")
                );
                return info;
            } else {
                LOGGER.info("No StaffInfo found for UserID: " + userId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting staff info for UserID: " + userId, e);
        }
        return null;
    }

    public boolean createStaffInfo(int userId, String startedDate, String endDate) {
        String sql = "INSERT INTO StaffInfo (UserID, StartedDate, EndDate) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            System.out.println("→ staffInfoDAO: Preparing insert for UserID = " + userId);

            ps.setInt(1, userId);
            ps.setString(2, startedDate);

            if (endDate == null || endDate.trim().isEmpty()) {
                ps.setNull(3, java.sql.Types.DATE);
                System.out.println("→ EndDate set to NULL");
            } else {
                ps.setDate(3, java.sql.Date.valueOf(endDate));
            }

            int rows = ps.executeUpdate();
            System.out.println("→ Inserted staff info rows: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❗ Error creating staff info: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStaffInfo(int userId, String startedDate, String endDate) {
        String sql = "UPDATE StaffInfo SET StartedDate = ?, EndDate = ? WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, startedDate);
            if (endDate == null || endDate.trim().isEmpty()) {
                ps.setNull(2, java.sql.Types.DATE);
            } else {
                ps.setDate(2, java.sql.Date.valueOf(endDate));
            }
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating staff info: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStaffInfo(int userId) {
        String sql = "DELETE FROM StaffInfo WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting staff info: " + e.getMessage());
            return false;
        }
    }

    // Get all active staff with their basic info
    public List<StaffInfo> getAllActiveStaffInfos() {
        List<StaffInfo> staffInfos = new ArrayList<>();
        String sql = "SELECT si.StaffInfoID, si.UserID, si.StartedDate, si.EndDate "
                + "FROM StaffInfo si "
                + "JOIN Users u ON si.UserID = u.UserID "
                + "WHERE u.Status = 1 AND u.RoleID = 2";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StaffInfo info = new StaffInfo(
                        rs.getInt("StaffInfoID"),
                        rs.getInt("UserID"),
                        rs.getString("StartedDate"),
                        rs.getString("EndDate")
                );
                staffInfos.add(info);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all active staff infos: " + e.getMessage());
        }
        return staffInfos;
    }

    // Get staff info with user details
    public StaffInfo getStaffInfoWithUser(int staffInfoId) {
        String sql = "SELECT si.*, u.FullName, u.Email, u.PhoneNumber, u.Status "
                + "FROM StaffInfo si "
                + "JOIN Users u ON si.UserID = u.UserID "
                + "WHERE si.StaffInfoID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, staffInfoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                StaffInfo info = new StaffInfo(
                        rs.getInt("StaffInfoID"),
                        rs.getInt("UserID"),
                        rs.getString("StartedDate"),
                        rs.getString("EndDate")
                );
                // You might want to set additional user information here if needed
                return info;
            }
        } catch (SQLException e) {
            System.out.println("Error getting staff info with user details: " + e.getMessage());
        }
        return null;
    }

    // Update staff working period
    public boolean updateWorkingPeriod(int userId, Date startedDate, Date endDate) {
        String sql = "UPDATE StaffInfo SET StartedDate = ?, EndDate = ? WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, startedDate);
            ps.setDate(2, endDate);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating staff working period: " + e.getMessage());
            return false;
        }
    }

    // Check if user has staff info
    public boolean hasStaffInfo(int userId) {
        String sql = "SELECT COUNT(*) FROM StaffInfo WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking staff info existence: " + e.getMessage());
        }
        return false;
    }

    // Get current staff count
    public int getActiveStaffCount() {
        String sql = "SELECT COUNT(*) FROM StaffInfo si "
                + "JOIN Users u ON si.UserID = u.UserID "
                + "WHERE u.Status = 1 AND u.RoleID = 2";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting active staff count: " + e.getMessage());
        }
        return 0;
    }
}
