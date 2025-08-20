/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dalAdmin;

import dalAdmin.DBAdminContext;
import models.Warranties;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import models.WarrantyDetails;

public class WarrantyDetailAdminDAO extends DBAdminContext {

    public List<WarrantyDetails> getAllWarrantyDetails() {
        List<WarrantyDetails> list = new ArrayList<>();
        String sql = """
        SELECT wd.WarrantyDetailID, wd.WarrantyID, w.WarrantyPeriod, w.Description,
               wd.BrandComID, b.BrandName, c.ComponentName,
               wd.Price, wd.Status
        FROM WarrantyDetails wd
        JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN BrandComs bc ON wd.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components c ON bc.ComponentID = c.ComponentID
        """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WarrantyDetails wd = new WarrantyDetails();
                wd.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                wd.setWarrantyID(rs.getInt("WarrantyID"));
                wd.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                wd.setDescription(rs.getString("Description"));
                wd.setBrandComID(rs.getInt("BrandComID"));
                wd.setBrandName(rs.getString("BrandName"));
                wd.setComponentName(rs.getString("ComponentName"));
                wd.setPrice(rs.getInt("Price"));
                wd.setStatus(rs.getInt("Status"));
                list.add(wd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public WarrantyDetails getWarrantyDetailByID(int id) {
        String sql = """
        SELECT wd.WarrantyDetailID, wd.WarrantyID, w.WarrantyPeriod, w.Description,
               wd.BrandComID, b.BrandName, c.ComponentName,
               wd.Price, wd.Status
        FROM WarrantyDetails wd
        JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN BrandComs bc ON wd.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components c ON bc.ComponentID = c.ComponentID
        WHERE wd.WarrantyDetailID = ?
        """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                WarrantyDetails wd = new WarrantyDetails();
                wd.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                wd.setWarrantyID(rs.getInt("WarrantyID"));
                wd.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                wd.setDescription(rs.getString("Description"));
                wd.setBrandComID(rs.getInt("BrandComID"));
                wd.setBrandName(rs.getString("BrandName"));
                wd.setComponentName(rs.getString("ComponentName"));
                wd.setPrice(rs.getInt("Price"));
                wd.setStatus(rs.getInt("Status"));
                return wd;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateWarrantyDetail(WarrantyDetails wd) {
        String sql = "UPDATE WarrantyDetails SET WarrantyID = ?, BrandComID = ?, Price = ?, Status = ? WHERE WarrantyDetailID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, wd.getWarrantyID());
            ps.setInt(2, wd.getBrandComID());
            ps.setInt(3, wd.getPrice());
            ps.setInt(4, wd.getStatus());
            ps.setInt(5, wd.getWarrantyDetailID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertWarrantyDetail(WarrantyDetails wd) {
        String sql = "INSERT INTO WarrantyDetails (WarrantyID, BrandComID, Price, Status) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, wd.getWarrantyID());
            ps.setInt(2, wd.getBrandComID());
            ps.setInt(3, wd.getPrice());
            ps.setInt(4, wd.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkExists(int warrantyID, int brandComID) {
        String sql = "SELECT * FROM WarrantyDetails WHERE WarrantyID = ? AND BrandComID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, warrantyID);
            ps.setInt(2, brandComID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePriceAndStatusByID(int warrantyDetailID, int price, int status) {
        String sql = "UPDATE WarrantyDetails SET Price = ?, Status = ? WHERE WarrantyDetailID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, price);
            ps.setInt(2, status);
            ps.setInt(3, warrantyDetailID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WarrantyDetails> getAllWarrantyDetailsByBrandComID(int brandComID) {
        List<WarrantyDetails> list = new ArrayList<>();
        String sql = """
        SELECT wd.WarrantyDetailID, wd.WarrantyID, w.WarrantyPeriod, w.Description,
               wd.BrandComID, b.BrandName, c.ComponentName,
               wd.Price, wd.Status
        FROM WarrantyDetails wd
        JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN BrandComs bc ON wd.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components c ON bc.ComponentID = c.ComponentID
        WHERE wd.BrandComID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, brandComID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WarrantyDetails wd = new WarrantyDetails();
                wd.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                wd.setWarrantyID(rs.getInt("WarrantyID"));
                wd.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                wd.setDescription(rs.getString("Description"));
                wd.setBrandComID(rs.getInt("BrandComID"));
                wd.setBrandName(rs.getString("BrandName"));
                wd.setComponentName(rs.getString("ComponentName"));
                wd.setPrice(rs.getInt("Price"));
                wd.setStatus(rs.getInt("Status"));
                list.add(wd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        WarrantyDetailAdminDAO dao = new WarrantyDetailAdminDAO();

        int testID = 1; // Thay bằng ID bạn muốn kiểm tra
        WarrantyDetails wd = dao.getWarrantyDetailByID(testID);

        if (wd != null) {
            System.out.println("Warranty Detail Found:");
            System.out.println("WarrantyDetailID: " + wd.getWarrantyDetailID());
            System.out.println("WarrantyID: " + wd.getWarrantyID());
            System.out.println("WarrantyPeriod: " + wd.getWarrantyPeriod());
            System.out.println("Description: " + wd.getDescription());
            System.out.println("BrandComID: " + wd.getBrandComID());
            System.out.println("BrandName: " + wd.getBrandName());
            System.out.println("ComponentName: " + wd.getComponentName());
            System.out.println("Price: " + wd.getPrice());
            System.out.println("Status: " + (wd.getStatus() == 1 ? "Active" : "Inactive"));
        } else {
            System.out.println("No warranty detail found for ID = " + testID);
        }
    }

}
