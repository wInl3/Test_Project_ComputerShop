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
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css?v=1.0.15" rel="stylesheet">
        <style>
            .product-card {
                height: 400px;                /* 👈 chỉnh chiều cao khung sản phẩm */
                border: 1px solid #eee;
                border-radius: 6px;
                padding: 10px;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                transition: box-shadow 0.3s;
                background: #fff;
            }

            .product-card:hover {
                box-shadow: 0 2px 8px rgba(0,0,0,0.15);
            }

            .product-card img {
                width: 100%;
                height: 240px;              
                object-fit: cover;
                margin-bottom: 10px;
            }

            .product-card h2 {
                font-size: 18px;
                color: #FE980F;
                margin-bottom: 8px;
            }

            .product-card p {
                font-size: 14px;
                color: #333;
                min-height: 60px;          
                overflow: hidden;
            }
        </style>
    </head><!--/head-->

    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="activePage" value="products"/>
        </jsp:include>


        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <h2 class="title text-center">CATEGORY</h2>
                            <div class="panel-group category-products" id="accordian">
                                <c:forEach var="comp" items="${Components}">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title" style="display: flex; justify-content: space-between; align-items: center;">
                                                <!-- Link: lọc theo Component -->
                                                <a href="${ctx}/CategoriesController?service=filter&component=${fn:escapeXml(comp.componentName)}" style="flex-grow: 1;">
                                                    ${comp.componentName}
                                                </a>

                                                <!-- Dấu + để mở brand list -->
                                                <a data-toggle="collapse" href="#comp${comp.componentID}" style="margin-left: 10px;">
                                                    <i class="fa fa-plus"></i> <!-- luôn là dấu + -->
                                                </a>
                                            </h4>
                                        </div>

                                        <!-- Brand list bên trong component -->
                                        <div id="comp${comp.componentID}" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <ul>
                                                    <c:forEach var="item" items="${BrandWithComponent}">
                                                        <c:if test="${item.componentID == comp.componentID}">
                                                            <li>
                                                                <a href="${ctx}/CategoriesController?service=filter&component=${fn:escapeXml(comp.componentName)}&brand=${fn:escapeXml(item.brandName)}">
                                                                    ${item.brandName}
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>



                            <div class="brands_products"><!--brands_products-->
                                <h2>Brands</h2>
                                <div class="brands-name">
                                    <ul class="nav nav-pills nav-stacked">
                                        <c:forEach var="brand" items="${listBrand}">
                                            <li>
                                                <a href="${ctx}/CategoriesController?service=filter&brand=${fn:escapeXml(brand.brandName)}">
                                                    ${brand.brandName}
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div><!--/brands_products-->

                            <div class="brands_products"><!-- price range styled like brands box -->
                                <h2 style="color: orange; font-weight: bold; text-align: center; position: relative;">
                                    <span style="background: #fff; padding: 0 10px; z-index: 2; position: relative;">PRICE RANGE</span>
                                </h2>
                                <div class="brands-name" style="border: 1px solid #f0f0f0; padding: 20px;">
                                    <form action="${ctx}/CategoriesController" method="get">
                                        <input type="hidden" name="service" value="filter" />
                                        <input type="hidden" name="component" value="${currentComponent}" />
                                        <input type="hidden" name="brand" value="${currentBrand}" />

                                        <div class="form-group">
                                            <label style="font-weight: bold;">Min Price:</label>
                                            <input type="number" name="minPrice" class="form-control"
                                                   placeholder="e.g. 10000000"
                                                   value="${not empty minPrice ? minPrice : ''}" min="0" />
                                        </div>

                                        <div class="form-group" style="margin-top: 10px;">
                                            <label style="font-weight: bold;">Max Price:</label>
                                            <input type="number" name="maxPrice" class="form-control"
                                                   placeholder="e.g. 50000000"
                                                   value="${not empty maxPrice ? maxPrice : ''}" min="0" />
                                        </div>

                                        <button type="submit" class="btn btn-warning btn-block" style="margin-top: 15px;">
                                            Search by Price
                                        </button>
                                    </form>
                                </div>
                            </div><!-- /price-range -->

                            <!-- build pc -->
                            <div class="brands_products"><!-- build pc -->
                                <h2 style="color: green; font-weight: bold; text-align: center; position: relative;">
                                    <span style="background: #fff; padding: 0 10px; z-index: 2; position: relative;">BUILD PC</span>
                                </h2>
                                <div class="brands-name" style="border: 1px solid #f0f0f0; padding: 20px;">
                                    <ul class="nav nav-pills nav-stacked">
                                        <li>
                                            <a href="${ctx}/ShopPages/Pages/BuildPC/BuildPC.html">
                                                <i class="fa fa-cogs"></i> Customize Your PC
                                            </a>
                                        </li>
                                        <li>
                                            <a href="${ctx}/ShopPages/Pages/HomePages">
                                                <i class="fa fa-book"></i> Build Guide
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div><!-- /build pc -->


                        </div>
                    </div>

                    <div class="col-sm-9 padding-right">
                        <div class="features_items"><!--features_items-->
                            <h2 class="title text-center">Products </h2>

                            <c:forEach var="product" items="${requestScope.data}">
                                <div class="col-sm-4 mb-4">
                                    <div class="product-card text-center">
                                        <a href="${ctx}/CategoriesController?service=detail&categoryID=${product.categoryID}">
                                            <img src="${ctx}/ShopPages/Pages/images/CatePicture/${product.imgURL}" alt="${product.categoryName}" />
                                            <h2>
                                                <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" /> VND
                                            </h2>
                                            <p>${product.categoryName}</p>
                                        </a>
                                    </div>
                                </div>

                            </c:forEach>

                            <c:if test="${empty requestScope.data}">
                                <p>Không có sản phẩm nào!</p>
                            </c:if>

                            <c:if test="${totalPages gt 1}">
                                <div class="pagination-area text-center" style="margin-top: 40px; clear: both;">
                                    <ul class="pagination" style="display: inline-block; float: none;">
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <c:url var="pageURL" value="CategoriesController">
                                                <c:param name="service" value="filter" />
                                                <c:param name="page" value="${i}" />
                                                <c:if test="${not empty currentBrand}">
                                                    <c:param name="brand" value="${currentBrand}" />
                                                </c:if>
                                                <c:if test="${not empty currentComponent}">
                                                    <c:param name="component" value="${currentComponent}" />
                                                </c:if>
                                                <c:if test="${not empty currentKeyword}">
                                                    <c:param name="keyword" value="${currentKeyword}" />
                                                </c:if>
                                                <c:if test="${not empty minPrice}">
                                                    <c:param name="minPrice" value="${minPrice}" />
                                                </c:if>
                                                <c:if test="${not empty maxPrice}">
                                                    <c:param name="maxPrice" value="${maxPrice}" />
                                                </c:if>
                                            </c:url>
                                            <li class="${i == currentPage ? 'active' : ''}">
                                                <a href="${pageURL}">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>


                        </div><!--features_items-->
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="components/footer.jsp"/>
        <script src="${ctx}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/bootstrap.min.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/jquery.scrollUp.min.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/price-range.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/jquery.prettyPhoto.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/main.js"></script>

    </body>

</html>
