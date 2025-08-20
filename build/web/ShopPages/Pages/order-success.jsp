<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Successful | E-Shopper</title>
    <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css" rel="stylesheet">
    <style>
        .order-success-container {
            background: #fff;
            border: 1px solid #ddd;
            padding: 40px 30px;
            margin-top: 40px;
            text-align: center;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        }
        .order-success-icon {
            font-size: 80px;
            color: #28a745;
        }
        .order-success-container h2 {
            margin-top: 20px;
            color: #28a745;
        }
        .order-success-container p {
            font-size: 16px;
            color: #555;
            margin: 20px 0;
        }
        .btn-return-home {
            background-color: #007bff;
            color: white;
            padding: 12px 28px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
        }
        .btn-return-home:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<jsp:include page="/ShopPages/Pages/components/header.jsp" />

<section id="cart_items">
    <div class="container">
        <div class="breadcrumbs">
            <ol class="breadcrumb mb-1">
                <li><a href="${pageContext.request.contextPath}/HomePages">Home</a></li>
                <li class="active">Order Success</li>
            </ol>
        </div>

        <div class="order-success-container">
            <div class="order-success-icon">
                <i class="fa fa-check-circle"></i>
            </div>
            <h2>Order Placed Successfully!</h2>
            <p>Thank you for shopping with us.<br>Your order has been received and is being processed.</p>
            <a href="${pageContext.request.contextPath}/HomePages" class="btn-return-home">Continue Shopping</a>
        </div>
    </div>
</section>

<jsp:include page="/ShopPages/Pages/components/footer.jsp" />

<script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
</body>
</html>
