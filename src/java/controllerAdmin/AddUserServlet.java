/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dal.RoleDAO;
import dal.UserDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import models.Role;
import utils.MailUtils;
import utils.PasswordUtils;

/**
 *
 * @author PC ASUS
 */
public class AddUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddUserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO dao = new RoleDAO();
        ArrayList<Role> roles = dao.getRoles();
        request.setAttribute("roles", roles);
        RequestDispatcher rs = request.getRequestDispatcher("/AdminLTE/AdminPages/pages/forms/addNewUser.jsp");
        rs.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String roleID = request.getParameter("roleID");
        String startDate = request.getParameter("startDate");
        RoleDAO roleDao = new RoleDAO();
        UserDAO userDao = new UserDAO();
        ArrayList<Role> roles = roleDao.getRoles();
        String error = "";

        // Validate theo đúng thứ tự các trường trong form (fullName, email, phoneNumber, address, roleID)
        if (utils.Validator.isNullOrEmpty(fullName)) {
            error = "Full name is required!";
        } else if (utils.Validator.isNullOrEmpty(email)) {
            error = "Email is required!";
        } else if (userDao.isEmailExist(email)) {
            error = "Email existed!";
        } else if (utils.Validator.isNullOrEmpty(phoneNumber)) {
            error = "Phone number is required!";
        } else if (!utils.Validator.isValidPhoneNumber(phoneNumber)) {
            error = "Invalid phone number!";
        } else if (userDao.isPhoneNumberExisted(phoneNumber)) {
            error = "Phone number existed!";
        } else if (utils.Validator.isNullOrEmpty(address) && roleID == "3") {
            error = "Address is required!";
        } else if (roleID == "3") {
            error = "Only Admin and Staff accounts can be added!";
        }

        if (!error.isEmpty()) {
            request.setAttribute("error", error);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("address", address);
            request.setAttribute("roleID", roleID);
            request.setAttribute("roles", roles);
            request.setAttribute("toast", error);
            request.setAttribute("toastType", "error");
            request.setAttribute("startDate", startDate);
            RequestDispatcher rs = request.getRequestDispatcher("/AdminLTE/AdminPages/pages/forms/addNewUser.jsp");
            rs.forward(request, response);
            return;
        } else {
            try {
                String newPassword = "123456"; // Mật khẩu mặc định, có thể thay đổi sau
                boolean addUser = userDao.createNewUser(email, fullName, phoneNumber, newPassword, Integer.parseInt(roleID), startDate);
                if (addUser) {
                    HttpSession session = request.getSession();
                    session.setAttribute("toast", "Add user succesfully!");
                    session.setAttribute("toastType", "success");
                    if (null != roleID) switch (roleID) {
                        case "1":
                            response.sendRedirect(request.getContextPath() + "/Admin/user?type=admin");
                            break;
                        case "2":
                            response.sendRedirect(request.getContextPath() + "/Admin/user?type=sale");
                            break;
                        case "4":
                            response.sendRedirect(request.getContextPath() + "/Admin/user?type=shipper");
                            break;
                        default:
                            response.sendRedirect(request.getContextPath() + "/Admin/user?type=admin");
                            break;
                    }
                } else {
                    request.setAttribute("toast", "Add user failed!");
                    request.setAttribute("toastType", "error");
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log lỗi để dễ debug
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
