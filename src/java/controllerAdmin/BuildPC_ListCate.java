package controllerAdmin;

import dalAdmin.BuildPCAdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Brands;
import models.BuildPCAdmin;
import models.BuildPCView;
import models.Categories;
import models.WarrantyDetails;

public class BuildPC_ListCate extends HttpServlet {

    BuildPCAdminDAO dao = new BuildPCAdminDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String service = request.getParameter("service");
        boolean ajax = "true".equalsIgnoreCase(request.getParameter("ajax"));

        if ("list".equals(service)) {
            List<BuildPCView> buildPCList = dao.getBuildPCSummaryView();
            request.setAttribute("buildPCList", buildPCList);
            request.getRequestDispatcher("/AdminLTE/AdminPages/pages/tables/BuildPCList.jsp").forward(request, response);
            return;
        }

        if ("filter".equals(service)) {
            handleFilter(request, response, ajax);
            return;
        }

        if ("loadBuildPC".equals(service)) {
            handleLoadBuildPC(request, response);
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String service = request.getParameter("service");
        HttpSession session = request.getSession();
        models.User user = (models.User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/ComputerOnlineShop/Login");
            return;
        }

        if ("insert".equals(service)) {
            handleInsert(request, response, user);
            return;
        }

        if ("update".equals(service)) {
            handleUpdate(request, response, user);
            return;
        }

        processRequest(request, response);
    }

    private void handleInsert(HttpServletRequest request, HttpServletResponse response, models.User user) throws IOException {
        try {
            String idsRaw = request.getParameter("categoryIDs");
            String warrantiesRaw = request.getParameter("warrantyIDs");

            if (idsRaw == null || warrantiesRaw == null || idsRaw.isEmpty() || warrantiesRaw.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin bắt buộc.");
                return;
            }

            String[] parts = idsRaw.split(",");
            String[] warranties = warrantiesRaw.split(",");

            if (parts.length != 6 || warranties.length != 6) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Phải chọn đủ 6 linh kiện.");
                return;
            }

            List<Integer> categoryIDs = new ArrayList<>();
            List<Integer> warrantyIDs = new ArrayList<>();

            for (int i = 0; i < 6; i++) {
                categoryIDs.add(Integer.parseInt(parts[i].trim()));
                int wID = Integer.parseInt(warranties[i].trim());
                warrantyIDs.add(Math.max(wID, 0));
            }

            String role = user.getRole() != null ? user.getRole().getRoleName() : "Customer";
            int userID = user.getUserId();

            if ("Admin".equalsIgnoreCase(role)) {
                if (dao.isDuplicateWithAdminBuildPC(categoryIDs)) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, "Cấu hình Build PC này đã được Admin tạo trước đó.");
                    return;
                }
            }

            boolean success = dao.insertBuildPC(categoryIDs, warrantyIDs, userID);
            if (success) {
                response.getWriter().write("OK");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Thêm Build PC thất bại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống.");
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, models.User user) throws IOException {
        try {
            int buildPCID = safeParseInt(request.getParameter("buildPCID"), -1);
            String idsRaw = request.getParameter("categoryIDs");
            String warrantiesRaw = request.getParameter("warrantyIDs");
            int newStatus = safeParseInt(request.getParameter("status"), 0);

            if (buildPCID == -1 || idsRaw == null || warrantiesRaw == null || idsRaw.isEmpty() || warrantiesRaw.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu dữ liệu bắt buộc.");
                return;
            }

            String[] parts = idsRaw.split(",");
            String[] warranties = warrantiesRaw.split(",");

            if (parts.length != 6 || warranties.length != 6) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Phải chọn đủ 6 linh kiện.");
                return;
            }

            List<Integer> newCategoryIDs = new ArrayList<>();
            List<Integer> warrantyIDs = new ArrayList<>();

            for (int i = 0; i < 6; i++) {
                newCategoryIDs.add(Integer.parseInt(parts[i].trim()));
                int wID = Integer.parseInt(warranties[i].trim());
                warrantyIDs.add(Math.max(wID, 0));
            }

            String roleParam = request.getParameter("role");
            String role = (roleParam != null && !roleParam.isEmpty()) ? roleParam : (user.getRole() != null ? user.getRole().getRoleName() : "Customer");

            // ✅ Nếu là Admin → Kiểm tra thay đổi cấu hình
            if ("Admin".equalsIgnoreCase(role)) {
                List<Integer> oldCategoryIDs = dao.getCategoryIDsByBuildPCID(buildPCID);

                // Sắp xếp để đảm bảo so sánh chính xác
                List<Integer> sortedOld = new ArrayList<>(oldCategoryIDs);
                List<Integer> sortedNew = new ArrayList<>(newCategoryIDs);
                Collections.sort(sortedOld);
                Collections.sort(sortedNew);

                // Nếu cấu hình mới khác cấu hình cũ → kiểm tra trùng
                if (!sortedOld.equals(sortedNew)) {
                    if (dao.isDuplicateWithAdminBuildPCId(sortedNew, buildPCID)) {
                        response.sendError(HttpServletResponse.SC_CONFLICT, "Build PC đã tồn tại.");
                        return;
                    }
                }
            }

            boolean success = dao.updateBuildPC(buildPCID, newCategoryIDs, warrantyIDs, newStatus, role);
            if (success) {
                response.getWriter().write("Cập nhật thành công.");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cập nhật thất bại hoặc dữ liệu không hợp lệ.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống.");
        }
    }

    private void handleFilter(HttpServletRequest request, HttpServletResponse response, boolean ajax) throws IOException, ServletException {
        try {
            int componentID = safeParseInt(request.getParameter("componentID"), -1);
            String keyword = request.getParameter("keyword");
            String brand = request.getParameter("brand");
            int minPrice = safeParseInt(request.getParameter("minPrice"), -1);
            int maxPrice = safeParseInt(request.getParameter("maxPrice"), Integer.MAX_VALUE);
            int page = safeParseInt(request.getParameter("page"), 1);
            int pageSize = 6;

            if (componentID == -1) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "componentID không hợp lệ");
                return;
            }

            int total = dao.countFilteredByComponent(componentID, brand, minPrice, maxPrice, keyword);
            int totalPages = (int) Math.ceil((double) total / pageSize);
            int start = (page - 1) * pageSize;

            List<Categories> products = dao.getCategoriesFiltered2(componentID, brand, keyword, minPrice, maxPrice, start, pageSize);
            List<Brands> brands = dao.getBrandsByComponent(componentID);
            Map<Integer, List<WarrantyDetails>> warrantyMap = new HashMap<>();
            for (Categories c : products) {
                warrantyMap.put(c.getCategoryID(), dao.getWarrantyByCategory(c.getCategoryID()));
            }

            request.setAttribute("products", products);
            request.setAttribute("brands", brands);
            request.setAttribute("componentID", componentID);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("warrantyMap", warrantyMap);

            if (ajax) {
                request.getRequestDispatcher("/AdminLTE/AdminPages/pages/tables/BuildPC_Cate_List.jsp").forward(request, response);
            } else {
                response.sendRedirect("BuildPC.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý filter.");
        }
    }

    private void handleLoadBuildPC(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int buildPCID = safeParseInt(request.getParameter("buildPCID"), -1);
        if (buildPCID == -1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid BuildPCID");
            return;
        }

        try {
            List<BuildPCAdmin> items = dao.getBuildPCItemsByBuildPCID(buildPCID);
            String creatorRole = dao.getRoleNameOfCreator(buildPCID);

            response.setContentType("text/plain;charset=UTF-8");
            PrintWriter out = response.getWriter();

            //  đầu tiên là role của người tạo
            out.println("ROLE|" + creatorRole);

            //  các thành phần
            for (BuildPCAdmin item : items) {
                String line = item.getComponentId() + "|"
                        + item.getCateId() + "|"
                        + escape(item.getCateName()) + "|"
                        + escape(item.getBrandName()) + "|"
                        + item.getPrice() + "|"
                        + escape(item.getImgUrl()) + "|"
                        + escape(item.getWarrantyDesc()) + "|"
                        + item.getWarrantyPrice() + "|"
                        + item.getWarrantyDetailId();

                out.println(line);
            }

            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading BuildPC");
        }
    }

    private int safeParseInt(String value, int defaultVal) {
        if (value == null) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    @Override
    public String getServletInfo() {
        return "BuildPC List and Management Controller";
    }
}
