/*
 * Click nb://source://SystemFileSystemAdmin/Templates/LicensesAdmin/license-default.txt to change this license
 * Click nb://source://SystemFileSystemAdmin/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.util.*;
import models.Feedback;

public class FeedbackDAO extends DBContext {

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

    public boolean insertFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedbacks (UserID, Content, OrderItemID, Rate, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, feedback.getUserID());
            ps.setString(2, feedback.getContent());
            ps.setInt(3, feedback.getOrderItemID());
            ps.setInt(4, feedback.getRate());
            ps.setInt(5, 1); // Status mặc định là 1
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Feedback> getFeedbackByCategoryId(int categoryId) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.FeedbackID, f.UserID, f.Content, f.OrderItemID, f.CreatedAt, f.Rate, f.Status, u.FullName, f.Reply, r.RoleName " +
                     "FROM Feedbacks f " +
                     "JOIN OrderItems oi ON f.OrderItemID = oi.OrderItemID " +
                     "JOIN Users u ON f.UserID = u.UserID " +
                     "JOIN Roles r ON u.RoleID = r.RoleID " +
                     "WHERE oi.CategoryID = ? ORDER BY f.CreatedAt DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
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

    public Feedback getFeedbackById(int feedbackID) {
        String sql = "SELECT f.FeedbackID, f.UserID, f.Content, f.OrderItemID, f.CreatedAt, f.Rate, f.Status, u.FullName, f.Reply, r.RoleName " +
                     "FROM Feedbacks f JOIN Users u ON f.UserID = u.UserID JOIN Roles r ON u.RoleID = r.RoleID WHERE f.FeedbackID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, feedbackID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Feedback newFeedback = new Feedback(
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
                    newFeedback.setOrderItems(new OrderItemDAO().getOrderItemByID(rs.getInt("OrderItemID")));
                    return newFeedback;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateFeedback(Feedback feedback) {
        String sql = "UPDATE Feedbacks SET Content=?, OrderItemID=?, Rate=?, Status=? WHERE FeedbackID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, feedback.getContent());
            ps.setInt(2, feedback.getOrderItemID());
            ps.setInt(3, feedback.getRate());
            ps.setInt(4, feedback.getStatus());
            ps.setInt(5, feedback.getFeedbackID());
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

    public ArrayList<Feedback> getFeedbackByUserId(int userID, int OrderItemID) {
        ArrayList<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.FeedbackID, f.UserID, f.Content, f.OrderItemID, f.CreatedAt, f.Rate, f.Status, u.FullName, f.Reply, r.RoleName " +
                     "FROM Feedbacks f JOIN Users u ON f.UserID = u.UserID JOIN Roles r ON u.RoleID = r.RoleID WHERE f.UserID = ? AND OrderItemID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, OrderItemID);
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
                    f.setOrderItems(new OrderItemDAO().getOrderItemByID(OrderItemID));
                    list.add(f);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
