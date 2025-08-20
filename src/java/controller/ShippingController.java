package controller;

import dal.ShippingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Shipping;
import models.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShippingController", urlPatterns = {"/ShippingController"})
public class ShippingController extends HttpServlet {

    private final ShippingDAO shippingDAO = new ShippingDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            session.setAttribute("redirectAfterLogin", "ShippingController");
            response.sendRedirect("ShopPages/Pages/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userID = user.getRole().getRoleID();
        int roleID = user.getRole().getRoleID();
        String service = request.getParameter("service") != null ? request.getParameter("service") : "list";

//        switch (service) {
//            case "update" ->
//                handleUpdate(request, response, userID, roleID);
//            default ->
//                handleList(request, response, userID, roleID);
//        }
    }

//    private void handleList(HttpServletRequest request, HttpServletResponse response, int userID, int roleID)
//            throws ServletException, IOException {
//        List<Shipping> shipments;
//        if (roleID == 3) { // Khách hàng
//            shipments = shippingDAO.getCustomerShipments(userID);
//            request.setAttribute("shipments", shipments);
//            request.getRequestDispatcher("/ShopPages/Pages/shipping.jsp").forward(request, response);
//        } else if (roleID == 2) { // Shipper
//            shipments = shippingDAO.getShipperAssignments(userID);
//            request.setAttribute("shipments", shipments);
//            request.getRequestDispatcher("/ShopPages/Pages/shipper_dashboard.jsp").forward(request, response);
//        } else {
//            response.sendRedirect("/ShopPages/Pages/error.jsp");
//        }
//    }

//    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, int userID, int roleID)
//            throws ServletException, IOException {
//        if (roleID == 2) { // Chỉ shipper được cập nhật
//            int shippingID = Integer.parseInt(request.getParameter("shippingID"));
//            String status = request.getParameter("status");
//            String feedback = request.getParameter("feedback");
//            shippingDAO.updateShippingStatus(shippingID, status, feedback);
//            response.sendRedirect("ShippingController?service=list");
//        } else {
//            response.sendRedirect("/ShopPages/Pages/error.jsp");
//        }
//    }

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
