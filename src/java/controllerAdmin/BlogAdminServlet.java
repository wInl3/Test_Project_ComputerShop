/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllerAdmin;

import dal.Blog_CateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import models.Blog_Cate;
import models.Post;

/**
 *
 * @author User
 */
@WebServlet(name = "BlogAdminServlet", urlPatterns = {"/bloga"})

public class BlogAdminServlet extends HttpServlet {

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
            out.println("<title>Servlet BlogAdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogAdminServlet at " + request.getContextPath() + "</h1>");
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
        String selectedRole = request.getParameter("role");
        String sort = request.getParameter("sort");
        String searchKeyword = request.getParameter("q");
        Blog_CateDAO dao = new Blog_CateDAO();
        List<Blog_Cate> categories = dao.getAllBlogCategory();
        List<Post> post = new ArrayList<>();
        
        String roleName = null;
        if (selectedRole == null || selectedRole.equals("all")) {
            roleName = null;
        } else {
            roleName = switch (selectedRole.toLowerCase()) {
                case "admin" ->
                    "Admin";
                case "sale", "staff" ->
                    "Sale";
                default ->
                    null;
            };

        }

        try {
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                post = dao.searchPostsByTitle(searchKeyword.trim());
            } else if (roleName == null) {
                post = dao.getAllPost();
            } else {
                post = dao.getPostsByAuthorRole(roleName);
            }
//            System.out.println("===> Role selected: " + selectedRole);
//            System.out.println("===> Role name used to filter: " + roleName);
//            System.out.println("===> Post count after filter: " + post.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("A to Z".equalsIgnoreCase(sort)) {
            post.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
        } else if ("Z to A".equalsIgnoreCase(sort)) {
            post.sort((a, b) -> b.getTitle().compareToIgnoreCase(a.getTitle()));
        }

        // Truyền dữ liệu qua JSP
        request.setAttribute("blog_categories", categories);
        request.setAttribute("postlist", post);
        request.setAttribute("selectedSort", sort);
        request.setAttribute("selectedRole", selectedRole);
        request.setAttribute("keyword", searchKeyword);

        request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/blogAdmin.jsp").forward(request, response);
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
