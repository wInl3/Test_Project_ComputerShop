package controller;

import dal.VnPayUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "PayVNPayServlet", urlPatterns = {"/pay-vnpay"})
public class PayVNPayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int amount = 0;
            try {
                String rawAmount = request.getParameter("amount");
                if (rawAmount != null) {
                    rawAmount = rawAmount.replaceAll("[^\\d]", "");
                    amount = Integer.parseInt(rawAmount);
                }
            } catch (NumberFormatException e) {
                amount = 0;
            }

            String bankCode = request.getParameter("bankCode");
            if (bankCode == null) bankCode = "";

            // NOTE: Không dùng tiếng Việt hay ký tự lạ!
            String note = request.getParameter("note");
            if (note == null || note.trim().isEmpty()) {
                note = "BuildPCDeposit";
            } else {
                note = note.replaceAll("[^a-zA-Z0-9 ]", "").trim(); // chỉ giữ chữ cái và số
            }

            String vnpUrl = VnPayUtil.generateVnPayUrlWithoutOrderId(amount, bankCode, note);

            System.out.println(">>> VNPay redirect URL: " + vnpUrl);
            response.sendRedirect(vnpUrl);

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Lỗi khi xử lý thanh toán: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
