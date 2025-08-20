package dalAdmin;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Brands;

public class BrandAdminDAO extends DBAdminContext {

    // Lấy toàn bộ danh sách Brand
    public List<Brands> getAllBrands() {
        List<Brands> list = new ArrayList<>();
        String sql = "SELECT * FROM Brands";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Brands b = new Brands(
                        rs.getInt("BrandID"),
                        rs.getString("BrandName"),
                        rs.getInt("Quantity"),
                        rs.getInt("Status")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            System.err.println("getAllBrands Error: " + e.getMessage());
        }
        return list;
    }

    public Brands getBrandByID(int id) {
        String sql = "SELECT * FROM Brands WHERE BrandID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Brands(
                        rs.getInt("BrandID"),
                        rs.getString("BrandName"),
                        rs.getInt("Quantity"),
                        rs.getInt("Status")
                );
            }
        } catch (SQLException e) {
            System.err.println("getBrandByID Error: " + e.getMessage());
        }
        return null;
    }

    // Thêm mới Brand
    public boolean insertBrand(Brands brand) {
        String sql = "INSERT INTO Brands (BrandName, Quantity, Status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, brand.getBrandName());
            ps.setInt(2, brand.getQuantity());
            ps.setInt(3, brand.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("insertBrand Error: " + e.getMessage());
        }
        return false;
    }

    public void updateBrandQuantitiesFromBrandComs() {
        String sql = """
        UPDATE Brands b
        LEFT JOIN (
            SELECT BrandID, SUM(Quantity) AS TotalQuantity
            FROM BrandComs
            GROUP BY BrandID
        ) AS bc_sum ON b.BrandID = bc_sum.BrandID
        SET b.Quantity = IFNULL(bc_sum.TotalQuantity, 0);
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isBrandNameExists(String brandName) {
        String sql = """
                     SELECT COUNT(*) FROM Brands
                     WHERE LOWER(TRIM(BrandName)) = LOWER(TRIM(?));""";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, brandName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void toggleStatus(int brandID) {
        String sql = "UPDATE Brands SET Status = CASE WHEN Status = 1 THEN 0 ELSE 1 END WHERE BrandID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, brandID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BrandAdminDAO dao = new BrandAdminDAO();
        // Truy vấn tất cả
        List<Brands> all = dao.getAllBrands();
        for (Brands c : all) {
            System.out.println(c.getBrandID() + " - " + c.getBrandName() + " - " + c.getStatus());
        }
    }
}
