<%-- 
    Document   : HomePage
    Created on : May 22, 2025, 1:25:48 PM
    Author     : Admin
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
            <jsp:param name="activePage" value="blog"/>
        </jsp:include>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar" style="margin-bottom: 30px;">
                            <h2>Blog Categories</h2>
                            <div>
                                <ul>
                                    <li><a href="blogc" style="color: black">All</a></li> 
                                        <c:forEach var="cat" items="${blog_categories}">
                                        <li>
                                            <a href="blogc?Bc_id=${cat.bc_id}" style="color: black;">${cat.bc_name}</a>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </div>

                            <h2 style="padding-top: 20px">Top 5 New Blogs</h2>
                            <ul>
                                <div class="blog-list">
                                    <c:forEach items="${top5Posts}" var="p">
                                        <li>
                                            <div class="blog-card">
                                                <a href="blogdetail?Post_id=${p.post_id}" style="color: black">
                                                    ${p.title} - <fmt:formatDate value="${p.updated_date}" pattern="dd/MM/yyyy"/>
                                                </a>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </div>
                            </ul>
                        </div>

                    </div>
                    <div class="col-sm-9">

                        <div class="blog-post-area">


                            <h2 class="title text-center">Latest From our Blog</h2>
                            <form action="${ctx}/blogc" method="get" style="display: flex; justify-content: center; margin-bottom: 20px;">
                                <input type="text" name="searchKeyword" class="form-control" placeholder="Search by title..."
                                       value="${not empty param.searchKeyword ? param.searchKeyword : ''}" style="width: 300px;" />
                                <button type="submit" style="margin-left: 10px;">Search</button>
                            </form>                           
                            <style>
                                .brief-text {
                                    display: -webkit-box;
                                    -webkit-line-clamp: 2;
                                    -webkit-box-orient: vertical;
                                    overflow: hidden;
                                    min-height: 3em; /* đảm bảo chiếm 2 dòng */
                                }

                                .blog-list {
                                    display: grid;
                                    grid-template-rows: repeat(5, auto);
                                    row-gap: 12px;
                                    margin-top: 10px;
                                }

                                .blog-card {
                                    padding: 14px;
                                    background-color: #f0f0f0;
                                    border: 1px solid #ddd;
                                    border-radius: 6px;
                                    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
                                }
                                .blog-card a {
                                    font-family: 'Arial', sans-serif;
                                    font-size: 13px;
                                    font-weight: normal;
                                    text-decoration: none;
                                    color: #000;
                                    display: block;
                                }
                            </style>

                            <c:forEach var="post" items="${postList}" varStatus="status">
                                <c:if test="${status.index % 2 == 0}">
                                    <div class="row gx-4 gy-4">
                                    </c:if>

                                    <div class="col-sm-6 d-flex" style="padding-bottom: 20px">
                                        <div class="w-100 d-flex flex-column mb-4" style="border:1px solid #ccc; padding:15px; height:100%;">
                                            <a href="${ctx}/blogdetail?Post_id=${post.post_id}" style="color: black;">
                                                <h3>${post.title}</h3>
                                                <p>${post.author}</p>
                                                <p>${post.updated_date}</p>
                                                <p class="brief-text">${post.brief}</p>

                                                <c:if test="${not empty post.thumbnail}">
                                                    <img 
                                                        src="${post.thumbnail}" 
                                                        alt="Thumbnail" 
                                                        style="width:100%; height:200px; object-fit:cover; margin-top:auto;" />
                                                </c:if>
                                            </a>

                                        </div>
                                    </div>

                                    <c:if test="${status.index % 2 == 1 || status.last}">
                                    </div>
                                </c:if>


                            </c:forEach>

                            <div class="pagination-area">
                                <ul class="pagination">
                                    <c:forEach begin="1" end="${endP}" var="i">
                                        <li class="${i == currentPage ? 'active' : ''}">
                                            <a href="blogc?page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li>
                                        <a href="blogc?page=${currentPage + 1 > endP ? endP : currentPage + 1}">
                                            <i class="fa fa-angle-double-right"></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="/ShopPages/Pages/components/footer.jsp" />

        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>

    </body>
</html>
