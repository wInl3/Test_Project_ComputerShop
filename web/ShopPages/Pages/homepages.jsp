<%-- 
    Document   : homepages
    Created on : May 22, 2025, 11:04:18 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Shop | E-Shopper</title>
        <link href="${ctx}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/buildpc.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="${ctx}/ShopPages/Pages/images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-57-precomposed.png">
    </head><!--/head-->

    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="activePage" value="home"/>
        </jsp:include>

        <section>
            <div class="container">
                <div class="row">

                    <div class="col-sm-12 padding-right">

                        <!-- PC Section -->
                        <div class="features_items" id="pc">
                            <h2 class="title text-center" style="margin-top: 30px">Sale events</h2>
                            <div class="row">
                                <c:forEach var="s" items="${sale}">
                                    <div class="col-sm-4">
                                        <div class="card mb-4 shadow-sm">
                                            <a href="${ctx}/CategoriesController?service=detail&categoryID=${s.categoryID}">
                                                <img src="${pageContext.request.contextPath}/ShopPages/Pages/images/CatePicture/${s.imgURL}" 
                                                     class="card-img-top img-fluid" 
                                                     alt="${s.brandName}" 
                                                     style="height: 250px; object-fit: contain;">
                                                <div class="card-body text-center">
                                                    <h5 class="card-title">${s.brandName}</h5>
                                                    <p class="card-text">Category ID: ${s.categoryID}</p>
                                                    <p class="card-text text-secondary">Start: ${s.startDate} - End: ${s.endDate}</p>
                                                    <p class="card-text text-danger">
                                                        Original: <fmt:formatNumber value="${s.originalPrice}" type="currency"/>
                                                    </p>
                                                    <p class="card-text text-success fw-bold">
                                                        Discounted: <fmt:formatNumber value="${s.discountedPrice}" type="currency"/>
                                                    </p>
                                                    <p class="card-text">Created by: ${s.createdByName}</p>
                                                    <c:if test="${not empty s.approvedByName}">
                                                        <p class="card-text text-success">Approved by: ${s.approvedByName}</p>
                                                    </c:if>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>


                            </div>

                            <c:if test="${empty pcProducts}">
                                <p class="text-center">Không có sản phẩm nào!</p>
                            </c:if>

                            <!-- Only show pagination if data exists -->
                            <c:if test="${not empty pcProducts && totalPagesPC > 1}">
                                <div class="text-center">
                                    <ul class="pagination">
                                        <c:forEach begin="1" end="${totalPagesPC}" var="i">
                                            <li class="page-item ${i eq currentPagePC ? 'active' : ''}">
                                                <a class="page-link" href="HomePages?pagePC=${i}&pageLaptop=${currentPageLaptop}#pc">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>

                            <div class="category-tab">
                                <div class="col-sm-12 text-center">
                                    <a href="${pageContext.request.contextPath}/CategoriesController?service=filter&component=pc" class="btn btn-warning" style="margin-top: 20px;">VIEW MORE</a>
                                </div>
                            </div>
                        </div>


                        <!-- Laptop Section -->
                        <div class="features_items" id="laptop">
                            <h2 class="title text-center" style="margin-top: 30px">Build PC</h2>
                            <div class="row">
                                <c:forEach var="product" items="${laptopProducts}">
                                    <div class="col-sm-4">
                                        <div class="card mb-4 shadow-sm">
                                            <img src="${pageContext.request.contextPath}/ShopPages/Pages/images/CatePicture/${product.imgURL}" class="card-img-top img-fluid" alt="${product.categoryName}" style="height: 250px; object-fit: contain;">
                                            <div class="card-body text-center">
                                                <h5 class="card-title">${product.categoryName}</h5>
                                                <p class="card-text">Brand: ${product.brandName}</p>
                                                <p class="card-text text-success">Price: <fmt:formatNumber value="${product.price}" type="currency"/></p>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <c:if test="${empty laptopProducts}">
                                <p class="text-center">Không có sản phẩm nào!</p>
                            </c:if>

                            <c:if test="${not empty laptopProducts && totalPagesLaptop > 1}">
                                <div class="text-center">
                                    <ul class="pagination">
                                        <c:forEach begin="1" end="${totalPagesLaptop}" var="i">
                                            <li class="page-item ${i eq currentPageLaptop ? 'active' : ''}">
                                                <a class="page-link" href="HomePages?pageLaptop=${i}&pagePC=${currentPagePC}#laptop">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>

                            <div class="category-tab">
                                <div class="col-sm-12 text-center">
                                    <a href="${pageContext.request.contextPath}/CategoriesController?service=filter&component=laptop" class="btn btn-warning" style="margin-top: 20px;">VIEW MORE</a>
                                </div>
                            </div>
                        </div>

                    </div>


                </div>
            </div>

        </section>

        <%@ include file="components/footer.jsp" %>
        <script src="${ctx}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/bootstrap.min.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/jquery.scrollUp.min.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/price-range.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/jquery.prettyPhoto.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/main.js"></script>

        <!-- Kích hoạt carousel nếu cần -->
        <script>
            window.onload = function () {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.has('pagePC')) {
                    document.getElementById('pc').scrollIntoView({behavior: 'smooth'});
                } else if (urlParams.has('pageLaptop')) {
                    document.getElementById('laptop').scrollIntoView({behavior: 'smooth'});
                }
            }
            $(document).ready(function () {
                $('#slider-carousel').carousel(); // Khởi động carousel thủ công
            });
        </script>
    </body>
</html>
