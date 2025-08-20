package dal;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import models.OrderCate; // Đảm bảo đã có class OrderCate
import models.OrderItems;

public class OrderDAO extends DBContext {

// Hàm tạo order và trả về OrderID vừa tạo, trả về -1 nếu lỗi
    public int createOrderAndReturnId(OrderCate order) {
        String sql = "INSERT INTO Orders (OrderCode, Product_Type, CustomerID, Address, FullName, PhoneNumber, TotalAmount, PaymentStatusID, Status, Note) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, order.getOrderCode());
            ps.setObject(2, order.getProduct_Type());
            ps.setInt(3, order.getCustomerID());
            ps.setString(4, order.getAddress());
            ps.setString(5, order.getFullName());
            ps.setString(6, order.getPhoneNumber());
            ps.setInt(7, order.getTotalAmount());
            ps.setInt(8, order.getPaymentStatusID());
            ps.setInt(9, order.getStatus());
            ps.setString(10, order.getNote()); // Thêm Note
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về OrderID vừa tạo
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Lỗi
    }

    // Tạo code ngẫu nhiên & đảm bảo không trùng
    public String generateRandomOrderCode() {
        Random rand = new Random();
        String code;
        do {
            int randomNum = 100000 + rand.nextInt(900000); // 6 số
            code = String.valueOf(randomNum);
        } while (isOrderCodeExist(code));
        return code;
    }

    public boolean isOrderCodeExist(String code) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE OrderCode = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public OrderCate getOrderByID(int orderID) {
        String sql = """
        SELECT o.OrderID, o.OrderCode, o.Product_Type, o.CustomerID, o.FullName, o.OrderDate, o.Address, o.PhoneNumber, o.TotalAmount, o.Status, o.PaymentStatusID, os.StatusID, os.StatusName
        FROM Orders o
        LEFT JOIN OrderStatus os ON o.Status = os.StatusID
        WHERE o.OrderID = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderCate order = new OrderCate();
                    order.setOrderID(rs.getInt("OrderID"));
                    order.setOrderCode(rs.getString("OrderCode"));
                    order.setProduct_Type(rs.getInt("Product_Type"));
                    order.setCustomerID(rs.getInt("CustomerID"));
                    order.setFullName(rs.getString("FullName"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setAddress(rs.getString("Address"));
                    order.setPhoneNumber(rs.getString("PhoneNumber"));
                    order.setTotalAmount(rs.getInt("TotalAmount"));
                    order.setStatus(rs.getInt("Status"));
                    order.setPaymentStatusID(rs.getInt("PaymentStatusID"));
                    // Thêm thông tin status chi tiết
                    models.Order_Status status = new models.Order_Status(rs.getInt("StatusID"), rs.getString("StatusName"));
                    order.setOrder_Status(status);
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrderCate getOrderCateByID(int orderID) {
        OrderCate order = null;
        String sql = "SELECT o.*, os.StatusID, os.StatusName FROM Orders o LEFT JOIN OrderStatus os ON o.Status = os.StatusID WHERE o.OrderID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = new OrderCate();
                order.setOrderID(rs.getInt("OrderID"));
                order.setOrderCode(rs.getString("OrderCode"));
                order.setProduct_Type(rs.getInt("Product_Type"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setAddress(rs.getString("Address"));
                order.setPhoneNumber(rs.getString("PhoneNumber"));
                order.setFullName(rs.getString("Fullname"));
                order.setNote(rs.getString("Note"));
                order.setPaymentStatusID(rs.getInt("PaymentStatusID"));
                order.setTotalAmount(rs.getInt("TotalAmount"));
                order.setStatus(rs.getInt("Status"));
                // Thêm thông tin status chi tiết
                models.Order_Status status = new models.Order_Status(rs.getInt("StatusID"), rs.getString("StatusName"));
                order.setOrder_Status(status);
                // Gọi OrderItemDAO để lấy danh sách OrderItems kèm OrderDetail
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                ArrayList<OrderItems> items = orderItemDAO.getOrderItemsByOrderID(orderID);
                order.setOrderItems(items);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public ArrayList<OrderCate> getOrdersByCustomerID(int customerID) {
        ArrayList<OrderCate> orders = new ArrayList<>();
        String sql = "SELECT o.*, os.StatusID, os.StatusName FROM Orders o LEFT JOIN OrderStatus os ON o.Status = os.StatusID WHERE o.CustomerID = ? AND o.Product_Type = 0 ORDER BY o.OrderID DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            while (rs.next()) {
                OrderCate order = new OrderCate();
                order.setOrderID(rs.getInt("OrderID"));
                order.setOrderCode(rs.getString("OrderCode"));
                order.setProduct_Type(rs.getInt("Product_Type"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setAddress(rs.getString("Address"));
                order.setPhoneNumber(rs.getString("PhoneNumber"));
                order.setFullName(rs.getString("Fullname"));
                order.setNote(rs.getString("Note"));
                order.setPaymentStatusID(rs.getInt("PaymentStatusID"));
                order.setTotalAmount(rs.getInt("TotalAmount"));
                order.setStatus(rs.getInt("Status"));
                // Thêm thông tin status chi tiết
                models.Order_Status status = new models.Order_Status(rs.getInt("StatusID"), rs.getString("StatusName"));
                order.setOrder_Status(status);
                // Gọi DAO để lấy danh sách OrderItems của OrderID hiện tại
                ArrayList<OrderItems> items = orderItemDAO.getOrderItemsByOrderID(order.getOrderID());
                order.setOrderItems(items);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public ArrayList<OrderCate> getOrdersByCustomerAndStatus(int customerID, int statusID) {
        ArrayList<OrderCate> orders = new ArrayList<>();
        String sql = """
        SELECT o.*, os.StatusID, os.StatusName 
        FROM Orders o 
        LEFT JOIN OrderStatus os ON o.Status = os.StatusID 
        WHERE o.CustomerID = ? AND o.Status = ? AND o.Product_Type = 0
        ORDER BY o.OrderID DESC
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ps.setInt(2, statusID);
            ResultSet rs = ps.executeQuery();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            while (rs.next()) {
                OrderCate order = new OrderCate();
                order.setOrderID(rs.getInt("OrderID"));
                order.setOrderCode(rs.getString("OrderCode"));
                order.setProduct_Type(rs.getInt("Product_Type"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setAddress(rs.getString("Address"));
                order.setPhoneNumber(rs.getString("PhoneNumber"));
                order.setFullName(rs.getString("Fullname"));
                order.setNote(rs.getString("Note"));
                order.setPaymentStatusID(rs.getInt("PaymentStatusID"));
                order.setTotalAmount(rs.getInt("TotalAmount"));
                order.setStatus(rs.getInt("Status"));

                // Trạng thái chi tiết
                models.Order_Status status = new models.Order_Status(rs.getInt("StatusID"), rs.getString("StatusName"));
                order.setOrder_Status(status);

                // Danh sách sản phẩm
                ArrayList<OrderItems> items = orderItemDAO.getOrderItemsByOrderID(order.getOrderID());
                order.setOrderItems(items);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public boolean activeWarrantyByOrderID(int OrderID) {
        int n = 0;
        String sql = """
        UPDATE Orders
        SET Status = ?
        WHERE OrderID = ?;
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, 6);
            ps.setInt(2, OrderID);
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static void main(String[] args) {
        OrderDAO dao = new OrderDAO();

        // 1. Test generateRandomOrderCode
        String randomCode = dao.generateRandomOrderCode();
        System.out.println("Generated Code: " + randomCode);

        // 2. Tạo OrderCate mẫu để test insert
        OrderCate newOrder = new OrderCate();
        newOrder.setOrderCode(randomCode);
        newOrder.setProduct_Type(0);
        newOrder.setCustomerID(6); // Đảm bảo CustomerID tồn tại
        newOrder.setAddress("123 Main Street");
        newOrder.setFullName("John Doe");
        newOrder.setPhoneNumber("0123456789");
        newOrder.setTotalAmount(5000000);
        newOrder.setPaymentStatusID(1); // Giả sử 1 là "Chưa thanh toán"
        newOrder.setStatus(5); // Giả sử 1 là "Chờ xử lý"
        newOrder.setNote("Giao hàng nhanh trong ngày");

        // 3. Gọi hàm insert và lấy OrderID vừa tạo
        int newOrderId = dao.createOrderAndReturnId(newOrder);
        System.out.println("▶️ New Order ID: " + newOrderId);

        // 4. Test getOrderByID
        OrderCate orderById = dao.getOrderByID(newOrderId);
        System.out.println("▶️ Order by ID:");
        System.out.println(orderById);

        // 5. Test getOrderCateByID (bao gồm cả OrderItems nếu có)
        OrderCate fullOrder = dao.getOrderCateByID(newOrderId);
        System.out.println("▶️ Full Order with Items:");
        System.out.println(fullOrder);
        if (fullOrder != null && fullOrder.getOrderItems() != null) {
            for (OrderItems item : fullOrder.getOrderItems()) {
                System.out.println(" - Item: " + item);
            }
        }

        // 6. Test getOrdersByCustomerID
        ArrayList<OrderCate> ordersByCustomer = dao.getOrdersByCustomerID(newOrder.getCustomerID());
        System.out.println("▶️ All Orders of Customer ID: " + newOrder.getCustomerID());
        for (OrderCate oc : ordersByCustomer) {
            System.out.println(oc);
        }
    }
    
    

}
