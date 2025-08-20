package controllerAdmin;

import dal.Blog_CateDAO;
import dal.CategoriesDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Categories;
import models.Post;
import models.SaleEvents;
import models.User;

@WebServlet(name = "AddSaleEvents", urlPatterns = {"/addsale"})
public class AddSaleEvents extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoriesDAO cdao = new CategoriesDAO();
        Blog_CateDAO dao = new Blog_CateDAO();

        List<Categories> categoryList = cdao.getAllCategories();
        List<Post> postList = dao.getAllPost();

        request.setAttribute("categoryList", categoryList);
        request.setAttribute("postList", postList);

        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertSaleEvents.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String cateIdStr = request.getParameter("categoryID");
            String postIdStr = request.getParameter("post_id");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String discountStr = request.getParameter("discountPercent");

            String imgURL = request.getParameter("imgURL");
            String brandName = request.getParameter("brandName");
            String originalPriceStr = request.getParameter("originalPrice");

            // Validate input
            if (cateIdStr == null || postIdStr == null || startDateStr == null || endDateStr == null
                    || discountStr == null || originalPriceStr == null
                    || cateIdStr.isEmpty() || postIdStr.isEmpty() || startDateStr.isEmpty()
                    || endDateStr.isEmpty() || discountStr.isEmpty() || originalPriceStr.isEmpty()) {

                request.setAttribute("error", "Please fill in all fields.");
                doGet(request, response);
                return;
            }

            int categoryID = Integer.parseInt(cateIdStr);
            int postID = Integer.parseInt(postIdStr);
            double discount = Double.parseDouble(discountStr);
            double originalPrice = Double.parseDouble(originalPriceStr);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            if (startDate.after(endDate)) {
                request.setAttribute("error", "Start date must be before end date!");
                doGet(request, response);
                return;
            }

            double discountedPrice = originalPrice * (1 - discount / 100.0);

            HttpSession session = request.getSession();
            User staff = (User) session.getAttribute("user");
            int createdBy = (staff != null) ? staff.getUserId() : 1;

            SaleEvents event = new SaleEvents();
            event.setCategoryID(categoryID);
            event.setPost_id(postID);
            event.setStartDate(startDate);
            event.setEndDate(endDate);
            event.setDiscountPercent(discount);
            event.setStatus(2); // Đang chờ duyệt
            event.setCreatedBy(createdBy);
            event.setApprovedBy(null);
            event.setImgURL(imgURL);
            event.setBrandName(brandName);
            event.setOriginalPrice(originalPrice);
            event.setDiscountedPrice(discountedPrice);

            Blog_CateDAO dao = new Blog_CateDAO();
            dao.addSaleEvent(event);

            response.sendRedirect("saleevents");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System Error: " + e.getMessage());
            doGet(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles adding new Sale Events";
    }
}
