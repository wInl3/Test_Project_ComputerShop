package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Categories;
import models.OrderDetail;
import models.OrderItems;

public class OrderItemDAO extends DBContext {

    // Hàm tạo order và trả về OrderID vừa tạo, trả về -1 nếu lỗi
    public int insertOrderItem(OrderItems orderItem) {
        String sql = """
            INSERT INTO OrderItems (OrderID, CategoryID, Quantity, Price)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, orderItem.getOrderID());
            ps.setInt(2, orderItem.getCategoryID());
            ps.setInt(3, orderItem.getQuantity());
            ps.setInt(4, orderItem.getPrice());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int newID = rs.getInt(1);
                        return newID;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // failed
    }

    public int getPriceByCategoryID(int categoryID) {
        String sql = "SELECT Price FROM Categories WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<OrderItems> getOrderItemsByOrderID(int orderID) {
        ArrayList<OrderItems> list = new ArrayList<>();
        String sql = "SELECT oi.OrderItemID, oi.OrderID, oi.CategoryID, oi.Quantity, oi.Price, "
                + "c.CategoryName, c.BrandComID, c.Quantity AS CatQuantity, c.Price AS CatPrice, "
                + "c.Inventory, c.Queue, c.Description, c.Status AS CatStatus, c.ImageURL "
                + "FROM OrderItems oi "
                + "JOIN Categories c ON oi.CategoryID = c.CategoryID "
                + "WHERE oi.OrderID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

            while (rs.next()) {
                OrderItems oi = new OrderItems();
                oi.setOrderItemID(rs.getInt("OrderItemID"));
                oi.setOrderID(rs.getInt("OrderID"));
                oi.setCategoryID(rs.getInt("CategoryID"));
                oi.setQuantity(rs.getInt("Quantity"));
                oi.setPrice(rs.getInt("Price"));

                // Lấy OrderDetails
                ArrayList<OrderDetail> details = orderDetailDAO.getOrderDetailsByOrderItemID(oi.getOrderItemID());
                oi.setOrderDetailList(details);

                // Map Category
                Categories category = new Categories();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setBrandComID(rs.getInt("BrandComID"));
                category.setQuantity(rs.getInt("CatQuantity"));
                category.setPrice(rs.getInt("CatPrice"));
                category.setInventory(rs.getInt("Inventory"));
                category.setQueue(rs.getInt("Queue"));
                category.setDescription(rs.getString("Description"));
                category.setStatus(rs.getInt("CatStatus"));
                category.setImgURL(rs.getString("ImageURL"));

                oi.setCategory(category);

                list.add(oi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int checkOrderItemExist(int orderID, int categoryID) {
        String sql = "SELECT OrderItemID FROM OrderItems WHERE OrderID = ? AND CategoryID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ps.setInt(2, categoryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("OrderItemID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Không tồn tại
    }

    public boolean addQuantity(int orderID, int categoryID, int quantity) {
        String sql = "UPDATE OrderItems SET Quantity = Quantity + ? WHERE OrderID = ? AND CategoryID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, orderID);
            ps.setInt(3, categoryID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Trả về true nếu update thành công
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Lỗi hoặc không tồn tại bản ghi để update
    }

    public OrderItems getOrderItemByID(int orderItemID) {
        String sql = """
        SELECT oi.OrderItemID, oi.OrderID, oi.CategoryID, oi.Quantity, oi.Price,
               c.CategoryName, c.BrandComID, c.Quantity AS CatQuantity, c.Price AS CatPrice,
               c.Inventory, c.Queue, c.Description, c.Status AS CatStatus, c.ImageURL
        FROM OrderItems oi
        JOIN Categories c ON oi.CategoryID = c.CategoryID
        WHERE oi.OrderItemID = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItemID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderItems oi = new OrderItems();
                    oi.setOrderItemID(rs.getInt("OrderItemID"));
                    oi.setOrderID(rs.getInt("OrderID"));
                    oi.setCategoryID(rs.getInt("CategoryID"));
                    oi.setQuantity(rs.getInt("Quantity"));
                    oi.setPrice(rs.getInt("Price"));

                    // Map Category
                    Categories category = new Categories();
                    category.setCategoryID(rs.getInt("CategoryID"));
                    category.setCategoryName(rs.getString("CategoryName"));
                    category.setBrandComID(rs.getInt("BrandComID"));
                    category.setQuantity(rs.getInt("CatQuantity"));
                    category.setPrice(rs.getInt("CatPrice"));
                    category.setInventory(rs.getInt("Inventory"));
                    category.setQueue(rs.getInt("Queue"));
                    category.setDescription(rs.getString("Description"));
                    category.setStatus(rs.getInt("CatStatus"));
                    category.setImgURL(rs.getString("ImageURL"));

                    oi.setCategory(category);

                    return oi;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
