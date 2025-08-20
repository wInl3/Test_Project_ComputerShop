package dalAdmin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.BrandComs;
import models.Brands;

public class BrandComAdminDAO extends DBAdminContext {

    // Lấy tất cả BrandCom
    public List<BrandComs> getAllBrandComs() {
        List<BrandComs> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "    bc.BrandComID,\n"
                + "    b.BrandID,\n"
                + "    b.BrandName,\n"
                + "    c.ComponentID,\n"
                + "    c.ComponentName,\n"
                + "    bc.Quantity AS BrandComQuantity\n"
                + "FROM BrandComs bc\n"
                + "JOIN Brands b ON bc.BrandID = b.BrandID\n"
                + "JOIN Components c ON bc.ComponentID = c.ComponentID;";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BrandComs b = new BrandComs();
                b.setBrandComID(rs.getInt("BrandComID"));
                b.setBrandID(rs.getInt("BrandID"));
                b.setBrandName(rs.getString("BrandName"));
                b.setComponentID(rs.getInt("ComponentID"));
                b.setComponentName(rs.getString("ComponentName"));
                b.setQuantity(rs.getInt("BrandComQuantity"));  // hoặc setBrandComQuantity nếu bạn đặt tên khác
                list.add(b);
            }
        } catch (SQLException e) {
            System.err.println("getAllBrandComs Error: " + e.getMessage());
        }

        return list;
    }

    public List<BrandComs> getBrandComsByBrandID(int brandID) {
        List<BrandComs> list = new ArrayList<>();
        String sql = "SELECT bc.BrandComID, b.BrandID, b.BrandName, c.ComponentID, c.ComponentName, bc.Quantity AS BrandComQuantity "
                + "FROM BrandComs bc "
                + "JOIN Brands b ON bc.BrandID = b.BrandID "
                + "JOIN Components c ON bc.ComponentID = c.ComponentID "
                + "WHERE bc.BrandID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, brandID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BrandComs b = new BrandComs();
                b.setBrandComID(rs.getInt("BrandComID"));
                b.setBrandID(rs.getInt("BrandID"));
                b.setBrandName(rs.getString("BrandName"));
                b.setComponentID(rs.getInt("ComponentID"));
                b.setComponentName(rs.getString("ComponentName"));
                b.setQuantity(rs.getInt("BrandComQuantity"));
                list.add(b);
            }

        } catch (SQLException e) {
            System.err.println("getBrandComsByBrandID Error: " + e.getMessage());
        }

        return list;
    }

    public List<BrandComs> getBrandComsByComponentID(int componentID) {
        List<BrandComs> list = new ArrayList<>();
        String sql = "SELECT bc.BrandComID, b.BrandID, b.BrandName, c.ComponentID, c.ComponentName, bc.Quantity AS BrandComQuantity "
                + "FROM BrandComs bc "
                + "JOIN Brands b ON bc.BrandID = b.BrandID "
                + "JOIN Components c ON bc.ComponentID = c.ComponentID "
                + "WHERE bc.ComponentID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, componentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BrandComs b = new BrandComs();
                b.setBrandComID(rs.getInt("BrandComID"));
                b.setBrandID(rs.getInt("BrandID"));
                b.setBrandName(rs.getString("BrandName"));
                b.setComponentID(rs.getInt("ComponentID"));
                b.setComponentName(rs.getString("ComponentName"));
                b.setQuantity(rs.getInt("BrandComQuantity"));
                list.add(b);
            }

        } catch (SQLException e) {
            System.err.println("getBrandComsByComponentID Error: " + e.getMessage());
        }

        return list;
    }

    // Lấy BrandCom theo ID
    public BrandComs getBrandComByID(int id) {
        String sql = "SELECT * FROM BrandComs WHERE BrandComID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BrandComs(
                        rs.getInt("BrandComID"),
                        rs.getInt("BrandID"),
                        rs.getInt("ComponentID"),
                        rs.getInt("Quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("getBrandComByID Error: " + e.getMessage());
        }
        return null;
    }

    // Thêm mới
    public boolean insertBrandCom(BrandComs b) {
        String sql = "INSERT INTO BrandComs (BrandID, ComponentID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, b.getBrandID());
            ps.setInt(2, b.getComponentID());
            ps.setInt(3, b.getQuantity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("insertBrandCom Error: " + e.getMessage());
        }
        return false;
    }

    public void updateBrandComQuantitiesFromCategories() {
        String sql = """
        UPDATE BrandComs
        LEFT JOIN (
            SELECT BrandComID, SUM(inventory) AS TotalInventory
            FROM Categories
            GROUP BY BrandComID
        ) AS InventorySum ON BrandComs.BrandComID = InventorySum.BrandComID
        SET BrandComs.Quantity = IFNULL(InventorySum.TotalInventory, 0);
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean existsBrandComponentPair(int brandID, int componentID) {
        String sql = "SELECT COUNT(*) FROM BrandComs WHERE BrandID = ? AND ComponentID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, brandID);
            ps.setInt(2, componentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getBrandComID(int brandID, int componentID) {
        int brandComID = -1;
        String sql = "SELECT BrandComID FROM BrandComs WHERE BrandID = ? AND ComponentID = ?";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, brandID);
            ps.setInt(2, componentID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brandComID = rs.getInt("BrandComID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return brandComID;
    }

    public static void main(String[] args) {
        BrandComAdminDAO dao = new BrandComAdminDAO();
        int id = 1;
        List<BrandComs> all = dao.getAllBrandComs();

        for (BrandComs c : all) {
            System.out.println(c.getBrandName() + " - " + c.getBrandComID() + " - " + c.getComponentName());
        }
    }

}
