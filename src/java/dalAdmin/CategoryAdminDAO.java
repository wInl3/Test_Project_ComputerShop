package dalAdmin;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import models.BrandComs;
import models.Categories;

public class CategoryAdminDAO extends DBAdminContext {

    public List<Categories> getAllCategories() {  //su dung trong cateAdmin
        List<Categories> list = new ArrayList<>();

        String sql = """
        SELECT 
            c.CategoryID,
            c.CategoryName,
            c.BrandComID,
            c.Quantity,
            c.inventory,
            c.Price,
            c.Queue,         
            c.Description,
            c.Status,
            c.ImageURL,
            b.BrandName,
            com.ComponentName
        FROM Categories c
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components com ON bc.ComponentID = com.ComponentID
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categories c = new Categories(); // Constructor rỗng

                c.setCategoryID(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setBrandComID(rs.getInt("BrandComID"));
                c.setQuantity(rs.getInt("Quantity"));
                c.setInventory(rs.getInt("inventory"));
                c.setQueue(rs.getInt("Queue"));
                c.setPrice(rs.getInt("Price"));
                c.setDescription(rs.getString("Description"));
                c.setStatus(rs.getInt("Status"));
                c.setImgURL(rs.getString("ImageURL"));
                c.setBrandName(rs.getString("BrandName"));
                c.setComponentName(rs.getString("ComponentName"));

                list.add(c);
            }

        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public List<Categories> getAllCategoriesByInvenory() {
        List<Categories> list = new ArrayList<>();

        String sql = """
        SELECT 
            c.CategoryID,
            c.CategoryName,
            c.BrandComID,
            c.Quantity,
            c.inventory,
            c.Price,
            c.Queue,             
            c.Description,
            c.Status,
            c.ImageURL,
            b.BrandName,
            com.ComponentName
        FROM Categories c
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components com ON bc.ComponentID = com.ComponentID
        WHERE (c.inventory = 0 OR c.Queue != 0) and c.inventory <= c.Queue
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categories c = new Categories(); // dùng constructor rỗng

                c.setCategoryID(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setBrandComID(rs.getInt("BrandComID"));
                c.setQuantity(rs.getInt("Quantity"));
                c.setInventory(rs.getInt("inventory"));
                c.setQueue(rs.getInt("Queue"));
                c.setPrice(rs.getInt("Price"));
                c.setDescription(rs.getString("Description"));
                c.setStatus(rs.getInt("Status"));
                c.setImgURL(rs.getString("ImageURL"));

                // Gán thêm tên thương hiệu và thành phần
                c.setBrandName(rs.getString("BrandName"));
                c.setComponentName(rs.getString("ComponentName"));

                list.add(c);
            }

        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public List<Categories> getAllCategoriesByBrandComID(int brandComID) { //su dung trong cateAdmin
        List<Categories> list = new ArrayList<>();

        String sql = """
        SELECT 
            c.CategoryID,
            c.CategoryName,
            c.BrandComID,
            c.Quantity,
            c.inventory,
            c.Price,
            c.Queue,         
            c.Description,
            c.Status,
            c.ImageURL,
            b.BrandName,
            com.ComponentName
        FROM Categories c
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components com ON bc.ComponentID = com.ComponentID
        WHERE c.BrandComID = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, brandComID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categories c = new Categories(); // dùng constructor rỗng

                    c.setCategoryID(rs.getInt("CategoryID"));
                    c.setCategoryName(rs.getString("CategoryName"));
                    c.setBrandComID(rs.getInt("BrandComID"));
                    c.setQuantity(rs.getInt("Quantity"));
                    c.setInventory(rs.getInt("inventory"));
                    c.setQueue(rs.getInt("Queue"));
                    c.setPrice(rs.getInt("Price"));
                    c.setDescription(rs.getString("Description"));
                    c.setStatus(rs.getInt("Status"));
                    c.setImgURL(rs.getString("ImageURL"));

                    // Gán thêm tên thương hiệu và thành phần
                    c.setBrandName(rs.getString("BrandName"));
                    c.setComponentName(rs.getString("ComponentName"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public Categories getCategoryByID(int id) { //su dung trong cateAdmin
        String sql = """
        SELECT c.*, comp.ComponentName, b.BrandName
        FROM Categories c
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Components comp ON bc.ComponentID = comp.ComponentID
        JOIN Brands b ON bc.BrandID = b.BrandID
        WHERE c.CategoryID = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categories cat = new Categories(); // constructor rỗng

                cat.setCategoryID(rs.getInt("CategoryID"));
                cat.setCategoryName(rs.getString("CategoryName"));
                cat.setBrandComID(rs.getInt("BrandComID"));
                cat.setQuantity(rs.getInt("Quantity"));
                cat.setInventory(rs.getInt("Inventory"));
                cat.setQueue(rs.getInt("Queue"));
                cat.setPrice(rs.getInt("Price"));
                cat.setDescription(rs.getString("Description"));
                cat.setStatus(rs.getInt("Status"));
                cat.setImgURL(rs.getString("ImageURL"));

                // Gán thêm tên thành phần và thương hiệu
                cat.setComponentName(rs.getString("ComponentName"));
                cat.setBrandName(rs.getString("BrandName"));

                return cat;
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void insertCategory(Categories c) { //su dung trong cateAdmin
        String sql = "INSERT INTO Categories (CategoryName, BrandComID, Quantity, Price, Description, Status, ImageURL) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getCategoryName());
            ps.setInt(2, c.getBrandComID());
            ps.setInt(3, c.getQuantity());
            ps.setInt(4, c.getPrice());
            ps.setString(5, c.getDescription());
            ps.setInt(6, c.getStatus());
            ps.setString(7, c.getImgURL());
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateCategory(Categories c) { //su dung trong cateAdmin
        String sql = "UPDATE Categories SET CategoryName = ?, Description = ?, Status = ?, ImageURL = ? "
                + "WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getStatus());
            ps.setString(4, c.getImgURL());
            ps.setInt(5, c.getCategoryID());
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteCategory(int id) {
        String sql = "DELETE FROM Categories WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateStatus(int categoryID, int newStatus) {
        String sql = "UPDATE Categories SET Status = ? WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, categoryID);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CategoryAdminDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateCategoryQuantities() { //su dung trong cateAdmin
        String sql = """
        UPDATE Categories
        SET Quantity = (
            SELECT COUNT(*)
            FROM Products p
            JOIN Imports i ON p.ImportID = i.ImportID
            WHERE i.CategoryID = Categories.CategoryID
        );
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategoryInventory() { //su dung trong cateAdmin
        String sql = """
        UPDATE Categories
            SET inventory = (
                SELECT COUNT(*)
                FROM Products p
                JOIN Imports i ON p.ImportID = i.ImportID
                WHERE i.CategoryID = Categories.CategoryID AND p.Status = 1
            )""";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategoryStatusIfInventoryZero() { //su dung trong cateAdmin
        String sql = "UPDATE Categories SET Status = 0 WHERE inventory = 0 AND Status IN (1, 2)";

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategoryQueue() {
        String sql = """
       UPDATE Categories
        SET Queue = 0
        WHERE Inventory > Queue;
    """;

        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCategoryNameExists(String categoryName) {
        String sql = "SELECT 1 FROM Categories WHERE CategoryName = ?";
        try (Connection conn = new DBAdminContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("isCategoryNameExists Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CategoryAdminDAO dao = new CategoryAdminDAO();
        int id = 1;
        List<Categories> c = dao.getAllCategoriesByBrandComID(1);

        System.out.printf("%-5s %-20s %-20s %-20s %-10s%n",
                "ID", "Brand Name", "Component Name", "Category Name", "BrandComID", "inventory");

        for (Categories a : c) {
            System.out.println(a.getBrandName() + a.getInventory() + a.getCategoryName());
        }

    }
}

// Tùy chọn: bạn có thể thêm phương thức updateAllCategoryQuantities nếu bảng Products vẫn còn liên kết theo CategoryID

