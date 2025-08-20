package controller;

import dal.CartBuildPCDAO;
import dal.VnPayUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import models.CartBuildPC;
import models.User;

public class CardBuildPcController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        CartBuildPCDAO dao = new CartBuildPCDAO();
        List<CartBuildPC> cartList = dao.getCartPCView(user.getUserId());

        request.setAttribute("cartBuildPC", cartList);
        RequestDispatcher rd = request.getRequestDispatcher("ShopPages/Pages/CartBuildPC.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String service = request.getParameter("service");
        if (service == null || service.trim().isEmpty()) {
            out.print("FAIL");
            return;
        }

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            out.print("NOT_LOGGED_IN");
            return;
        }

        CartBuildPCDAO dao = new CartBuildPCDAO();

        try {
            switch (service) {
                case "deleteCartBuildPC" -> handleDelete(request, response, dao);
              case "depositBuildPC" -> handleDeposit(request, response, dao, user.getUserId());

                default -> out.print("FAIL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("FAIL");
        }
    }

private void handleDelete(HttpServletRequest request, HttpServletResponse response, CartBuildPCDAO dao)
        throws IOException {
    try {
        int cartID = Integer.parseInt(request.getParameter("id"));
        boolean success = dao.deleteCartBuildPC(cartID);

        if (success) {
            response.sendRedirect("CardBuildPc"); // Load lại giỏ hàng sau khi xóa
        } else {
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print("FAIL");
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print("FAIL");
    }
}




private void handleDeposit(HttpServletRequest request, HttpServletResponse response, CartBuildPCDAO dao, int userID)
        throws IOException, ServletException {
    String idsRaw = request.getParameter("ids");
    if (idsRaw == null || idsRaw.trim().isEmpty()) {
        response.getWriter().print("FAIL");
        return;
    }

    String[] idArr = idsRaw.split(",");
    int total = 0;

    for (String idStr : idArr) {
        try {
            int cartBuildPCID = Integer.parseInt(idStr.trim());
            int price = dao.getCartBuildPCPrice(cartBuildPCID); // đảm bảo hàm này không trả -1 khi lỗi
            total += price;
        } catch (NumberFormatException e) {
            response.getWriter().print("FAIL");
            return;
        }
    }

    int depositAmount = (int) (total * 0.2);

    // Đẩy sang trang xác nhận đặt cọc
    request.setAttribute("cartIDs", idsRaw); // danh sách ID sẽ dùng ở confirmVnPayOrder.jsp
    request.setAttribute("amount", depositAmount);
    request.getRequestDispatcher("ShopPages/Pages/confirmVnPayOrder.jsp").forward(request, response);
}

    @Override
    public String getServletInfo() {
        return "Controller for managing Build PC cart operations.";
    }
}
