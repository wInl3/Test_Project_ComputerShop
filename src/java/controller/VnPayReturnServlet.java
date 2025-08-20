/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import config.VnPayConfig;
import dal.CartBuildPCDAO;
import dal.VnPayUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
@WebServlet(name="VnPayReturnServlet", urlPatterns={"/vnpay-return"})
public class VnPayReturnServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet VnPayReturnServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VnPayReturnServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String paramName = params.nextElement();
            String value = request.getParameter(paramName);
            fields.put(paramName, value);
        }

        String vnp_SecureHash = fields.remove("vnp_SecureHash");
        String signValue = VnPayUtil.hmacSHA512(VnPayConfig.vnp_HashSecret, buildData(fields));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (!signValue.equals(vnp_SecureHash)) {
            out.println("<h3 style='color:red'>Sai chữ ký! Giao dịch không hợp lệ.</h3>");
            return;
        }

        String responseCode = fields.get("vnp_ResponseCode");
        String amountStr = fields.get("vnp_Amount");
        String orderInfo = fields.get("vnp_OrderInfo");

        int orderID = extractOrderID(orderInfo);
        double amount = Double.parseDouble(amountStr) / 100.0;

        CartBuildPCDAO dao = new CartBuildPCDAO(); 

        if (orderID <= 0) {
            out.println("<h3 style='color:red'>Không tìm thấy mã đơn hàng hợp lệ.</h3>");
            return;
        }

//        if ("00".equals(responseCode)) {
//            dao.insertPayment(orderID, amount, "Success");
//            dao.updatePaymentStatus(orderID, 2); // Chuyển khoản
//            out.println("<h3 style='color:green'>Thanh toán thành công cho đơn hàng #" + orderID + "!</h3>");
//        } else {
//            dao.insertPayment(orderID, amount, "Failed");
//            out.println("<h3 style='color:red'>Thanh toán thất bại. Mã lỗi: " + responseCode + "</h3>");
//        }
    }

    private String buildData(Map<String, String> fields) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String name : fieldNames) {
            if (sb.length() > 0) sb.append('&');
            sb.append(name).append('=').append(URLEncoder.encode(fields.get(name), "UTF-8"));
        }
        return sb.toString();
    }

    private int extractOrderID(String orderInfo) {
        if (orderInfo != null && orderInfo.startsWith("OrderID:")) {
            try {
                return Integer.parseInt(orderInfo.substring(8));
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }
    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
