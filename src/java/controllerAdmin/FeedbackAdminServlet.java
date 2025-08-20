/*
 * Click nb://source://SystemFileSystemAdmin/Templates/LicensesAdmin/license-default.txt to change this license
 * Click nb://source://SystemFileSystemAdmin/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dalAdmin.FeedbackAdminDAO;
import models.Feedback;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class FeedbackAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FeedbackAdminDAO dao = new FeedbackAdminDAO();
        List<Feedback> feedbacks = dao.getAllFeedbacks();
        request.setAttribute("feedbacks", feedbacks);
        request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewFeedback.jsp").forward(request, response);
    }
}
