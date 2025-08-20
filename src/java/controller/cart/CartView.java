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

public class CartView extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        CartItemDAO dao = new CartItemDAO();

        ArrayList<CartItem> cart = dao.getCartItemsByUserIdWithOffset(user.getUserId(), 0, 10);
        request.setAttribute("cart", cart);
        request.setAttribute("initialOffset", 0);
        request.setAttribute("pageLimit", 5);
        RequestDispatcher rd = request.getRequestDispatcher("ShopPages/Pages/cart.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "CartView - Loads the main shopping cart page";
    }
}
