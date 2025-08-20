/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dalAdmin.BrandAdminDAO;
import dalAdmin.CategoryAdminDAO;
import dal.ComponentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Brands;
import models.Categories;
import models.Components;
import models.User;

/**
 *
 * @author Admin
 */
public class BrandAdminServlet extends HttpServlet {

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
                service = "listall";
            }

            BrandAdminDAO ban = new BrandAdminDAO();
            ban.updateBrandQuantitiesFromBrandComs();

            if (service.equals("listall")) {
                List<Brands> bra = ban.getAllBrands();
                request.setAttribute("bra", bra);
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/viewBrand.jsp").forward(request, response);

            } else if (service.equals("insert")) {
                String submit = request.getParameter("submit");

                if (submit == null) {
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertBrand.jsp").forward(request, response);
                } else {
                    String brandName = request.getParameter("brandName");
                    String statusRaw = request.getParameter("status");

                    String error = null;
                    if (brandName == null || brandName.trim().isEmpty()) {
                        error = "Brand name cannot be empty or whitespace only.";
                    } else if (ban.isBrandNameExists(brandName.trim())) {
                        error = "Brand name already exists.";
                    } else if (brandName.length() > 50) {
                        error = "Brand name is too long (maximum 50 characters).";
                    } else if (!brandName.matches("^[A-Za-z0-9 ]+$")) {
                        error = "Brand name must contain only letters, digits and spaces.";
                    }

                    if (error != null) {
                        request.setAttribute("error", error);
                        request.setAttribute("brandName", brandName);
                        request.setAttribute("status", statusRaw);
                        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertBrand.jsp").forward(request, response);
                        return;
                    }

                    try {
                        int quantity = 0;
                        int status = Integer.parseInt(statusRaw);
                        Brands brand = new Brands(brandName.trim(), quantity, status);
                        ban.insertBrand(brand);
                        response.sendRedirect("BrandAdmin?service=listall");
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Quantity and status must be valid numbers.");
                        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/insertBrand.jsp").forward(request, response);
                    }
                }
            } else if (service.equals("toggleStatus")) {
                int brandID = Integer.parseInt(request.getParameter("brandID"));
                BrandAdminDAO dao = new BrandAdminDAO();
                dao.toggleStatus(brandID);
                response.sendRedirect("BrandAdmin"); // quay lại danh sách sau khi cập nhật
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
