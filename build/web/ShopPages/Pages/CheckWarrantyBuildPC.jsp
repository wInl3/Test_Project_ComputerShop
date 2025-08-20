<%-- 
    Document   : ProductDetail.jsp
    Created on : May 24, 2025, 7:32:03 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<c:set var="sessionUser" value="${sessionScope.user}" />
<jsp:useBean id="now" class="java.util.Date" />


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Product Details | E-Shopper</title>
        <link href="${ctx}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/custom.css?v=1.0.14" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="${ctx}/ShopPages/Pages/js/html5shiv.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            .price-box {
                background-color: #ebf7ff;
                width: 240px;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 8px;
                border: #a3acff 1px solid;
            }
            .price-box span {
                margin-bottom: 0;
                color: #000;
                font-weight: 600;
                font-size: 2.4rem;
            }
            .price-box p {
                margin-bottom: 0;
            }
            .warranty-card-list {
                display: flex;
                flex-wrap: wrap;
                gap: 16px;
            }
            .warranty-card {
                background: #f7faff;
                border: 2px solid #e0e7ff;
                border-radius: 12px;
                padding: 18px 22px;
                min-width: 220px;
                cursor: pointer;
                transition: border 0.2s, box-shadow 0.2s;
                box-shadow: 0 2px 8px 0 rgba(31, 38, 135, 0.07);
                margin-bottom: 0;
            }
            .warranty-card-content {
                font-size: 15px;
                color: #222;
            }
            .warranty-card input[type="radio"]:checked + .warranty-card-content,
            .warranty-card.selected {
                border-color: #4f8cff;
                background: #eaf3ff;
                box-shadow: 0 4px 16px 0 rgba(79, 140, 255, 0.08);
            }
            .warranty-card:hover {
                border-color: #4f8cff;
            }
            .warranty-price {
                color: #e53935;
                font-size: 1.5rem;
                font-weight: bold;
                margin-top: 6px;
            }


            .warranty-card-list {
                display: flex;
                flex-wrap: wrap;
                gap: 16px;
            }
            .warranty-card {
                background: #f7faff;
                border: 2px solid #e0e7ff;
                border-radius: 12px;
                padding: 18px 22px;
                min-width: 220px;
                cursor: pointer;
                transition: border 0.2s, box-shadow 0.2s;
                box-shadow: 0 2px 8px 0 rgba(31, 38, 135, 0.07);
                margin-bottom: 0;
            }
            .warranty-card-content {
                font-size: 15px;
                color: #222;
            }
            .warranty-card input[type="radio"]:checked + .warranty-card-content,
            .warranty-card.selected {
                border-color: #4f8cff;
                background: #eaf3ff;
                box-shadow: 0 4px 16px 0 rgba(79, 140, 255, 0.08);
            }
            .warranty-card:hover {
                border-color: #4f8cff;
            }
            .warranty-price {
                color: #e53935;
                font-size: 1.5rem;
                font-weight: bold;
                margin-top: 6px;
            }

            .star-rating .fa-star {
                font-size: 18px;
                margin-right: 2px;
            }
            .star-rating .fa-star.star-gold {
                color: #FFD700;
            }
            .star-rating .fa-star.star-gray {
                color: #ccc;
            }
        </style>
    </head><!--/head-->

    <body>

        <jsp:include page="/ShopPages/Pages/components/header.jsp" />

        <div class="container my-5">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10">
                    <div class="card shadow-sm border-0">
                        <div class="card-body p-4">
                            <h4 class="text-primary mb-4">🔍 Build PC Warranty Lookup</h4>

                            <!-- Search Form -->
                            <form action="Product" method="get" class="row g-2 align-items-center">
                                <input type="hidden" name="service" value="checkWarrantyBuildPC" />
                                <div class="col-sm-9">
                                    <input type="text" name="productCode" class="form-control form-control-lg"
                                           placeholder="Enter Product Code..." required>
                                </div>
                                <div class="col-sm-3 text-sm-left text-center">
                                    <button type="submit" class="btn btn-warning btn-lg w-100">🔎 Search</button>
                                </div>
                            </form>

                            <!-- Result Block -->
                            <c:if test="${not empty orderInfo}">
                                <div class="mt-5">
                                    <div class="alert alert-success d-flex align-items-center" role="alert">
                                        <strong class="me-2">✔ Build PC Warranty Info</strong>
                                    </div>

                                    <div class="table-responsive">
                                        <table class="table table-bordered table-hover">
                                            <tbody>
                                                <tr>
                                                    <th scope="row" class="w-25">Order Code</th>
                                                    <td>${orderInfo.orderCode}</td>
                                                </tr>
                                                <tr>
                                                    <th>Customer</th>
                                                    <td>${orderInfo.fullName}</td>
                                                </tr>
                                                <tr>
                                                    <th>Category</th>
                                                    <td>${orderInfo.cateName}</td>
                                                </tr>
                                                <tr>
                                                    <th>Product Code</th>
                                                    <td>${orderInfo.productCode}</td>
                                                </tr>
                                                <tr>
                                                    <th>Warranty Period</th>
                                                    <td>${orderInfo.warrantyPeriod} month(s)</td>
                                                </tr>
                                                <tr>
                                                    <th>Description</th>
                                                    <td>${orderInfo.warrantyDesc}</td>
                                                </tr>
                                                <tr>
                                                    <th>Start Date</th>
                                                    <td>
                                                        <fmt:formatDate value="${orderInfo.warrantyStartDate}" pattern="dd/MM/yyyy"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>End Date</th>
                                                    <td>
                                                        <fmt:formatDate value="${orderInfo.warrantyEndDate}" pattern="dd/MM/yyyy"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                    <!-- Button to send warranty -->
                                    <c:if test="${not empty sessionUser 
                                                  && orderInfo.userId == sessionUser.userId 
                                                  && orderInfo.warrantyEndDate.time > now.time}">
                                          <form action="Product" method="post" class="mt-3">
                                              <input type="hidden" name="service" value="updateWarrantyStatusBPC" />
                                              <input type="hidden" name="productCode" value="${orderInfo.productCode}" />
                                              <input type="hidden" name="orderCode" value="${orderInfo.orderCode}" />
                                              <button type="submit" class="btn btn-danger w-100">
                                                  🛠 Send this Build PC product to warranty
                                              </button>
                                          </form>
                                    </c:if>
                                </div>
                            </c:if>

                            <!-- Notifications -->
                            <c:if test="${not empty success}">
                                <div class="alert alert-success mt-4">${success}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger mt-4">${error}</div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- LOGIN REQUIRED MODAL -->
        <div class="modal fade" id="loginRequiredModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-warning" style="background-color: #fcf8e3;">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="loginModalLabel">Require sign in.</h4>
                    </div>
                    <div class="modal-body">
                        You have to sign in to add to cart.
                    </div>
                    <div class="modal-footer" style="display: flex; gap: 10px; justify-content: flex-end;">
                        <a href="${ctx}/SignUp" class="btn btn-default" style="flex: 1;">Sign up</a>
                        <a href="${ctx}/Login" class="btn btn-primary" style="flex: 1; margin-top: 0">Sign in</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- TOAST NOTIFICATION -->
        <c:if test="${not empty sessionScope.toast}">
            <div class="toast-container" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">
                <div class="alert alert-${sessionScope.toastType == 'error' ? 'danger' : 'success'} alert-dismissible" role="alert" id="toastMessage">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    ${sessionScope.toast}
                </div>
            </div>
            <c:remove var="toast" scope="session" />
            <c:remove var="toastType" scope="session" />
        </c:if>

        <jsp:include page="/ShopPages/Pages/components/footer.jsp" />

        <script src="${ctx}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/price-range.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/jquery.scrollUp.min.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/bootstrap.min.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/jquery.prettyPhoto.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/main.js"></script>

        <!-- TOAST AUTO-CLOSE SCRIPT -->
        <script>
            setTimeout(function () {
                var toast = document.getElementById("toastMessage");
                if (toast) {
                    $(toast).fadeOut("slow", function () {
                        $(this).remove();
                    });
                }
            }, 3000);
        </script>

    </body>
</html>
