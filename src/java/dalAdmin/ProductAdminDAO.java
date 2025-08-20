package dalAdmin;

import java.sql.*;
import java.util.*;
import models.Products;

public class ProductAdminDAO extends DBAdminContext {

    // Lấy danh sách tất cả sản phẩm
    public List<Products> getAllProducts() {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductCode, p.ImportID, i.ImportCode, c.CategoryID, c.CategoryName, p.Status "
                + "FROM Products p "
                + "JOIN Imports i ON p.ImportID = i.ImportID "
                + "JOIN Categories c ON i.CategoryID = c.CategoryID";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Products p = new Products(
                        rs.getInt("ProductID"),
                        rs.getInt("ImportID"),
                        rs.getString("ImportCode"),
                        rs.getString("ProductCode"),
                        rs.getInt("Status"),
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("getAllProducts Error: " + e.getMessage());
        }

        return list;
    }

    public List<Products> getAllProductsByCategoryID(int categoryID) {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductCode, p.ImportID, i.ImportCode, c.CategoryID, c.CategoryName, p.Status "
                + "FROM Products p "
                + "JOIN Imports i ON p.ImportID = i.ImportID "
                + "JOIN Categories c ON i.CategoryID = c.CategoryID "
                + "WHERE c.CategoryID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products p = new Products(
                            rs.getInt("ProductID"),
                            rs.getInt("ImportID"),
                            rs.getString("ImportCode"),
                            rs.getString("ProductCode"),
                            rs.getInt("Status"),
                            rs.getInt("CategoryID"),
                            rs.getString("CategoryName")
                    );
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("getAllProductsByCategoryID Error: " + e.getMessage());
        }

        return list;
    }

    public List<Products> getAllProductsByImportID(int importID) {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductCode, p.ImportID, i.ImportCode, c.CategoryID, c.CategoryName, p.Status "
                + "FROM Products p "
                + "JOIN Imports i ON p.ImportID = i.ImportID "
                + "JOIN Categories c ON i.CategoryID = c.CategoryID "
                + "WHERE p.ImportID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, importID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products p = new Products(
                            rs.getInt("ProductID"),
                            rs.getInt("ImportID"),
                            rs.getString("ImportCode"),
                            rs.getString("ProductCode"),
                            rs.getInt("Status"),
                            rs.getInt("CategoryID"),
                            rs.getString("CategoryName")
                    );
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("getAllProductsByImportID Error: " + e.getMessage());
        }

        return list;
    }

    // Lấy sản phẩm theo ID
    public Products getProductByID(int id) {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Products(
                        rs.getInt("ProductID"),
                        rs.getInt("ImportID"),
                        rs.getString("ProductCode"),
                        rs.getInt("Status")
                );
            }
        } catch (SQLException e) {
            System.err.println("getProductByID Error: " + e.getMessage());
        }
        return null;
    }

    // Thêm sản phẩm mới
    public boolean insertProduct(Products p) {
        String sql = "INSERT INTO Products (ImportID, CategoryID, ProductCode, Status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, p.getImportID());
            ps.setInt(2, p.getCategoryID());
            ps.setString(3, p.getProductCode());
            ps.setInt(4, p.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("insertProduct Error: " + e.getMessage());
        }
        return false;
    }

    // Cập nhật sản phẩm
    public boolean updateProduct(Products p) {
        String sql = "UPDATE Products SET ImportID = ?, CategoryID = ?, ProductCode = ?, Status = ? WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, p.getImportID());
            ps.setInt(2, p.getCategoryID());
            ps.setString(3, p.getProductCode());
            ps.setInt(4, p.getStatus());
            ps.setInt(5, p.getProductID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updateProduct Error: " + e.getMessage());
        }
        return false;
    }

    public void insertProductsFromExcel(List<String> productCodes, int categoryID, int importID) {
        String sql = "INSERT INTO Products (ProductCode, CategoryID, ImportID, Status) VALUES (?, ?, ?, 1)";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String code : productCodes) {
                ps.setString(1, code);
                ps.setInt(2, categoryID);
                ps.setInt(3, importID);
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("insertProductsFromExcel Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isProductCodeExists(String code) {
        String sql = "SELECT 1 FROM Products WHERE ProductCode = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void toggleStatus(int productID) {
        String selectSQL = "SELECT Status FROM Products WHERE ProductID = ?";
        String updateSQL = "UPDATE Products SET Status = ? WHERE ProductID = ?";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement selectPS = conn.prepareStatement(selectSQL)) {

            selectPS.setInt(1, productID);
            ResultSet rs = selectPS.executeQuery();

            if (rs.next()) {
                int currentStatus = rs.getInt("Status");
                int newStatus;

                if (currentStatus == 1) {
                    newStatus = 0; // 1 → 0
                } else if (currentStatus == 0) {
                    newStatus = 4; // 0 → 2
                } else {
                    newStatus = 0; // 2 → 0
                }

                try (PreparedStatement updatePS = conn.prepareStatement(updateSQL)) {
                    updatePS.setInt(1, newStatus);
                    updatePS.setInt(2, productID);
                    updatePS.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertProductsFromExcel(List<String> productCodes, int importID) {
        String sql = "INSERT INTO Products (ProductCode, ImportID, Status) VALUES (?, ?, 1)";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String code : productCodes) {
                ps.setString(1, code);
                ps.setInt(2, importID);
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            System.err.println("insertProductsFromExcel Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
   
    public static void main(String[] args) {
        // Tạo kết nối database (giả sử bạn có một lớp DBContext để lấy connection)
        try {
            ProductAdminDAO dao = new ProductAdminDAO();
            int id = 1;
            List<Products> productList = dao.getAllProductsByImportID(id);

            for (Products p : productList) {
                System.out.println("ProductID: " + p.getProductID());
                System.out.println("ImportID: " + p.getImportID());
                System.out.println("CategoryID: " + p.getCategoryID());
                System.out.println("CategoryName: " + p.getCategoryName());
                System.out.println("ProductCode: " + p.getProductCode());
                System.out.println("Status: " + p.getStatus());
                System.out.println("------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
