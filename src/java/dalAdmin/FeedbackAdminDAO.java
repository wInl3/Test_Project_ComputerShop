/*
 * Click nb://source://SystemFileSystemAdmin/Templates/LicensesAdmin/license-default.txt to change this license
 * Click nb://source://SystemFileSystemAdmin/Templates/Classes/Class.java to edit this template
 */
package dalAdmin;

import java.sql.*;
import java.util.*;
import models.Feedback;

public class FeedbackAdminDAO extends DBAdminContext {

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.FeedbackID, f.UserID, f.Content, f.OrderItemID, f.CreatedAt, f.Rate, f.Status, u.FullName, f.Reply, " +
                     "c.CategoryID, c.CategoryName, r.RoleName " +
                     "FROM Feedbacks f " +
                     "JOIN Users u ON f.UserID = u.UserID " +
                     "JOIN Roles r ON u.RoleID = r.RoleID " +
                     "JOIN OrderItems oi ON f.OrderItemID = oi.OrderItemID " +
                     "JOIN Categories c ON oi.CategoryID = c.CategoryID " +
                     "ORDER BY f.CreatedAt DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Feedback f = new Feedback(
                    rs.getInt("FeedbackID"),
                    rs.getInt("UserID"),
                    rs.getString("Content"),
                    rs.getInt("OrderItemID"),
                    rs.getString("CreatedAt"),
                    rs.getInt("Rate"),
                    rs.getInt("Status"),
                    rs.getString("FullName"),
                    rs.getString("Reply"),
                    rs.getInt("CategoryID"),
                    rs.getString("CategoryName"),
                    rs.getString("RoleName")
                );
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Feedback getFeedbackById(int feedbackID) {
        String sql = "SELECT f.FeedbackID, f.UserID, f.Content, f.OrderItemID, f.CreatedAt, f.Rate, f.Status, u.FullName, f.Reply, r.RoleName " +
                     "FROM Feedbacks f JOIN Users u ON f.UserID = u.UserID JOIN Roles r ON u.RoleID = r.RoleID WHERE f.FeedbackID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, feedbackID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getInt("UserID"),
                        rs.getString("Content"),
                        rs.getInt("OrderItemID"),
                        rs.getString("CreatedAt"),
                        rs.getInt("Rate"),
                        rs.getInt("Status"),
                        rs.getString("FullName"),
                        rs.getString("Reply"),
                        0, // categoryID (không lấy ở đây)
                        null, // categoryName (không lấy ở đây)
                        rs.getString("RoleName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedbacks (UserID, Content, OrderItemID, Rate, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, feedback.getUserID());
            ps.setString(2, feedback.getContent());
            ps.setInt(3, feedback.getOrderItemID());
            ps.setInt(4, feedback.getRate());
            ps.setInt(5, feedback.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFeedback(int feedbackID, Feedback feedback) {
        String sql = "UPDATE Feedbacks SET Content=?, OrderItemID=?, Rate=?, Status=? WHERE FeedbackID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, feedback.getContent());
            ps.setInt(2, feedback.getOrderItemID());
            ps.setInt(3, feedback.getRate());
            ps.setInt(4, feedback.getStatus());
            ps.setInt(5, feedbackID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFeedback(int feedbackID) {
        String sql = "DELETE FROM Feedbacks WHERE FeedbackID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, feedbackID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm các hàm updateFeedbackStatus và replyFeedback nếu cần cho admin
    public boolean updateFeedbackStatus(int feedbackID, int status) {
        String sql = "UPDATE Feedbacks SET Status=? WHERE FeedbackID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, feedbackID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean replyFeedback(int feedbackID, String reply) {
        String sql = "UPDATE Feedbacks SET Reply=? WHERE FeedbackID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reply);
            ps.setInt(2, feedbackID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Feedback> getUsersWithFeedbackSummary() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT DISTINCT u.UserID, u.FullName, r.RoleName, " +
                     "COUNT(f.FeedbackID) as totalFeedbacks, " +
                     "SUM(CASE WHEN f.Status = 0 THEN 1 ELSE 0 END) as inactiveCount, " +
                     "SUM(CASE WHEN f.Status = 1 THEN 1 ELSE 0 END) as activeCount, " +
                     "SUM(CASE WHEN f.Status = 2 THEN 1 ELSE 0 END) as repliedCount, " +
                     "MAX(f.FeedbackID) as latestFeedbackID, " +
                     "MAX(f.Status) as latestStatus " +
                     "FROM Users u " +
                     "JOIN Roles r ON u.RoleID = r.RoleID " +
                     "LEFT JOIN Feedbacks f ON u.UserID = f.UserID " +
                     "WHERE f.FeedbackID IS NOT NULL " +
                     "GROUP BY u.UserID, u.FullName, r.RoleName " +
                     "ORDER BY totalFeedbacks DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setUserID(rs.getInt("UserID"));
                f.setFullname(rs.getString("FullName"));
                f.setRoleName(rs.getString("RoleName"));
                f.setFeedbackID(rs.getInt("latestFeedbackID"));
                f.setStatus(rs.getInt("latestStatus"));
                f.setContent("Total: " + rs.getInt("totalFeedbacks") + 
                           " | Active: " + rs.getInt("activeCount") + 
                           " | Inactive: " + rs.getInt("inactiveCount") + 
                           " | Replied: " + rs.getInt("repliedCount"));
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Feedback> getFeedbacksByUserId(int userID) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.FeedbackID, f.UserID, f.Content, f.OrderItemID, f.CreatedAt, f.Rate, f.Status, u.FullName, f.Reply, r.RoleName " +
                     "FROM Feedbacks f " +
                     "JOIN Users u ON f.UserID = u.UserID " +
                     "JOIN Roles r ON u.RoleID = r.RoleID " +
                     "WHERE f.UserID = ? " +
                     "ORDER BY f.CreatedAt DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback f = new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getInt("UserID"),
                        rs.getString("Content"),
                        rs.getInt("OrderItemID"),
                        rs.getString("CreatedAt"),
                        rs.getInt("Rate"),
                        rs.getInt("Status"),
                        rs.getString("FullName"),
                        rs.getString("Reply"),
                        0, // categoryID (không lấy ở đây)
                        null, // categoryName (không lấy ở đây)
                        rs.getString("RoleName")
                    );
                    list.add(f);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
