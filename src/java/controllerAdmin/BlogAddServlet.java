package controllerAdmin;

import dal.Blog_CateDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Blog_Cate;
import models.Post;
import models.User;

@WebServlet(name = "BlogAddServlet", urlPatterns = {"/blogadd"})
public class BlogAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Blog_CateDAO dao = new Blog_CateDAO();
        List<Blog_Cate> blog_categories = dao.getAllBlogCategory();
        request.setAttribute("blog_categories", blog_categories);
        request.getRequestDispatcher("/AdminLTE/AdminPages/pages/forms/insertBlog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String updatedDateStr = request.getParameter("Updated_date");
            String content = request.getParameter("content");
            String thumbnail = request.getParameter("thumbnail");
            String bc_id_raw = request.getParameter("bc_id");
            String brief = request.getParameter("brief");
            String status_raw = request.getParameter("status");

            // Lấy người dùng từ session
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                throw new IllegalArgumentException("Bạn cần đăng nhập để thực hiện thao tác này.");
            }

            int bc_id = Integer.parseInt(bc_id_raw);
            int add_id = currentUser.getUserId();
            int status = (status_raw != null && !status_raw.isEmpty()) ? Integer.parseInt(status_raw) : 1;

            // Chuyển ngày string -> timestamp
            Timestamp updatedDate = Timestamp.valueOf(updatedDateStr + " 00:00:00");

            // Tạo đối tượng post
            Post newPost = new Post(title, author, updatedDate, content, bc_id, thumbnail, brief, add_id, status);

            // Lưu vào DB
            Blog_CateDAO dao = new Blog_CateDAO();
            dao.insertPost(newPost);

            response.sendRedirect(request.getContextPath() + "/bloga");

        } catch (Exception e) {
            e.printStackTrace();
            Blog_CateDAO dao = new Blog_CateDAO();
            List<Blog_Cate> blog_categories = dao.getAllBlogCategory();
            request.setAttribute("blog_categories", blog_categories);
            request.setAttribute("error", "Lỗi khi thêm blog: " + e.getMessage());
            request.getRequestDispatcher("/AdminLTE/AdminPages/pages/forms/insertBlog.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles blog post creation";
    }
}
