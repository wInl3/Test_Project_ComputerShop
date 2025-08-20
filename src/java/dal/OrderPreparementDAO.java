package dal;

import dalAdmin.DBAdminContext;
import java.sql.*;

public class OrderPreparementDAO extends DBAdminContext {

    public int countOrderPreparementByID(int UserID) {
        int count = 0;
        String sql = """
                       SELECT COUNT(OrderID) as numberOfOrders
                       FROM OrderPreparements
                       Where UserID = ?
                     """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, UserID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("numberOfOrders");
            }
        } catch (SQLException e) {
            System.err.println("getAllBrands Error: " + e.getMessage());
        }
        return count;
    }

    public int countOrder1MonthByID(int UserID) {
        int count = 0;
        String sql = """
                       SELECT COUNT(OrderID) as numberOfOrders
                         FROM OrderPreparements
                         WHERE UserID = ?
                         AND PrepareTime >= DATE_FORMAT(NOW(), '%Y-%m-01');
                     """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, UserID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("numberOfOrders");
            }
        } catch (SQLException e) {
            System.err.println("getAllBrands Error: " + e.getMessage());
        }
        return count;
    }
}
