package controller;

import dal.Blog_CateDAO;
import java.io.IOException;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Blog_Cate;
import models.Comment;
import models.Post;

@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/blogdetail"})
public class BlogDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String Bc_id_raw = request.getParameter("Bc_id");
        String Post_id_raw = request.getParameter("Post_id");

        Blog_CateDAO dao = new Blog_CateDAO();
        Post postDetail = null;
        List<Comment> nestedComments = null;

        try {
            if (Bc_id_raw != null && !Bc_id_raw.isEmpty()) {
                int Bc_id = Integer.parseInt(Bc_id_raw);
                List<Post> postList = dao.getPostsByCategoryId(Bc_id);
                request.setAttribute("postList", postList);
            }

            int Post_id = Integer.parseInt(Post_id_raw);
            postDetail = dao.getPostById(Post_id);

            // Lấy danh sách comment theo Post_id
            List<Comment> flatList = dao.getCommentsByPostId(Post_id);
            nestedComments = dao.buildNestedComments(flatList);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        request.setAttribute("post", postDetail);
        request.setAttribute("comments", nestedComments);

        List<Blog_Cate> categories = dao.getAllBlogCategory();
        List<Post> top5Posts = dao.getTop5NewestPosts();

        request.setAttribute("blog_categories", categories);
        request.setAttribute("top5Posts", top5Posts);

        request.getRequestDispatcher("ShopPages/Pages/blog_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String Post_idRaw = request.getParameter("Post_id");
        String Bc_idRaw = request.getParameter("Bc_id");
        String commentText = request.getParameter("comment_text");
        String ParentCommentIDRaw = request.getParameter("ParentCommentID");

        int userId = 3; // giả sử user đang login

        try {
            int Post_id = Integer.parseInt(Post_idRaw);
            Integer ParentCommentID = null;

            if (ParentCommentIDRaw != null && !ParentCommentIDRaw.isEmpty()) {
                ParentCommentID = Integer.parseInt(ParentCommentIDRaw);
            }

            Comment comment = new Comment();
            comment.setPost_id(Post_id);
            comment.setUserID(userId);
            comment.setCommentText(commentText);
            comment.setParentCommentID(ParentCommentID);
            comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            Blog_CateDAO dao = new Blog_CateDAO();
            dao.addComment(comment);

            if (Bc_idRaw != null && !Bc_idRaw.isEmpty()) {
                response.sendRedirect("blogdetail?Post_id=" + Post_id + "&Bc_id=" + Bc_idRaw);
            } else {
                response.sendRedirect("blogdetail?Post_id=" + Post_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Blog Detail Servlet";
    }
}
