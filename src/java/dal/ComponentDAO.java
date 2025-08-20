/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

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
public class ComponentDAO extends DBContext {

    public List<Components> getAllComponent(String sql) {
        List<Components> componentList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Components c = new Components();
                c.setComponentID(rs.getInt("ComponentID"));
                c.setComponentName(rs.getString("ComponentName"));
                c.setQuantity(rs.getInt("Quantity"));
                c.setStatus(rs.getInt("Status"));
                componentList.add(c);
            }

        } catch (SQLException e) {
            Logger.getLogger(ComponentDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return componentList;
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

    public void updateComponent(Components c) {
        String sql = "UPDATE Components SET ComponentName = ?, Quantity = ?, Status = ? WHERE ComponentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getComponentName());
            ps.setInt(2, c.getQuantity());
            ps.setInt(3, c.getStatus());
            ps.setInt(4, c.getComponentID());

            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ComponentDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertComponent(Components c) {
        String sql = "INSERT INTO Components (ComponentName, Quantity, Status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getComponentName());
            ps.setInt(2, c.getQuantity());
            ps.setInt(3, c.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ComponentDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(ComponentDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(ComponentDAO.class.getName()).log(Level.SEVERE, null, e);
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

    public static void main(String[] args) {
        ComponentDAO dao = new ComponentDAO();
        String keyword = "pC";
        // Truy vấn tất cả
        List<Components> all = dao.getAllComponent("SELECT * FROM Components WHERE ComponentName LIKE '" + keyword + "'");

        for (Components c : all) {
            System.out.println(c.getComponentID() + " - " + c.getComponentName() + " - " + c.getStatus());
        }
    }

}
