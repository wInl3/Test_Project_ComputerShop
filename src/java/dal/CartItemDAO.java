package dal;

import java.sql.*;
import java.util.ArrayList;
import models.CartItem;
import models.Products;
import org.apache.jasper.tagplugins.jstl.core.Out;

public class CartItemDAO extends DBContext {

    public ArrayList<CartItem> getCartItemsByUserId(int userId) {
        ArrayList<CartItem> itemList = new ArrayList<>();
        String sql = "SELECT CartItemID, UserID, CategoryID, WarrantyDetailID, Quantity, Status FROM CartItems WHERE UserID = ?";

        WarrantyDetailDAO warrantyDetailDAO = new WarrantyDetailDAO();  // Gọi lại DAO phụ nếu cần
        CategoriesDAO categoriesDAO = new CategoriesDAO();              // Giả định bạn có DAO này

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int cartItemID = rs.getInt("CartItemID");
                int categoryID = rs.getInt("CategoryID");
                int warrantyDetailID = rs.getInt("WarrantyDetailID");

                CartItem item = new CartItem();
                item.setCartItemID(cartItemID);
                item.setUserID(userId);
                item.setQuantity(rs.getInt("Quantity"));
                item.setStatus(rs.getInt("Status"));

                item.setCategory(categoriesDAO.getCategoryByID(categoryID).get(0));
                item.setWarranty(warrantyDetailDAO.getWarrantyDetailById(warrantyDetailID));

                itemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    public boolean updateQuantity(int cartItemID, int quantity) {
        // Bước 1: Lấy cart của user
        String cartItemSql = """
                               Update CartItems 
                               Set Quantity = ?
                               Where CartItemID = ?
                             """;
        try (PreparedStatement ps = connection.prepareStatement(cartItemSql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemID);
            int n = ps.executeUpdate();
            if (n == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean addCartItem(int userID, int CategoryID, int quantity, int WarrantyDetailID) {
        // Bước 1: Lấy cart của user
        String cartItemSql = """
   INSERT INTO CartItems (UserID, CategoryID, Quantity, WarrantyDetailID)
   VALUES (?, ?, ?, ?)
   ON DUPLICATE KEY UPDATE Quantity = Quantity + VALUES(Quantity)
""";
        try (PreparedStatement ps = connection.prepareStatement(cartItemSql)) {
            ps.setInt(1, userID);
            ps.setInt(2, CategoryID);
            ps.setInt(3, quantity);
            ps.setInt(4, WarrantyDetailID);
            int n = ps.executeUpdate();
            if (n == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteCartItem(int CartItemID) {
        String sql = "DELETE FROM CartItems WHERE CartItemID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, CartItemID);
            int n = ps.executeUpdate();
            if (n == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<CartItem> getCartItemsByUserIdWithOffset(int userId, int offset, int limit) {
        ArrayList<CartItem> itemList = new ArrayList<>();
        String sql = """
  SELECT CartItemID, UserID, CategoryID, WarrantyDetailID, Quantity, Status 
  FROM CartItems 
  WHERE UserID = ?
  ORDER BY CartItemID DESC
  LIMIT ?, ?
""";

        WarrantyDetailDAO warrantyDetailDAO = new WarrantyDetailDAO();
        CategoriesDAO categoriesDAO = new CategoriesDAO();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, offset);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int cartItemID = rs.getInt("CartItemID");
                int categoryID = rs.getInt("CategoryID");
                int warrantyDetailID = rs.getInt("WarrantyDetailID");

                CartItem item = new CartItem();
                item.setCartItemID(cartItemID);
                item.setUserID(userId);
                item.setQuantity(rs.getInt("Quantity"));
                item.setStatus(rs.getInt("Status"));

                item.setCategory(categoriesDAO.getCategoryByID(categoryID).get(0));
                item.setWarranty(warrantyDetailDAO.getWarrantyDetailById(warrantyDetailID));

                itemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    public CartItem getCartItemById(int cartItemID) {
        String sql = "SELECT CartItemID, UserID, CategoryID, WarrantyDetailID, Quantity, Status "
                + "FROM CartItems WHERE CartItemID = ?";

        WarrantyDetailDAO warrantyDetailDAO = new WarrantyDetailDAO();
        CategoriesDAO categoriesDAO = new CategoriesDAO();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartItemID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userID = rs.getInt("UserID");
                int categoryID = rs.getInt("CategoryID");
                int warrantyDetailID = rs.getInt("WarrantyDetailID");

                CartItem item = new CartItem();
                item.setCartItemID(cartItemID);
                item.setUserID(userID);
                item.setQuantity(rs.getInt("Quantity"));
                item.setStatus(rs.getInt("Status"));

                item.setCategory(categoriesDAO.getCategoryByID(categoryID).get(0));
                item.setWarranty(warrantyDetailDAO.getWarrantyDetailById(warrantyDetailID));

                return item;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        CartItemDAO dao = new CartItemDAO();
//        System.err.println(dao.deleteCartItemINT(1));
    }
}
