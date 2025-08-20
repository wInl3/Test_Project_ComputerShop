package dal;

import java.sql.*;
import java.util.*;
import models.CartBuildPC;

public class CartBuildPCDAO extends DBContext {

    public List<CartBuildPC> getCartPCView(int userID) {
        List<CartBuildPC> list = new ArrayList<>();
        String sql = """
        SELECT 
            cb.CartBuildPCID,
            MAX(CASE WHEN bc.ComponentID = 2 THEN c.CategoryName END) AS MainBoard,
            MAX(CASE WHEN bc.ComponentID = 3 THEN c.CategoryName END) AS CPU,
            MAX(CASE WHEN bc.ComponentID = 4 THEN c.CategoryName END) AS GPU,
            MAX(CASE WHEN bc.ComponentID = 5 THEN c.CategoryName END) AS RAM,
            MAX(CASE WHEN bc.ComponentID = 6 THEN c.CategoryName END) AS SSD,
            MAX(CASE WHEN bc.ComponentID = 7 THEN c.CategoryName END) AS PCCase,
            SUM(cbi.Price) AS TotalPrice,
            MAX(cb.Status) AS CartStatus
        FROM Cart_Build_PC cb
        JOIN Cart_Build_PC_Items cbi ON cb.CartBuildPCID = cbi.CartBuildPCID
        JOIN Categories c ON cbi.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        WHERE cb.UserID = ? And cb.Status = 1
        GROUP BY cb.CartBuildPCID
        ORDER BY cb.CartBuildPCID
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartBuildPC cart = new CartBuildPC();
                cart.setCartBuildPCID(rs.getInt("CartBuildPCID"));
                cart.setMainBoard(rs.getString("MainBoard"));
                cart.setCpu(rs.getString("CPU"));
                cart.setGpu(rs.getString("GPU"));
                cart.setRam(rs.getString("RAM"));
                cart.setSsd(rs.getString("SSD"));
                cart.setPcCase(rs.getString("PCCase"));
                cart.setPrice(rs.getInt("TotalPrice"));
                cart.setStatus(rs.getInt("CartStatus"));
                list.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //lấy CategoryID theo đúng ComponentID thứ tự 2 -> 7
    public List<Integer> getCategoryIDsInCartBuildPC(int cartBuildPCID) {
        List<Integer> list = new ArrayList<>();
        String sql = """
        SELECT c.CategoryID
        FROM Cart_Build_PC_Items cbi
        JOIN Categories c ON cbi.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        WHERE cbi.CartBuildPCID = ?
        ORDER BY bc.ComponentID
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartBuildPCID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("CategoryID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //  lấy WarrantyID theo đúng thứ tự ComponentID
    public List<Integer> getWarrantyIDsInCartBuildPC(int cartBuildPCID) {
        List<Integer> list = new ArrayList<>();
        String sql = """
        SELECT cbi.WarrantyDetailID
        FROM Cart_Build_PC_Items cbi
        JOIN Categories c ON cbi.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        WHERE cbi.CartBuildPCID = ?
        ORDER BY bc.ComponentID
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartBuildPCID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int warrantyID = rs.getInt("WarrantyDetailID");
                if (rs.wasNull()) {
                    list.add(0); // không có bảo hành
                } else {
                    list.add(warrantyID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteCartBuildPC(int cartBuildPCID) {
        String sql1 = "DELETE FROM Cart_Build_PC_Items WHERE CartBuildPCID = ?";
        String sql2 = "DELETE FROM Cart_Build_PC WHERE CartBuildPCID = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps1 = connection.prepareStatement(sql1)) {
                ps1.setInt(1, cartBuildPCID);
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                ps2.setInt(1, cartBuildPCID);
                ps2.executeUpdate();
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public int insertOrderFromCartAndReturnOrderID(int cartBuildPCID, int userID) {
        String getUserSQL = "SELECT FullName, PhoneNumber FROM Users WHERE UserID = ?";
        String getItemsSQL = """
        SELECT cbi.CategoryID, cbi.WarrantyDetailID, cbi.Price
        FROM Cart_Build_PC_Items cbi
        WHERE cbi.CartBuildPCID = ?
    """;
        String insertBuildPCSQL = "INSERT INTO Build_PC (Price, Status, UserID) VALUES (?, 1, ?)";
        String insertBuildPCItemSQL = """
        INSERT INTO Build_PC_Items (BuildPCID, CategoryID, Price, WarrantyDetailID, Status)
        VALUES (?, ?, ?, ?, 1)
    """;
        String insertOrderSQL = """
        INSERT INTO Orders (OrderCode, Product_Type, CustomerID, OrderDate, Address, PhoneNumber, Fullname, PaymentStatusID, TotalAmount, Status)
        VALUES (NULL, 1, ?, ?, '', ?, ?, 2, ?, 1)
    """;
        String insertOrderItemSQL = "INSERT INTO Order_BuildPCItems (OrderID, BuildPCID, Price) VALUES (?, ?, ?)";
        String insertDetailSQL = """
        INSERT INTO Order_BuildPCDetails (OrderBuildPCItemID, CategoryID, Price, Status)
        VALUES (?, ?, ?, 1)
    """;
        String insertProductSQL = """
        INSERT INTO Order_BuildPC_Products (OrderBuildPCDetailID, ProductID, WarrantyDetailID)
        VALUES (?, NULL, ?)
    """;
        String deleteCartItemsSQL = "DELETE FROM Cart_Build_PC_Items WHERE CartBuildPCID = ?";
        String deleteCartSQL = "DELETE FROM Cart_Build_PC WHERE CartBuildPCID = ?";

        try {
            connection.setAutoCommit(false);

            // Thời gian thực
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            System.out.println("✅ JVM OrderDate = " + currentTime);

            // Lấy thông tin user
            PreparedStatement psUser = connection.prepareStatement(getUserSQL);
            psUser.setInt(1, userID);
            ResultSet rsUser = psUser.executeQuery();
            if (!rsUser.next()) {
                connection.rollback();
                return -1;
            }
            String fullName = rsUser.getString("FullName");
            String phone = rsUser.getString("PhoneNumber");

            // Lấy item trong giỏ
            PreparedStatement psItems = connection.prepareStatement(getItemsSQL);
            psItems.setInt(1, cartBuildPCID);
            ResultSet rsItems = psItems.executeQuery();

            List<Object[]> items = new ArrayList<>();
            int totalPrice = 0;
            while (rsItems.next()) {
                int categoryID = rsItems.getInt("CategoryID");
                int warrantyID = rsItems.getInt("WarrantyDetailID");
                int price = rsItems.getInt("Price");
                items.add(new Object[]{categoryID, warrantyID, price});
                totalPrice += price;
            }

            if (items.size() != 6) {
                connection.rollback();
                return -1;
            }

            // Insert Build_PC
            PreparedStatement psBuildPC = connection.prepareStatement(insertBuildPCSQL, Statement.RETURN_GENERATED_KEYS);
            psBuildPC.setInt(1, totalPrice);
            psBuildPC.setInt(2, userID);
            psBuildPC.executeUpdate();
            ResultSet rsBuildPC = psBuildPC.getGeneratedKeys();
            if (!rsBuildPC.next()) {
                connection.rollback();
                return -1;
            }
            int buildPCID = rsBuildPC.getInt(1);

            // Insert Build_PC_Items
            PreparedStatement psInsertBuildPCItem = connection.prepareStatement(insertBuildPCItemSQL);
            for (Object[] item : items) {
                psInsertBuildPCItem.setInt(1, buildPCID);
                psInsertBuildPCItem.setInt(2, (int) item[0]);
                psInsertBuildPCItem.setInt(3, (int) item[2]);
                if ((int) item[1] > 0) {
                    psInsertBuildPCItem.setInt(4, (int) item[1]);
                } else {
                    psInsertBuildPCItem.setNull(4, Types.INTEGER);
                }
                psInsertBuildPCItem.addBatch();
            }
            psInsertBuildPCItem.executeBatch();

            // Insert Orders (dùng currentTime)
            PreparedStatement psOrder = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, userID);
            psOrder.setTimestamp(2, currentTime);  // ✅ Thời gian thực
            psOrder.setString(3, phone);
            psOrder.setString(4, fullName);
            psOrder.setInt(5, totalPrice);
            psOrder.executeUpdate();

            ResultSet rsOrder = psOrder.getGeneratedKeys();
            if (!rsOrder.next()) {
                connection.rollback();
                return -1;
            }
            int orderID = rsOrder.getInt(1);

            // Update OrderCode
            String updateOrderCodeSQL = "UPDATE Orders SET OrderCode = ? WHERE OrderID = ?";
            PreparedStatement psUpdateOrderCode = connection.prepareStatement(updateOrderCodeSQL);
            psUpdateOrderCode.setString(1, "OD" + orderID);
            psUpdateOrderCode.setInt(2, orderID);
            psUpdateOrderCode.executeUpdate();

            // Insert Order_BuildPCItems
            PreparedStatement psInsertOrderItem = connection.prepareStatement(insertOrderItemSQL, Statement.RETURN_GENERATED_KEYS);
            psInsertOrderItem.setInt(1, orderID);
            psInsertOrderItem.setInt(2, buildPCID);
            psInsertOrderItem.setInt(3, totalPrice);
            psInsertOrderItem.executeUpdate();

            ResultSet rsOrderItem = psInsertOrderItem.getGeneratedKeys();
            if (!rsOrderItem.next()) {
                connection.rollback();
                return -1;
            }
            int orderBuildPCItemID = rsOrderItem.getInt(1);

            // Insert Order_BuildPCDetails + Products
            PreparedStatement psInsertDetail = connection.prepareStatement(insertDetailSQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psInsertProduct = connection.prepareStatement(insertProductSQL);

            for (Object[] item : items) {
                int categoryID = (int) item[0];
                int warrantyID = (int) item[1];
                int price = (int) item[2];

                psInsertDetail.setInt(1, orderBuildPCItemID);
                psInsertDetail.setInt(2, categoryID);
                psInsertDetail.setInt(3, price);
                psInsertDetail.executeUpdate();

                ResultSet rsDetail = psInsertDetail.getGeneratedKeys();
                if (!rsDetail.next()) {
                    connection.rollback();
                    return -1;
                }
                int orderBuildPCDetailID = rsDetail.getInt(1);

                psInsertProduct.setInt(1, orderBuildPCDetailID);
                if (warrantyID > 0) {
                    psInsertProduct.setInt(2, warrantyID);
                } else {
                    psInsertProduct.setNull(2, Types.INTEGER);
                }
                psInsertProduct.executeUpdate();
            }

            // Xoá giỏ hàng
            PreparedStatement psDeleteItems = connection.prepareStatement(deleteCartItemsSQL);
            psDeleteItems.setInt(1, cartBuildPCID);
            psDeleteItems.executeUpdate();

            PreparedStatement psDeleteCart = connection.prepareStatement(deleteCartSQL);
            psDeleteCart.setInt(1, cartBuildPCID);
            psDeleteCart.executeUpdate();

            connection.commit();
            return orderID;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

    public int getCartBuildPCPrice(int cartBuildPCID) {
        String sql = "SELECT SUM(Price) AS TotalPrice FROM Cart_Build_PC_Items WHERE CartBuildPCID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartBuildPCID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalPrice"); // Nếu null sẽ trả về 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

// Updated getDepositAmountByOrderID with 20% deposit
    public int getDepositAmountByOrderID(int orderID) {
        String sql = "SELECT TotalAmount FROM Orders WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("TotalAmount");
                return (int) (total * 0.2); // 20% đặt cọc
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updatePaymentStatus(int orderID, int statusID) {
        String sql = "UPDATE Orders SET PaymentStatusID = ? WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, statusID);
            ps.setInt(2, orderID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertPayment(int orderID, double amount, String paymentMethod, String status) {
        String sql = "INSERT INTO Payments (OrderID, PaymentMethod, PaidAmount, PaymentStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ps.setString(2, paymentMethod);  // có thể là "VNPay" hoặc "COD"
            ps.setDouble(3, amount);
            ps.setString(4, status);         // ví dụ "paid" hoặc "pending"
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateOrderCustomerInfo(int orderID, String fullname, String phone, String address, String note, int paymentStatusID) {
        String sql = "UPDATE Orders SET Fullname = ?, PhoneNumber = ?, Address = ?, Note = ?, PaymentStatusID = ? WHERE OrderID = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, fullname);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, note);
            ps.setInt(5, paymentStatusID);
            ps.setInt(6, orderID);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Giả sử cartBuildPCID bạn muốn xóa là 5
        int cartID = 3;

        CartBuildPCDAO dao = new CartBuildPCDAO();

        boolean result = dao.deleteCartBuildPC(cartID);

        if (result) {
            System.out.println("✅ Xóa thành công cartBuildPCID = " + cartID);
        } else {
            System.out.println("❌ Xóa thất bại hoặc không tồn tại cartBuildPCID = " + cartID);
        }
    }
}
