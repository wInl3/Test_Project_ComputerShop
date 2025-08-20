package controllerAdmin;

import dalAdmin.NotificationAdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import models.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dalAdmin.DBAdminContext;
import models.User;
import jakarta.servlet.http.HttpSession;
import dal.UserDAO;

/**
 *
 * @author Admin
 */
@WebServlet(name="NotificationServlet", urlPatterns={"/NotificationServlet"})
public class NotificationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String service = request.getParameter("service");
        NotificationAdminDAO dao = new NotificationAdminDAO();
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        int userID = (user != null) ? user.getUserId() : -1;
        
        if (service == null) {
            service = "list";
        }
        
        switch (service) {
            case "list": {
                // Hiển thị danh sách thông báo của user hiện tại
                List<Notification> userNotifications = (userID > 0) ? dao.getNotificationsByUserID(userID) : new ArrayList<>();
                request.setAttribute("notifications", userNotifications);
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/notifications.jsp").forward(request, response);
                break;
            }
                
            case "markAsRead": {
                // Đánh dấu thông báo đã đọc
                if (userID > 0) {
                    String notificationID = request.getParameter("notificationID");
                    if (notificationID != null) {
                        dao.markAsRead(Integer.parseInt(notificationID));
                    }
                }
                response.sendRedirect("NotificationServlet?service=list");
                break;
            }
                
            case "markAllAsRead": {
                // Đánh dấu tất cả thông báo đã đọc
                if (userID > 0) {
                    dao.markAllAsRead(userID);
                }
                response.sendRedirect("NotificationServlet?service=list");
                break;
            }
                
            case "delete": {
                // Xóa thông báo
                if (userID > 0) {
                    String deleteID = request.getParameter("notificationID");
                    if (deleteID != null) {
                        dao.deleteNotification(Integer.parseInt(deleteID));
                    }
                }
                response.sendRedirect("NotificationServlet?service=list");
                break;
            }
                
            case "getUnreadCount": {
                // Lấy số lượng thông báo chưa đọc (cho AJAX)
                int unreadCount = (userID > 0) ? dao.getUnreadCount(userID) : 0;
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print("{\"count\": " + unreadCount + "}");
                out.flush();
                break;
            }
                
            case "getUnreadNotifications": {
                // Lấy danh sách thông báo chưa đọc (cho dropdown)
                if (userID > 0) {
                    List<Notification> unreadNotifications = dao.getUnreadNotifications(userID);
                    request.setAttribute("unreadNotifications", unreadNotifications);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/notifications.jsp").forward(request, response);
                } else {
                    response.sendRedirect("NotificationServlet?service=list");
                }
                break;
            }
                
            case "showSendForm": {
                UserDAO userDao = new UserDAO();
                List<User> userList = new ArrayList<>();
                userList.addAll(userDao.getAllAdmins());
                userList.addAll(userDao.getAllSales());
                userList.addAll(userDao.getAllCustomers());
                userList.addAll(userDao.getAllShippers());
                request.setAttribute("userList", userList);
                request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/NotificationSend.jsp").forward(request, response);
                break;
            }
                
            case "send": {
                try {
                    String[] userIds = request.getParameterValues("userID");
                    boolean sendAll = "true".equals(request.getParameter("sendAll"));
                    String senderIdStr = request.getParameter("senderID");
                    String title = request.getParameter("title");
                    String message = request.getParameter("message");
                    System.out.println("[DEBUG] sendAll=" + sendAll + ", senderIdStr=" + senderIdStr + ", title=" + title + ", message=" + message);
                    List<Integer> targetUserIds = new ArrayList<>();
                    UserDAO userDao = new UserDAO();
                    if (sendAll) {
                        List<User> allUsers = new ArrayList<>();
                        allUsers.addAll(userDao.getAllAdmins());
                        allUsers.addAll(userDao.getAllSales());
                        allUsers.addAll(userDao.getAllCustomers());
                        allUsers.addAll(userDao.getAllShippers());
                        for (User u : allUsers) targetUserIds.add(u.getUserId());
                    } else if (userIds != null) {
                        for (String userIdStr : userIds) targetUserIds.add(Integer.parseInt(userIdStr));
                    }
                    int senderId = (senderIdStr != null && !senderIdStr.isEmpty()) ? Integer.parseInt(senderIdStr) : -1;
                    for (int userId : targetUserIds) {
                        Notification notification = new Notification();
                        notification.setUserID(userId);
                        notification.setSenderID(senderId);
                        notification.setTitle(title);
                        notification.setMessage(message);
                        notification.setIsRead(false);
                        notification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        boolean inserted = dao.addNotification(notification);
                        System.out.println("[DEBUG] Insert notification for userID=" + userId + ", senderID=" + senderId + ", title=" + title + ", message=" + message + ", result=" + inserted);
                    }
                    // Sau khi gửi, forward lại về form và truyền thông báo thành công
                    request.setAttribute("successMessage", "Đã gửi thông báo thành công!");
                    List<User> userList = new ArrayList<>();
                    userList.addAll(userDao.getAllAdmins());
                    userList.addAll(userDao.getAllSales());
                    userList.addAll(userDao.getAllCustomers());
                    userList.addAll(userDao.getAllShippers());
                    request.setAttribute("userList", userList);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/NotificationSend.jsp").forward(request, response);
                } catch (Exception e) {
                    System.err.println("[DEBUG] Exception in send: " + e.getMessage());
                    response.sendRedirect("NotificationServlet?service=showSendForm");
                }
                break;
            }
                
            case "detail": {
                // Xem chi tiết thông báo
                String id = request.getParameter("id");
                if (id != null) {
                    Notification notification = dao.getNotificationById(Integer.parseInt(id));
                    request.setAttribute("notification", notification);
                    request.getRequestDispatcher("AdminLTE/AdminPages/pages/tables/NotificationDetail.jsp").forward(request, response);
                } else {
                    response.sendRedirect("NotificationServlet?service=list");
                }
                break;
            }
                
            case "toggleRead": {
                // Chuyển đổi trạng thái đã đọc/chưa đọc
                String toggleId = request.getParameter("id");
                if (toggleId != null) {
                    int nid = Integer.parseInt(toggleId);
                    boolean currentStatus = dao.getReadStatus(nid);
                    dao.updateReadStatus(nid, !currentStatus);
                }
                response.sendRedirect("NotificationServlet?service=list");
                break;
            }
                
            case "ajaxList": {
                // Trả về JSON danh sách thông báo chưa đọc cho user hiện tại
                int totalUnread = (userID > 0) ? dao.getUnreadCount(userID) : 0;
                List<Notification> notifications = (userID > 0) ? dao.getUnreadNotifications(userID) : new ArrayList<>();
                if (notifications.size() > 5) {
                    notifications = notifications.subList(0, 5);
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append("\"totalUnread\":").append(totalUnread).append(",");
                json.append("\"notifications\": [");
                for (int i = 0; i < notifications.size(); i++) {
                    Notification n = notifications.get(i);
                    json.append("{")
                        .append("\"id\":").append(n.getNotificationID()).append(",")
                        .append("\"title\":\"").append(n.getTitle().replace("\"", "\\\"")).append("\",")
                        .append("\"message\":\"").append(n.getMessage().replace("\"", "\\\"")).append("\",")
                        .append("\"createdAt\":\"").append(n.getCreatedAt()).append("\"")
                        .append("}");
                    if (i < notifications.size() - 1) json.append(",");
                }
                json.append("]}");
                out.print(json.toString());
                out.flush();
                break;
            }
                
            default:
                response.sendRedirect("NotificationServlet?service=list");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}