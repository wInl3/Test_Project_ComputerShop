/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import dal.CustomerInfoDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import models.CustomerInfo;
import utils.PasswordUtils;
import utils.Validator;

/**
 * SignUpServlet handles user registration for customers only.
 */
public class SignUpServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SignUpServlet.class.getName());
    private final UserDAO userDAO;
    private final CustomerInfoDAO customerInfoDAO;

    public SignUpServlet() {
        this.userDAO = new UserDAO();
        this.customerInfoDAO = new CustomerInfoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            RequestDispatcher rs = request.getRequestDispatcher("ShopPages/Pages/signUp.jsp");
            rs.forward(request, response);
        } else {
            String redirectURL;
            if (user.getRole().getRoleID() == 1) { // Admin role
                redirectURL = "Admin";
            } else {
                redirectURL = (String) session.getAttribute("redirectAfterLogin");
                if (redirectURL == null) {
                    redirectURL = "HomePages";
                }
                session.setAttribute("redirectAfterLogin", null);
            }
            response.sendRedirect(redirectURL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");

        try {
            // Validate input
            String error = validateSignUpInput(email, fullName, address, phoneNumber, password);
            if (error != null) {
                setSignUpAttributes(request, error, email, fullName, address, phoneNumber);
                forwardToSignUp(request, response);
                return;
            }

            // Check email and phone number existence
            if (userDAO.isEmailExist(email)) {
                setSignUpAttributes(request, "Email already exists!", email, fullName, address, phoneNumber);
                forwardToSignUp(request, response);
                return;
            }

            if (userDAO.isPhoneNumberExisted(phoneNumber)) {
                setSignUpAttributes(request, "Phone number already exists!", email, fullName, address, phoneNumber);
                forwardToSignUp(request, response);
                return;
            }

            // Hash password and create user
//            String hashedPassword = PasswordUtils.hashPassword(password);
            
            // Create user with role 3 (customer)
            boolean userCreated = userDAO.createNewUser(email, fullName, phoneNumber, password, 3, null);
            
            if (userCreated) {
                // Get the newly created user's ID and create customer info
                User createdUser = userDAO.getUserByEmail(email);
                if (createdUser != null) {
                    boolean customerInfoCreated = customerInfoDAO.createCustomerInfo(createdUser.getUserId(), address);
                    
                    if (customerInfoCreated) {
                        LOGGER.log(Level.INFO, String.format("New customer registered successfully: %s", email));
                        request.setAttribute("toast", "Sign up successfully! Please login.");
                        request.setAttribute("toastType", "success");
                        response.sendRedirect("Login");
                        return;
                    }
                }
                
                // If we get here, customer info creation failed - rollback user creation
                userDAO.deleteUser(createdUser.getUserId());
                LOGGER.log(Level.SEVERE, String.format("Failed to create customer info for user: %s", email));
            }
            
            // If we get here, something went wrong
            LOGGER.log(Level.SEVERE, String.format("Failed to create user account for: %s", email));
            setSignUpAttributes(request, "Sign up failed! Please try again.", email, fullName, address, phoneNumber);
            forwardToSignUp(request, response);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during user registration", e);
            setSignUpAttributes(request, "An error occurred during registration. Please try again.", 
                    email, fullName, address, phoneNumber);
            forwardToSignUp(request, response);
        }
    }

    private String validateSignUpInput(String email, String fullName, String address,
            String phoneNumber, String password) {
        if (Validator.isNullOrEmpty(email)) {
            return "Email is required!";
        }
        if (!Validator.isValidEmail(email)) {
            return "Invalid email format!";
        }
        if (Validator.isNullOrEmpty(fullName)) {
            return "Full name is required!";
        }
        if (Validator.isNullOrEmpty(address)) {
            return "Address is required!";
        }
        if (Validator.isNullOrEmpty(phoneNumber)) {
            return "Phone number is required!";
        }
        if (!Validator.isValidPhoneNumber(phoneNumber)) {
            return "Invalid phone number format!";
        }
        if (Validator.isNullOrEmpty(password)) {
            return "Password is required!";
        }
        return null;
    }

    private void setSignUpAttributes(HttpServletRequest request, String error, 
            String email, String fullName, String address, String phoneNumber) {
        request.setAttribute("error", error);
        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);
        request.setAttribute("address", address);
        request.setAttribute("phoneNumber", phoneNumber);
    }

    private void forwardToSignUp(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher rs = request.getRequestDispatcher("/ShopPages/Pages/signUp.jsp");
        rs.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "SignUp Servlet - Handles customer registration";
    }
}
