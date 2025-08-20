/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dalAdmin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Components;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class ComponentAdminDAO extends DBAdminContext {

    public List<Components> getAllComponent() {
        List<Components> list = new ArrayList<>();
        String sql = "SELECT * FROM Components";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Components c = new Components(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentName"),
                        rs.getInt("Quantity"),
                        rs.getInt("Status")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.err.println("getAllCompoents Error: " + e.getMessage());
        }
        return list;
    }

    public Components searchComponentByID(int componentID) {
        Components component = null;
        String sql = "SELECT * FROM Components WHERE ComponentID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, componentID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                component = new Components();
                component.setComponentID(rs.getInt("ComponentID"));
                component.setComponentName(rs.getString("ComponentName"));
                component.setQuantity(rs.getInt("Quantity"));
                component.setStatus(rs.getInt("Status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return component;
    }

    public void insertComponent(Components c) {
        String sql = "INSERT INTO Components (ComponentName, Quantity, Status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getComponentName());
            ps.setInt(2, c.getQuantity());
            ps.setInt(3, c.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ComponentAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateComponentQuantityFromCategories(int componentID) {
        String sql = "SELECT SUM(Quantity) AS total FROM Categories WHERE ComponentID = ?";
        String updateSql = "UPDATE Components SET Quantity = ? WHERE ComponentID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, componentID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");

                try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                    updatePs.setInt(1, total);
                    updatePs.setInt(2, componentID);
                    updatePs.executeUpdate();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ComponentAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateAllComponentQuantities() {
        String getAllIDs = "SELECT ComponentID FROM Components";

        try (PreparedStatement ps = connection.prepareStatement(getAllIDs); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ComponentID");
                updateComponentQuantityFromCategories(id);
            }

        } catch (SQLException e) {
            Logger.getLogger(ComponentAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateStatus(int componentID, int newStatus) {
        String sql = "UPDATE Components SET Status = ? WHERE ComponentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, componentID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateComponentQuantitiesFromBrandComs() {
        String sql = """
        UPDATE Components
        SET Quantity = (
            SELECT SUM(bc.Quantity)
            FROM BrandComs bc
            WHERE bc.ComponentID = Components.ComponentID
        )
    """;
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isComponentNameExist(String name) {
        String sql = "SELECT COUNT(*) FROM Components WHERE LOWER(LTRIM(RTRIM(ComponentName))) = LOWER(LTRIM(RTRIM(?)))";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void toggleStatus(int componentID) {
    String sql = "UPDATE Components SET Status = CASE WHEN Status = 1 THEN 0 ELSE 1 END WHERE ComponentID = ?";
    try (Connection conn = new DBAdminContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, componentID);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        ComponentAdminDAO dao = new ComponentAdminDAO();
        String keyword = "pC";
        // Truy vấn tất cả
        List<Components> all = dao.getAllComponent();

        for (Components c : all) {
            System.out.println(c.getComponentID() + " - " + c.getComponentName() + " - " + c.getStatus());
        }
    }

}
