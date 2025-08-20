<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    // Lấy notification từ request attribute
    models.Notification notification = (models.Notification) request.getAttribute("notification");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Notification Detail</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Notification Detail</h2>
    <table class="table table-bordered">
        <tr><th>ID</th><td><%= notification.getNotificationID() %></td></tr>
        <tr><th>Title</th><td><%= notification.getTitle() %></td></tr>
        <tr><th>Sender</th><td><%= notification.getSenderName() %></td></tr>
        <tr><th>Status</th><td><%= notification.isIsRead() ? "Read" : "Unread" %></td></tr>
        <tr><th>Created At</th><td><fmt:formatDate value="<%= notification.getCreatedAt() %>" pattern="dd/MM/yyyy HH:mm"/></td></tr>
        <tr><th>Message</th><td><%= notification.getMessage() %></td></tr>
    </table>
    <a href="${pageContext.request.contextPath}/NotificationServlet?service=list" class="btn btn-default">Back to List</a>
</div>
</body>
</html> 