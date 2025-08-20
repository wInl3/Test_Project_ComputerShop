package controller;

import dal.CategoriesDAO;
import dal.WarrantyDetailDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Categories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.WarrantyDetails;

@WebServlet(name = "CategoriesController", urlPatterns = {"/CategoriesController"})
public class CategoriesController extends HttpServlet {

    private static final int PAGE_SIZE = 9;
    private final CategoriesDAO dao = new CategoriesDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }

        // --- Chi tiết sản phẩm ---
        if ("detail".equals(service)) {
            int categoryId = parseIntegerOrDefault(request.getParameter("categoryID"), -1);
            ArrayList<Categories> detailList = new ArrayList<>(dao.getCategoryByID(categoryId));
            List<WarrantyDetails> warrantyDetailList = (new WarrantyDetailDAO()).getWarrantyDetailsByCategoryId(categoryId);
            if (!detailList.isEmpty()) {
                request.setAttribute("product", detailList.get(0));
                request.setAttribute("warrantyList", warrantyDetailList);
                // Lấy feedbackList
                dal.FeedbackDAO feedbackDAO = new dal.FeedbackDAO();
                ArrayList<models.Feedback> feedbackList = new ArrayList<>(feedbackDAO.getFeedbackByCategoryId(categoryId));
                request.setAttribute("feedbackList", feedbackList);
                request.setAttribute("categoryID", categoryId);
                request.getRequestDispatcher("/ShopPages/Pages/CategoriesDetails.jsp").forward(request, response);
            }
            return;
        }

        // --- Lọc và phân trang ---
        String componentName = request.getParameter("component");
        String brandName = request.getParameter("brand");
        String keyword = request.getParameter("keyword");
        Integer minPrice = parseInteger(request.getParameter("minPrice"));
        Integer maxPrice = parseInteger(request.getParameter("maxPrice"));
        int page = parseIntegerOrDefault(request.getParameter("page"), 1);
        int start = (page - 1) * PAGE_SIZE;

        List<Categories> data;
        int totalItems;
        int totalPages;

        if ("filter".equals(service)) {
            totalItems = dao.countFiltered(componentName, brandName, minPrice, maxPrice, keyword);
            totalPages = (int) Math.ceil(totalItems * 1.0 / PAGE_SIZE);
            data = dao.getCategoriesFiltered(componentName, brandName, minPrice, maxPrice, keyword, start, PAGE_SIZE);

            request.setAttribute("currentComponent", componentName);
            request.setAttribute("currentBrand", brandName);
            request.setAttribute("minPrice", request.getParameter("minPrice"));
            request.setAttribute("maxPrice", request.getParameter("maxPrice"));
            request.setAttribute("currentKeyword", keyword);
            request.setAttribute("currentService", "filter");
        } else {
            totalItems = dao.countAllCategories();
            totalPages = (int) Math.ceil(totalItems * 1.0 / PAGE_SIZE);
            data = dao.getAllCategoriesPaginated(page, PAGE_SIZE);
            request.setAttribute("currentService", "list");
        }

        request.setAttribute("data", data);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("Components", dao.getAllComponents());
        request.setAttribute("BrandWithComponent", dao.getBrandsGroupedByComponent());
        request.setAttribute("listBrand", dao.getAllBrands());
        request.getRequestDispatcher("/ShopPages/Pages/Categories.jsp").forward(request, response);
    }

    private Integer parseInteger(String str) {
        try {
            return (str != null && !str.isEmpty()) ? Integer.parseInt(str) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int parseIntegerOrDefault(String str, int def) {
        try {
            return (str != null) ? Integer.parseInt(str) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }
}
