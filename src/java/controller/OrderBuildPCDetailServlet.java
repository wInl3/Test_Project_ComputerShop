package controller;

import dal.BuildPCOrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.BuildPCAdmin;
import models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderBuildPCDetailServlet", urlPatterns = {"/OrderDetailBuildPC"})
public class OrderBuildPCDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String orderIDParam = request.getParameter("orderID");

        if (orderIDParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing orderID.");
            return;
        }

        try {
            int orderID = Integer.parseInt(orderIDParam);
            BuildPCOrderDAO dao = new BuildPCOrderDAO();

            // Lấy danh sách các OrderBuildPCItemID theo OrderID
            List<Integer> itemIDs = dao.getOrderBuildPCItemIDsByOrderID(orderID);

            // Danh sách chứa toàn bộ thông tin từng item
            List<BuildPCAdmin> allDetails = new ArrayList<>();

            for (int itemID : itemIDs) {
                List<BuildPCAdmin> itemDetails = dao.getBuildPCOrderByBuildPCItemID(itemID);

                if (itemDetails != null && !itemDetails.isEmpty()) {
                    // Kiểm tra quyền truy cập
                    if (itemDetails.get(0).getUserId() != user.getUserId()) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to view this order.");
                        return;
                    }

                    allDetails.addAll(itemDetails);
                }
            }

  
            request.setAttribute("orderDetail", allDetails);
            request.getRequestDispatcher("/ShopPages/Pages/OrderDetailBuildPC.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid orderID format.");
        }
    }
}
