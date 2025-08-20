package dalAdmin;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.BuildPCAdmin;
import models.OrderCate;
import models.Products;

public class OrderBuildPCAdmin extends DBAdminContext {

    // 1. Lấy danh sách đơn hàng Build PC theo trạng thái
    public List<OrderCate> getOrdersByStatus(int status) {
        List<OrderCate> list = new ArrayList<>();

        String sql = """
        SELECT 
            o.OrderID, o.OrderCode, o.Product_Type,
            o.FullName AS Consignee, o.PhoneNumber, o.Note, o.OrderDate,
            o.Address AS OrderAddress, o.TotalAmount, o.Status, o.PaymentStatusID,

            customer.UserID AS CustomerUserID, customer.FullName AS CustomerName,

            prepareStaff.UserID AS PrepareStaffID, prepareStaff.FullName AS PrepareStaffName, op.PrepareTime,

            shipper.UserID AS ShipperID, shipper.FullName AS ShipperName, shipper.PhoneNumber AS ShipperPhone,         
            s.ShipTime, s.Note AS ShipNote,

            warrantyStaff.UserID AS WarrantyStaffID, warrantyStaff.FullName AS WarrantyStaffName, wa.AssignedDate

        FROM Orders o
        JOIN Users customer ON o.CustomerID = customer.UserID

        LEFT JOIN OrderPreparements op ON o.OrderID = op.OrderID
        LEFT JOIN Users prepareStaff ON op.UserID = prepareStaff.UserID

        LEFT JOIN Shipping s ON o.OrderID = s.OrderID
        LEFT JOIN Users shipper ON s.ShipperID = shipper.UserID

        LEFT JOIN WarrantyAssignments wa ON o.OrderID = wa.OrderID
        LEFT JOIN Users warrantyStaff ON wa.UserID = warrantyStaff.UserID

        WHERE o.Product_Type = 1 AND o.Status = ?
        ORDER BY o.OrderDate DESC;
        """;

        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderCate order = new OrderCate();

                    order.setOrderID(rs.getInt("OrderID"));
                    order.setOrderCode(rs.getString("OrderCode"));
                    order.setProduct_Type(rs.getInt("Product_Type"));
                    order.setFullName(rs.getNString("Consignee"));
                    order.setPhoneNumber(rs.getNString("PhoneNumber"));
                    order.setNote(rs.getNString("Note"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setAddress(rs.getString("OrderAddress"));
                    order.setTotalAmount(rs.getInt("TotalAmount"));
                    order.setStatus(rs.getInt("Status"));
                    order.setPaymentStatusID(rs.getInt("PaymentStatusID"));

                    order.setCustomerID(rs.getInt("CustomerUserID"));
                    order.setCustomerName(rs.getString("CustomerName"));

                    order.setStaffID(rs.getInt("PrepareStaffID"));
                    order.setStaffName(rs.getString("PrepareStaffName"));
                    order.setPrepareTime(rs.getTimestamp("PrepareTime"));

                    order.setShipperID(rs.getInt("ShipperID"));
                    order.setShipperName(rs.getString("ShipperName"));
                    order.setShipperPhone(rs.getString("ShipperPhone"));
                    order.setShipTime(rs.getDate("ShipTime"));
                    order.setShipNote(rs.getNString("ShipNote"));

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

    // 2. Lấy danh sách linh kiện BuildPC trong 1 đơn hàng
    public List<BuildPCAdmin> getBuildPCItemsByOrderID(int orderID) {
        List<BuildPCAdmin> list = new ArrayList<>();

        String sql = """
        SELECT 
            obi.OrderBuildPCItemID,
            obi.BuildPCID,
            o.OrderCode,
            u.FullName AS UserName,
            p.ProductID,
            p.ProductCode,
            obd.OrderBuildPCDetailID,
            obd.CategoryID,
            c.CategoryName,
            c.ImageURL,
            c.Price,
            c.Status,
            c.BrandComID,
            bc.ComponentID,
            b.BrandName,
            c.Inventory,
            c.queue,
            obp.WarrantyDetailID,
            wd.Price AS WarrantyPrice,
            w.Description AS WarrantyDesc,
            1 AS Quantity  
        FROM Order_BuildPCItems obi
        JOIN Orders o ON obi.OrderID = o.OrderID
        JOIN Build_PC bp ON obi.BuildPCID = bp.BuildPCID
        JOIN Users u ON bp.UserID = u.UserID

        JOIN Order_BuildPCDetails obd ON obi.OrderBuildPCItemID = obd.OrderBuildPCItemID
        LEFT JOIN Order_BuildPC_Products obp ON obd.OrderBuildPCDetailID = obp.OrderBuildPCDetailID
        LEFT JOIN Products p ON obp.ProductID = p.ProductID  
        JOIN Categories c ON obd.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        LEFT JOIN WarrantyDetails wd ON obp.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID

        WHERE obi.OrderID = ?
        ORDER BY obi.OrderBuildPCItemID, obd.OrderBuildPCDetailID;
        """;

        try (
                Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                int currentQueue = 0;
                int lastItemId = -1;

                while (rs.next()) {
                    int currentItemId = rs.getInt("OrderBuildPCItemID");

                    if (currentItemId != lastItemId) {
                        currentQueue++;
                        lastItemId = currentItemId;
                    }

                    BuildPCAdmin item = new BuildPCAdmin();
                    item.setOrderBuildPcItemId(rs.getInt("OrderBuildPCItemID"));
                    item.setOrderBuildPcDetailId(rs.getInt("OrderBuildPCDetailID"));
                    item.setQuantity(rs.getInt("Quantity"));
                    item.setQueue(rs.getInt("queue"));
                    item.setBuildPcItem(currentItemId);
                    item.setBuildPcId(rs.getInt("BuildPCID"));
                    item.setOrderBuildPcItemId(currentItemId);
                    item.setOrderCode(rs.getString("OrderCode"));
                    item.setFullName(rs.getString("UserName"));
                    item.setProductId(rs.getInt("ProductID"));
                    item.setProductCode(rs.getString("ProductCode"));
                    item.setCateId(rs.getInt("CategoryID"));
                    item.setCateName(rs.getString("CategoryName"));
                    item.setImgUrl(rs.getString("ImageURL"));
                    item.setPrice(rs.getInt("Price"));
                    item.setStatus(rs.getInt("Status"));
                    item.setComponentId(rs.getInt("ComponentID"));
                    item.setBrandName(rs.getString("BrandName"));
                    item.setInventory(rs.getInt("Inventory"));

                    // Null-safe values
                    Integer warrantyDetailId = rs.getObject("WarrantyDetailID") != null ? rs.getInt("WarrantyDetailID") : null;
                    Integer warrantyPrice = rs.getObject("WarrantyPrice") != null ? rs.getInt("WarrantyPrice") : null;

                    item.setWarrantyDetailId(warrantyDetailId);
                    item.setWarrantyPrice(warrantyPrice);
                    item.setWarrantyDesc(rs.getString("WarrantyDesc"));

                    list.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 3. Cập nhật trạng thái đơn hàng
    public void updateOrderStatus(int orderID, int status) {
        String updateOrderSQL = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        String updateBuildPCSQL = """
        UPDATE Build_PC 
        SET Status = ? 
        WHERE BuildPCID IN (
            SELECT BuildPCID FROM Order_BuildPCItems WHERE OrderID = ?
        )
    """;

        try (Connection conn = new DBAdminContext().connection; // ✅ mở kết nối mới tại đây
                 PreparedStatement psOrder = conn.prepareStatement(updateOrderSQL); PreparedStatement psBuildPC = conn.prepareStatement(updateBuildPCSQL)) {

            // Cập nhật đơn hàng
            psOrder.setInt(1, status);
            psOrder.setInt(2, orderID);
            psOrder.executeUpdate();

            // Cập nhật Build_PC tương ứng
            psBuildPC.setInt(1, status);
            psBuildPC.setInt(2, orderID);
            psBuildPC.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Gán nhân viên chuẩn bị
    public void insertOrderPreparementForBuildPC(int userID, int orderID) {
        String sql = "INSERT INTO OrderPreparements (UserID, OrderID, PrepareTime) VALUES (?, ?, CURRENT_DATE)";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, orderID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Gán shipper và ngày giao hàng
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

    // check inventory
    public List<BuildPCAdmin> getAllOrderBuildPCItemsByOrderID(int orderID) {
        List<BuildPCAdmin> list = new ArrayList<>();

        String sql = """
        SELECT 
            obi.OrderBuildPCItemID,
            bpi.BuildPCID,
            c.CategoryName,
            bpi.Price,
            c.Inventory,
            c.Queue,
            bpi.Status
        FROM Order_BuildPCItems obi
        JOIN Build_PC bp ON obi.BuildPCID = bp.BuildPCID
        JOIN Build_PC_Items bpi ON bpi.BuildPCID = bp.BuildPCID
        JOIN Categories c ON bpi.CategoryID = c.CategoryID
        WHERE obi.OrderID = ?;
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BuildPCAdmin item = new BuildPCAdmin();

                    item.setOrderBuildPcItemId(rs.getInt("OrderBuildPCItemID"));
                    item.setBuildPcId(rs.getInt("BuildPCID"));
                    item.setCateName(rs.getString("CategoryName"));
                    item.setPrice(rs.getInt("Price"));
                    item.setInventory(rs.getInt("Inventory"));
                    item.setQueue(rs.getInt("Queue"));
                    item.setStatus(rs.getInt("Status"));

                    list.add(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //update inventory
    public void updateQueueForBuildPCOrder(int orderID) {
        String sql = """
        SELECT bpi.CategoryID, COUNT(*) AS Quantity
        FROM Order_BuildPCItems obi
        JOIN Build_PC_Items bpi ON obi.BuildPCID = bpi.BuildPCID
        WHERE obi.OrderID = ?
        GROUP BY bpi.CategoryID
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("CategoryID");
                int quantity = rs.getInt("Quantity");

                String updateSQL = "UPDATE Categories SET Queue = Queue + ? WHERE CategoryID = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(updateSQL)) {
                    psUpdate.setInt(1, quantity);
                    psUpdate.setInt(2, categoryId);
                    psUpdate.executeUpdate();

                    System.out.println("✅ Tăng Queue cho CategoryID " + categoryId + " thêm " + quantity);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BuildPCAdmin> getBuildPCProductsByOrderBuildPCItemID(int orderBuildPCItemID) {
        List<BuildPCAdmin> list = new ArrayList<>();
        String sql = """
    SELECT 
        p.ProductID, 
        p.ProductCode,
        p.Note AS ProductNote,
        p.Status AS ProductStatus,
        w.WarrantyPeriod,
        w.Description AS WarrantyDescription,
        obpp.OrderBuildPCProductID,
        obpp.OrderBuildPCDetailID
    FROM Order_BuildPCItems obpi
    JOIN Order_BuildPCDetails obpd ON obpi.OrderBuildPCItemID = obpd.OrderBuildPCItemID
    JOIN Order_BuildPC_Products obpp ON obpd.OrderBuildPCDetailID = obpp.OrderBuildPCDetailID
    LEFT JOIN Products p ON obpp.ProductID = p.ProductID
    LEFT JOIN WarrantyDetails wd ON obpp.WarrantyDetailID = wd.WarrantyDetailID
    LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
    WHERE obpi.OrderBuildPCItemID = ?
""";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderBuildPCItemID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BuildPCAdmin b = new BuildPCAdmin();
                    b.setProductId(rs.getInt("ProductID"));
                    b.setProductCode(rs.getString("ProductCode"));
                    b.setProductNote(rs.getString("ProductNote"));
                    b.setProductStatus(rs.getInt("ProductStatus"));
                    b.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    b.setWarrantyDesc(rs.getString("WarrantyDescription"));
                    b.setOrderBuildPcProductId(rs.getInt("OrderBuildPCProductID"));
                    b.setOrderBuildPcDetailId(rs.getInt("OrderBuildPCDetailID"));

                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
// insert product code

    public List<Products> getPendingWarrantyProductsByOrderId(int orderId) {
        List<Products> productList = new ArrayList<>();
        String sql = """
        SELECT p.ProductCode, p.Status
        FROM Products p
        JOIN Order_BuildPC_Products obpp ON p.ProductID = obpp.ProductID
        JOIN Order_BuildPCDetails obd ON obpp.OrderBuildPCDetailID = obd.OrderBuildPCDetailID
        JOIN Order_BuildPCItems obpi ON obd.OrderBuildPCItemID = obpi.OrderBuildPCItemID
        WHERE (p.Status = 3 OR p.Status = 4) AND obpi.OrderID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products product = new Products();
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

    public boolean hasApprovedBuildPCProduct(int orderID) {
        String sql = """
        SELECT 1
        FROM Order_BuildPC_Products obpp
        JOIN Products p ON obpp.ProductID = p.ProductID
        JOIN Order_BuildPCDetails obd ON obpp.OrderBuildPCDetailID = obd.OrderBuildPCDetailID
        JOIN Order_BuildPCItems obi ON obd.OrderBuildPCItemID = obi.OrderBuildPCItemID
        WHERE obi.OrderID = ? AND p.Status = 4
        LIMIT 1
    """;

        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void assignProductsToBuildPCItem(int itemID) {
        String getDetailsSQL = "SELECT OrderBuildPCDetailID, CategoryID FROM Order_BuildPCDetails WHERE OrderBuildPCItemID = ?";
        String getUnassignedSQL = "SELECT OrderBuildPCProductID FROM Order_BuildPC_Products WHERE OrderBuildPCDetailID = ? AND ProductID IS NULL";
        String getProductsSQL = "SELECT p.ProductID FROM Products p JOIN Imports i ON p.ImportID = i.ImportID WHERE i.CategoryID = ? AND p.Status = 1 LIMIT ?";
        String updateSQL = "UPDATE Order_BuildPC_Products SET ProductID = ? WHERE OrderBuildPCProductID = ?";
        String markUsedSQL = "UPDATE Products SET Status = 0 WHERE ProductID = ?";

        try (Connection conn = new DBAdminContext().connection) {
            conn.setAutoCommit(false);
            PreparedStatement psGetDetails = conn.prepareStatement(getDetailsSQL);
            PreparedStatement psGetUnassigned = conn.prepareStatement(getUnassignedSQL);
            PreparedStatement psGetProducts = conn.prepareStatement(getProductsSQL);
            PreparedStatement psUpdate = conn.prepareStatement(updateSQL);
            PreparedStatement psMarkUsed = conn.prepareStatement(markUsedSQL);

            psGetDetails.setInt(1, itemID);

            List<Integer> detailIDs = new ArrayList<>();
            Map<Integer, Integer> detailToCategory = new HashMap<>();

            try (ResultSet rs = psGetDetails.executeQuery()) {
                while (rs.next()) {
                    int detailID = rs.getInt("OrderBuildPCDetailID");
                    int categoryID = rs.getInt("CategoryID");
                    detailIDs.add(detailID);
                    detailToCategory.put(detailID, categoryID);
                }
            }

            if (detailIDs.isEmpty()) {
                System.out.println(" Không có BuildPCDetail nào thuộc ItemID = " + itemID);
                return;
            }

            for (int detailID : detailIDs) {
                int categoryID = detailToCategory.get(detailID);

                List<Integer> buildProductIDs = new ArrayList<>();
                psGetUnassigned.setInt(1, detailID);
                try (ResultSet rs = psGetUnassigned.executeQuery()) {
                    while (rs.next()) {
                        buildProductIDs.add(rs.getInt("OrderBuildPCProductID"));
                    }
                }

                if (buildProductIDs.isEmpty()) {
                    System.out.println("️ Không có dòng nào cần gán cho DetailID = " + detailID);
                    continue;
                }

                psGetProducts.setInt(1, categoryID);
                psGetProducts.setInt(2, buildProductIDs.size());

                List<Integer> productIDs = new ArrayList<>();
                try (ResultSet rs = psGetProducts.executeQuery()) {
                    while (rs.next()) {
                        productIDs.add(rs.getInt("ProductID"));
                    }
                }

                if (productIDs.size() < buildProductIDs.size()) {
                    System.out.println("️ Không đủ sản phẩm tồn kho cho CategoryID = " + categoryID);
                    continue;
                }

                for (int i = 0; i < buildProductIDs.size(); i++) {
                    int obpID = buildProductIDs.get(i);
                    int productID = productIDs.get(i);

                    psUpdate.setInt(1, productID);
                    psUpdate.setInt(2, obpID);
                    psUpdate.addBatch();

                    psMarkUsed.setInt(1, productID);
                    psMarkUsed.addBatch();
                }

                psUpdate.executeBatch();
                psMarkUsed.executeBatch();

                System.out.println("Đã gán sản phẩm thành công cho DetailID = " + detailID);
            }

            conn.commit();
            System.out.println(" Đã gán sản phẩm cho tất cả chi tiết trong BuildPCItemID = " + itemID);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Có lỗi xảy ra, rollback!");
            try (Connection conn = new DBAdminContext().connection) {
                conn.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }

    public boolean isBuildPCDetailCompleted(int buildPCDetailID, int expectedQuantity) {
        String sql = """
        SELECT COUNT(*) AS cnt 
        FROM Order_BuildPC_Products 
        WHERE OrderBuildPCDetailID = ? AND ProductID IS NOT NULL
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, buildPCDetailID);
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

    public List<Products> getProductsByBuildPCDetailID(int buildPCDetailID) {
        List<Products> list = new ArrayList<>();

        String sql = """
        SELECT 
            p.ProductID, 
            p.ProductCode,
            w.WarrantyPeriod,
            w.Description,
            bp.Start,
            bp.End
        FROM Order_BuildPC_Products bp
        JOIN Products p ON bp.ProductID = p.ProductID
        JOIN WarrantyDetails wd ON bp.WarrantyDetailID = wd.WarrantyDetailID
        JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        WHERE bp.OrderBuildPCDetailID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, buildPCDetailID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products p = new Products();
                    p.setProductID(rs.getInt("ProductID"));
                    p.setProductCode(rs.getString("ProductCode"));
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

    public void updateBuildPCOrderStatusIfComplete(int orderID) {
        String checkSql = """
        SELECT COUNT(*) AS MissingCount
        FROM Order_BuildPCDetails d
        JOIN Order_BuildPC_Products p ON d.OrderBuildPCDetailID = p.OrderBuildPCDetailID
        WHERE d.OrderID = ? AND p.ProductID IS NULL
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

        WHERE o.Product_Type = 1 AND o.OrderID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new OrderCate();

                    // Order info
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

    public int getLeastBusyStaffLastMonth() {
        String sql = """
        SELECT op.UserID
        FROM OrderPreparements op
        WHERE op.PrepareTime >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
        GROUP BY op.UserID
        ORDER BY COUNT(*) ASC
        LIMIT 1
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("UserID"); // ID của nhân viên ít đơn nhất
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // Không tìm thấy
    }

    public void updateInventoryAfterAssign(int categoryId, int qty) {
        String sql = "UPDATE Categories SET Inventory = Inventory - ? WHERE CategoryID = ? AND Inventory >= ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, categoryId);
            ps.setInt(3, qty);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("️ Không đủ tồn kho để trừ cho CategoryID = " + categoryId);
            }

        } catch (SQLException e) {
            System.err.println(" Lỗi khi trừ tồn kho:");
            e.printStackTrace();
        }
    }

    public boolean hasApprovedProductInOrder(int orderID) {
        String sql = """
        SELECT 1
        FROM Products p
        JOIN OrderDetails od ON p.ProductID = od.ProductID
        JOIN OrderItems oi ON od.OrderItemID = oi.OrderItemID
        JOIN Orders o ON oi.OrderID = o.OrderID
        WHERE o.OrderID = ? AND o.Product_Type = 1 AND p.Status = 4
        LIMIT 1
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true nếu có ít nhất 1 sản phẩm đã approved
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Integer> getBuildPCItemIDsByOrderID(int orderID) {
        List<Integer> detailIDs = new ArrayList<>();
        String sql = """
        SELECT d.OrderBuildPCDetailID
        FROM Order_BuildPC o
        JOIN Order_BuildPCItems i ON o.OrderBuildPCID = i.OrderBuildPCID
        JOIN Order_BuildPCDetails d ON i.OrderBuildPCItemID = d.OrderBuildPCItemID
        WHERE o.OrderID = ?
    """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, orderID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    detailIDs.add(rs.getInt("OrderBuildPCDetailID"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailIDs;
    }

    public boolean hasUnassignedBuildPCProducts(int orderId) {
        String sql = """
    SELECT COUNT(*) 
    FROM Order_BuildPC_Products p
    JOIN Order_BuildPCDetails d ON p.OrderBuildPCDetailID = d.OrderBuildPCDetailID
    JOIN Order_BuildPCItems i ON d.OrderBuildPCItemID = i.OrderBuildPCItemID
    WHERE i.OrderID = ? AND p.ProductID IS NULL
""";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void decreaseQueueByCateId(int cateId, int amount) {
        String sql = "UPDATE Categories SET Queue = GREATEST(Queue - ?, 0) WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setInt(2, cateId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfItemCompleted(int orderBuildPcItemId) {
        String sql = """
        SELECT COUNT(*) 
        FROM Order_BuildPCDetails d
        JOIN Order_BuildPC_Products p ON d.OrderBuildPCDetailID = p.OrderBuildPCDetailID
        WHERE d.OrderBuildPCItemID = ? AND p.ProductID IS NULL
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderBuildPcItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;  // ✅ true nếu không còn dòng nào thiếu ProductID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getInventoryByCategory(int cateID) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Products WHERE CategoryID = ? AND IsDeleted = 0 AND ProductID NOT IN ("
                + "SELECT ProductID FROM Order_BuildPC_Products"
                + ")";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cateID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
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

    public void updateBuildPCProductsWarrantyDates(int orderBuildPCItemID) {
        String sql = """
        UPDATE Order_BuildPC_Products obpp
        JOIN Order_BuildPCDetails obpd ON obpp.OrderBuildPCDetailID = obpd.OrderBuildPCDetailID
        JOIN Order_BuildPCItems obpi ON obpd.OrderBuildPCItemID = obpi.OrderBuildPCItemID
        JOIN Orders o ON obpi.OrderID = o.OrderID
        JOIN Shipping s ON s.OrderID = o.OrderID
        LEFT JOIN WarrantyDetails wd ON obpp.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        SET 
            obpp.StartDate = s.ShipTime,
            obpp.EndDate = IFNULL(DATE_ADD(s.ShipTime, INTERVAL w.WarrantyPeriod MONTH), s.ShipTime)
        WHERE 
            s.ShipTime IS NOT NULL
            AND obpp.StartDate IS NULL
            AND obpi.OrderBuildPCItemID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderBuildPCItemID);

            int rows = ps.executeUpdate();
            System.out.println("✅ Đã cập nhật " + rows + " dòng trong Order_BuildPC_Products theo OrderBuildPCItemID " + orderBuildPCItemID);

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật ngày bảo hành theo OrderBuildPCItemID " + orderBuildPCItemID + ":");
            e.printStackTrace();
        }
    }

    public OrderCate getBuildPCOrderByID(int orderID) {
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

        WHERE o.Product_Type = 2 AND o.OrderID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new OrderCate();

                    // Order Info
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

                    // Prepare Staff
                    order.setStaffID(rs.getInt("PrepareStaffID"));
                    order.setStaffName(rs.getString("PrepareStaffName"));
                    order.setPrepareTime(rs.getTimestamp("PrepareTime"));

                    // Shipping
                    order.setShipperID(rs.getInt("ShipperID"));
                    order.setShipperName(rs.getString("ShipperName"));
                    order.setShipperPhone(rs.getString("ShipperPhone"));
                    order.setShipTime(rs.getDate("ShipTime"));
                    order.setShipNote(rs.getNString("ShipNote"));

                    // Warranty Staff
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

    public void increaseQueueByCategoryId(int categoryId, int amount) {
        String sql = "UPDATE Categories SET Queue = Queue + ? WHERE CategoryID = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Products> getPendingWarrantyBuildPCProductsByOrderId(int orderId) {
        List<Products> productList = new ArrayList<>();
        String sql = """
        SELECT p.ProductCode, p.Status
        FROM Products p
        JOIN Order_BuildPC_Products obpp ON p.ProductID = obpp.ProductID
        JOIN Order_BuildPCDetails obd ON obpp.OrderBuildPCDetailID = obd.OrderBuildPCDetailID
        JOIN Order_BuildPCItems obi ON obd.OrderBuildPCItemID = obi.OrderBuildPCItemID
        WHERE (p.Status = 3 OR p.Status = 4) AND obi.OrderID = ?
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products product = new Products(); // Sử dụng constructor rỗng
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

    public static void main(String[] args) {
        OrderBuildPCAdmin dao = new OrderBuildPCAdmin();
        int orderBuildPCItemID = 1;
        dao.updateBuildPCProductsWarrantyDates(orderBuildPCItemID);
    }

}
