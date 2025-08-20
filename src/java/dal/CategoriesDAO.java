package dal;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import models.BrandByComponentName;
import models.Brands;
import models.BuildPCView;
import models.Categories;
import models.Components;
import models.WarrantyDetails;

public class CategoriesDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(CategoriesDAO.class.getName());

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

    private List<Object> buildFilter(StringBuilder sql, String componentName, String brandName,
            Integer minPrice, Integer maxPrice, String keyword) {
        List<Object> params = new ArrayList<>();
        if (componentName != null && !componentName.isEmpty()) {
            sql.append(" AND comp.ComponentName = ?");
            params.add(componentName);
        }
        if (brandName != null && !brandName.isEmpty()) {
            sql.append(" AND b.BrandName = ?");
            params.add(brandName);
        }
        if (minPrice != null) {
            sql.append(" AND c.Price >= ?");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND c.Price <= ?");
            params.add(maxPrice);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND LOWER(c.CategoryName) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }
        return params;
    }

    public List<Brands> getAllBrands() {
        String sql = "SELECT BrandID, BrandName, Quantity, Status FROM Brands";
        List<Brands> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Brands(
                        rs.getInt("BrandID"),
                        rs.getString("BrandName"),
                        rs.getInt("Quantity"),
                        rs.getInt("Status")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    // 2. Lấy danh sách brand theo component (dùng bảng BrandComs)
    public List<BrandByComponentName> getBrandsGroupedByComponent() {
        String sql = """
                SELECT DISTINCT
                  bc.ComponentID,
                  comp.ComponentName,
                  b.BrandName
                FROM BrandComs bc
                JOIN Components comp ON bc.ComponentID = comp.ComponentID
                JOIN Brands b ON bc.BrandID = b.BrandID
                """;
        List<BrandByComponentName> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new BrandByComponentName(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentName"),
                        rs.getString("BrandName")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    // 3. Lấy toàn bộ Components
    public List<Components> getAllComponents() {
        String sql = "SELECT * FROM Components";
        List<Components> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Components(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentName"),
                        rs.getInt("Quantity"),
                        rs.getInt("Status")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    // 4. Lấy category theo ID (kèm thông tin brand & component)
     public List<Categories> getCategoryByID(int id) {
        String sql = """
                SELECT
                  c.*,
                  bc.ComponentID,
                  bc.BrandID,
                  b.BrandName,
                  comp.ComponentName
                FROM Categories c
                JOIN BrandComs bc ON c.BrandComID   = bc.BrandComID
                JOIN Brands b ON bc.BrandID = b.BrandID
                JOIN Components comp ON bc.ComponentID = comp.ComponentID
                WHERE c.CategoryID = ?
                """;
        List<Categories> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }
    // 5. Phân trang theo component
    public List<Categories> getCategoriesByComponent(int componentId, int start, int size) {
        String sql = """
                SELECT c.*, bc.ComponentID, bc.BrandID, b.BrandName, comp.ComponentName
                FROM Categories c
                JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
                JOIN Brands b ON bc.BrandID = b.BrandID
                JOIN Components comp ON bc.ComponentID = comp.ComponentID
                WHERE bc.ComponentID = ? AND c.Status IN (1, 2)
                ORDER BY c.CategoryID
                LIMIT ? OFFSET ?
                """;
        List<Categories> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, componentId);
            ps.setInt(2, size);
            ps.setInt(3, start);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    // 6. Phân trang tất cả categories
    public List<Categories> getAllCategoriesPaginated(int page, int size) {
        String sql = """
                SELECT c.*, bc.ComponentID, bc.BrandID, b.BrandName, comp.ComponentName
                FROM Categories c
                JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
                JOIN Brands b ON bc.BrandID = b.BrandID
                JOIN Components comp ON bc.ComponentID = comp.ComponentID
                ORDER BY c.CategoryID
                LIMIT ? OFFSET ?
                """;
        List<Categories> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    // 7. Đếm tổng số products theo component
    public int countTotalProducts(int componentId) {
        String sql = """
                SELECT COUNT(*)
                FROM Categories c
                JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
                WHERE bc.ComponentID = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, componentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // 8. Đếm tất cả categories
    public int countAllCategories() {
        String sql = "SELECT COUNT(*) FROM Categories";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // 9. Lọc nâng cao với phân trang
    public List<Categories> getCategoriesFiltered(String componentName, String brandName,
            Integer minPrice, Integer maxPrice,
            String keyword, int start, int size) {

        StringBuilder sql = new StringBuilder("""
                SELECT c.*, bc.ComponentID, bc.BrandID, b.BrandName, comp.ComponentName
                FROM Categories c
                JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
                JOIN Brands b ON bc.BrandID = b.BrandID
                JOIN Components comp ON bc.ComponentID = comp.ComponentID
               
                """);

        List<Object> params = buildFilter(sql, componentName, brandName, minPrice, maxPrice, keyword);

        sql.append(" ORDER BY c.CategoryID LIMIT ? OFFSET ?");
        params.add(size);
        params.add(start);

        List<Categories> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    // 10. Đếm kết quả lọc
    public int countFiltered(
            String componentName, String brandName,
            Integer minPrice, Integer maxPrice,
            String keyword) {

        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM Categories c
                JOIN BrandComs bc ON c.BrandComID= bc.BrandComID
                JOIN Brands b ON bc.BrandID = b.BrandID
                JOIN Components comp ON bc.ComponentID = comp.ComponentID
                WHERE 1=1
                """);
        List<Object> params = buildFilter(sql, componentName, brandName, minPrice, maxPrice, keyword);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
    }

    // BuilDPC_ListCate
    public List<Categories> getCategoriesFiltered2(int componentID, String brand, String keyword,
            int minPrice, int maxPrice, int start, int size) {

        StringBuilder sql = new StringBuilder("""
                SELECT c.*, bc.ComponentID, bc.BrandID, b.BrandName, comp.ComponentID
                FROM Categories c
                JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
                JOIN Brands b ON bc.BrandID = b.BrandID
                JOIN Components comp ON bc.ComponentID = comp.ComponentID
                WHERE bc.ComponentID = ?
                """);

        List<Object> params = new ArrayList<>();
        params.add(componentID);

        if (brand != null && !brand.isEmpty()) {
            sql.append(" AND b.BrandName = ?");
            params.add(brand);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND LOWER(c.CategoryName) LIKE ?");
            params.add("%" + keyword.toLowerCase() + "%");
        }
        if (minPrice >= 0) {
            sql.append(" AND c.Price >= ?");
            params.add(minPrice);
        }
        if (maxPrice < Integer.MAX_VALUE) {
            sql.append(" AND c.Price <= ?");
            params.add(maxPrice);
        }

        sql.append(" ORDER BY c.CategoryID LIMIT ? OFFSET ?");
        params.add(size);
        params.add(start);

        List<Categories> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractCategory(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
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
            LOGGER.log(Level.SEVERE, null, e);
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
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    public Map<Integer, List<WarrantyDetails>> getWarrantyMapForBuildPC(int buildPCID) {
        Map<Integer, List<WarrantyDetails>> map = new HashMap<>();

        String sql = """
        SELECT 
            c.CategoryID, 
            wd.WarrantyDetailID,
            wd.Price AS WarrantyPrice,
            wd.Status AS WarrantyStatus,
            wd.WarrantyID,
            w.Description AS WarrantyDesc
        FROM Build_PC_Items bi
        JOIN Categories c ON bi.CategoryID = c.CategoryID
        LEFT JOIN WarrantyDetails wd ON bi.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        WHERE bi.BuildPCID = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, buildPCID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int categoryID = rs.getInt("CategoryID");

                    // Lấy danh sách hiện có, nếu chưa có thì khởi tạo rỗng
                    List<WarrantyDetails> warranties = map.getOrDefault(categoryID, new ArrayList<>());

                    int warrantyDetailID = rs.getInt("WarrantyDetailID");

                    if (!rs.wasNull() && warrantyDetailID > 0) {
                        WarrantyDetails w = new WarrantyDetails();
                        w.setWarrantyDetailID(warrantyDetailID);
                        w.setWarrantyID(rs.getInt("WarrantyID"));
                        w.setPrice(rs.getInt("WarrantyPrice"));
                        w.setStatus(rs.getInt("WarrantyStatus"));
                        w.setDescription(rs.getString("WarrantyDesc"));
                        warranties.add(w);
                    }

                    map.put(categoryID, warranties); // Luôn put vào map, kể cả khi list rỗng
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<BuildPCView> getBuiltPCsForCustomer() {
        List<BuildPCView> list = new ArrayList<>();

        String sql = """
        SELECT 
            bp.BuildPCID,

            -- Tên linh kiện
            MAX(CASE WHEN bc.ComponentID = 2 THEN c.CategoryName END) AS MainBoard,
            MAX(CASE WHEN bc.ComponentID = 3 THEN c.CategoryName END) AS CPU,
            MAX(CASE WHEN bc.ComponentID = 4 THEN c.CategoryName END) AS GPU,
            MAX(CASE WHEN bc.ComponentID = 5 THEN c.CategoryName END) AS RAM,
            MAX(CASE WHEN bc.ComponentID = 6 THEN c.CategoryName END) AS SSD,
            MAX(CASE WHEN bc.ComponentID = 7 THEN c.CategoryName END) AS PCCase,

            -- ID linh kiện
            MAX(CASE WHEN bc.ComponentID = 2 THEN c.CategoryID END) AS MainBoardID,
            MAX(CASE WHEN bc.ComponentID = 3 THEN c.CategoryID END) AS CpuID,
            MAX(CASE WHEN bc.ComponentID = 4 THEN c.CategoryID END) AS GpuID,
            MAX(CASE WHEN bc.ComponentID = 5 THEN c.CategoryID END) AS RamID,
            MAX(CASE WHEN bc.ComponentID = 6 THEN c.CategoryID END) AS SsdID,
            MAX(CASE WHEN bc.ComponentID = 7 THEN c.CategoryID END) AS CaseID,

            -- Ảnh
            MAX(CASE WHEN bc.ComponentID = 2 THEN c.ImageURL END) AS ImgMain,
            MAX(CASE WHEN bc.ComponentID = 3 THEN c.ImageURL END) AS ImgCPU,
            MAX(CASE WHEN bc.ComponentID = 4 THEN c.ImageURL END) AS ImgGPU,
            MAX(CASE WHEN bc.ComponentID = 5 THEN c.ImageURL END) AS ImgRAM,
            MAX(CASE WHEN bc.ComponentID = 6 THEN c.ImageURL END) AS ImgSSD,
            MAX(CASE WHEN bc.ComponentID = 7 THEN c.ImageURL END) AS ImgCase,

            -- Thời gian bảo hành (tháng)
            MAX(CASE WHEN bc.ComponentID = 2 THEN w.WarrantyPeriod END) AS MainWarranty,
            MAX(CASE WHEN bc.ComponentID = 3 THEN w.WarrantyPeriod END) AS CpuWarranty,
            MAX(CASE WHEN bc.ComponentID = 4 THEN w.WarrantyPeriod END) AS GpuWarranty,
            MAX(CASE WHEN bc.ComponentID = 5 THEN w.WarrantyPeriod END) AS RamWarranty,
            MAX(CASE WHEN bc.ComponentID = 6 THEN w.WarrantyPeriod END) AS SsdWarranty,
            MAX(CASE WHEN bc.ComponentID = 7 THEN w.WarrantyPeriod END) AS CaseWarranty,

            -- Tổng giá
            SUM(bpi.Price) AS Price,
            MAX(bp.Status) AS Status,

            -- Thông tin người dùng
            u.UserID,
            u.FullName,
            r.RoleName

        FROM Build_PC bp
        JOIN Build_PC_Items bpi ON bp.BuildPCID = bpi.BuildPCID
        JOIN Categories c ON bpi.CategoryID = c.CategoryID
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        LEFT JOIN WarrantyDetails wd ON bpi.WarrantyDetailID = wd.WarrantyDetailID
        LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
        JOIN Users u ON bp.UserID = u.UserID
        JOIN Roles r ON u.RoleID = r.RoleID

        WHERE bc.ComponentID BETWEEN 2 AND 7
          AND r.RoleID = 1

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

                b.setMainBoardID(rs.getInt("MainBoardID"));
                b.setCpuID(rs.getInt("CpuID"));
                b.setGpuID(rs.getInt("GpuID"));
                b.setRamID(rs.getInt("RamID"));
                b.setSsdID(rs.getInt("SsdID"));
                b.setCaseID(rs.getInt("CaseID"));

                b.setImgMain(rs.getString("ImgMain"));
                b.setImgCPU(rs.getString("ImgCPU"));
                b.setImgGPU(rs.getString("ImgGPU"));
                b.setImgRAM(rs.getString("ImgRAM"));
                b.setImgSSD(rs.getString("ImgSSD"));
                b.setImgCase(rs.getString("ImgCase"));

                b.setMainWarranty(rs.getInt("MainWarranty"));
                b.setCpuWarranty(rs.getInt("CpuWarranty"));
                b.setGpuWarranty(rs.getInt("GpuWarranty"));
                b.setRamWarranty(rs.getInt("RamWarranty"));
                b.setSsdWarranty(rs.getInt("SsdWarranty"));
                b.setCaseWarranty(rs.getInt("CaseWarranty"));

                b.setPrice(rs.getInt("Price"));
                b.setStatus(rs.getInt("Status"));
                b.setUserID(rs.getInt("UserID"));
                b.setFullName(rs.getString("FullName"));
                b.setRole(rs.getString("RoleName"));

                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Categories> getCategoriesInBuildPC(int buildPCID) {
        List<Categories> list = new ArrayList<>();

        String sql = """
   SELECT 
        c.CategoryID, 
        c.CategoryName, 
        b.BrandName, 
        c.Price, 
        c.ImageURL, 
        comp.ComponentID,
        IFNULL(bi.WarrantyDetailID, 0) AS WarrantyDetailID,
        IFNULL(w.Description, 'Không có') AS WarrantyDesc,
        IFNULL(wd.Price, 0) AS WarrantyPrice
    FROM Build_PC_Items bi
    JOIN Categories c ON bi.CategoryID = c.CategoryID
    JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
    JOIN Brands b ON bc.BrandID = b.BrandID
    JOIN Components comp ON bc.ComponentID = comp.ComponentID
    LEFT JOIN WarrantyDetails wd ON bi.WarrantyDetailID = wd.WarrantyDetailID
    LEFT JOIN Warranties w ON wd.WarrantyID = w.WarrantyID
    WHERE bi.BuildPCID = ?
""";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, buildPCID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Categories c = new Categories();
                c.setCategoryID(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("CategoryName"));
                c.setBrandName(rs.getString("BrandName"));
                c.setPrice(rs.getInt("Price"));
                c.setImgURL(rs.getString("ImageURL"));
                c.setComponentID(rs.getInt("ComponentID"));

                int warrantyDetailID = rs.getInt("WarrantyDetailID");
                c.setWarrantyDetailID(warrantyDetailID);

                if (warrantyDetailID > 0) {
                    c.setWarrantyDesc(rs.getString("WarrantyDesc") != null ? rs.getString("WarrantyDesc") : "Không rõ");
                    c.setWarrantyPrice(rs.getInt("WarrantyPrice"));
                } else {
                    c.setWarrantyDesc("Không chọn");
                    c.setWarrantyPrice(0);
                }

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertBuildPCToCart(List<Integer> categoryIDs, List<Integer> warrantyIDs, int userID) {
        if (categoryIDs == null || warrantyIDs == null || categoryIDs.size() != 6 || warrantyIDs.size() != 6) {
            System.out.println("❌ Dữ liệu không hợp lệ, cần đủ 6 linh kiện và 6 bảo hành.");
            return false;
        }

        String insertCartSQL = """
        INSERT INTO Cart_Build_PC (UserID, Price, Status)
        VALUES (?, ?, 1)
    """;

        String insertItemSQL = """
        INSERT INTO Cart_Build_PC_Items (CartBuildPCID, CategoryID, Price, WarrantyDetailID, Status)
        VALUES (?, ?, ?, ?, 1)
    """;

        String getProductPriceSQL = "SELECT Price FROM Categories WHERE CategoryID = ?";

        String getWarrantySQL = """
        SELECT Price, Status FROM WarrantyDetails 
        WHERE WarrantyDetailID = ? AND BrandComID = (
            SELECT BrandComID FROM Categories WHERE CategoryID = ?
        )
    """;

        try {
            connection.setAutoCommit(false);

            int totalCartPrice = 0;
            List<Object[]> itemData = new ArrayList<>();

            PreparedStatement psProduct = connection.prepareStatement(getProductPriceSQL);
            PreparedStatement psWarranty = connection.prepareStatement(getWarrantySQL);

            for (int i = 0; i < categoryIDs.size(); i++) {
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
                        System.out.println("❌ Không tìm thấy sản phẩm với CategoryID: " + categoryID);
                        connection.rollback();
                        return false;
                    }
                }

                // Lấy giá bảo hành nếu có (warrantyID > 0)
                if (warrantyID > 0) {
                    psWarranty.setInt(1, warrantyID);
                    psWarranty.setInt(2, categoryID);

                    try (ResultSet rs = psWarranty.executeQuery()) {
                        if (rs.next()) {
                            int status = rs.getInt("Status");
                            if (status == 1) {
                                warrantyPrice = rs.getInt("Price");
                                finalWarrantyID = warrantyID;
                            } else {
                                System.out.println("⚠️ Bảo hành không hợp lệ hoặc đã ngừng bán, bỏ qua bảo hành.");
                            }
                        } else {
                            System.out.println("⚠️ Không tìm thấy thông tin bảo hành, bỏ qua bảo hành.");
                        }
                    }
                }

                int itemTotalPrice = productPrice + warrantyPrice;
                totalCartPrice += itemTotalPrice;

                // Lưu dữ liệu: CategoryID, giá cuối cùng, WarrantyID hoặc null
                itemData.add(new Object[]{categoryID, itemTotalPrice, finalWarrantyID});
            }

            // Insert giỏ hàng
            PreparedStatement psCart = connection.prepareStatement(insertCartSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            psCart.setInt(1, userID);
            psCart.setInt(2, totalCartPrice);
            psCart.executeUpdate();

            ResultSet rsCart = psCart.getGeneratedKeys();
            if (!rsCart.next()) {
                System.out.println("❌ Không lấy được CartBuildPCID.");
                connection.rollback();
                return false;
            }
            int cartBuildPCID = rsCart.getInt(1);

            // Insert từng item
            PreparedStatement psItem = connection.prepareStatement(insertItemSQL);
            for (Object[] item : itemData) {
                psItem.setInt(1, cartBuildPCID);
                psItem.setInt(2, (int) item[0]);
                psItem.setInt(3, (int) item[1]);

                if (item[2] != null) {
                    psItem.setInt(4, (int) item[2]);
                } else {
                    psItem.setNull(4, java.sql.Types.INTEGER);
                }
                psItem.addBatch();
            }
            psItem.executeBatch();

            connection.commit();
            System.out.println("✅ Thêm Build PC vào giỏ hàng thành công. Tổng tiền: " + totalCartPrice);
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

    public List<Categories> getAllCategories() {
        String sql = """
        SELECT c.*, bc.ComponentID, bc.BrandID, b.BrandName, comp.ComponentName
        FROM Categories c
        JOIN BrandComs bc ON c.BrandComID = bc.BrandComID
        JOIN Brands b ON bc.BrandID = b.BrandID
        JOIN Components comp ON bc.ComponentID = comp.ComponentID
    """;
        List<Categories> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractCategory(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return list;
    }

    /* public static void main(String[] args) {
    CategoriesDAO dao = new CategoriesDAO();

    // Danh sách CategoryID tương ứng với từng linh kiện
    List<Integer> categoryIDs = Arrays.asList(3, 10, 5, 42, 1, 2); 

    // Danh sách WarrantyID tương ứng: 
    // 4, 2, 27, 19 => Bảo hành hợp lệ
    // 0 => Không chọn bảo hành
    // 50 => Bảo hành hợp lệ
    List<Integer> warrantyIDs = Arrays.asList(5, 1, 29, 19, 23, 1); 

    int userID = 3;

    boolean success = dao.insertBuildPCToCart(categoryIDs, warrantyIDs, userID);

    System.out.println("Kết quả thêm giỏ hàng: " + success);
}
     */
    public static void main(String[] args) {
        CategoriesDAO dao = new CategoriesDAO();

        int buildPCID = 1; // ✅ Thay đổi ID này để test các cấu hình khác

        List<Categories> categories = dao.getCategoriesInBuildPC(buildPCID);

        if (categories == null || categories.isEmpty()) {
            System.out.println("⚠️ Không có linh kiện nào trong Build PC #" + buildPCID);
            return;
        }

        System.out.println("=== 🔧 Danh sách linh kiện cho Build PC ID = " + buildPCID + " ===");

        for (Categories c : categories) {
            System.out.println("🧩 " + c.getCategoryName()
                    + " | Hãng: " + c.getBrandName()
                    + " | Giá: " + c.getPrice() + "₫"
                    + " | Ảnh: " + c.getImgURL()
                    + " | ComponentID: " + c.getComponentID());

            System.out.println("🛡️  Bảo hành: " + c.getWarrantyDesc()
                    + " | Giá bảo hành: " + c.getWarrantyPrice() + "₫");

            System.out.println("----------------------------------");
        }
    }

}
