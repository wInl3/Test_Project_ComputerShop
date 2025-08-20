package controller;

import dal.FeedbackDAO;
import dal.OrderItemDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Feedback;
import models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.OrderItems;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    private FeedbackDAO dao;
    private static final Logger LOGGER = Logger.getLogger(FeedbackServlet.class.getName());

    @Override
    public void init() {
        dao = new FeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        try {
            if (action == null) {
                session.removeAttribute("error");
                int userId = ((User) session.getAttribute("user")).getUserId();
                int OrderItemID = Integer.parseInt(req.getParameter("orderItemID"));
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                OrderItems item = orderItemDAO.getOrderItemByID(OrderItemID);
                ArrayList<Feedback> allFeedback = dao.getFeedbackByUserId(userId, OrderItemID);
                req.setAttribute("item", item);
                req.setAttribute("feedbackList", allFeedback);
                RequestDispatcher rs = req.getRequestDispatcher("/ShopPages/Pages/feedback.jsp");
                rs.forward(req, res);
            } else {
                switch (action) {
                    case "delete":
                        handleDeleteFeedback(req, res);
                        break;
                    case "edit":
                        handleEditFeedback(req, res);
                        break;
                    default:
                        session.removeAttribute("error");
                        List<Feedback> allFeedback = dao.getAllFeedbacks();
                        req.setAttribute("feedbackList", allFeedback);
                        RequestDispatcher rs = req.getRequestDispatcher("/ShopPages/Pages/feedback.jsp");
                        rs.forward(req, res);
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doGet: " + e.getMessage(), e);
            session.setAttribute("error", "Failed to load feedback page: " + e.getMessage());
            req.setAttribute("feedbackList", null);
            session.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/ShopPages/Pages/feedback.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        try {
            if ("update".equals(action)) {
                handleUpdateFeedback(req, res);
            } else {
                User currentUser = (User) session.getAttribute("user");
                if (currentUser == null) {
                    session.setAttribute("error", "Please login to submit feedback");
                    res.sendRedirect(req.getContextPath() + "/Login");
                    return;
                }
                int userID = currentUser.getRole().getRoleID();
                int orderItemID = Integer.parseInt(req.getParameter("orderItemID"));
                String content = req.getParameter("content");
                int rate = Integer.parseInt(req.getParameter("rate"));

                if (content == null || content.trim().isEmpty() || content.length() > 500 || rate < 1 || rate > 5) {
                    session.setAttribute("error", "Invalid content or rating");
                    res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=1");
                    return;
                }

                Feedback fb = new Feedback(userID, content, orderItemID, rate);
                boolean success = dao.insertFeedback(fb);

                if (success) {
                    session.setAttribute("message", "Feedback submitted successfully");
                    res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=1");
                } else {
                    session.setAttribute("error", "Failed to save feedback");
                    res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=1");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doPost: " + e.getMessage(), e);
            session.setAttribute("error", "An error occurred while submitting feedback: " + e.getMessage());
            res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=1");
        }
    }

    private void handleDeleteFeedback(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        int feedbackId;
        int categoryId = 1; // default fallback
        try {
            feedbackId = Integer.parseInt(req.getParameter("id"));
            if (req.getParameter("categoryID") != null) {
                categoryId = Integer.parseInt(req.getParameter("categoryID"));
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid feedback ID");
            res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=" + categoryId);
            return;
        }

        Feedback feedback = dao.getFeedbackById(feedbackId);
        if (feedback == null) {
            session.setAttribute("error", "Feedback not found");
            res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=" + categoryId);
            return;
        }

        if (currentUser == null || (currentUser.getUserId() != feedback.getUserID() && currentUser.getRole().getRoleID() != 1)) {
            session.setAttribute("error", "You are not authorized to delete this feedback");
            res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=" + categoryId);
            return;
        }

        boolean success = dao.deleteFeedback(feedbackId);
        if (success) {
            session.setAttribute("message", "Feedback deleted successfully");
        } else {
            session.setAttribute("error", "Failed to delete feedback");
        }
        res.sendRedirect(req.getContextPath() + "/feedback?action=category&categoryID=" + categoryId);
    }

    private void handleEditFeedback(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        String categoryID = req.getParameter("categoryID");
        int feedbackId;
        try {
            feedbackId = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid feedback ID");
            res.sendRedirect(req.getContextPath() + "/ShopPages/Pages/feedback.jsp");
            return;
        }

        Feedback feedback = dao.getFeedbackById(feedbackId);
        if (feedback == null) {
            session.setAttribute("error", "Feedback not found");
            res.sendRedirect(req.getContextPath() + "/ShopPages/Pages/feedback.jsp");
            return;
        }

        if (currentUser == null || (currentUser.getUserId() != feedback.getUserID() && currentUser.getRole().getRoleID() != 1)) {
            session.setAttribute("error", "You are not authorized to edit this feedback");
            res.sendRedirect(req.getContextPath() + "/ShopPages/Pages/feedback.jsp");
            return;
        }

        if (categoryID != null && !categoryID.isEmpty()) {
            req.setAttribute("categoryID", categoryID);
        }
        req.setAttribute("feedback", feedback);
        req.getRequestDispatcher("/ShopPages/Pages/edit-feedback.jsp").forward(req, res);
    }

    private void handleUpdateFeedback(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            session.setAttribute("error", "Please login to edit feedback");
            res.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        int feedbackId;
        String categoryId = req.getParameter("categoryID"); // default fallback

        try {
            feedbackId = Integer.parseInt(req.getParameter("feedbackID"));
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid feedback ID");
            res.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        Feedback feedback = dao.getFeedbackById(feedbackId);
        if (feedback == null) {
            session.setAttribute("error", "Feedback not found");
            res.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        if (currentUser.getUserId() != feedback.getUserID() && currentUser.getRole().getRoleID() != 1) {
            session.setAttribute("error", "You are not authorized to edit this feedback");
            res.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        String content = req.getParameter("content");
        int rate;
        int orderItemID;
        try {
            rate = Integer.parseInt(req.getParameter("rate"));
            orderItemID = Integer.parseInt(req.getParameter("orderItemID"));
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid rating or order item ID");
            req.setAttribute("feedback", feedback);
            req.getRequestDispatcher("/ShopPages/Pages/edit-feedback.jsp").forward(req, res);
            return;
        }

        if (content == null || content.trim().isEmpty() || rate < 1 || rate > 5) {
            session.setAttribute("error", "Invalid content or rating");
            req.setAttribute("feedback", feedback);
            req.getRequestDispatcher("/ShopPages/Pages/edit-feedback.jsp").forward(req, res);
            return;
        }

        feedback.setContent(content);
        feedback.setRate(rate);
        feedback.setOrderItemID(orderItemID);
        boolean success = dao.updateFeedback(feedback);

        if (success) {
            session.setAttribute("message", "Feedback updated successfully");
            if (categoryId != null && !categoryId.isEmpty()) {
                res.sendRedirect(req.getContextPath() + "/CategoriesController?service=detail&categoryID=" + categoryId);
            } else {
                res.sendRedirect(req.getContextPath() + "/Order?orderID=" + feedback.getOrderItems().getOrderID());
            }
        } else {
            session.setAttribute("error", "Failed to update feedback");
            req.setAttribute("feedback", feedback);
            req.getRequestDispatcher("/ShopPages/Pages/edit-feedback.jsp").forward(req, res);
        }
    }
}
