<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
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

        <!-- Bộ lọc trạng thái -->
        <div class="filter-container text-center" style="margin-bottom: 30px;">
            <form method="get" action="OrderHistory2">
                <div class="select-wrapper">
                    <select name="status" onchange="this.form.submit()">
                        <option value="-1" ${selectedStatus == -1 ? 'selected' : ''}>All</option>
                        <option value="0" ${selectedStatus == 0 ? 'selected' : ''}>Pending</option>
                        <option value="1" ${selectedStatus == 1 ? 'selected' : ''}>Processing</option>
                        <option value="2" ${selectedStatus == 2 ? 'selected' : ''}>Shipping</option>
                        <option value="3" ${selectedStatus == 3 ? 'selected' : ''}>Completed</option>
                        <option value="4" ${selectedStatus == 4 ? 'selected' : ''}>Cancelled</option>
                    </select>
                </div>
            </form>
        </div>

        <!-- Danh sách đơn hàng -->
        <c:choose>
            <c:when test="${not empty orders}">
                <div class="container">
                    <c:forEach var="order" items="${orders}">
                        <c:if test="${order.buildPcId != 0}"> <!-- Đảm bảo chỉ hiển thị Build PC thực sự -->

                        <div class="panel panel-default" style="margin-bottom: 25px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); border-radius: 8px;">
                            <div class="panel-heading" style="background-color: #f5f5f5; border-bottom: 1px solid #ddd; border-radius: 8px 8px 0 0;">
                                <strong>Order Code:</strong> ${order.orderCode} |
                                <strong>Date:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /> |
                                <strong>Status: 
                                    <c:choose>
                                        <c:when test="${order.buildPcStatus == 0}">Pending</c:when>
                                        <c:when test="${order.buildPcStatus == 1}">Processing</c:when>
                                        <c:when test="${order.buildPcStatus == 2}">Shipping</c:when>
                                        <c:when test="${order.buildPcStatus == 3}">Completed</c:when>
                                        <c:when test="${order.buildPcStatus == 4}">Cancelled</c:when>
                                        <c:otherwise>Unknown</c:otherwise>
                                    </c:choose>
                                </strong>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>Receiver:</strong> ${order.fullName}</p>
                                        <p><strong>Phone:</strong> ${order.phoneNumber}</p>
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
                                            <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true" /> VND
                                        </p>
                                        <a href="OrderHistory2?orderID=${order.orderId}" class="btn btn-info">View Details</a>
                                    </div>
                                </div>

                                <hr>
                                <table class="table table-striped table-hover table-bordered">
                                    <thead style="background-color: #eee;">
                                        <tr>
                                            <th>Component</th>
                                            <th>Brand</th>
                                            <th>Category</th>
                                            <th>Unit Price</th>
                                            <th>Quantity</th>
                                            <th>Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>${order.componentName}</td>
                                            <td>${order.brandName}</td>
                                            <td>${order.cateName}</td>
                                            <td><fmt:formatNumber value="${order.price}" type="number" groupingUsed="true" /> VND</td>
                                            <td>${order.quantity}</td>
                                            <td><fmt:formatNumber value="${order.quantity * order.price}" type="number" groupingUsed="true" /> VND</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        </c:if>
                    </c:forEach>
                </div>
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
