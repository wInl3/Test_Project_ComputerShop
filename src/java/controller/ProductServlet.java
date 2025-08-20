/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import models.BuildPCAdmin;
import models.OrderInfo;
import models.User;

/**
 *
 * @author Admin
 */
public class ProductServlet extends HttpServlet {

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
        String service = request.getParameter("service");
        if (service.equals("checkWarranty")) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("account") != null) {
                User loggedInUser = (User) session.getAttribute("account");
                request.setAttribute("userSessionId", loggedInUser.getUserId());
            }
            String productCode = request.getParameter("productCode");
            OrderInfo info = null;

            if (productCode != null && !productCode.trim().isEmpty()) {
                ProductDAO dao = new ProductDAO();
                info = dao.getOrderInfoByProductCode(productCode.trim());

                if (info != null) {
                    request.setAttribute("orderInfo", info);
                } else {
                    request.setAttribute("error", "No order found for the given Product Code.");
                }
            } else {
                // Chỉ đặt lỗi nếu form thực sự được gửi (đã có param)
                if (request.getParameter("productCode") != null) {
                    request.setAttribute("error", "Please enter a valid Product Code.");
                }
            }

            request.getRequestDispatcher("ShopPages/Pages/CheckWarranty.jsp").forward(request, response);
        } else if (service.equals("updateWarrantyStatus")) {
            String orderCode = request.getParameter("orderCode");
            String productCode = request.getParameter("productCode");

            if (orderCode != null && productCode != null
                    && !orderCode.trim().isEmpty() && !productCode.trim().isEmpty()) {

                ProductDAO dao = new ProductDAO();
                boolean success = dao.updateOrderAndProductToWarranty(orderCode.trim(), productCode.trim());

                if (success) {
                    request.setAttribute("success", "Order and product have been sent to warranty.");
                } else {
                    request.setAttribute("error", "Failed to update warranty status.");
                }
            } else {
                request.setAttribute("error", "Missing order code or product code.");
            }

            request.getRequestDispatcher("ShopPages/Pages/CheckWarranty.jsp").forward(request, response);
        } else if (service.equals("checkWarrantyBuildPC")) {
            String productCode = request.getParameter("productCode");
            if (productCode != null && !productCode.isEmpty()) {
                ProductDAO dao = new ProductDAO();

                BuildPCAdmin orderInfo = dao.getBuildPCWarrantyInfoByProductCode(productCode);
                if (orderInfo != null) {
                    request.setAttribute("orderInfo", orderInfo);
                } else {
                    request.setAttribute("error", "Product code not found or no warranty information available.");
                }
            } else {
                request.setAttribute("error", "Please enter a valid product code.");
            }

            // Lấy người dùng để kiểm tra quyền gửi bảo hành
            HttpSession session = request.getSession(false);
            if (session != null) {
                User sessionUser = (User) session.getAttribute("user");
                request.setAttribute("sessionUser", sessionUser);
                request.setAttribute("now", new Date());
            }

            request.getRequestDispatcher("ShopPages/Pages/CheckWarrantyBuildPC.jsp").forward(request, response);
        } else if (service.equals("updateWarrantyStatusBPC")) {
            String orderCode = request.getParameter("orderCode");
            String productCode = request.getParameter("productCode");

            if (orderCode != null && productCode != null
                    && !orderCode.trim().isEmpty() && !productCode.trim().isEmpty()) {

                ProductDAO dao = new ProductDAO();
                boolean success = dao.updateOrderAndProductToWarranty(orderCode.trim(), productCode.trim());

                if (success) {
                    request.setAttribute("success", "✔ Order and product have been sent to warranty.");
                } else {
                    request.setAttribute("error", "❌ Failed to update warranty status.");
                }
            } else {
                request.setAttribute("error", "❌ Missing order code or product code.");
            }

            request.getRequestDispatcher("ShopPages/Pages/CheckWarrantyBuildPC.jsp").forward(request, response);
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
