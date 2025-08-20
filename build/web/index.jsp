<%-- 
    Document   : index
    Created on : May 23, 2025, 11:58:49 AM
    Author     : PC ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="models.User" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Object u = session.getAttribute("user");
            if (u == null || ((User) u).getRole().getRoleID() == 3) {
                response.sendRedirect(request.getContextPath() + "/HomePages");
            } else {
                response.sendRedirect(request.getContextPath() + "/AdminDashbordServlet");
            }
        %>
    </body>
</html>
