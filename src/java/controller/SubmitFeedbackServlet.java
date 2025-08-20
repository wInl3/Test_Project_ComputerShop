package controller;

import dal.FeedbackDAO;
import dal.OrderItemDAO;
import models.Feedback;
import models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.OrderItems;

@WebServlet(name = "SubmitFeedbackServlet", urlPatterns = {"/submitFeedback"})
public class SubmitFeedbackServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SubmitFeedbackServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String orderItemIdStr = request.getParameter("orderItemID");
        String rateStr = request.getParameter("rate");
        String content = request.getParameter("content");
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                session.setAttribute("error", "Please login to submit feedback");
                response.sendRedirect(request.getContextPath() + "/Login");
                return;
            }

            if (orderItemIdStr == null || rateStr == null || content == null) {
                session.setAttribute("error", "Missing required fields");
                response.sendRedirect(request.getContextPath() + "/feedback?orderItemID=" + orderItemIdStr);
                return;
            }

            int orderItemID, rate;
            try {
                orderItemID = Integer.parseInt(orderItemIdStr);
                rate = Integer.parseInt(rateStr);
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Invalid order item ID or rating");
                response.sendRedirect(request.getContextPath() + "/feedback?orderItemID=" + orderItemIdStr);
                return;
            }

            if (content.trim().isEmpty() || content.length() > 500) {
                session.setAttribute("error", "Content cannot be empty or exceed 500 characters");
                response.sendRedirect(request.getContextPath() + "/feedback?orderItemID=" + orderItemIdStr);
                return;
            }
            if (rate < 1 || rate > 5) {
                session.setAttribute("error", "Rating must be between 1 and 5");
                response.sendRedirect(request.getContextPath() + "/feedback?orderItemID=" + orderItemIdStr);
                return;
            }

            Feedback feedback = new Feedback(user.getUserId(), content, orderItemID, rate);
            FeedbackDAO dao = new FeedbackDAO();
            boolean success = dao.insertFeedback(feedback);
            OrderItems item = new OrderItemDAO().getOrderItemByID(orderItemID);

            if (success) {
                session.setAttribute("message", "Feedback submitted successfully");
            } else {
                session.setAttribute("error", "Failed to save feedback");
            }
            
            response.sendRedirect(request.getContextPath() + "/Order?orderID=" + item.getOrderID());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in SubmitFeedbackServlet: " + e.getMessage(), e);
            session.setAttribute("error", "An error occurred while submitting feedback: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/feedback?orderItemID=" + orderItemIdStr);
        }
    }
}
