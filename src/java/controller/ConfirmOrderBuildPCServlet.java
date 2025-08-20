package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.User;
import dal.CartBuildPCDAO;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

@WebServlet(name = "ConfirmOrderBuildPCServlet", urlPatterns = {"/ConfirmOrderBuildPC"})
public class ConfirmOrderBuildPCServlet extends HttpServlet {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");

            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String note = request.getParameter("note");
            String bankCode = request.getParameter("bankCode");
            String paymentMethod = request.getParameter("paymentMethod");
            String cartIDsParam = request.getParameter("cartIDs");
            String amountRaw = request.getParameter("amount");

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("Login");
                return;
            }

            // Validate inputs
            if (fullname == null || fullname.trim().isEmpty()) {
                throw new IllegalArgumentException("Full name cannot be empty.");
            }

            if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
                throw new IllegalArgumentException("Invalid phone number. It must be 10 digits and start with 0.");
            }

            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("Address cannot be empty.");
            }

            if (cartIDsParam == null || cartIDsParam.trim().isEmpty()) {
                throw new IllegalArgumentException("No cart IDs selected.");
            }

            // Clean and process input
            String[] cartIDs = cartIDsParam.trim().split(",");
            amountRaw = amountRaw.replaceAll("[^\\d]", "");
            int amount = Integer.parseInt(amountRaw);

            CartBuildPCDAO dao = new CartBuildPCDAO();

            if ("cod".equalsIgnoreCase(paymentMethod)) {
                // ✅ Handle Cash On Delivery (COD)
                for (String idStr : cartIDs) {
                    int cartID = Integer.parseInt(idStr.trim());
                    int orderID = dao.insertOrderFromCartAndReturnOrderID(cartID, user.getUserId());
                    dao.updateOrderCustomerInfo(orderID, fullname, phone, address, note, 1); // 1 = COD
                }

                response.sendRedirect("ShopPages/Pages/order-success.jsp");

            } else {
                // ✅ Handle VNPay payment
                session.setAttribute("pendingCartIDs", cartIDsParam);
                session.setAttribute("pendingAmount", amount);
                session.setAttribute("pendingNote", note != null ? note : "");
                session.setAttribute("pendingBankCode", bankCode != null ? bankCode : "");
                session.setAttribute("pendingFullname", fullname);
                session.setAttribute("pendingPhone", phone);
                session.setAttribute("pendingAddress", address);

                String redirectURL = request.getContextPath() + "/pay-vnpay"
                        + "?amount=" + amount
                        + "&bankCode=" + URLEncoder.encode(bankCode != null ? bankCode : "", "UTF-8")
                        + "&note=" + URLEncoder.encode(note != null ? note : "", "UTF-8");

                response.sendRedirect(redirectURL);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while confirming the order: " + e.getMessage());
            request.setAttribute("ids", request.getParameter("cartIDs"));
            request.setAttribute("amount", request.getParameter("amount"));
            request.getRequestDispatcher("ShopPages/Pages/confirmVnPayOrder.jsp").forward(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
