package controllerAdmin;

import dalAdmin.FeedbackAdminDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Feedback;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/userFeedback")
public class UserFeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        FeedbackAdminDAO dao = new FeedbackAdminDAO();
        List<Feedback> userFeedbacks = dao.getFeedbacksByUserId(userID);
        request.setAttribute("userFeedbacks", userFeedbacks);
        request.setAttribute("userID", userID);
        request.getRequestDispatcher("/AdminLTE/AdminPages/pages/tables/userFeedback.jsp").forward(request, response);
    }
} 