/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dalAdmin.ComponentAdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Categories;
import models.Components;
import models.User;

/**
 *
 * @author Admin
 */
public class ComAdminServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
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
            if (service == null) {
                service = "list";
            }
            ComponentAdminDAO dao = new ComponentAdminDAO();
            dao.updateComponentQuantitiesFromBrandComs();

            if (service.equals("list")) {

                List<Components> components;

                components = dao.getAllComponent();

                request.setAttribute("data", components);

                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewComponent.jsp").forward(request, response);
            } else if (service.equals("insert")) {
                String submit = request.getParameter("submit");

                if (submit == null) {
                    List<Components> components = dao.getAllComponent();
                    request.setAttribute("data", components);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertComponent.jsp").forward(request, response);
                } else {
                    String name = request.getParameter("component_name");
                    String statusRaw = request.getParameter("status");
                    int quantity = 0; // mặc định khi insert mới

                    String error = null;

                    // Validate name
                    if (name == null || name.trim().isEmpty()) {
                        error = "Component name cannot be empty or whitespace only.";
                    } else if (name.length() > 50) {
                        error = "Component name is too long (maximum 50 characters).";
                    } else if (!name.matches("^[A-Za-z0-9 ]+$")) {
                        error = "Component name must contain only letters, digits and spaces.";
                    } else if (dao.isComponentNameExist(name.trim())) {
                        error = "Component name already exists.";
                    }

                    if (error != null) {
                        request.setAttribute("error", error);
                        request.setAttribute("component_name", name);
                        request.setAttribute("status", statusRaw);
                        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertComponent.jsp").forward(request, response);
                        return;
                    }

                    try {
                        int status = Integer.parseInt(statusRaw);

                        Components component = new Components();
                        component.setComponentName(name.trim());
                        component.setQuantity(quantity);
                        component.setStatus(status);

                        dao.insertComponent(component);
                        response.sendRedirect(request.getContextPath() + "/ComAdmin?service=list");

                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Invalid status.");
                        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertComponent.jsp").forward(request, response);
                    }
                }
            } else if (service.equals("toggleStatus")) {
                int componentID = Integer.parseInt(request.getParameter("componentID"));

                dao.toggleStatus(componentID);
                response.sendRedirect("ComAdmin");
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
