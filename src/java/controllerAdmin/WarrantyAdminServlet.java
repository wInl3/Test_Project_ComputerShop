/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dalAdmin.WarrantyAdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.User;
import models.Warranties;

/**
 *
 * @author Admin
 */
public class WarrantyAdminServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || currentUser.getRole().getRoleID() != 1) {
            if (session != null) {
                session.invalidate();
            }
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("error", "You do not have permission to access this task.");
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        WarrantyAdminDAO dao = new WarrantyAdminDAO();
        List<Warranties> warranties = dao.getAllWarranties();
        String service = request.getParameter("service");
        if (service == null) {
            service = "list";
        }

        if (service.equals("list")) {
            request.setAttribute("warranties", warranties);
            request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewWarranty.jsp").forward(request, response);
        } else if (service.equals("insert")) {
            String submit = request.getParameter("submit");

            if (submit == null) {
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertWarranty.jsp")
                        .forward(request, response);
            } else {
                String periodRaw = request.getParameter("warrantyPeriod");
                String statusRaw = request.getParameter("status");
                String description = request.getParameter("description");

                String error = "";
                int warrantyPeriod = 0;
                int status = 1;

                if (periodRaw == null || periodRaw.trim().isEmpty() || !periodRaw.matches("^[0-9]{1,2}$")) {
                    error = "Warranty Period must be a number (1-99) and must not contain special characters.";
                } else {
                    warrantyPeriod = Integer.parseInt(periodRaw);
                }

                if (description == null || description.trim().isEmpty()) {
                    error = "Description cannot be empty.";
                } else if (description.length() > 100) {
                    error = "Description must not exceed 100 characters.";
                }

                if (statusRaw != null && statusRaw.matches("^[01]$")) {
                    status = Integer.parseInt(statusRaw);
                }

                request.setAttribute("warrantyPeriod", periodRaw);
                request.setAttribute("status", statusRaw);
                request.setAttribute("description", description);

                if (!error.isEmpty()) {
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertWarranty.jsp")
                            .forward(request, response);
                    return;
                }

                Warranties warranty = new Warranties();
                warranty.setWarrantyPeriod(warrantyPeriod);
                warranty.setStatus(status);
                warranty.setDescription(description.trim());
                new WarrantyAdminDAO().insertWarranty(warranty);

                response.sendRedirect("WarrantyAdmin");
            }

        } else if (service.equals("update")) {
            String submit = request.getParameter("submit");

            if (submit == null) {
                try {
                    int warrantyID = Integer.parseInt(request.getParameter("warrantyID"));
                    Warranties warranty = new WarrantyAdminDAO().getWarrantyByID(warrantyID);
                    request.setAttribute("warranty", warranty);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/updateWarranty.jsp")
                            .forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid warranty ID");
                }
            } else {
                String warrantyIDRaw = request.getParameter("warrantyID");
                String periodRaw = request.getParameter("warrantyPeriod");
                String statusRaw = request.getParameter("status");
                String description = request.getParameter("description");

                String error = "";
                int warrantyID = -1;
                int warrantyPeriod = 0;
                int status = 1;

                try {
                    warrantyID = Integer.parseInt(warrantyIDRaw);
                } catch (Exception e) {
                    error = "Invalid warranty ID.";
                }

                if (periodRaw == null || periodRaw.trim().isEmpty() || !periodRaw.matches("^[0-9]{1,2}$")) {
                    error = "Warranty Period must be a number (1-99) and must not contain special characters.";
                } else {
                    warrantyPeriod = Integer.parseInt(periodRaw);
                }

                if (description == null || description.trim().isEmpty()) {
                    error = "Description cannot be empty.";
                } else if (description.length() > 100) {
                    error = "Description must not exceed 100 characters.";
                }

                if (statusRaw != null && statusRaw.matches("^[01]$")) {
                    status = Integer.parseInt(statusRaw);
                }

                request.setAttribute("warrantyPeriod", periodRaw);
                request.setAttribute("status", statusRaw);
                request.setAttribute("description", description);
                request.setAttribute("warrantyID", warrantyIDRaw);

                if (!error.isEmpty()) {
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/updateWarranty.jsp")
                            .forward(request, response);
                    return;
                }

                Warranties warranty = new Warranties();
                warranty.setWarrantyID(warrantyID);
                warranty.setWarrantyPeriod(warrantyPeriod);
                warranty.setStatus(status);
                warranty.setDescription(description.trim());

                new WarrantyAdminDAO().updateWarranty(warranty);
                response.sendRedirect("WarrantyAdmin");
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
