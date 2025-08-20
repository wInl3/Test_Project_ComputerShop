<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%
    Set<Integer> renderedOrderIds = new HashSet<>();
    request.setAttribute("renderedOrderIds", renderedOrderIds);
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Build PC Order History</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css?v=1.0.22" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/ShopPages/Pages/components/header.jsp"/>

        <section id="order_details">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/HomePages">Home</a></li>
                        <li class="active">Build PC Order History</li>
                    </ol>
                </div>

                <h2 class="text-center" style="margin-bottom: 30px;">Build PC Order History</h2>
                <div class="filter-container" style="text-align: center; margin-bottom: 30px;">
                    <div class="row justify-content-center">
                        <!-- Loại đơn hàng -->
                        <div class="col-md-6 col-sm-12">
                            <select class="form-control" onchange="location.href = this.value;">
                               
                                <option value="${pageContext.request.contextPath}/OrderHistory2"
                                        ${fn:endsWith(pageContext.request.requestURI, 'OrderHistory2') ? 'selected' : ''}>
                                   ️ Build PC Orders
                                </option>
                                 <option value="${pageContext.request.contextPath}/OrderHistory"
                                        ${fn:endsWith(pageContext.request.requestURI, 'OrderHistory') ? 'selected' : ''}>
                                   Normal Orders
                                </option>
                            </select>
                        </div>

                        <!-- Trạng thái đơn hàng -->
                        <div class="col-md-6 col-sm-12">
                            <form method="get" action="OrderHistory2">
                                <div class="select-wrapper">
                                    <select name="status" class="form-control" onchange="this.form.submit()">
                                        <option value="-1" ${selectedStatus == -1 ? 'selected' : ''}>All</option>
                                        <option value="0" ${selectedStatus == 0 ? 'selected' : ''}>Reject</option>
                                        <option value="1" ${selectedStatus == 1 ? 'selected' : ''}>Waiting Confirm</option>
                                        <option value="2" ${selectedStatus == 2 ? 'selected' : ''}>Process</option>
                                        <option value="3" ${selectedStatus == 3 ? 'selected' : ''}>Waiting Ship</option>
                                        <option value="4" ${selectedStatus == 4 ? 'selected' : ''}>On Shipping</option>
                                        <option value="5" ${selectedStatus == 5 ? 'selected' : ''}>Complete</option>
                                    </select>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${not empty orders}">
                        <c:forEach var="order" items="${orders}">
                            <c:set var="currentOrderId" value="${order.orderId}" />
                            <c:if test="${not renderedOrderIds.contains(currentOrderId)}">
                                <%
                                    ((Set<Integer>) request.getAttribute("renderedOrderIds"))
                                        .add((Integer) pageContext.getAttribute("currentOrderId"));
                                %>

                                <div class="panel panel-default" style="margin-bottom: 25px;">
                                    <div class="panel-heading">
                                        <strong>Order Code:</strong> ${order.orderCode} |
                                        <strong>Date:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /> |
                                        <strong>Status:</strong>
                                        <c:choose>
                                            <c:when test="${order.buildPcStatus == 0}">Reject</c:when>
                                            <c:when test="${order.buildPcStatus == 1}">Waiting Confirm</c:when>
                                            <c:when test="${order.buildPcStatus == 2}">Process</c:when>
                                            <c:when test="${order.buildPcStatus == 3}">Waiting Ship</c:when>
                                            <c:when test="${order.buildPcStatus == 4}">On Ship</c:when>
                                            <c:when test="${order.buildPcStatus == 5}">Complete</c:when>
                                            <c:otherwise>Unknown</c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p><strong>Receiver:</strong> ${order.fullName}</p>
                                                <p><strong>Phone:</strong> ${order.userPhone}</p>
                                                <p><strong>Address:</strong> ${order.address}</p>
                                                <p><strong>Note:</strong> ${order.note}</p>
                                            </div>
                                            <div class="col-md-6 text-right">
                                                <p><strong>Payment:</strong>
                                                    <c:choose>
                                                        <c:when test="${order.paymentStatusId == 1}">💵 Unpaid</c:when>
                                                        <c:when test="${order.paymentStatusId == 2}">💳 Paid</c:when>
                                                        <c:otherwise>Unknown</c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <p><strong>Total:</strong>
                                                    <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> VND
                                                </p>
                                                <a href="OrderDetailBuildPC?orderID=${order.orderId}" class="btn btn-info">View Details</a>
                                            </div>
                                        </div>

                                        <hr>
                                        <table class="table table-striped table-hover table-bordered">
                                            <thead style="background-color: #eee;">
                                                <tr>
                                                    <th>Component</th>
                                                    <th>Brand</th>
                                                    <th>Category</th>
                                                    <th>Quantity</th>
                                                    <th>Unit Price</th>
                                                    <th>Subtotal</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="item" items="${orders}">
                                                    <c:if test="${item.orderId == currentOrderId}">
                                                        <tr>
                                                            <td>${item.componentName}</td>
                                                            <td>${item.brandName}</td>
                                                            <td>${item.cateName}</td>
                                                            <td>${item.quantity}</td>
                                                            <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VND</td>
                                                            <td><fmt:formatNumber value="${item.quantity * item.price}" type="number" groupingUsed="true"/> VND</td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-warning text-center" style="margin-top: 40px; font-size: 18px;">
                            🚫 No Build PC orders found.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>

        <jsp:include page="/ShopPages/Pages/components/footer.jsp"/>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
    </body>
</html>