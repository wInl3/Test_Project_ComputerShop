package dalAdmin;

import dalAdmin.DBAdminContext;
import models.Warranties;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class WarrantyAdminDAO extends DBAdminContext{

    public List<Warranties> getAllWarranties() {
        List<Warranties> list = new ArrayList<>();
        String sql = "SELECT * FROM Warranties";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Warranties(
                        rs.getInt("WarrantyID"),
                        rs.getInt("WarrantyPeriod"),
                        rs.getString("Description"),
                        rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertWarranty(Warranties warranty) {
        String sql = "INSERT INTO Warranties (WarrantyPeriod,Description, Status) VALUES (?,?, ?)";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, warranty.getWarrantyPeriod());
            ps.setString(2, warranty.getDescription());
            ps.setInt(3, warranty.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateWarranty(Warranties warranty) {
        String sql = "UPDATE Warranties SET WarrantyPeriod = ?, Description= ?, Status = ? WHERE WarrantyID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, warranty.getWarrantyPeriod());
            ps.setString(2, warranty.getDescription());
            ps.setInt(3, warranty.getStatus());
            ps.setInt(4, warranty.getWarrantyID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Warranties getWarrantyByID(int id) {
        String sql = "SELECT * FROM Warranties WHERE WarrantyID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Warranties(
                        rs.getInt("WarrantyID"),
                        rs.getInt("WarrantyPeriod"),
                        rs.getString("Description"),
                        rs.getInt("Status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void toggleStatus(int id) {
        String sql = "UPDATE Warranties SET Status = CASE WHEN Status = 0 THEN 1 ELSE 0 END WHERE WarrantyID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
