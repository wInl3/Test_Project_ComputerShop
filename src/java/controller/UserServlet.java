/*
 * Click nfs://netbeans/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nfs://netbeans/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import utils.MailUtils;
import utils.PasswordUtils;

/**
 *
 * @author PC ASUS
 */
public class UserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();

        String service = request.getParameter("service");

        if ("resetPassword".equals(service)) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            String roleID = request.getParameter("roleID");
            UserDAO dao = new UserDAO();
            User user = dao.getUserByID(userID);
            if (user != null) {
                String newPassword = PasswordUtils.generateRandomPassword(10);
                String hashedPassword = PasswordUtils.hashPassword(newPassword);
                boolean ok = dao.resetPassword(userID, hashedPassword);
                if (ok) {
                    boolean mailSent = MailUtils.send(user.getEmail(),
                            "Your password has been reset",
                            "Hello " + user.getFullname() + ",\n\nYour new password is: " + newPassword + "\n\nPlease log in and change it.");
                    if (mailSent) {
                        request.setAttribute("toast", "Reset password successfully!");
                        request.setAttribute("toastType", "success");
                    } else {
                        request.setAttribute("toast", "Reset password failed to send email!");
                        request.setAttribute("toastType", "warning");
                    }
                }
            } else {
                request.setAttribute("toast", "User not found!");
                request.setAttribute("toastType", "error");
            }
            if ("1".equals(roleID)) {
                response.sendRedirect("Admin/user?type=admin");
            } else if ("2".equals(roleID)) {
                response.sendRedirect("Admin/user?type=sale");
            } else if ("3".equals(roleID)) {
                response.sendRedirect("Admin/user?type=customer");
            } else if ("4".equals(roleID)) {
                response.sendRedirect("Admin/user?type=shipper");
            } else {
                response.sendRedirect("Login");
            }
        } else if ("toggleStatus".equals(service)) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            UserDAO dao = new UserDAO();
            boolean toggle = dao.toggleStatus(userID);
            HttpSession session = request.getSession();
            if (toggle) {
                session.setAttribute("toast", "Update user successfully!");
                session.setAttribute("toastType", "success");
            } else {
                session.setAttribute("toast", "Update user failed!");
                session.setAttribute("toastType", "error");
            }
            response.sendRedirect("Admin/user");
        } else if ("myAccount".equals(service)) {
            HttpSession session = request.getSession();
            User sessionUser = (User) session.getAttribute("user");

            if (sessionUser != null) {
                // Cập nhật lại bản mới nhất từ DB để tránh thông tin cũ
                UserDAO dao = new UserDAO();
                User latestUser = dao.getUserByID(sessionUser.getUserId());
                request.setAttribute("user", latestUser);
                request.getRequestDispatcher("/ShopPages/Pages/myAccount.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/Login");
            }
        } else if ("forgotPassword".equals(service)) {
            request.getRequestDispatcher("/ShopPages/Pages/forgotPassword.jsp").forward(request, response);
        } else if ("changePassword".equals(service)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                request.setAttribute("email", user.getEmail());
                request.getRequestDispatcher("/ShopPages/Pages/changePassword.jsp").forward(request, response);
            } else {
                response.sendRedirect("/Login");
            }
        } else {
            response.sendRedirect("Homepages");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        if ("updateUser".equals(service)) {
            UserDAO dao = new UserDAO();
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phoneNumber");
            String roleID = request.getParameter("roleID");
            String StartedDate = request.getParameter("StartedDate");
            String EndDate = request.getParameter("EndDate");
            int userID = Integer.parseInt(request.getParameter("userID"));
            int status = Integer.parseInt(request.getParameter("status"));
            boolean isEmailExist = dao.isEmailExist(email);
            boolean isPhoneNumberExisted = dao.isPhoneNumberExisted(phoneNumber);
            String error = null;
            if (utils.Validator.isNullOrEmpty(fullName)) {
                error = "Full name is required!";
            } else if (utils.Validator.isNullOrEmpty(email)) {
                error = "Email is required!";
            } else if (isEmailExist && !email.equals(dao.getUserByID(userID).getEmail())) {
                error = "Email existed!";
            } else if (utils.Validator.isNullOrEmpty(address) && "3".equals(roleID)) {
                error = "Address is required!";
            } else if (utils.Validator.isNullOrEmpty(phoneNumber)) {
                error = "Phone number is required!";
            } else if (!utils.Validator.isValidPhoneNumber(phoneNumber)) {
                error = "Invalid phone number";
            } else if (isPhoneNumberExisted && !phoneNumber.equals(dao.getUserByID(userID).getPhoneNumber())) {
                error = "Phone number existed!";
            }
            if (error != null) {
                request.setAttribute("error", error);
                User user = dao.getUserByID(userID);
                user.setFullname(fullName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                request.setAttribute("user", user);
                if (address != null) {
                    request.setAttribute("address", address);
                }
                request.getRequestDispatcher("/AdminLTE/AdminPages/pages/forms/updateUser.jsp").forward(request, response);
            } else {
                try {
                    boolean updated = dao.updateUser(userID, fullName, email, phoneNumber, status, address, StartedDate, EndDate);
                    if (updated) {
                        request.setAttribute("toast", "User updated successfully!");
                        request.setAttribute("toastType", "success");
                    } else {
                        request.setAttribute("toast", "Failed to update user!");
                        request.setAttribute("toastType", "error");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                switch (roleID) {
                    case "1":
                        response.sendRedirect("Admin/user?type=admin");
                        break;
                    case "2":
                        response.sendRedirect("Admin/user?type=sale");
                        break;
                    case "3":
                        response.sendRedirect("Admin/user?type=customer");
                        break;
                    case "4":
                        response.sendRedirect("Admin/user?type=shipper");
                        break;
                    default:
                        response.sendRedirect("Admin");
                }
            }
        } else if ("updateProfile".equals(service)) {
            UserDAO dao = new UserDAO();
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");

            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/Login");
                return;
            }

            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phoneNumber");

            int userID = currentUser.getUserId();
            int status = currentUser.getStatus();

            String error = null;

            // Kiểm tra điều kiện đầu vào
            if (utils.Validator.isNullOrEmpty(email)) {
                error = "Email is required!";
            } else if (utils.Validator.isNullOrEmpty(fullName)) {
                error = "Full name is required!";
            } else if (utils.Validator.isNullOrEmpty(address)) {
                error = "Address is required!";
            } else if (utils.Validator.isNullOrEmpty(phoneNumber)) {
                error = "Phone number is required!";
            } else if (!utils.Validator.isValidPhoneNumber(phoneNumber)) {
                error = "Invalid phone number!";
            } else {
                // Kiểm tra trùng email nhưng KHÁC với hiện tại
                boolean emailExisted = dao.isEmailExist(email);
                if (emailExisted && !email.equalsIgnoreCase(currentUser.getEmail())) {
                    error = "Email already exists!";
                }

                // Kiểm tra trùng số điện thoại nhưng KHÁC với hiện tại
                boolean phoneExisted = dao.isPhoneNumberExisted(phoneNumber);
                if (phoneExisted && !phoneNumber.equals(currentUser.getPhoneNumber())) {
                    error = "Phone number already exists!";
                }
            }

            if (error != null) {
                request.setAttribute("toast", error);
                request.setAttribute("toastType", "error");

                // Gán lại thông tin đã nhập
                currentUser.setFullname(fullName);
                currentUser.setEmail(email);
                currentUser.setPhoneNumber(phoneNumber);
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("/ShopPages/Pages/myAccount.jsp").forward(request, response);
            } else {
                try {
                    boolean updated = dao.updateUser(userID, fullName, email, phoneNumber, status, address, null, null);
                    if (updated) {
                        User updatedUser = dao.getUserByID(userID);
                        session.setAttribute("user", updatedUser);
                        request.setAttribute("toast", "Profile updated successfully!");
                        request.setAttribute("toastType", "success");
                    } else {
                        request.setAttribute("toast", "Failed to update profile!");
                        request.setAttribute("toastType", "error");
                    }
                    request.setAttribute("user", dao.getUserByID(userID));
                    request.getRequestDispatcher("/ShopPages/Pages/myAccount.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("HomePages");
                }
            }
        } else if ("sendOTP".equals(service)) {
            String email = request.getParameter("email");
            UserDAO dao = new UserDAO();
            boolean isExisted = dao.isEmailExist(email);
            String error = "";
            if (isExisted) {
                // Tạo OTP thực tế
                String otp = String.valueOf((int) ((Math.random() * 900000) + 100000));
                java.util.Date now = new java.util.Date();
                java.util.Date expiration = new java.util.Date(now.getTime() + 5 * 60 * 1000); // 5 phút
                dal.OTPDAO otpDAO = new dal.OTPDAO();
                otpDAO.insertOTP(email, otp, expiration);
                User user = dao.getUserByEmail(email);
                boolean mailSent = utils.MailUtils.send(user.getEmail(),
                        "Online Computer Shop - OTP to reset your password.",
                        "Your OTP is: " + otp + "\nThis OTP is valid for 1 minutes.");
                // Lấy OTP mới nhất
                models.OTP latestOtp = otpDAO.getLatestOTP(email, otp);
                if (mailSent && latestOtp != null) {
                    request.setAttribute("email", email);
                    request.setAttribute("expirationTime", latestOtp.getExpirationTime().getTime());
                    request.getRequestDispatcher("/ShopPages/Pages/otp.jsp").forward(request, response);
                } else {
                    error = "Fail to send OTP email.";
                    request.setAttribute("email", email);
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("/ShopPages/Pages/forgotPassword.jsp").forward(request, response);
                }
            } else {
                error = "This email is not registered in our system. Please check and try again.";
                request.setAttribute("email", email);
                request.setAttribute("error", error);
                request.getRequestDispatcher("/ShopPages/Pages/forgotPassword.jsp").forward(request, response);
            }
        } else if ("changePassword".equals(service)) {
            String email = request.getParameter("email");
            String password = request.getParameter("hashedPassword");
            String error = null;
            boolean hasError = false;
            if (hasError) {
                request.setAttribute("error", error);
                request.setAttribute("email", email);
                request.getRequestDispatcher("/ShopPages/Pages/changePassword.jsp").forward(request, response);
            } else {
                UserDAO dao = new UserDAO();
                boolean updated = dao.updatePasswordByEmail(email, password);
                if (updated) {
                    HttpSession session = request.getSession();
                    User user = (User) session.getAttribute("user");
                    if (user != null) {
                        if (user.getRole().getRoleID() == 3) {
                            response.sendRedirect(request.getContextPath() +"/User?service=myAccount");
                        } else {
                            response.sendRedirect(request.getContextPath() +"/AdminProfile");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() +"/Login");
                    }
                } else {
                    request.setAttribute("errorPassword", "Failed to update password. Please try again.");
                    request.setAttribute("email", email);
                    request.getRequestDispatcher("/ShopPages/Pages/changePassword.jsp").forward(request, response);
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "UserServlet handles user-related operations";
    }
}
