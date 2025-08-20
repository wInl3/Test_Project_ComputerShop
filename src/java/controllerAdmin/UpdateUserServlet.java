package controllerAdmin;

import dal.UserDAO;
import dal.RoleDAO;
import models.User;
import models.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIDStr = request.getParameter("userID");
        if (userIDStr == null) {
            response.sendRedirect(request.getContextPath() + "/Admin/user");
            return;
        }
        int userID = Integer.parseInt(userIDStr);
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByID(userID);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Admin/user");
            return;
        }
        request.setAttribute("user", user);
        RequestDispatcher rd = request.getRequestDispatcher("/AdminLTE/AdminPages/pages/forms/updateUser.jsp");
        rd.forward(request, response);
    }
}
