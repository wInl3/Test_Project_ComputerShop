package dalAdmin;

import java.sql.*;
import java.util.*;
import models.Imports;

public class ImportDAO extends DBAdminContext {

    // Lấy tất cả bản ghi nhập hàng
    public List<Imports> getAllImports() {
        List<Imports> list = new ArrayList<>();
        String sql = "SELECT "
                + "    i.ImportID, i.ImportCode, i.CategoryID, c.CategoryName, "
                + "    i.CreatedAt, i.Quantity, i.Price "
                + "FROM Imports i "
                + "JOIN Categories c ON i.CategoryID = c.CategoryID";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Imports imp = new Imports(
                        rs.getInt("ImportID"),
                        rs.getString("ImportCode"),
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getDate("CreatedAt"),
                        rs.getInt("Quantity"),
                        rs.getInt("Price")
                );
                list.add(imp);
            }
        } catch (SQLException e) {
            System.err.println("getAllImports Error: " + e.getMessage());
        }

        return list;
    }

    // Kiểm tra ImportCode đã tồn tại chưa
    public boolean isImportCodeExists(String code) {
        String sql = "SELECT 1 FROM Imports WHERE ImportCode = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("isImportCodeExists Error: " + e.getMessage());
        }
        return false;
    }

    // Insert và trả về ImportID
    public int insertImportAndGetID(String code, int categoryID, int price) {
        int importID = -1;
        String sql = "INSERT INTO Imports (ImportCode, CategoryID, Price, CreatedAt) VALUES (?, ?, ?, ?)";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, code);
            ps.setInt(2, categoryID);
            ps.setInt(3, price);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                importID = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("insertImport Error: " + e.getMessage());
        }

        return importID;
    }

    // Cập nhật Quantity của mỗi Import = tổng số lượng sản phẩm có ImportID tương ứng
    public void updateImportQuantitiesFromProducts() {
        String sql = "UPDATE Imports SET Quantity = ("
                + "SELECT COUNT(*) FROM Products p WHERE p.ImportID = Imports.ImportID)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("updateImportQuantitiesFromProducts Error: " + e.getMessage());
        }
    }

    // Lấy danh sách Imports theo CategoryID (có join với CategoryName)
    public List<Imports> getImportsWithProductsByCategoryID(int categoryID) {
        List<Imports> list = new ArrayList<>();
        String sql = "SELECT i.ImportID, i.ImportCode, i.CategoryID, c.CategoryName, i.CreatedAt, i.Quantity, i.Price "
                + "FROM Imports i JOIN Categories c ON i.CategoryID = c.CategoryID "
                + "WHERE i.CategoryID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Imports imp = new Imports(
                        rs.getInt("ImportID"),
                        rs.getString("ImportCode"),
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getDate("CreatedAt"),
                        rs.getInt("Quantity"),
                        rs.getInt("Price")
                );
                list.add(imp);
            }
        } catch (SQLException e) {
            System.err.println("getImportsWithProductsByCategoryID Error: " + e.getMessage());
        }

        return list;
    }

    // Lấy Import từ ProductCode
    public Imports getImportByProductCode(String productCode) {
        String sql = "SELECT i.ImportID, i.ImportCode, i.CategoryID, c.CategoryName, i.CreatedAt, i.Quantity, i.Price "
                + "FROM Products p JOIN Imports i ON p.ImportID = i.ImportID "
                + "JOIN Categories c ON i.CategoryID = c.CategoryID "
                + "WHERE p.ProductCode = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Imports(
                        rs.getInt("ImportID"),
                        rs.getString("ImportCode"),
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getDate("CreatedAt"),
                        rs.getInt("Quantity"),
                        rs.getInt("Price")
                );
            }
        } catch (SQLException e) {
            System.err.println("getImportByProductCode Error: " + e.getMessage());
        }
        return null;
    }

    public void deleteImportByID(int importID) {
        String sql = "DELETE FROM Imports WHERE ImportID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, importID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("deleteImportByID Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
