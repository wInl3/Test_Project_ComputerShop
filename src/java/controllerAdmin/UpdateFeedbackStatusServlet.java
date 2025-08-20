package controllerAdmin;

import dalAdmin.FeedbackAdminDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/updateFeedbackStatus")
public class UpdateFeedbackStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int feedbackID = Integer.parseInt(req.getParameter("feedbackID"));
        int status = Integer.parseInt(req.getParameter("status"));
        FeedbackAdminDAO dao = new FeedbackAdminDAO();
        dao.updateFeedbackStatus(feedbackID, status);
        resp.sendRedirect(req.getContextPath() + "/FeedBackAdmin");
    }
}