package dalAdmin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Notification;
import java.sql.Connection;

/**
 *
 * @author Admin
 */
public class NotificationAdminDAO extends DBAdminContext {
    
    public List<Notification> getAllNotifications() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.NotificationID, n.UserID, n.SenderID, u.FullName as SenderName, n.Title, n.Message, n.IsRead, n.CreatedAt " +
                     "FROM Notifications n LEFT JOIN Users u ON n.SenderID = u.UserID ORDER BY n.CreatedAt DESC";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(rs.getInt("NotificationID"));
                notification.setUserID(rs.getInt("UserID"));
                notification.setSenderID(rs.getInt("SenderID"));
                notification.setSenderName(rs.getString("SenderName"));
                notification.setTitle(rs.getString("Title"));
                notification.setMessage(rs.getString("Message"));
                notification.setIsRead(rs.getBoolean("IsRead"));
                notification.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(notification);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all notifications: " + e.getMessage());
        }
        return list;
    }
    
    public List<Notification> getNotificationsByUserID(int userID) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.NotificationID, n.UserID, n.SenderID, u.FullName as SenderName, n.Title, n.Message, n.IsRead, n.CreatedAt " +
                     "FROM Notifications n LEFT JOIN Users u ON n.SenderID = u.UserID WHERE n.UserID = ? ORDER BY n.CreatedAt DESC";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(rs.getInt("NotificationID"));
                notification.setUserID(rs.getInt("UserID"));
                notification.setSenderID(rs.getInt("SenderID"));
                notification.setSenderName(rs.getString("SenderName"));
                notification.setTitle(rs.getString("Title"));
                notification.setMessage(rs.getString("Message"));
                notification.setIsRead(rs.getBoolean("IsRead"));
                notification.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(notification);
            }
        } catch (SQLException e) {
            System.out.println("Error getting notifications by user ID: " + e.getMessage());
        }
        return list;
    }
    
    public List<Notification> getUnreadNotifications(int userID) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.NotificationID, n.UserID, n.SenderID, u.FullName as SenderName, n.Title, n.Message, n.IsRead, n.CreatedAt " +
                     "FROM Notifications n LEFT JOIN Users u ON n.SenderID = u.UserID WHERE n.UserID = ? AND n.IsRead = 0 ORDER BY n.CreatedAt DESC";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(rs.getInt("NotificationID"));
                notification.setUserID(rs.getInt("UserID"));
                notification.setSenderID(rs.getInt("SenderID"));
                notification.setSenderName(rs.getString("SenderName"));
                notification.setTitle(rs.getString("Title"));
                notification.setMessage(rs.getString("Message"));
                notification.setIsRead(rs.getBoolean("IsRead"));
                notification.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(notification);
            }
        } catch (SQLException e) {
            System.out.println("Error getting unread notifications: " + e.getMessage());
        }
        return list;
    }
    
    public int getUnreadCount(int userID) {
        String sql = "SELECT COUNT(*) FROM Notifications WHERE UserID = ? AND IsRead = 0";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting unread count: " + e.getMessage());
        }
        return 0;
    }
    
    public boolean markAsRead(int notificationID) {
        String sql = "UPDATE Notifications SET IsRead = 1 WHERE NotificationID = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, notificationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error marking notification as read: " + e.getMessage());
        }
        return false;
    }
    
    public boolean markAllAsRead(int userID) {
        String sql = "UPDATE Notifications SET IsRead = 1 WHERE UserID = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error marking all notifications as read: " + e.getMessage());
        }
        return false;
    }
    
    public boolean deleteNotification(int notificationID) {
        String sql = "DELETE FROM Notifications WHERE NotificationID = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, notificationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting notification: " + e.getMessage());
        }
        return false;
    }
    
    public boolean addNotification(Notification notification) {
        String sql = "INSERT INTO Notifications (UserID, SenderID, Title, Message, IsRead, CreatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, notification.getUserID());
            ps.setInt(2, notification.getSenderID());
            ps.setString(3, notification.getTitle());
            ps.setString(4, notification.getMessage());
            ps.setBoolean(5, notification.isIsRead());
            ps.setTimestamp(6, notification.getCreatedAt());
            boolean result = ps.executeUpdate() > 0;
            if (!result) {
                System.err.println("[addNotification] Insert failed: " + notification);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("[addNotification] SQLException: " + e.getMessage());
            System.err.println("[addNotification] Notification: " + notification);
        } catch (Exception ex) {
            System.err.println("[addNotification] Exception: " + ex.getMessage());
            System.err.println("[addNotification] Notification: " + notification);
        }
        return false;
    }

    public Notification getNotificationById(int notificationID) {
        String sql = "SELECT n.NotificationID, n.UserID, n.SenderID, u.FullName as SenderName, n.Title, n.Message, n.IsRead, n.CreatedAt " +
                     "FROM Notifications n LEFT JOIN Users u ON n.SenderID = u.UserID WHERE n.NotificationID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, notificationID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(rs.getInt("NotificationID"));
                notification.setUserID(rs.getInt("UserID"));
                notification.setSenderID(rs.getInt("SenderID"));
                notification.setSenderName(rs.getString("SenderName"));
                notification.setTitle(rs.getString("Title"));
                notification.setMessage(rs.getString("Message"));
                notification.setIsRead(rs.getBoolean("IsRead"));
                notification.setCreatedAt(rs.getTimestamp("CreatedAt"));
                return notification;
            }
        } catch (SQLException e) {
            System.out.println("Error getting notification by id: " + e.getMessage());
        }
        return null;
    }

    public void updateReadStatus(int notificationId, boolean isRead) {
        String sql = "UPDATE Notifications SET IsRead = ? WHERE NotificationID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, isRead);
            ps.setInt(2, notificationId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean getReadStatus(int notificationID) {
        String sql = "SELECT IsRead FROM Notifications WHERE NotificationID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, notificationID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("IsRead");
            }
        } catch (SQLException e) {
            System.out.println("Error getting read status: " + e.getMessage());
        }
        return false;
    }
}