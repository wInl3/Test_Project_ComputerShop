/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dalAdmin.BrandComAdminDAO;
import dalAdmin.BrandAdminDAO;
import dalAdmin.CategoryAdminDAO;
import dalAdmin.ComponentAdminDAO;
import dalAdmin.ImportDAO;
import dalAdmin.NotificationAdminDAO;
import dalAdmin.ProductAdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.BrandComs;
import models.Brands;
import models.Categories;
import models.Components;
import models.Imports;
import models.Products;
import models.User;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminDashbordServlet", urlPatterns = {"/AdminDashbordServlet"})
public class AdminDashbordServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            CategoryAdminDAO cate = new CategoryAdminDAO();
            ComponentAdminDAO Com = new ComponentAdminDAO();
            BrandAdminDAO bra = new BrandAdminDAO();
            BrandComAdminDAO brc = new BrandComAdminDAO();
            ImportDAO im = new ImportDAO();
            NotificationAdminDAO notificationDAO = new NotificationAdminDAO();

            // Load số lượng thông báo chưa đọc cho user hiện tại
            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;
            int userID = (user != null) ? user.getUserId() : -1;
            int unreadCount = (userID > 0) ? notificationDAO.getUnreadCount(userID) : 0;
            request.setAttribute("unreadCount", unreadCount);
            im.updateImportQuantitiesFromProducts();
            cate.updateCategoryQuantities();
            cate.updateCategoryInventory();
            brc.updateBrandComQuantitiesFromCategories();
            bra.updateBrandQuantitiesFromBrandComs();
            Com.updateComponentQuantitiesFromBrandComs();

            List<Components> com = Com.getAllComponent();
            request.setAttribute("com", com);
            List<Categories> list = cate.getAllCategoriesByInvenory();
            request.setAttribute("list", list);
            request.getRequestDispatcher("AdminLTE/AdminPages/AdminDashbord.jsp").forward(request, response);
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
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        System.out.println("SessionID: " + (session != null ? session.getId() : "null"));
        System.out.println("User in session: " + user);
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
