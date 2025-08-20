<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="activePage" value="${param.activePage}" />
<c:set var="activeHeader" value="${param.activeHeader}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Header Section Extracted from homepages.jsp -->
<link href="${pageContext.request.contextPath}/ShopPages/Pages/css/CartCss.css" rel="stylesheet">
<style>
    .back-home-btn {
        margin-left: 20px;
        padding: 8px 16px;
        background-color: #333;
        color: #fff !important;
        text-decoration: none;
        border-radius: 4px;
        font-weight: bold;
        transition: background-color 0.3s ease;
    }
    .back-home-btn:hover {
        background-color: #555;
    }
</style>

<header id="header"><!--header-->
    <c:if test="${activeHeader != 'no'}">
        <div class="header_top"><!--header_top-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="contactinfo">
                            <ul class="nav nav-pills">
                                <li><a href="#"><i class="fa fa-phone"></i> 0337593524</a></li>
                                <li><a href="#"><i class="fa fa-envelope"></i> CyberBeast@gmail.com</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="social-icons pull-right">
                            <ul class="nav navbar-nav">
                                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                                <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                                <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                                <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--/header_top-->
        <div class="header-middle"><!--header-middle-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="logo pull-left">
                            <a href="${pageContext.request.contextPath}/HomePages"><img src="${ctx}/ShopPages/Pages/images/home/CyberBeast2.png" style="height: 150px; width: 200px;  "/></a>
                        </div>
                        <div class="btn-group pull-right">
                        </div>
                    </div>
                    <div class="col-sm-8">
                        <div class="shop-menu pull-right">
                            <ul class="nav navbar-nav"  style=" padding-top: 50px ">

                                <c:choose>
                                    <c:when test="${not empty sessionScope.user}">                                        
                                        <c:if test="${not empty sessionScope.user and sessionScope.user.role.roleID == 3}">    
                                            <li class="cart-dropdown">
                                                <a href="#"><i class="fa fa-shopping-cart"></i> Warranty</a>
                                                <div class="dropdown-content">
                                                    <div class="arrow-up"></div>
                                                    <a href="${pageContext.request.contextPath}/Product?service=checkWarranty">Warranty Item</a>
                                                    <a href="${pageContext.request.contextPath}/Product?service=checkWarrantyBuildPC">Warranty Build PC</a>
                                                </div>
                                            </li>
                                            <li class="cart-dropdown">
                                                <a href="#"><i class="fa fa-shopping-cart"></i> Cart</a>
                                                <div class="dropdown-content">
                                                    <div class="arrow-up"></div>
                                                    <a href="${pageContext.request.contextPath}/Cart">CartItem</a>
                                                    <a href="CardBuildPc">CartPC</a>
                                                </div>
                                            </li>
                                            <li><a href="${pageContext.request.contextPath}/OrderHistory"><i class="fa fa-list-alt"></i> Order history</a></li>
                                            <li><a href="${pageContext.request.contextPath}/User?service=myAccount"><i class="fa fa-user"></i> Account</a></li>
                                            </c:if>
                                            <c:if test="${not empty sessionScope.user and sessionScope.user.role.roleID != 3}">
                                            <li><a href="Login"><i class="fa fa-home"></i>Back to admin pages</a></li>
                                            </c:if>
                                        <li><a href="Logout"><i class="fa fa-sign-out"></i> Logout</a></li>
                                        </c:when>
                                        <c:otherwise>
                                        <li><a href="Login"><i class="fa fa-lock"></i> Login</a></li>
                                        <li><a href="SignUp"><i class="fa fa-user-plus"></i> Sign Up</a></li>
                                        </c:otherwise>
                                    </c:choose>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--/header-middle-->
        <div class="header-bottom"><!--header-bottom-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-9">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                            </button>
                        </div>
                        <div class="mainmenu pull-left">
                            <ul class="nav navbar-nav collapse navbar-collapse">
                                <li><a href="${pageContext.request.contextPath}/HomePages" class="${activePage == 'home' ? 'active' : ''}">Home</a></li>
                                <li><a href="${pageContext.request.contextPath}/CategoriesController?service=list" class="${activePage == 'products' ? 'active' : ''}">Products</a></li>
                                <li><a href="${ctx}/blogc" class="${activePage == 'blog' ? 'active' : ''}">Blog</a></li>
                                <li><a href="${ctx}/ShopPages/Pages/BuildPC/BuildPC.html">Build your PC</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="search_box pull-right">

                        <form action="${ctx}/CategoriesController" method="get" style="display: flex;">
                            <input type="hidden" name="service" value="filter"/>
                            <input type="text" name="keyword" class="form-control" placeholder="Insert product name"
                                   value="${not empty param.keyword ? param.keyword : ''}" style="width: 180px;" />
                        </form>

                    </div>


                </div>
            </div>
        </div><!--/header-bottom-->
    </c:if>

    <c:if test="${activeHeader == 'no'}">
        <div class="header-no-active" style="padding: 10px; background: #f8f8f8;">
            <div style="display: flex; align-items: center;">
                <c:if test="${user == null}">
                    <a href="${ctx}/HomePages">
                        <img src="${ctx}/ShopPages/Pages/images/home/CyberBeast2.png" alt="Logo" style="height: 100px; width: auto;"/>
                    </a>
                    <a href="${ctx}/HomePages" class="back-home-btn">
                        Back to Homepage
                    </a>
                </c:if>
                <c:if test="${user != null && user.role.roleID == 3}">
                    <a href="${ctx}/HomePages">
                        <img src="${ctx}/ShopPages/Pages/images/home/CyberBeast2.png" alt="Logo" style="height: 100px; width: auto;"/>
                    </a>
                    <a href="${ctx}/User?service=myAccount" class="back-home-btn">
                        Back to my account
                    </a>
                </c:if>
                <c:if test="${user != null && user.role.roleID == 1}">
                    <a href="${ctx}/HomePages">
                        <img src="${ctx}/ShopPages/Pages/images/home/CyberBeast2.png" alt="Logo" style="height: 100px; width: auto;"/>
                    </a>
                    <a href="${ctx}/AdminDashbordServlet" class="back-home-btn">
                        Back to dashboard
                    </a>
                </c:if>
                <c:if test="${user != null && user.role.roleID == 2}">
                    <a href="${ctx}/HomePages">
                        <img src="${ctx}/ShopPages/Pages/images/home/CyberBeast2.png" alt="Logo" style="height: 100px; width: auto;"/>
                    </a>
                    <a href="${ctx}/OrderAdminCate?service=listPending" class="back-home-btn">
                        Back to Order
                    </a>
                </c:if>
                <c:if test="${user != null && user.role.roleID == 4}">
                    <a href="${ctx}/HomePages">
                        <img src="${ctx}/ShopPages/Pages/images/home/CyberBeast2.png" alt="Logo" style="height: 100px; width: auto;"/>
                    </a>
                    <a href="${ctx}/OrderAdminCate?service=listWaitShip" class="back-home-btn">
                        Back to Order
                    </a>
                </c:if>
            </div>
        </div>
    </c:if> 
</header><!--/header-->
