package controller;

import dal.BuildPCOrderDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.BuildPCAdmin;
import models.User;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "OrderBuildPCHistoryServlet", urlPatterns = {"/OrderHistory2"})
public class OrderBuildPCHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");  // CHÚ Ý: "user" thay vì "account" cho đồng bộ

        if (user != null) {
            BuildPCOrderDAO dao = new BuildPCOrderDAO();

            String statusParam = request.getParameter("status");
            int status = -1;
            if (statusParam != null) {
                try {
                    status = Integer.parseInt(statusParam);
                } catch (NumberFormatException e) {
                    status = -1; // Nếu không hợp lệ, mặc định lấy tất cả
                }
            }

            ArrayList<BuildPCAdmin> orders;
            if (status == -1) {
                orders = dao.getBuildPCOrdersByCustomerID(user.getUserId());
            } else {
                orders = dao.getBuildPCOrdersByCustomerAndStatus(user.getUserId(), status);
            }

            request.setAttribute("orders", orders);
            request.setAttribute("selectedStatus", status);

            RequestDispatcher rd = request.getRequestDispatcher("ShopPages/Pages/OrderPcHistory.jsp");
            rd.forward(request, response);

        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Gửi POST về GET
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị lịch sử đơn hàng Build PC cho user";
    }
}
