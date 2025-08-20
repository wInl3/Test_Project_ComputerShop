/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dalAdmin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import models.Brands;
import models.BuildPCAdmin;
import models.BuildPCView;
import models.Categories;
import models.Components;
import models.WarrantyDetails;

/**
 *
 * @author PC
 */
public class BuildPCAdminDAO extends DBAdminContext {

    public List<Components> getALLComponent() {
        List<Components> list = new ArrayList<>();
        String sql = "SELECT * FROM Components";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Components c = new Components(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentName"),
                        rs.getInt("Quantity"),
                        rs.getInt("Status")
                );
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BuildPCView> getBuildPCSummaryView() {
        List<BuildPCView> list = new ArrayList<>();
        String sql = """
            SELECT 
                bp.BuildPCID,
                MAX(CASE WHEN bc.ComponentID = 2 THEN c.CategoryName END) AS MainBoard,
                MAX(CASE WHEN bc.ComponentID = 3 THEN c.CategoryName END) AS CPU,
                MAX(CASE WHEN bc.ComponentID = 4 THEN c.CategoryName END) AS GPU,
                MAX(CASE WHEN bc.ComponentID = 5 THEN c.CategoryName END) AS RAM,
                MAX(CASE WHEN bc.ComponentID = 6 THEN c.CategoryName END) AS SSD,
                MAX(CASE WHEN bc.ComponentID = 7 THEN c.CategoryName END) AS PCCase,
                SUM(bpi.Price) AS TotalPrice, 
                MAX(bp.Status) AS Status,
                u.UserID,
                u.FullName,
                r.RoleName
            FROM Build_PC bp
            JOIN Build_PC_Items bpi ON bp.BuildPCID = bpi.BuildPCID
            JOIN Categories c ON bpi.CategoryID = c.CategoryID
            JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
            JOIN Users u ON bp.UserID = u.UserID
            JOIN Roles r ON u.RoleID = r.RoleID
            WHERE bc.ComponentID BETWEEN 2 AND 7
            GROUP BY bp.BuildPCID, u.UserID, u.FullName, r.RoleName
            ORDER BY bp.BuildPCID
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BuildPCView b = new BuildPCView();
                b.setBuildPCID(rs.getInt("BuildPCID"));
                b.setMainBoard(rs.getString("MainBoard"));
                b.setCpu(rs.getString("CPU"));
                b.setGpu(rs.getString("GPU"));
                b.setRam(rs.getString("RAM"));
                b.setSsd(rs.getString("SSD"));
                b.setPcCase(rs.getString("PCCase"));
                b.setPrice(rs.getInt("TotalPrice"));
                b.setStatus(rs.getInt("Status"));
                b.setUserID(rs.getInt("UserID"));
                b.setFullName(rs.getString("FullName"));
                b.setRole(rs.getString("RoleName"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Categories extractCategory(ResultSet rs) throws SQLException {
        Categories category = new Categories(
                rs.getInt("CategoryID"),
                rs.getString("CategoryName"),
                rs.getInt("ComponentID"),
                rs.getInt("BrandID"),
                rs.getString("BrandName"),
                rs.getInt("Quantity"),
                rs.getInt("Price"),
                rs.getString("Description"),
                rs.getInt("Status")
        );
        try {
            category.setComponentName(rs.getString("ComponentName"));
        } catch (SQLException ignored) {
        }
        try {
            category.setImgURL(rs.getString("ImageURL"));
        } catch (SQLException ignored) {
        }
        return category;
    }

    private List<Object> buildFilter(
            StringBuilder sql,
            String componentName, String brandName,
            Integer minPrice, Integer maxPrice,
            String keyword) {

        List<Object> params = new ArrayList<>();
        if (componentName != null && !componentName.isEmpty()) {
            sql.append(" AND comp.ComponentName = ? ");
            params.add(componentName);
        }
        if (brandName != null && !brandName.isEmpty()) {
            sql.append(" AND b.BrandName = ? ");
            params.add(brandName);
        }
        if (minPrice != null) {
            sql.append(" AND c.Price >= ? ");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND c.Price <= ? ");
            params.add(maxPrice);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND LOWER(c.CategoryName) LIKE ? ");
            params.add("%" + keyword.toLowerCase() + "%");
        }
        return params;
    }

    // BuilDPC_ListCate
    public List<Categories> getCategoriesFiltered2(int componentID, String brand, String keyword, int minPrice, int maxPrice, int start, int size) {
        List<Categories> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT 
          c.*, 
          bc.ComponentID, 
          bc.BrandID, 
          b.BrandName, 
          comp.ComponentName,
          c.ImageURL
        FROM Categories c
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components comp ON bc.ComponentID = comp.ComponentID
        WHERE bc.ComponentID = ?
    """);

        List<Object> params = new ArrayList<>();
        params.add(componentID);

        if (brand != null && !brand.trim().isEmpty()) {
            sql.append(" AND b.BrandName = ?");
            params.add(brand.trim());
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND LOWER(c.CategoryName) LIKE ?");
            params.add("%" + keyword.trim().toLowerCase() + "%");
        }
        if (minPrice >= 0) {
            sql.append(" AND c.Price >= ?");
            params.add(minPrice);
        }
        if (maxPrice < Integer.MAX_VALUE) {
            sql.append(" AND c.Price <= ?");
            params.add(maxPrice);
        }

        sql.append(" ORDER BY c.CategoryID LIMIT ?, ?");
        params.add(start);
        params.add(size);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categories c = extractCategory(rs);  // dùng hàm chung
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countFilteredByComponent(int componentID, String brand, int minPrice, int maxPrice, String keyword) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*)
        FROM Categories c
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        WHERE bc.ComponentID = ? AND c.Status IN (1, 2)
    """);
        List<Object> params = new ArrayList<>();
        params.add(componentID);

        if (brand != null && !brand.isEmpty()) {
            sql.append(" AND b.BrandName = ?");
            params.add(brand);
        }
        if (minPrice >= 0) {
            sql.append(" AND c.Price >= ?");
            params.add(minPrice);
        }
        if (maxPrice < Integer.MAX_VALUE) {
            sql.append(" AND c.Price <= ?");
            params.add(maxPrice);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND LOWER(c.CategoryName) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public List<Brands> getBrandsByComponent(int componentID) {
        List<Brands> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT b.BrandID, b.BrandName, b.Quantity, b.Status
        FROM BrandComs bc
        JOIN Brands b ON bc.BrandID = b.BrandID
        WHERE bc.ComponentID = ?
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, componentID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Brands(
                            rs.getInt("BrandID"),
                            rs.getString("BrandName"),
                            rs.getInt("Quantity"),
                            rs.getInt("Status")
                    ));
                }
            }
        } catch (SQLException e) {

        }
        return list;
    }

    private int getWarrantyDetailIDByCategoryID(int categoryID) {
        String sql = """
    SELECT TOP 1 wd.WarrantyDetailID
    FROM Categories c
    JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
    JOIN WarrantyDetails wd ON bc.BrandComID = wd.BrandComID
    WHERE c.CategoryID = ?
      AND wd.Status = 1
    ORDER BY wd.Price ASC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("WarrantyDetailID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 3;  // Trường hợp không có thì mặc định bảo hành ID = 3
    }

    public boolean insertBuildPC(List<Integer> categoryIDs, List<Integer> warrantyIDs, int userID) {
        if (categoryIDs == null || warrantyIDs == null || categoryIDs.size() != 6 || warrantyIDs.size() != 6) {
            return false;
        }

        String insertBuildPCSQL = "INSERT INTO Build_PC (Price, Status, UserID) VALUES (?, 1, ?)";
        String insertItemSQL = """
        INSERT INTO Build_PC_Items (BuildPCID, CategoryID, Price, WarrantyDetailID, Status)
        VALUES (?, ?, ?, ?, 1)
    """;

        String getProductSQL = "SELECT Price FROM Categories WHERE CategoryID = ?";
        String getWarrantySQL = """
        SELECT Price, Status FROM WarrantyDetails 
        WHERE WarrantyDetailID = ? AND BrandComID = (
            SELECT BrandComID FROM Categories WHERE CategoryID = ?
        )
    """;

        try {
            connection.setAutoCommit(false);

            int totalPrice = 0;
            List<Object[]> itemData = new ArrayList<>();

            PreparedStatement psProduct = connection.prepareStatement(getProductSQL);
            PreparedStatement psWarranty = connection.prepareStatement(getWarrantySQL);

            for (int i = 0; i < 6; i++) {
                int categoryID = categoryIDs.get(i);
                int warrantyID = warrantyIDs.get(i);

                int productPrice = 0;
                int warrantyPrice = 0;
                Integer finalWarrantyID = null;

                psProduct.setInt(1, categoryID);
                try (ResultSet rs = psProduct.executeQuery()) {
                    if (rs.next()) {
                        productPrice = rs.getInt("Price");
                    } else {
                        connection.rollback();
                        return false;
                    }
                }

                if (warrantyID > 0) {
                    psWarranty.setInt(1, warrantyID);
                    psWarranty.setInt(2, categoryID);
                    try (ResultSet rs = psWarranty.executeQuery()) {
                        if (rs.next() && rs.getInt("Status") == 1) {
                            warrantyPrice = rs.getInt("Price");
                            finalWarrantyID = warrantyID;
                        }
                    }
                }

                int itemTotal = productPrice + warrantyPrice;
                totalPrice += itemTotal;
                itemData.add(new Object[]{categoryID, itemTotal, finalWarrantyID});
            }

            PreparedStatement psInsertPC = connection.prepareStatement(insertBuildPCSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            psInsertPC.setInt(1, totalPrice);
            psInsertPC.setInt(2, userID);
            psInsertPC.executeUpdate();

            ResultSet rsPC = psInsertPC.getGeneratedKeys();
            if (!rsPC.next()) {
                connection.rollback();
                return false;
            }
            int buildPCID = rsPC.getInt(1);

            PreparedStatement psInsertItem = connection.prepareStatement(insertItemSQL);
            for (Object[] item : itemData) {
                psInsertItem.setInt(1, buildPCID);
                psInsertItem.setInt(2, (int) item[0]);
                psInsertItem.setInt(3, (int) item[1]);

                if (item[2] != null) {
                    psInsertItem.setInt(4, (int) item[2]);
                } else {
                    psInsertItem.setNull(4, java.sql.Types.INTEGER);
                }
                psInsertItem.addBatch();
            }
            psInsertItem.executeBatch();

            connection.commit();
            return true;

        } catch (Exception e) {
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

    private int getPriceByCategoryID(int categoryID) {
        String sql = "SELECT Price FROM Categories WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateBuildPC(int buildPCID, List<Integer> categoryIDs, List<Integer> warrantyIDs, int status, String role) {
        if (categoryIDs == null || warrantyIDs == null || categoryIDs.size() != 6 || warrantyIDs.size() != 6) {
            System.out.println("Danh sách linh kiện hoặc bảo hành không hợp lệ.");
            return false;
        }

        // Kiểm tra status theo role
        if ("Admin".equalsIgnoreCase(role)) {
            if (status < 1 || status > 2) {
                System.out.println("Status không hợp lệ cho Admin: " + status);
                return false;
            }
        } else {
            if (status < 0 || status > 10) {
                System.out.println("Status không hợp lệ cho Customer: " + status);
                return false;
            }
        }

        String deleteItemsSQL = "DELETE FROM Build_PC_Items WHERE BuildPCID = ?";
        String updatePCSQL = "UPDATE Build_PC SET Price = ?, Status = ? WHERE BuildPCID = ?";
        String insertItemSQL = """
        INSERT INTO Build_PC_Items (BuildPCID, CategoryID, Price, WarrantyDetailID, Status)
        VALUES (?, ?, ?, ?, 1)
    """;

        String getProductSQL = "SELECT Price FROM Categories WHERE CategoryID = ?";
        String getWarrantySQL = """
        SELECT Price, Status FROM WarrantyDetails 
        WHERE WarrantyDetailID = ? AND BrandComID = (
            SELECT BrandComID FROM Categories WHERE CategoryID = ?
        )
    """;

        try {
            connection.setAutoCommit(false);

            int totalPrice = 0;
            List<Object[]> itemData = new ArrayList<>();

            PreparedStatement psProduct = connection.prepareStatement(getProductSQL);
            PreparedStatement psWarranty = connection.prepareStatement(getWarrantySQL);

            for (int i = 0; i < 6; i++) {
                int categoryID = categoryIDs.get(i);
                int warrantyID = warrantyIDs.get(i);

                int productPrice = 0;
                int warrantyPrice = 0;
                Integer finalWarrantyID = null;

                // Lấy giá sản phẩm
                psProduct.setInt(1, categoryID);
                try (ResultSet rs = psProduct.executeQuery()) {
                    if (rs.next()) {
                        productPrice = rs.getInt("Price");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm với CategoryID: " + categoryID);
                        connection.rollback();
                        return false;
                    }
                }

                // Lấy thông tin bảo hành nếu có
                if (warrantyID > 0) {
                    psWarranty.setInt(1, warrantyID);
                    psWarranty.setInt(2, categoryID);
                    try (ResultSet rs = psWarranty.executeQuery()) {
                        if (rs.next()) {
                            if (rs.getInt("Status") == 1) {
                                warrantyPrice = rs.getInt("Price");
                                finalWarrantyID = warrantyID;
                            } else {
                                System.out.println("Bảo hành không hoạt động. WarrantyID: " + warrantyID);
                            }
                        } else {
                            System.out.println("Không tìm thấy bảo hành. WarrantyID: " + warrantyID);
                        }
                    }
                }

                int itemTotal = productPrice + warrantyPrice;
                totalPrice += itemTotal;
                itemData.add(new Object[]{categoryID, itemTotal, finalWarrantyID});
            }

            // Xóa hết item cũ
            try (PreparedStatement psDelete = connection.prepareStatement(deleteItemsSQL)) {
                psDelete.setInt(1, buildPCID);
                psDelete.executeUpdate();
            }

            // Update giá và trạng thái BuildPC
            try (PreparedStatement psUpdate = connection.prepareStatement(updatePCSQL)) {
                psUpdate.setInt(1, totalPrice);
                psUpdate.setInt(2, status);
                psUpdate.setInt(3, buildPCID);
                psUpdate.executeUpdate();
            }

            // Insert lại từng item
            PreparedStatement psInsertItem = connection.prepareStatement(insertItemSQL);
            for (Object[] item : itemData) {
                psInsertItem.setInt(1, buildPCID);
                psInsertItem.setInt(2, (int) item[0]);  // CategoryID
                psInsertItem.setInt(3, (int) item[1]);  // Tổng tiền sản phẩm + bảo hành

                if (item[2] != null) {
                    psInsertItem.setInt(4, (int) item[2]); // WarrantyDetailID
                } else {
                    psInsertItem.setNull(4, java.sql.Types.INTEGER);
                }
                psInsertItem.addBatch();
            }
            psInsertItem.executeBatch();

            connection.commit();
            System.out.println("Cập nhật Build PC thành công. Tổng tiền: " + totalPrice);
            return true;

        } catch (Exception e) {
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

    public boolean deleteBuildPC(int buildPCID) {
        String deleteItemsSQL = "DELETE FROM Build_PC_Items WHERE BuildPCID = ?";
        String deletePCSQL = "DELETE FROM Build_PC WHERE BuildPCID = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps1 = connection.prepareStatement(deleteItemsSQL)) {
                ps1.setInt(1, buildPCID);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = connection.prepareStatement(deletePCSQL)) {
                ps2.setInt(1, buildPCID);
                ps2.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
            }
        }

        return false;
    }

public List<BuildPCAdmin> getBuildPCItemsByBuildPCID(int buildPCID) {
    List<BuildPCAdmin> list = new ArrayList<>();
    String sql = """
        SELECT 
            com.ComponentID, 
            c.CategoryID, 
            c.CategoryName, 
            b.BrandName, 
            i.Price AS ItemPrice, 
            c.ImageURL,
            i.WarrantyDetailID,
            wd.Price AS WarrantyPrice,
            w.Description AS WarrantyDesc
        FROM Build_PC_Items i
        JOIN Categories c ON i.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components com ON bc.ComponentID = com.ComponentID
        LEFT JOIN WarrantyDetails wd ON i.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        WHERE i.BuildPCID = ?
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, buildPCID);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                Integer warrantyDetailID = rs.getObject("WarrantyDetailID") != null ? rs.getInt("WarrantyDetailID") : null;
                Integer warrantyPrice = rs.getObject("WarrantyPrice") != null ? rs.getInt("WarrantyPrice") : null;
                String warrantyDesc = rs.getString("WarrantyDesc");
                if (warrantyDesc == null) warrantyDesc = "";

                BuildPCAdmin item = new BuildPCAdmin(
                    rs.getInt("CategoryID"),
                    rs.getString("CategoryName"),
                    rs.getInt("ComponentID"),
                    warrantyPrice,
                    warrantyDesc,
                    warrantyDetailID,
                    rs.getInt("ItemPrice"),
                    rs.getString("BrandName"),
                    rs.getString("ImageURL")
                );

                list.add(item);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    public boolean isDuplicateBuildPC(List<Integer> categoryIDs, int excludeBuildPCID) {
        if (categoryIDs == null || categoryIDs.size() != 6) {
            return true;
        }
        List<Integer> sortedIDs = new ArrayList<>(categoryIDs);
        sortedIDs.sort(Integer::compareTo);

        String sql = """
        SELECT bpi.BuildPCID
        FROM Build_PC_Items bpi
        JOIN BuildPC bp ON bpi.BuildPCID = bp.BuildPCID
        WHERE bp.Role = 'Admin' AND bpi.CategoryID IN (?, ?, ?, ?, ?, ?)
        GROUP BY bpi.BuildPCID
        HAVING COUNT(*) = 6 AND
               SUM(CASE WHEN bpi.CategoryID IN (?, ?, ?, ?, ?, ?) THEN 1 ELSE 0 END) = 6
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < 6; i++) {
                ps.setInt(i + 1, sortedIDs.get(i));
            }
            for (int i = 0; i < 6; i++) {
                ps.setInt(i + 7, sortedIDs.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int foundID = rs.getInt("BuildPCID");
                if (excludeBuildPCID == -1 || foundID != excludeBuildPCID) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isDuplicateBuildPCForAdminExcludeID(List<Integer> categoryIDs, int excludedBuildPCID) {
        String sql = """
        SELECT COUNT(*) FROM BuildPC
        WHERE Role = 'Admin'
        AND BuildPCID != ?
        AND CateID1 = ? AND CateID2 = ? AND CateID3 = ?
        AND CateID4 = ? AND CateID5 = ? AND CateID6 = ?
    """;

        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, excludedBuildPCID);
            for (int i = 0; i < categoryIDs.size(); i++) {
                ps.setInt(i + 2, categoryIDs.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<WarrantyDetails> getWarrantyByCategory(int categoryID) {
        List<WarrantyDetails> list = new ArrayList<>();
        String sql = """
        SELECT 
            wd.WarrantyDetailID,
            wd.WarrantyID,
            w.WarrantyPeriod,
            w.Description,
            wd.BrandComID,
            b.BrandName,
            bc.ComponentID,
            comp.ComponentName,
            wd.Price,
            wd.Status
        FROM WarrantyDetails wd
        JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN BrandComs bc ON wd.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components comp ON bc.ComponentID = comp.ComponentID
        JOIN Categories c ON c.BrandComID = bc.BrandComID
        WHERE c.CategoryID = ?
        ORDER BY wd.Price ASC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WarrantyDetails w = new WarrantyDetails();
                w.setWarrantyDetailID(rs.getInt("WarrantyDetailID"));
                w.setWarrantyID(rs.getInt("WarrantyID"));
                w.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                w.setDescription(rs.getString("Description"));
                w.setBrandComID(rs.getInt("BrandComID"));
                w.setBrandName(rs.getString("BrandName"));
                w.setComponentName(rs.getString("ComponentName"));
                w.setPrice(rs.getInt("Price"));
                w.setStatus(rs.getInt("Status"));
                list.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public String getRoleNameOfCreator(int buildPCID) {
        String sql = "SELECT r.RoleName FROM Build_PC b "
                + "JOIN Users u ON b.UserID = u.UserID "
                + "JOIN Roles r ON u.RoleID = r.RoleID "
                + "WHERE b.BuildPCID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, buildPCID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("RoleName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
public boolean isDuplicateWithAdminBuildPC(List<Integer> categoryIDs) {
    String getAdminBuildPCsSQL = """
        SELECT bp.BuildPCID
        FROM Build_PC bp
        JOIN Users u ON bp.UserID = u.UserID
        WHERE u.RoleID = 1
    """;

    String getItemSQL = """
        SELECT CategoryID
        FROM Build_PC_Items
        WHERE BuildPCID = ?
        ORDER BY CategoryID
    """;

    try (
        PreparedStatement psBuild = connection.prepareStatement(getAdminBuildPCsSQL);
        ResultSet rsBuild = psBuild.executeQuery()
    ) {
        List<Integer> inputSorted = new ArrayList<>(categoryIDs);
        Collections.sort(inputSorted);

        while (rsBuild.next()) {
            int buildPCID = rsBuild.getInt("BuildPCID");

            try (
                PreparedStatement psItems = connection.prepareStatement(getItemSQL)
            ) {
                psItems.setInt(1, buildPCID);
                ResultSet rsItems = psItems.executeQuery();

                List<Integer> existingSorted = new ArrayList<>();
                while (rsItems.next()) {
                    existingSorted.add(rsItems.getInt("CategoryID"));
                }

                Collections.sort(existingSorted);

                if (inputSorted.equals(existingSorted)) {
                    return true; // Trùng với 1 cấu hình của Admin
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
public boolean isDuplicateWithAdminBuildPCId(List<Integer> categoryIDs, int excludeBuildPCID) {
    String getAdminBuildPCsSQL = """
        SELECT bp.BuildPCID
        FROM Build_PC bp
        JOIN Users u ON bp.UserID = u.UserID
        WHERE u.RoleID = 1 AND bp.BuildPCID != ?
    """;

    String getItemSQL = """
        SELECT CategoryID
        FROM Build_PC_Items
        WHERE BuildPCID = ?
        ORDER BY CategoryID
    """;

    try (
        PreparedStatement psBuild = connection.prepareStatement(getAdminBuildPCsSQL)
    ) {
        psBuild.setInt(1, excludeBuildPCID);
        ResultSet rsBuild = psBuild.executeQuery();

        List<Integer> inputSorted = new ArrayList<>(categoryIDs);
        Collections.sort(inputSorted);

        while (rsBuild.next()) {
            int buildPCID = rsBuild.getInt("BuildPCID");

            try (PreparedStatement psItems = connection.prepareStatement(getItemSQL)) {
                psItems.setInt(1, buildPCID);
                ResultSet rsItems = psItems.executeQuery();

                List<Integer> existingSorted = new ArrayList<>();
                while (rsItems.next()) {
                    existingSorted.add(rsItems.getInt("CategoryID"));
                }

                Collections.sort(existingSorted);

                if (inputSorted.equals(existingSorted)) {
                    return true; // Trùng với 1 cấu hình của Admin khác
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

public List<Integer> getCategoryIDsByBuildPCID(int buildPCID) {
    List<Integer> categoryIDs = new ArrayList<>();
    String sql = "SELECT CategoryID FROM Build_PC_Items WHERE BuildPCID = ? ORDER BY CategoryID ASC";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, buildPCID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            categoryIDs.add(rs.getInt("CategoryID"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return categoryIDs;
}


}
