/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author PC ASUS
 */
public class OTP extends HttpServlet {

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
            out.println("<title>Servlet OTP</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OTP at " + request.getContextPath() + "</h1>");
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
        String email = request.getParameter("email");
        String otpCode = request.getParameter("otp");

        dal.OTPDAO otpDAO = new dal.OTPDAO();
        models.OTP otp = otpDAO.getLatestOTPByEmail(email);
        long expirationTime = 0;

        if (otp != null && otp.getExpirationTime() != null) {
            expirationTime = otp.getExpirationTime().getTime();
        }

        if (otp != null && !otp.isUsed() && otp.getExpirationTime().after(new java.util.Date())) {
            if (otp.getOtpCode().equals(otpCode)) {
                otpDAO.setOTPUsed(otp.getOtpId());
                request.setAttribute("email", email);
                request.getRequestDispatcher("/ShopPages/Pages/changePassword.jsp").forward(request, response);
                return;
            } else {
                // Mã OTP sai
                request.setAttribute("error", "invalid");
            }
        } else {
            // Hết hạn hoặc đã dùng
            request.setAttribute("error", "expired");
        }

        request.setAttribute("email", email);
        request.setAttribute("expirationTime", expirationTime);
        request.getRequestDispatcher("/ShopPages/Pages/otp.jsp").forward(request, response);
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
