package dal;

import dalAdmin.OrderCateAdminDAO;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import models.BuildPCAdmin;
import models.OrderCate;
import models.OrderInfo;
import models.Products;

public class ProductDAO extends DBContext {

    public List<Products> getProductsByUserID(int userID) {
        List<Products> list = new ArrayList<>();
        String sql = """
        SELECT 
            p.ProductID,
            p.ProductCode,
            w.WarrantyPeriod,
            w.Description AS WarrantyDescription
        FROM Orders o
        JOIN OrderItems oi ON o.OrderID = oi.OrderID
        JOIN OrderDetails od ON oi.OrderItemID = od.OrderItemID
        JOIN Products p ON od.ProductID = p.ProductID
        LEFT JOIN WarrantyDetails wd ON od.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        WHERE o.CustomerID = ?
        ORDER BY o.OrderDate DESC
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products p = new Products();
                    p.setProductID(rs.getInt("ProductID"));
                    p.setProductCode(rs.getString("ProductCode"));
                    p.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    p.setWarrantyDescription(rs.getString("WarrantyDescription"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public OrderCate getOrderByProductCode(String productCode) {
        OrderCate order = null;

        String sql = """
        SELECT 
            o.OrderID,
            o.OrderCode,
            o.Product_Type,
            o.FullName,
            o.PhoneNumber,
            o.Note,
            o.OrderDate,
            o.Address AS OrderAddress,
            o.TotalAmount,
            o.Status,
            o.PaymentStatusID,

            customer.UserID AS CustomerUserID,
            customer.FullName AS CustomerName
        FROM Products p
        JOIN OrderDetails od ON p.ProductID = od.ProductID
        JOIN OrderItems oi ON od.OrderItemID = oi.OrderItemID
        JOIN Orders o ON oi.OrderID = o.OrderID
        JOIN Users customer ON o.CustomerID = customer.UserID
        WHERE p.ProductCode = ?
        LIMIT 1
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productCode);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new OrderCate();

                    // Order basic
                    order.setOrderID(rs.getInt("OrderID"));
                    order.setOrderCode(rs.getString("OrderCode"));
                    order.setProduct_Type((Integer) rs.getObject("Product_Type"));
                    order.setFullName(rs.getNString("FullName"));
                    order.setPhoneNumber(rs.getNString("PhoneNumber"));
                    order.setNote(rs.getNString("Note"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setAddress(rs.getString("OrderAddress"));
                    order.setTotalAmount(rs.getInt("TotalAmount"));
                    order.setStatus(rs.getInt("Status"));
                    order.setPaymentStatusID(rs.getInt("PaymentStatusID"));

                    // Customer
                    order.setCustomerID(rs.getInt("CustomerUserID"));
                    order.setCustomerName(rs.getString("CustomerName"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    public OrderInfo getOrderInfoByProductCode(String productCode) {
        String sql = """
        SELECT o.OrderCode, o.CustomerID, o.Fullname, c.CategoryName, 
               p.ProductCode, w.WarrantyPeriod, w.Description,
               od.`Start`, od.`End`
        FROM OrderDetails od
        JOIN Products p ON od.ProductID = p.ProductID
        JOIN OrderItems oi ON od.OrderItemID = oi.OrderItemID
        JOIN Orders o ON oi.OrderID = o.OrderID
        JOIN Categories c ON oi.CategoryID = c.CategoryID
        JOIN WarrantyDetails wd ON od.WarrantyDetailID = wd.WarrantyDetailID
        JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        WHERE p.ProductCode = ?
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, productCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderInfo info = new OrderInfo();
                    info.setOrderCode(rs.getString("OrderCode"));
                    info.setCustomerID(rs.getInt("CustomerID")); // ✅ Thêm dòng này
                    info.setFullName(rs.getString("Fullname"));
                    info.setCategoryName(rs.getString("CategoryName"));
                    info.setProductCode(rs.getString("ProductCode"));
                    info.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    info.setWarrantyDescription(rs.getString("Description"));
                    info.setStart(rs.getDate("Start"));
                    info.setEnd(rs.getDate("End"));
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateOrderAndProductToWarranty(String orderCode, String productCode) {
        String updateOrderSQL = "UPDATE Orders SET Status = 6 WHERE OrderCode = ?";
        String updateProductSQL = "UPDATE Products SET Status = 3 WHERE ProductCode = ?";

        try (Connection conn = new DBContext().connection; PreparedStatement ps1 = conn.prepareStatement(updateOrderSQL); PreparedStatement ps2 = conn.prepareStatement(updateProductSQL)) {

            // Cập nhật đơn hàng
            ps1.setString(1, orderCode);
            int rowsOrder = ps1.executeUpdate();

            // Cập nhật sản phẩm
            ps2.setString(1, productCode);
            int rowsProduct = ps2.executeUpdate();

            return rowsOrder > 0 && rowsProduct > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BuildPCAdmin getBuildPCWarrantyInfoByProductCode(String productCode) {
        String sql = """
        SELECT 
            o.OrderCode,
            o.Fullname,
            o.CustomerID,
            c.CategoryName,
            p.ProductCode,
            w.WarrantyPeriod,
            w.Description AS WarrantyDescription,
            obpp.StartDate,
            obpp.EndDate
        FROM Order_BuildPC_Products obpp
        JOIN Order_BuildPCDetails obd ON obpp.OrderBuildPCDetailID = obd.OrderBuildPCDetailID
        JOIN Order_BuildPCItems obpi ON obd.OrderBuildPCItemID = obpi.OrderBuildPCItemID
        JOIN Orders o ON obpi.OrderID = o.OrderID
        JOIN Categories c ON obd.CategoryID = c.CategoryID
        JOIN Products p ON obpp.ProductID = p.ProductID
        LEFT JOIN WarrantyDetails wd ON obpp.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        WHERE p.ProductCode = ?
    """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, productCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BuildPCAdmin b = new BuildPCAdmin();
                    b.setOrderCode(rs.getString("OrderCode"));
                    b.setFullName(rs.getString("Fullname"));
                    b.setUserId(rs.getInt("CustomerID"));
                    b.setCateName(rs.getString("CategoryName"));
                    b.setProductCode(productCode);
                    b.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    b.setWarrantyDesc(rs.getString("WarrantyDescription"));
                    b.setWarrantyStartDate(rs.getDate("StartDate"));
                    b.setWarrantyEndDate(rs.getDate("EndDate"));
                    return b;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
     public boolean activeWarrantyByProductID(int ProductID) {
        int n = 0;
        String sql = """
        UPDATE Products
        SET Status = ?
        WHERE ProductID = ?;
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, 2);
            ps.setInt(2, ProductID);
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static void main(String[] args) {

        int userID = 5;

        ProductDAO dao = new ProductDAO(); // hoặc DAO bạn đang dùng
        List<Products> products = dao.getProductsByUserID(userID);

        if (products.isEmpty()) {
            System.out.println("⚠️ Không có sản phẩm nào được tìm thấy cho userID: " + userID);
        } else {
            System.out.println("✅ Danh sách sản phẩm:");
            for (Products p : products) {
                System.out.println("- Product ID: " + p.getProductID());
                System.out.println("  Code: " + p.getProductCode());
                System.out.println("  Warranty: " + (p.getWarrantyPeriod()));
                System.out.println("  Description: " + (p.getWarrantyDescription() != null ? p.getWarrantyDescription() : "Không có"));
                System.out.println("-----------------------------------");
            }
        }

    }

//    public List<Products> getAllProduct(String sql) {
//        List<Products> listProduct = new ArrayList<>();
//        try (PreparedStatement ptm = connection.prepareStatement(sql); ResultSet rs = ptm.executeQuery()) {
//            while (rs.next()) {
//                Products p = new Products(
//                        rs.getInt("ProductID"),
//                        rs.getString("Name"),
//                        rs.getDate("CreatedAt"),
//                        rs.getInt("CategoryID"),
//                        rs.getString("ProductCode"),
//                        rs.getInt("Status"));
//                listProduct.add(p);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return listProduct;
//    }
//
//    public Products getProductByID(int id) {
//        String sql = "SELECT * FROM Products WHERE ProductID = ?";
//        try (PreparedStatement ptm = connection.prepareStatement(sql)) {
//            ptm.setInt(1, id);
//            ResultSet rs = ptm.executeQuery();
//            if (rs.next()) {
//                return new Products(
//                        rs.getInt("ProductID"),
//                        rs.getString("Name"),
//                        rs.getDate("CreatedAt"),
//                        rs.getInt("CategoryID"),
//                        rs.getString("ProductCode"),
//                        rs.getInt("Status"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//
//    public void insertProduct(Products p) {
//        String sql = "INSERT INTO Products (Name, CreatedAt, CategoryID, ProductCode, Status) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, p.getName());
//            ps.setDate(2, p.getCreatedAt());
//            ps.setInt(3, p.getCategoryID());
//            ps.setString(4, p.getProductCode());
//            ps.setInt(5, p.getStatus());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
//
//    public void updateProduct(Products p) {
//        String sql = "UPDATE Products SET Name = ?, CreatedAt = ?, CategoryID = ?, ProductCode = ?,  Status = ? WHERE ProductID = ?";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, p.getName());
//            ps.setDate(2, p.getCreatedAt());
//            ps.setInt(3, p.getCategoryID());
//            ps.setString(4, p.getProductCode());
//            ps.setInt(5, p.getStatus());
//            ps.setInt(6, p.getProductID());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
    public void deleteProduct(int id) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(ProductDAO.class
                    .getName()).log(Level.SEVERE, null, e);
        }
    }

//    public List<Products> searchByName(String keyword) {
//        List<Products> list = new ArrayList<>();
//        String sql = "SELECT * FROM Products WHERE Name LIKE ?";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, "%" + keyword + "%");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Products p = new Products(
//                        rs.getInt("ProductID"),
//                        rs.getString("Name"),
//                        rs.getDate("CreatedAt"),
//                        rs.getInt("CategoryID"),
//                        rs.getString("ProductCode"),
//                        rs.getInt("Status"));
//                list.add(p);
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return list;
//    }
//
//    public void syncProductNameWithCategoryName() {
//        String sql = "UPDATE Products "
//                + "SET Name = (SELECT CategoryName FROM Categories WHERE Categories.CategoryID = Products.CategoryID)";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
}
