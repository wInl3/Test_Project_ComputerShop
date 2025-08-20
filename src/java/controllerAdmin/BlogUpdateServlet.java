package controllerAdmin;

import dal.Blog_CateDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import models.Blog_Cate;
import models.Post;

@WebServlet(name = "BlogUpdateServlet", urlPatterns = {"/blogupdate"})
public class BlogUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String post_id_raw = request.getParameter("post_id");
        Blog_CateDAO dao = new Blog_CateDAO();
        try {
            int post_id = Integer.parseInt(post_id_raw);
            Post post = dao.getPostById(post_id);
            request.setAttribute("post", post);

            List<Blog_Cate> categories = dao.getAllBlogCategory();
            request.setAttribute("blog_categories", categories);

            request.getRequestDispatcher("/AdminLTE/AdminPages/pages/forms/update-post.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("blogAdmin?error=Invalid+Post+ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            int post_id = Integer.parseInt(request.getParameter("Post_id"));
            String title = request.getParameter("Title");
            String author = request.getParameter("Author");
            String content = request.getParameter("Content");
            String thumbnail = request.getParameter("Thumbnail");
            int bc_id = Integer.parseInt(request.getParameter("Bc_id"));
            String brief = request.getParameter("Brief");
            int status = Integer.parseInt(request.getParameter("status"));
            Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

            // Tạo đối tượng Post
            Post updatedPost = new Post(post_id, title, author, updatedTime, content,
                    bc_id, thumbnail, brief);
            updatedPost.setStatus(status);

            // Gọi DAO để update
            Blog_CateDAO dao = new Blog_CateDAO();
            dao.updatePost(updatedPost);

            response.sendRedirect("bloga");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Update failed: " + e.getMessage());
            doGet(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles blog post updating";
    }
}
