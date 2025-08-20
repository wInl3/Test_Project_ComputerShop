package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.CustomerInfo;

public class CustomerInfoDAO extends DBContext {
    private static final Logger LOGGER = Logger.getLogger(CustomerInfoDAO.class.getName());
    
    public CustomerInfo getCustomerInfoByUserId(int userId) {
        LOGGER.info("Getting customer info for UserID: " + userId);
        String sql = "SELECT * FROM CustomerInfo WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            LOGGER.info("Executing query: " + sql + " with UserID = " + userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CustomerInfo info = new CustomerInfo(
                    rs.getInt("CustomerInfoID"),
                    rs.getInt("UserID"),
                    rs.getString("Address")
                );
                LOGGER.info("Found CustomerInfo - ID: " + info.getCustomerInfoID() + ", Address: " + info.getAddress());
                return info;
            } else {
                LOGGER.info("No CustomerInfo found for UserID: " + userId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting customer info for UserID: " + userId, e);
        }
        return null;
    }
    
    public boolean createCustomerInfo(int userId, String address) {
        String sql = "INSERT INTO CustomerInfo (UserID, Address) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, address);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating customer info: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateCustomerInfo(int userId, String address) {
        String sql = "UPDATE CustomerInfo SET Address = ? WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating customer info: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteCustomerInfo(int userId) {
        String sql = "DELETE FROM CustomerInfo WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting customer info: " + e.getMessage());
            return false;
        }
    }
    
    // Get all customers with their basic info
    public List<CustomerInfo> getAllCustomerInfos() {
        List<CustomerInfo> customerInfos = new ArrayList<>();
        String sql = "SELECT CustomerInfoID, UserID, Address FROM CustomerInfo";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerInfo info = new CustomerInfo(
                    rs.getInt("CustomerInfoID"),
                    rs.getInt("UserID"),
                    rs.getString("Address")
                );
                customerInfos.add(info);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all customer infos: " + e.getMessage());
        }
        return customerInfos;
    }

    // Get customer info with user details
    public CustomerInfo getCustomerInfoWithUser(int customerInfoId) {
        String sql = "SELECT ci.*, u.FullName, u.Email, u.PhoneNumber, u.Status " +
                    "FROM CustomerInfo ci " +
                    "JOIN Users u ON ci.UserID = u.UserID " +
                    "WHERE ci.CustomerInfoID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerInfoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CustomerInfo info = new CustomerInfo(
                    rs.getInt("CustomerInfoID"),
                    rs.getInt("UserID"),
                    rs.getString("Address")
                );
                // You might want to set additional user information here if needed
                return info;
            }
        } catch (SQLException e) {
            System.out.println("Error getting customer info with user details: " + e.getMessage());
        }
        return null;
    }

    // Check if user has customer info
    public boolean hasCustomerInfo(int userId) {
        String sql = "SELECT COUNT(*) FROM CustomerInfo WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking customer info existence: " + e.getMessage());
        }
        return false;
    }
}
