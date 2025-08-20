package dal;

import java.sql.*;
import java.util.*;
import models.*;


public class BuildPCOrderDAO extends DBContext {

public ArrayList<BuildPCAdmin> getBuildPCOrdersByCustomerID(int customerID) {
    ArrayList<BuildPCAdmin> orders = new ArrayList<>();
    String sql = """
        SELECT o.OrderID, o.OrderCode, o.OrderDate, o.Status AS OrderStatus, o.TotalAmount, o.Note,
               o.PaymentStatusID, os.StatusName,
               u.UserID, u.FullName, u.Email, u.PhoneNumber AS UserPhone, u.RoleID, u.Status AS UserStatus, u.CreatedAt,
               ci.Address,
               obpi.OrderBuildPCItemID, obpi.BuildPCID,
               bpc.Status AS BuildPCStatus, bpc.Price AS BuildPCPrice, bpc.UserID AS BuildPCUserID,
               obpd.OrderBuildPCDetailID, obpd.CategoryID, obpd.Price,
               c.CategoryName, c.Queue, c.Inventory, c.ImageURL,
               bc.BrandComID, bc.BrandID, b.BrandName,
               cp.ComponentID, cp.ComponentName,
               COUNT(obpp.OrderBuildPCProductID) AS Quantity,
               obpp.OrderBuildPCProductID, obpp.ProductID, p.ProductCode, p.Note AS ProductNote, p.Status AS ProductStatus,
               obpp.StartDate, obpp.EndDate,
               obpp.WarrantyDetailID, wd.Price AS WarrantyPrice,
               wd.WarrantyID, w.WarrantyPeriod, w.Description AS WarrantyDesc
        FROM Orders o
        JOIN Order_BuildPCItems obpi ON obpi.OrderID = o.OrderID
        JOIN Order_BuildPCDetails obpd ON obpd.OrderBuildPCItemID = obpi.OrderBuildPCItemID
        LEFT JOIN Order_BuildPC_Products obpp ON obpp.OrderBuildPCDetailID = obpd.OrderBuildPCDetailID
        LEFT JOIN Products p ON obpp.ProductID = p.ProductID
        LEFT JOIN WarrantyDetails wd ON obpp.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN Categories c ON obpd.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components cp ON bc.ComponentID = cp.ComponentID
        JOIN Build_PC bpc ON obpi.BuildPCID = bpc.BuildPCID
        LEFT JOIN OrderStatus os ON o.Status = os.StatusID
        JOIN Users u ON o.CustomerID = u.UserID
        LEFT JOIN CustomerInfo ci ON ci.UserID = u.UserID
        WHERE o.CustomerID = ? AND o.Product_Type = 1
        GROUP BY 
            o.OrderID, o.OrderCode, o.OrderDate, o.Status, o.TotalAmount, o.Note, o.PaymentStatusID, os.StatusName,
            u.UserID, u.FullName, u.Email, u.PhoneNumber, u.RoleID, u.Status, u.CreatedAt, ci.Address,
            obpi.OrderBuildPCItemID, obpi.BuildPCID,
            bpc.Status, bpc.Price, bpc.UserID,
            obpd.OrderBuildPCDetailID, obpd.CategoryID, obpd.Price,
            c.CategoryName, c.Queue, c.Inventory, c.ImageURL,
            bc.BrandComID, bc.BrandID, b.BrandName,
            cp.ComponentID, cp.ComponentName,
            obpp.OrderBuildPCProductID, obpp.ProductID, p.ProductCode, p.Note, p.Status,
            obpp.StartDate, obpp.EndDate,
            obpp.WarrantyDetailID, wd.Price,
            wd.WarrantyID, w.WarrantyPeriod, w.Description
        ORDER BY o.OrderID DESC
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            BuildPCAdmin order = new BuildPCAdmin();

            // Thông tin đơn hàng
            order.setOrderId(rs.getInt("OrderID"));
            order.setOrderCode(rs.getString("OrderCode"));
            order.setOrderDate(rs.getTimestamp("OrderDate"));
            order.setOrderStatus(rs.getInt("OrderStatus"));
            order.setTotalAmount(rs.getInt("TotalAmount"));
            order.setNote(rs.getString("Note"));
            order.setPaymentStatusId(rs.getInt("PaymentStatusID"));

            // Người dùng
            order.setUserId(rs.getInt("UserID"));
            order.setFullName(rs.getString("FullName"));
            order.setEmail(rs.getString("Email"));
            order.setUserPhone(rs.getString("UserPhone"));
            order.setRoleId(rs.getInt("RoleID"));
            order.setUserStatus(rs.getInt("UserStatus"));
            order.setCreatedAt(rs.getTimestamp("CreatedAt"));
            order.setAddress(rs.getString("Address"));

            // Build PC
            order.setOrderBuildPcItemId(rs.getInt("OrderBuildPCItemID"));
            order.setBuildPcId(rs.getInt("BuildPCID"));
            order.setBuildPcStatus(rs.getInt("BuildPCStatus"));
            order.setBuildPcPrice(rs.getInt("BuildPCPrice"));
            order.setBuildPcUserId(rs.getInt("BuildPCUserID"));

            // Chi tiết Build
            order.setOrderBuildPcDetailId(rs.getInt("OrderBuildPCDetailID"));
            order.setCateId(rs.getInt("CategoryID"));
            order.setPrice(rs.getInt("Price"));
            order.setCateName(rs.getString("CategoryName"));
            order.setQueue(rs.getInt("Queue"));
            order.setInventory(rs.getInt("Inventory"));
            order.setImgUrl(rs.getString("ImageURL"));
            order.setQuantity(rs.getInt("Quantity")); // ✅ Số lượng mới

            // Brand & Component
            order.setBrandComId(rs.getInt("BrandComID"));
            order.setBrandId(rs.getInt("BrandID"));
            order.setBrandName(rs.getString("BrandName"));
            order.setComponentId(rs.getInt("ComponentID"));
            order.setComponentName(rs.getString("ComponentName"));

            // Sản phẩm thực tế
            order.setOrderBuildPcProductId(rs.getInt("OrderBuildPCProductID"));
            order.setProductId(rs.getInt("ProductID"));
            order.setProductCode(rs.getString("ProductCode"));
            order.setProductNote(rs.getString("ProductNote"));
            order.setProductStatus(rs.getInt("ProductStatus"));
            order.setWarrantyStartDate(rs.getDate("StartDate"));
            order.setWarrantyEndDate(rs.getDate("EndDate"));

            // Bảo hành
            order.setWarrantyDetailId(rs.getInt("WarrantyDetailID"));
            order.setWarrantyPrice(rs.getInt("WarrantyPrice"));
            order.setWarrantyId(rs.getInt("WarrantyID"));
            order.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
            order.setWarrantyDesc(rs.getString("WarrantyDesc"));

            orders.add(order);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return orders;
}



  public ArrayList<BuildPCAdmin> getBuildPCOrdersByCustomerAndStatus(int customerID, int statusID) {
    ArrayList<BuildPCAdmin> orders = new ArrayList<>();
    String sql = """
        SELECT o.OrderID, o.OrderCode, o.OrderDate, o.Status AS OrderStatus, o.TotalAmount, o.Note,
               o.PaymentStatusID, os.StatusName,
               u.UserID, u.FullName, u.Email, u.PhoneNumber AS UserPhone, u.RoleID, u.Status AS UserStatus, u.CreatedAt,
               ci.Address,
               obpi.OrderBuildPCItemID, obpi.BuildPCID,
               bpc.Status AS BuildPCStatus, bpc.Price AS BuildPCPrice, bpc.UserID AS BuildPCUserID,
               obpd.OrderBuildPCDetailID, obpd.CategoryID, obpd.Price,
               c.CategoryName, c.Queue, c.Inventory, c.ImageURL,
               bc.BrandComID, bc.BrandID, b.BrandName,
               cp.ComponentID, cp.ComponentName,
               obpp.OrderBuildPCProductID, obpp.ProductID, p.ProductCode, p.Note AS ProductNote, p.Status AS ProductStatus,
               obpp.StartDate, obpp.EndDate,
               obpp.WarrantyDetailID, wd.Price AS WarrantyPrice,
               wd.WarrantyID, w.WarrantyPeriod, w.Description AS WarrantyDesc
        FROM Orders o
        JOIN Order_BuildPCItems obpi ON obpi.OrderID = o.OrderID
        JOIN Order_BuildPCDetails obpd ON obpd.OrderBuildPCItemID = obpi.OrderBuildPCItemID
        LEFT JOIN Order_BuildPC_Products obpp ON obpp.OrderBuildPCDetailID = obpd.OrderBuildPCDetailID
        LEFT JOIN Products p ON obpp.ProductID = p.ProductID
        LEFT JOIN WarrantyDetails wd ON obpp.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN Categories c ON obpd.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components cp ON bc.ComponentID = cp.ComponentID
        JOIN Build_PC bpc ON obpi.BuildPCID = bpc.BuildPCID
        LEFT JOIN OrderStatus os ON o.Status = os.StatusID
        JOIN Users u ON o.CustomerID = u.UserID
        LEFT JOIN CustomerInfo ci ON ci.UserID = u.UserID
        WHERE o.CustomerID = ? AND o.Product_Type = 1 AND o.Status = ?
        ORDER BY o.OrderID DESC
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, customerID);
        ps.setInt(2, statusID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            BuildPCAdmin order = new BuildPCAdmin();

            // Mapping giống hệt như ở trên, bạn có thể copy từ DAO #1
            order.setOrderId(rs.getInt("OrderID"));
            order.setOrderCode(rs.getString("OrderCode"));
            order.setOrderDate(rs.getTimestamp("OrderDate"));
            order.setOrderStatus(rs.getInt("OrderStatus"));
            order.setTotalAmount(rs.getInt("TotalAmount"));
            order.setNote(rs.getString("Note"));
            order.setPaymentStatusId(rs.getInt("PaymentStatusID"));
            order.setUserId(rs.getInt("UserID"));
            order.setFullName(rs.getString("FullName"));
            order.setEmail(rs.getString("Email"));
            order.setUserPhone(rs.getString("UserPhone"));
            order.setRoleId(rs.getInt("RoleID"));
            order.setUserStatus(rs.getInt("UserStatus"));
            order.setCreatedAt(rs.getTimestamp("CreatedAt"));
            order.setAddress(rs.getString("Address"));
            order.setOrderBuildPcItemId(rs.getInt("OrderBuildPCItemID"));
            order.setBuildPcId(rs.getInt("BuildPCID"));
            order.setBuildPcStatus(rs.getInt("BuildPCStatus"));
            order.setBuildPcPrice(rs.getInt("BuildPCPrice"));
            order.setBuildPcUserId(rs.getInt("BuildPCUserID"));
            order.setOrderBuildPcDetailId(rs.getInt("OrderBuildPCDetailID"));
            order.setCateId(rs.getInt("CategoryID"));
            order.setPrice(rs.getInt("Price"));
            order.setCateName(rs.getString("CategoryName"));
            order.setQueue(rs.getInt("Queue"));
            order.setInventory(rs.getInt("Inventory"));
            order.setImgUrl(rs.getString("ImageURL"));
            order.setBrandComId(rs.getInt("BrandComID"));
            order.setBrandId(rs.getInt("BrandID"));
            order.setBrandName(rs.getString("BrandName"));
            order.setComponentId(rs.getInt("ComponentID"));
            order.setComponentName(rs.getString("ComponentName"));
            order.setOrderBuildPcProductId(rs.getInt("OrderBuildPCProductID"));
            order.setProductId(rs.getInt("ProductID"));
            order.setProductCode(rs.getString("ProductCode"));
            order.setProductNote(rs.getString("ProductNote"));
            order.setProductStatus(rs.getInt("ProductStatus"));
            order.setWarrantyStartDate(rs.getDate("StartDate"));
            order.setWarrantyEndDate(rs.getDate("EndDate"));
            order.setWarrantyDetailId(rs.getInt("WarrantyDetailID"));
            order.setWarrantyPrice(rs.getInt("WarrantyPrice"));
            order.setWarrantyId(rs.getInt("WarrantyID"));
            order.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
            order.setWarrantyDesc(rs.getString("WarrantyDesc"));

            orders.add(order);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return orders;
}

public ArrayList<BuildPCAdmin> getBuildPCOrderByBuildPCItemID(int orderBuildPCItemID) {
    ArrayList<BuildPCAdmin> orderDetails = new ArrayList<>();

    String sql = """
        SELECT o.OrderID, o.OrderCode, o.OrderDate, o.Status AS OrderStatus, o.TotalAmount, o.Note,
               o.PaymentStatusID, os.StatusName,
               u.UserID, u.FullName, u.Email, u.PhoneNumber AS UserPhone, u.RoleID, u.Status AS UserStatus, u.CreatedAt,
               ci.Address,
               obpi.OrderBuildPCItemID, obpi.BuildPCID,
               bpc.Status AS BuildPCStatus, bpc.Price AS BuildPCPrice, bpc.UserID AS BuildPCUserID,
               obpd.OrderBuildPCDetailID, obpd.CategoryID, obpd.Price,
               c.CategoryName, c.Queue, c.Inventory, c.ImageURL,
               bc.BrandComID, bc.BrandID, b.BrandName,
               cp.ComponentID, cp.ComponentName,
               obpp.OrderBuildPCProductID, obpp.ProductID, p.ProductCode, p.Note AS ProductNote, p.Status AS ProductStatus,
               obpp.StartDate, obpp.EndDate,
               obpp.WarrantyDetailID, wd.Price AS WarrantyPrice,
               wd.WarrantyID, w.WarrantyPeriod, w.Description AS WarrantyDesc
        FROM Orders o
        JOIN Order_BuildPCItems obpi ON obpi.OrderID = o.OrderID
        JOIN Order_BuildPCDetails obpd ON obpd.OrderBuildPCItemID = obpi.OrderBuildPCItemID
        LEFT JOIN Order_BuildPC_Products obpp ON obpp.OrderBuildPCDetailID = obpd.OrderBuildPCDetailID
        LEFT JOIN Products p ON obpp.ProductID = p.ProductID
        LEFT JOIN WarrantyDetails wd ON obpp.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN Categories c ON obpd.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components cp ON bc.ComponentID = cp.ComponentID
        JOIN Build_PC bpc ON obpi.BuildPCID = bpc.BuildPCID
        LEFT JOIN OrderStatus os ON o.Status = os.StatusID
        JOIN Users u ON o.CustomerID = u.UserID
        LEFT JOIN CustomerInfo ci ON ci.UserID = u.UserID
        WHERE obpi.OrderBuildPCItemID = ? AND o.Product_Type = 1
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, orderBuildPCItemID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            BuildPCAdmin order = new BuildPCAdmin();

            // Đơn hàng
            order.setOrderId(rs.getInt("OrderID"));
            order.setOrderCode(rs.getString("OrderCode"));
            order.setOrderDate(rs.getTimestamp("OrderDate"));
            order.setOrderStatus(rs.getInt("OrderStatus"));
            order.setTotalAmount(rs.getInt("TotalAmount"));
            order.setNote(rs.getString("Note"));
            order.setPaymentStatusId(rs.getInt("PaymentStatusID"));

            // Người dùng
            order.setUserId(rs.getInt("UserID"));
            order.setFullName(rs.getString("FullName"));
            order.setEmail(rs.getString("Email"));
            order.setUserPhone(rs.getString("UserPhone"));
            order.setRoleId(rs.getInt("RoleID"));
            order.setUserStatus(rs.getInt("UserStatus"));
            order.setCreatedAt(rs.getTimestamp("CreatedAt"));
            order.setAddress(rs.getString("Address"));

            // Build PC
            order.setOrderBuildPcItemId(rs.getInt("OrderBuildPCItemID"));
            order.setBuildPcId(rs.getInt("BuildPCID"));
            order.setBuildPcStatus(rs.getInt("BuildPCStatus"));
            order.setBuildPcPrice(rs.getInt("BuildPCPrice"));
            order.setBuildPcUserId(rs.getInt("BuildPCUserID"));

            // Chi tiết linh kiện
            order.setOrderBuildPcDetailId(rs.getInt("OrderBuildPCDetailID"));
            order.setCateId(rs.getInt("CategoryID"));
            order.setPrice(rs.getInt("Price"));
            order.setCateName(rs.getString("CategoryName"));
            order.setQueue(rs.getInt("Queue"));
            order.setInventory(rs.getInt("Inventory"));
            order.setImgUrl(rs.getString("ImageURL"));

            // Brand / Component
            order.setBrandComId(rs.getInt("BrandComID"));
            order.setBrandId(rs.getInt("BrandID"));
            order.setBrandName(rs.getString("BrandName"));
            order.setComponentId(rs.getInt("ComponentID"));
            order.setComponentName(rs.getString("ComponentName"));

            // Sản phẩm thực tế
            order.setOrderBuildPcProductId(rs.getInt("OrderBuildPCProductID"));
            order.setProductId(rs.getInt("ProductID"));
            order.setProductCode(rs.getString("ProductCode"));
            order.setProductNote(rs.getString("ProductNote"));
            order.setProductStatus(rs.getInt("ProductStatus"));
            order.setWarrantyStartDate(rs.getDate("StartDate"));
            order.setWarrantyEndDate(rs.getDate("EndDate"));

            // Bảo hành
            order.setWarrantyDetailId(rs.getInt("WarrantyDetailID"));
            order.setWarrantyPrice(rs.getInt("WarrantyPrice"));
            order.setWarrantyId(rs.getInt("WarrantyID"));
            order.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
            order.setWarrantyDesc(rs.getString("WarrantyDesc"));

            orderDetails.add(order);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return orderDetails;
}
public ArrayList<Integer> getOrderBuildPCItemIDsByOrderID(int orderId) {
    ArrayList<Integer> itemIds = new ArrayList<>();
    String sql = "SELECT OrderBuildPCItemID FROM Order_BuildPCItems WHERE OrderID = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            itemIds.add(rs.getInt("OrderBuildPCItemID"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return itemIds;
}



}
