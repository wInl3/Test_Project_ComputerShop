/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

/**
 *
 * @author PC ASUS
 */
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import models.User;

//user auth filter
@WebFilter({
    "/CartItemsLoader",
    "/OrderHistory",
    "/Order",
    "/ConfirmOrder",
    "/Checkout",
    "/FeedbackServlet",
    "/Cart",
})
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            // Lưu lại URL trang mà người dùng muốn vào
            String requestedURL = req.getRequestURI();
            session = req.getSession(true);
            session.setAttribute("redirectAfterLogin", requestedURL);

            // Chuyển hướng đến trang đăng nhập
            res.sendRedirect(req.getContextPath() + "/Login");
        } else {
            User user = (User) session.getAttribute("user");
            if (user.getRole().getRoleID() == 3) {
                chain.doFilter(request, response); // Tiếp tục nếu đã đăng nhập
            } else {
                res.sendRedirect(req.getContextPath() + "/Login");
            }
        }
    }
}
