/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dal.Blog_CateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.SaleEvents;
import models.User;

/**
 *
 * @author User
 */
public class UpdateSaleEventServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateSaleEventServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateSaleEventServlet at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        int eventID = Integer.parseInt(request.getParameter("eventID"));
        SaleEvents event = new Blog_CateDAO().getSaleEventByID(eventID);
        request.setAttribute("event", event);
        request.getRequestDispatcher("AdminLTE/AdminPages/pages/forms/updatesale.jsp").forward(request, response);
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
//        processRequest(request, response
        try {

            int eventID = Integer.parseInt(request.getParameter("eventID"));
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            int postID = Integer.parseInt(request.getParameter("postID"));
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startDate"));
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endDate"));
            double discount = Double.parseDouble(request.getParameter("discount"));
            int status = Integer.parseInt(request.getParameter("status"));

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            SaleEvents event = new SaleEvents();
            event.setEventID(eventID);
            event.setCategoryID(categoryID);
            event.setPost_id(postID);
            event.setStartDate(startDate);
            event.setEndDate(endDate);
            event.setDiscountPercent(discount);
            event.setStatus(status);
            event.setCreatedBy(user.getUserId());
            event.setApprovedBy(null); // Nếu bạn cần cập nhật approvedBy, có thể set khác

            Blog_CateDAO dao = new Blog_CateDAO();
            dao.updateSaleEvent(event);

            response.sendRedirect("saleevents");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
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
