package controller;

import dal.Blog_CateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Blog_Cate;
import models.Post;

@WebServlet(name = "Blog_CateServlet", urlPatterns = {"/blogc"})
public class Blog_CateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Blog_CateDAO dao = new Blog_CateDAO();
        List<Blog_Cate> categories = dao.getAllBlogCategory();
        String Bc_id_raw = request.getParameter("Bc_id");
        String searchKeyword = request.getParameter("searchKeyword");
        String sort = request.getParameter("sort");
        String page_raw = request.getParameter("page");

        int currentPage = 1;
        if (page_raw != null) {
            try {
                currentPage = Integer.parseInt(page_raw);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        List<Post> postList = null;
        int count = 0;

        try {
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                postList = dao.searchPostsByTitle(searchKeyword);
                count = postList.size();
            System.out.println("Keyword: " + searchKeyword);
            System.out.println("Search result size: " + postList.size());
            }else if (Bc_id_raw != null) {
                int Bc_id = Integer.parseInt(Bc_id_raw);
                postList = dao.getPostsByCategorySorted(Bc_id, sort);
                count = postList.size();
            } else {
                postList = dao.getPostsByPage(currentPage);
                count = dao.countAllPosts();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            postList = dao.getPostsByPage(currentPage);
            count = dao.countAllPosts();
        } catch (SQLException e) {
            Logger.getLogger(Blog_CateServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        int endPage = count / 4;
        if (count % 4 != 0) {
            endPage++;
        }

        List<Post> top5Posts = dao.getTop5NewestPosts();

        request.setAttribute("blog_categories", categories);
        request.setAttribute("postList", postList);
        request.setAttribute("endP", endPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("searchKeyword", searchKeyword);
        request.setAttribute("selectedSort", sort);
        request.setAttribute("top5Posts", top5Posts);

        request.getRequestDispatcher("ShopPages/Pages/blog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Tạm thời chưa xử lý POST
    }

    @Override
    public String getServletInfo() {
        return "Blog Category Servlet with pagination";
    }
}
