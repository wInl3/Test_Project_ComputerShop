package controller.cart;

import dal.CartItemDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import models.CartItem;
import models.User;

public class CartItemsLoader extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        String offsetParam = request.getParameter("offset");
        String limitParam = request.getParameter("limit");

        int offset = 0;
        int limit = 5; // default page size

        if (offsetParam != null && !offsetParam.isEmpty()) {
            offset = Integer.parseInt(offsetParam);
        }

        if (limitParam != null && !limitParam.isEmpty()) {
            limit = Integer.parseInt(limitParam);
        }

        CartItemDAO dao = new CartItemDAO();
        ArrayList<CartItem> cartItems = dao.getCartItemsByUserIdWithOffset(user.getUserId(), offset, limit);

        request.setAttribute("cartItems", cartItems);

        RequestDispatcher rd = request.getRequestDispatcher("ShopPages/Pages/CartItemsFragment.jsp");
        rd.forward(request, response);
    }
}
