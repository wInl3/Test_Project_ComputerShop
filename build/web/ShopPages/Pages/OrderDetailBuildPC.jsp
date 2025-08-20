<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    java.util.Calendar cal = java.util.Calendar.getInstance();
    models.OrderCate orderObj = (models.OrderCate) request.getAttribute("order");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Cart | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css?v=1.0.22" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            .cart-card-header {
                display: flex;
                gap: 20px;
                align-items: flex-start;
            }

            .card-left {
                flex: 0 0 250px;  /* Cố định width */
                width: 250px;     /* Cố định width */
                word-break: break-word;
            }

            .card-right {
                flex: 1;          /* Chiếm phần còn lại */
                max-width: none;  /* Xoá max-width 70% */
            }

            .card-left img {
                max-width: 100%;
                height: auto;
            }

            .category-name {
                word-wrap: break-word;
            }

            .card-right {
                flex: 0 0 70%;
                max-width: 100%;
            }

            /* ===== ORDER TRACKING ===== */
            .order-steps {
                display: flex;
                justify-content: space-between;
                margin: 30px 0;
                list-style: none;
                padding-left: 0;
                gap: 20px;
            }

            .order-steps li {
                flex: 1;
                text-align: center;
                position: relative;
                color: #aaa;
                font-weight: 400;
                padding: 0 5px;
            }

            .order-steps li::before {
                content: "";
                position: absolute;
                top: 20px;
                left: 0;
                right: 0;
                height: 4px;
                background-color: #ccc;
                z-index: -1;
                margin: 0 auto;
                width: 100%;
            }

            /* ✅ Đã hoàn thành */
            .order-steps li.completed {
                color: #28a745;
                font-weight: bold;
            }

            .order-steps li.completed::before {
                background-color: #28a745;
            }

            .order-steps li.completed::after {
                content: "✓";
                display: block;
                margin-top: 5px;
                color: #28a745;
                font-weight: bold;
            }

            /* ✅ Đang thực hiện */
            .order-steps li.current {
                color: #ffc107;
                font-weight: bold;
            }

            .order-steps li.current::before {
                background-color: #ffc107;
            }

            .order-steps li.current::after {
                content: "⟳";
                display: block;
                margin-top: 5px;
                color: #ffc107;
                font-weight: bold;
            }

    
            .reject-step {
                text-align: center;
                color: #dc3545;
                font-weight: bold;
                font-size: 18px;
                margin: 30px 0;
            }

            .reject-step::after {
                content: "✘";
                display: block;
                font-size: 24px;
                color: #dc3545;
                margin-top: 5px;
            }

            @media (max-width: 768px) {
                .cart-card-header {
                    display: flex;
                    flex-direction: column; /* chiều dọc */
                    gap: 16px;
                }

                .card-right {
                    display: none !important; /* Ẩn card-right trên mobile */
                }

                .responsive-card {
                    display: block;
                    background-color: #fff;
                    border: 1px solid #ccc;
                    border-radius: 12px;
                    padding: 20px;
                    margin: 20px 0;
                    box-shadow: 0 2px 10px rgba(0,0,0,0.05);
                    max-width: 95%;
                    margin-left: auto;
                    margin-right: auto;
                }

                .responsive-card h5 {
                    text-align: center;
                    margin-bottom: 16px;
                    font-size: 16px;
                    font-weight: 600;
                    color: #333;
                }

                .responsive-card .rc-item {
                    margin-bottom: 12px;
                    font-size: 14px;
                    line-height: 1.6;
                }

                .responsive-card .rc-item strong {
                    display: inline-block;
                    min-width: 140px;
                    color: #222;
                    font-weight: 600;
                }

                .responsive-card .btn {
                    padding: 6px 14px;
                    font-size: 13px;
                    border-radius: 6px;
                    font-weight: 500;
                }
            }

            @media (min-width: 769px) {
                .responsive-card {
                    display: none;
                }
            }
            .error {
                color: red;
                font-weight: bold;
            }
            .message {
                color: green;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <jsp:include page="/ShopPages/Pages/components/header.jsp"/>

        <section class="container">
            <div class="breadcrumbs">
                <ol class="breadcrumb">
                    <li><a href="OrderHistory2">Build PC Order History</a></li>
                    <li class="active">Order Detail</li>
                </ol>
            </div>

            <c:if test="${not empty orderDetail}">
                <h3 class="mb-3">Order Status</h3>
                <p>Payment status:
                    <c:choose>
                        <c:when test="${orderDetail[0].paymentStatusId == 1}">Not Paid</c:when>
                        <c:when test="${orderDetail[0].paymentStatusId == 2}">Paid</c:when>
                        <c:otherwise>Unknown</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Order Date:</strong> <fmt:formatDate value="${orderDetail[0].orderDate}" pattern="HH:mm dd/MM/yyyy"/></p>

                <ul class="order-steps">
                    <c:set var="currentStatus" value="${orderDetail[0].buildPcStatus}" />

                   <c:if test="${currentStatus == 0}">
                        <li class="reject-step">Rejected</li>
                        </c:if>

                    <c:if test="${currentStatus != 0}">
                        <c:forEach var="step" begin="1" end="5">
                            <c:set var="stepClass" value="" />
                            <c:choose>
      
                                <c:when test="${step < currentStatus}">
                                    <c:set var="stepClass" value="completed" />
                                </c:when>

                                <c:when test="${step == currentStatus && currentStatus == 5}">
                                    <c:set var="stepClass" value="completed" />
                                </c:when>

                                <c:when test="${step == currentStatus}">
                                    <c:set var="stepClass" value="current" />
                                </c:when>
                            </c:choose>

                            <li class="${stepClass}">
                                <c:choose>
                                    <c:when test="${step == 1}">Pending</c:when>
                                    <c:when test="${step == 2}">On-progress</c:when>
                                    <c:when test="${step == 3}">Waiting for ship</c:when>
                                    <c:when test="${step == 4}">On Ship</c:when>
                                    <c:when test="${step == 5}">Complete</c:when>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </c:if>
                </ul>



                <div class="review-payment">
                    <h2>Order Details</h2>
                </div>

                <c:forEach var="item" items="${orderDetail}">
                    <div class="detail-card">
                        <div class="row">
                            <div class="col-md-3">
                                <img src="${pageContext.request.contextPath}/ShopPages/Pages/images/CatePicture/${item.imgUrl}" alt="${item.componentName}" class="img-responsive">
                                <h4>${item.cateName}</h4>

                                <a href="${pageContext.request.contextPath}/CategoriesController?service=detail&categoryID=${item.cateId}" class="btn btn-success">
                                    Buy new one
                                </a>

                                <c:if test="${item.buildPcStatus == 5}">
                                    <a href="${pageContext.request.contextPath}/feedback?orderBuildPCDetailID=${item.orderBuildPcItemId}" class="btn btn-success">
                                        Feedback
                                    </a>
                                </c:if>
                            </div>
                            <div class="col-md-9">
                                <h5>Order Detail</h5>
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Product Code</th>
                                            <th>Unit Price</th>
                                            <th>Warranty Price</th>
                                            <th>Warranty Period</th>
                                            <th>Warranty Desc</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>${item.productCode != null ? item.productCode : "N/A"}</td>
                                            <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VND</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${item.warrantyPrice != null}">
                                                        <fmt:formatNumber value="${item.warrantyPrice}" type="number" groupingUsed="true"/> VND
                                                    </c:when>
                                                    <c:otherwise>0 VND</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${item.warrantyPeriod} Months</td>
                                            <td>${item.warrantyDesc}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${item.productStatus == 0}">Delivered</c:when>
                                                    <c:when test="${item.productStatus == 1}">Not Prepared</c:when>
                                                    <c:when test="${item.productStatus == 2}">Under Warranty</c:when>
                                                    <c:otherwise>Preparing</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:set var="subTotal" value="0" />
                <c:forEach var="item" items="${orderDetail}">
                    <c:set var="itemTotal" value="${item.price + (item.warrantyPrice != null ? item.warrantyPrice : 0)}" />
                    <c:set var="subTotal" value="${subTotal + itemTotal}" />
                </c:forEach>
                <table class="table table-condensed total-result">
                    <tr>
                        <td>Sub Total</td>
                        <td><fmt:formatNumber value="${subTotal}" type="number" groupingUsed="true"/> VND</td>
                    </tr>
                    <tr class="shipping-cost">
                        <td>Shipping Cost</td>
                        <td>Free</td>
                    </tr>
                    <tr>
                        <td><strong>Grand Total</strong></td>
                        <td><strong><fmt:formatNumber value="${subTotal}" type="number" groupingUsed="true"/> VND</strong></td>
                    </tr>
                </table>


                <div class="shopper-informations mb-4">
                    <div class="bill-to">
                        <h4>Receiver's Information</h4>
                        <p><strong>Name:</strong> ${orderDetail[0].fullName}</p>
                        <p><strong>Phone:</strong> ${orderDetail[0].userPhone}</p>
                        <p><strong>Address:</strong> ${orderDetail[0].address}</p>
                        <p><strong>Note:</strong> <c:out value="${orderDetail[0].note}" default="No note"/></p>
                    </div>
                </div>
            </c:if>
        </section>

        <jsp:include page="/ShopPages/Pages/components/footer.jsp"/>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
    </body>
</html>
