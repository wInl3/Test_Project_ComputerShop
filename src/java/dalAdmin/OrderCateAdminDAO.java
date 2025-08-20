package dalAdmin;

import java.util.ArrayList;
import java.util.List;
import models.OrderCate;
import java.sql.*;
import models.Categories;
import models.OrderItems;
import models.Products;

public class OrderCateAdminDAO extends DBAdminContext {

    public List<OrderCate> getOrdersByStatus(int status) {
        List<OrderCate> list = new ArrayList<>();

        String sql = """
        SELECT 
            o.OrderID,
            o.OrderCode,
            o.Product_Type,
            o.FullName AS Consignee,
            o.PhoneNumber,
            o.Note,                  
            o.OrderDate,
            o.Address AS OrderAddress,
            o.TotalAmount,
            o.Status,
            o.PaymentStatusID,

            customer.UserID AS CustomerUserID,
            customer.FullName AS CustomerName,

            prepareStaff.UserID AS PrepareStaffID,
            prepareStaff.FullName AS PrepareStaffName,
            op.PrepareTime,

            shipper.UserID AS ShipperID,
            shipper.FullName AS ShipperName,
            shipper.PhoneNumber AS ShipperPhone,         
            s.ShipTime,
            s.Note AS ShipNote,

            warrantyStaff.UserID AS WarrantyStaffID,
            warrantyStaff.FullName AS WarrantyStaffName,
            wa.AssignedDate

        FROM Orders o
        JOIN Users customer ON o.CustomerID = customer.UserID

        LEFT JOIN OrderPreparements op ON o.OrderID = op.OrderID
        LEFT JOIN Users prepareStaff ON op.UserID = prepareStaff.UserID

        LEFT JOIN Shipping s ON o.OrderID = s.OrderID
        LEFT JOIN Users shipper ON s.ShipperID = shipper.UserID

        LEFT JOIN WarrantyAssignments wa ON o.OrderID = wa.OrderID
        LEFT JOIN Users warrantyStaff ON wa.UserID = warrantyStaff.UserID

        WHERE o.Product_Type = 0 AND o.Status = ?
        ORDER BY o.OrderDate DESC;
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderCate order = new OrderCate();

                    // Order
                    order.setOrderID(rs.getInt("OrderID"));
                    order.setOrderCode(rs.getString("OrderCode"));
                    order.setProduct_Type((Integer) rs.getObject("Product_Type"));
                    order.setFullName(rs.getNString("Consignee"));
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

                    // Staff chuẩn bị
                    order.setStaffID(rs.getInt("PrepareStaffID"));
                    order.setStaffName(rs.getString("PrepareStaffName"));
                    order.setPrepareTime(rs.getTimestamp("PrepareTime"));

                    // Shipper
                    order.setShipperID(rs.getInt("ShipperID"));
                    order.setShipperName(rs.getString("ShipperName"));
                    order.setShipperPhone(rs.getString("ShipperPhone"));
                    order.setShipTime(rs.getDate("ShipTime"));
                    order.setShipNote(rs.getNString("ShipNote"));
                    // Warranty staff
                    order.setWarrantyStaffID(rs.getInt("WarrantyStaffID"));
                    order.setWarrantyStaffName(rs.getString("WarrantyStaffName"));
                    order.setWarrantyAssignedDate(rs.getTimestamp("AssignedDate"));

                    list.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<OrderItems> getAllOrderCateItemsByOrderID(int orderID) {
        List<OrderItems> list = new ArrayList<>();

        String sql = """
        SELECT 
            oi.OrderItemID,
            oi.OrderID,
            oi.CategoryID,
            oi.Quantity,
            oi.Price,
            o.OrderCode,
            c.CategoryName,
            c.Inventory,
            c.Queue
        FROM OrderItems oi
        JOIN Orders o ON oi.OrderID = o.OrderID
        JOIN Categories c ON oi.CategoryID = c.CategoryID
        WHERE oi.OrderID = ?;
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItems item = new OrderItems();

                    int orderItemID = rs.getInt("OrderItemID");
                    int quantity = rs.getInt("Quantity");

                    item.setOrderItemID(orderItemID);
                    item.setOrderID(rs.getInt("OrderID"));
                    item.setCategoryID(rs.getInt("CategoryID"));
                    item.setQuantity(quantity);
                    item.setPrice(rs.getInt("Price"));

                    item.setOrderCode(rs.getString("OrderCode"));
                    item.setCategoryName(rs.getString("CategoryName"));
                    item.setInventory(rs.getInt("Inventory"));
                    item.setQueue(rs.getInt("Queue"));

                    int assigned = getAssignedProductCount(orderItemID, conn);
                    item.setCompleted(assigned >= quantity);

                    list.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateOrderStatus(int orderID, int status) {
        String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);
            ps.setInt(2, orderID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOrderPreparement(int userID, int orderID) {
        String sql = "INSERT INTO OrderPreparements (UserID, OrderID, PrepareTime) VALUES (?, ?, CURRENT_DATE)";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, orderID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertShipping(int shipperID, int orderID, java.sql.Date shipTime) {
        String sql = "INSERT INTO Shipping (OrderID, ShipperID, ShipTime) VALUES (?, ?, ?)";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ps.setInt(2, shipperID);

            if (shipTime != null) {
                ps.setDate(3, shipTime);
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void fillProductsForOrderItem(int orderItemID) {
        String getDetailsSQL = """
        SELECT OrderDetailID 
        FROM OrderDetails 
        WHERE OrderItemID = ? AND ProductID IS NULL
    """;

        String getProductsSQL = """
        SELECT p.ProductID 
        FROM Products p
        JOIN Imports i ON p.ImportID = i.ImportID
        WHERE i.CategoryID = (
            SELECT CategoryID FROM OrderItems WHERE OrderItemID = ?
        ) AND p.Status = 1
        LIMIT ?
    """;

        String updateSQL = """
        UPDATE OrderDetails 
        SET ProductID = ? 
        WHERE OrderDetailID = ?
    """;

        String markUsedSQL = """
        UPDATE Products 
        SET Status = 0 
        WHERE ProductID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement psGetDetails = conn.prepareStatement(getDetailsSQL); PreparedStatement psGetProducts = conn.prepareStatement(getProductsSQL); PreparedStatement psUpdate = conn.prepareStatement(updateSQL); PreparedStatement psMarkUsed = conn.prepareStatement(markUsedSQL)) {

            List<Integer> detailIDs = new ArrayList<>();

            psGetDetails.setInt(1, orderItemID);
            try (ResultSet rs = psGetDetails.executeQuery()) {
                while (rs.next()) {
                    detailIDs.add(rs.getInt("OrderDetailID"));
                }
            }

            if (detailIDs.isEmpty()) {
                return;
            }

            psGetProducts.setInt(1, orderItemID);
            psGetProducts.setInt(2, detailIDs.size());

            List<Integer> productIDs = new ArrayList<>();
            try (ResultSet rs = psGetProducts.executeQuery()) {
                while (rs.next()) {
                    productIDs.add(rs.getInt("ProductID"));
                }
            }

            // Gán từng product vào từng dòng order detail
            for (int i = 0; i < Math.min(detailIDs.size(), productIDs.size()); i++) {
                int orderDetailID = detailIDs.get(i);
                int productID = productIDs.get(i);

                psUpdate.setInt(1, productID);
                psUpdate.setInt(2, orderDetailID);
                psUpdate.addBatch();

                psMarkUsed.setInt(1, productID);
                psMarkUsed.addBatch();
            }

            psUpdate.executeBatch();
            psMarkUsed.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOrderItemCompleted(int orderItemID, int expectedQuantity) {
        String sql = "SELECT COUNT(*) AS cnt FROM OrderDetails WHERE OrderItemID = ? AND ProductID IS NOT NULL";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItemID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("cnt");
                    return count >= expectedQuantity;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getAssignedProductCount(int orderItemID, Connection conn) {
        String sql = """
        SELECT COUNT(*) 
        FROM OrderDetails 
        WHERE OrderItemID = ? AND ProductID IS NOT NULL;
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItemID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Products> getProductsByOrderItemID(int orderItemID) {
        List<Products> list = new ArrayList<>();

        String sql = """
        SELECT 
            p.ProductID, 
            p.ProductCode,
            w.WarrantyPeriod,
            w.Description,
            od.Start,
            od.End
        FROM OrderDetails od
        JOIN Products p ON od.ProductID = p.ProductID
        JOIN WarrantyDetails wd ON od.WarrantyDetailID = wd.WarrantyDetailID
        JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        WHERE od.OrderItemID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderItemID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products p = new Products();
                    p.setProductID(rs.getInt("ProductID"));
                    p.setProductCode(rs.getString("ProductCode"));

                    // Optional: only if your Products class supports these fields
                    p.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    p.setWarrantyDescription(rs.getString("Description"));
                    p.setStart(rs.getDate("Start"));
                    p.setEnd(rs.getDate("End"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateOrderStatusIfComplete(int orderID) {
        String checkSql = """
        SELECT COUNT(*) AS MissingCount
        FROM OrderItems oi
        JOIN OrderDetails od ON oi.OrderItemID = od.OrderItemID
        WHERE oi.OrderID = ? AND od.ProductID IS NULL;
    """;

        String updateSql = "UPDATE Orders SET Status = 3 WHERE OrderID = ?";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, orderID);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next() && rs.getInt("MissingCount") == 0) {
                    try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                        updatePs.setInt(1, orderID);
                        updatePs.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShippingHandledByUser(int userID, int orderID) {
        String sql = "SELECT 1 FROM Shipping WHERE OrderID = ? AND ShipperID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ps.setInt(2, userID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true nếu có bản ghi
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean completeShipping(int userID, int orderID, String note) {
        String sql = "UPDATE Shipping SET ShippingStatus = 1, ShipTime = CURRENT_DATE, Note = ? WHERE OrderID = ? AND ShipperID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, note);
            ps.setInt(2, orderID);
            ps.setInt(3, userID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public OrderCate getOrderByID(int orderID) {
        OrderCate order = null;

        String sql = """
    SELECT 
        o.OrderID,
        o.OrderCode,
        o.Product_Type,
        o.FullName AS Consignee,
        o.PhoneNumber,
        o.Note,                  
        o.OrderDate,
        o.Address AS OrderAddress,
        o.TotalAmount,
        o.Status,
        o.PaymentStatusID,

        customer.UserID AS CustomerUserID,
        customer.FullName AS CustomerName,

        prepareStaff.UserID AS PrepareStaffID,
        prepareStaff.FullName AS PrepareStaffName,
        op.PrepareTime,

        shipper.UserID AS ShipperID,
        shipper.FullName AS ShipperName,
        shipper.PhoneNumber AS ShipperPhone,         
        s.ShipTime,
        s.Note AS ShipNote,

        warrantyStaff.UserID AS WarrantyStaffID,
        warrantyStaff.FullName AS WarrantyStaffName,
        wa.AssignedDate

    FROM Orders o
    JOIN Users customer ON o.CustomerID = customer.UserID

    LEFT JOIN OrderPreparements op ON o.OrderID = op.OrderID
    LEFT JOIN Users prepareStaff ON op.UserID = prepareStaff.UserID

    LEFT JOIN Shipping s ON o.OrderID = s.OrderID
    LEFT JOIN Users shipper ON s.ShipperID = shipper.UserID

    LEFT JOIN WarrantyAssignments wa ON o.OrderID = wa.OrderID
    LEFT JOIN Users warrantyStaff ON wa.UserID = warrantyStaff.UserID

    WHERE o.OrderID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new OrderCate();

                    // Order
                    order.setOrderID(rs.getInt("OrderID"));
                    order.setOrderCode(rs.getString("OrderCode"));
                    order.setProduct_Type((Integer) rs.getObject("Product_Type"));
                    order.setFullName(rs.getNString("Consignee"));
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

                    // Staff chuẩn bị
                    order.setStaffID(rs.getInt("PrepareStaffID"));
                    order.setStaffName(rs.getString("PrepareStaffName"));
                    order.setPrepareTime(rs.getTimestamp("PrepareTime"));

                    // Shipper
                    order.setShipperID(rs.getInt("ShipperID"));
                    order.setShipperName(rs.getString("ShipperName"));
                    order.setShipperPhone(rs.getString("ShipperPhone"));
                    order.setShipTime(rs.getDate("ShipTime"));
                    order.setShipNote(rs.getNString("ShipNote"));

                    // Warranty staff
                    order.setWarrantyStaffID(rs.getInt("WarrantyStaffID"));
                    order.setWarrantyStaffName(rs.getString("WarrantyStaffName"));
                    order.setWarrantyAssignedDate(rs.getTimestamp("AssignedDate"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    public void updateOrderDetailsWarrantyDates() {
        String sql = """
            UPDATE OrderDetails od
            JOIN OrderItems oi ON od.OrderItemID = oi.OrderItemID
            JOIN Orders o ON oi.OrderID = o.OrderID
            JOIN Shipping s ON s.OrderID = o.OrderID
            JOIN WarrantyDetails wd ON wd.WarrantyDetailID = od.WarrantyDetailID
            JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
            SET 
                od.`Start` = s.ShipTime,
                od.`End` = DATE_ADD(s.ShipTime, INTERVAL w.WarrantyPeriod MONTH)
            WHERE s.ShipTime IS NOT NULL
              AND od.`Start` IS NULL
        """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            int rows = ps.executeUpdate();
            System.out.println("✅ Đã cập nhật " + rows + " dòng trong OrderDetails (Start/End).");

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật ngày bảo hành trong OrderDetails:");
            e.printStackTrace();
        }
    }

    public List<Products> getPendingWarrantyProductsByOrderId(int orderId) {
        List<Products> productList = new ArrayList<>();
        String sql = """
        SELECT p.ProductCode, p.Status
        FROM Products p
        JOIN OrderDetails od ON p.ProductID = od.ProductID
        JOIN OrderItems oi ON od.OrderItemID = oi.OrderItemID
        WHERE (p.Status = 3 OR p.Status = 4) AND oi.OrderID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products product = new Products(); // sử dụng constructor rỗng
                    product.setProductCode(rs.getString("ProductCode"));
                    product.setStatus(rs.getInt("Status"));
                    productList.add(product);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    public boolean updateProductStatusByCode(String productCode, int status) {
        String sql = "UPDATE Products SET Status = ? WHERE ProductCode = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setString(2, productCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasApprovedProductInOrder(int orderID) {
        String sql = """
        SELECT 1
        FROM Products p
        JOIN OrderDetails od ON p.ProductID = od.ProductID
        JOIN OrderItems oi ON od.OrderItemID = oi.OrderItemID
        WHERE oi.OrderID = ? AND p.Status = 4
        LIMIT 1
    """;
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // có ít nhất 1 sản phẩm Status = 4
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void increaseQueueByCategoryId(int categoryId, int quantity) {
        String sql = "UPDATE Categories SET Queue = Queue + ? WHERE CategoryID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasUnassignedProducts(int orderID) {
        String sql = """
        SELECT 1
        FROM OrderItems oi
        JOIN OrderDetails od ON oi.OrderItemID = od.OrderItemID
        WHERE oi.OrderID = ? AND od.ProductID IS NULL
        LIMIT 1
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true nếu còn dòng chưa gán ProductID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void decreaseQueueByCategoryId(int categoryId, int quantity) {
        String sql = "UPDATE Categories SET Queue = GREATEST(Queue - ?, 0) WHERE CategoryID = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, categoryId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OrderCateAdminDAO dao = new OrderCateAdminDAO();
        List<OrderItems> orders = dao.getAllOrderCateItemsByOrderID(4);

        if (orders.isEmpty()) {
            System.out.println("No orders found in the database.");
        } else {
            for (OrderItems order : orders) {
                System.out.println("Order ID: " + order.getInventory());

            }
        }
    }
}
