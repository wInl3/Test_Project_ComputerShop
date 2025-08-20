<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Brand List</title>
    </head>
    <body>
        <div >
            <h3>Brand List</h3>
            <table >
                <thead>
                    <tr>
                        <th>Brand ID</th>
                        <th>Brand Name</th>
                        <th>Quantity</th>
                        <th>Status</th>
                        <th>Update</th>
                        <th>View</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty bra}">
                        <c:forEach var="brand" items="${bra}">
                            <tr>
                                <td>${brand.brandID}</td>
                                <td>${brand.brandName}</td>
                                <td>${brand.quantity}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${brand.status == 0}">Disable</c:when>
                                        <c:when test="${brand.status == 1}">Active</c:when>
                                        <c:otherwise>Unknown</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="BrandAdmin?service=update&brandID=${brand.brandID}" 
                                       class="btn btn-warning btn-sm">Update</a>
                                </td>
                                <td>
                                    <a href="BraComAdmin?service=listbybrand&brandID=${brand.brandID}" 
                                       class="btn btn-info btn-sm">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty bra}">
                        <tr><td colspan="6" style="text-align:center;">No brands found.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </body>
</html>
