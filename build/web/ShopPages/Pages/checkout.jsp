<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    </head>

    <body>
        <jsp:include page="/ShopPages/Pages/components/header.jsp" />

        <section id="cart_items">
            <div class="container">

                <!-- Breadcrumb -->
                <div class="breadcrumbs">
                    <ol class="breadcrumb  mb-1">
                        <li><a href="${pageContext.request.contextPath}/Cart">Cart</a></li>
                        <li class="active">Check out</li>
                    </ol>
                </div>

                <!-- Review & Payment Title -->
                <div class="review-payment">
                    <h2 class="mt-3">Review & Payment</h2>
                </div>

                <form action="ConfirmOrder" method="post">

                    <div class="cart-cards">
                        <c:forEach var="cartItem" items="${cartItems}">
                            <div class="cart-card clearfix">
                                <input type="hidden" name="cartItemIds" value="${cartItem.cartItemID}" />
                                <!-- Ảnh -->
                                <div class="cart-card-image pull-left">
                                    <img src="${pageContext.request.contextPath}/ShopPages/Pages/images/CatePicture/${cartItem.category.imgURL}"
                                         alt="Product">
                                </div>

                                <!-- Nội dung -->
                                <div class="cart-card-content">
                                    <div class="cart-info">
                                        <strong>${cartItem.category.categoryName}</strong><br>
                                        Warranty: ${cartItem.warranty.warranty.warrantyPeriod} months<br>
                                        <small class="text-muted">(${cartItem.warranty.warranty.description})</small><br>

                                        <div class="cart-price">
                                            Product: <fmt:formatNumber value="${cartItem.category.price}" type="number" groupingUsed="true"/> VND<br>
                                            Warranty: <fmt:formatNumber value="${cartItem.warranty.price}" type="number" groupingUsed="true"/> VND
                                        </div>
                                    </div>

                                    <!-- Quantity & Total -->
                                    <div class="cart-qty-total">
                                        <div class="cart-quantity-group">
                                            Quantity
                                            <input type="number"
                                                   value="${cartItem.quantity}"
                                                   readonly>
                                        </div>
                                        <div class="cart_total">
                                            <strong>
                                                <fmt:formatNumber value="${(cartItem.category.price + cartItem.warranty.price) * cartItem.quantity}" type="number" groupingUsed="true"/> VND
                                            </strong>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Total Result -->
                    <table class="table table-condensed total-result">
                        <tr>
                            <td>Cart Sub Total</td>
                            <td><fmt:formatNumber value="${subTotal}" type="number" groupingUsed="true"/> VND</td>
                        </tr>
                        <tr class="shipping-cost">
                            <td>Shipping Cost</td>
                            <td>Free</td>
                        </tr>
                        <tr>
                            <td><strong>Total</strong></td>
                            <td><span><fmt:formatNumber value="${subTotal}" type="number" groupingUsed="true"/> VND</span></td>
                        <input type="hidden" name="subTotal" value="${subTotal}" />
                        </tr>
                    </table>

                    <!-- Receiver Info + Note -->
                    <div class="shopper-informations">
                        <div class="row">
                            <div class="col-md-6 col-sm-12">
                                <div class="bill-to">
                                    <p>Receiver's Information</p>   
                                    <input 
                                        type="text" 
                                        class ="${error == 'Full name is required!' ? 'is-invalid' : ''}"
                                        placeholder="Name" 
                                        name="receiverName" 
                                        value="${not empty error ? receiverName : sessionScope.user.fullname}" 
                                        required>
                                    <input 
                                        type="text" 
                                        class ="${error == 'Phone number is required!' || error == 'Phone number is invalid!' ? 'is-invalid' : ''}"
                                        placeholder="Phone number" 
                                        name="phoneNumber" 
                                        value="${not empty error ? phoneNumber : sessionScope.user.phoneNumber}" 
                                        required>
                                    <input
                                        type="text" 
                                        class ="${error == 'Address is required!' ? 'is-invalid' : ''}"
                                        placeholder="Address"
                                        name="address" 
                                        value="${not empty error ? address : sessionScope.user.customerInfo.address}" 
                                        required>
                                </div>
                                <c:if test="${not empty error}">
                                    <span class="error-message">${error}</span>
                                </c:if>
                            </div>
                            <div class="col-md-12">
                                <div class="order-message">
                                    <p>Note</p>
                                    <textarea name="message" placeholder="Notes about your order, special instructions for delivery">${message}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Payment Options -->
                    <div class="payment-options">
                        <p>Payment Options</p>
                        <label><input type="radio" name="paymentMethod" value="COD" checked> Pay when receive</label>
                        <label><input type="radio" name="paymentMethod" value="Bank"> Online Banking</label>
                    </div>

                    <!-- Confirm Button -->
                    <button type="submit" class="btn btn-confirm-order">Confirm Order</button>
                </form>
            </div>
        </section>

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

        <script>
            // Đợi DOM load xong
            document.addEventListener("DOMContentLoaded", function () {
                // Lấy tất cả các input có class is-invalid hoặc in-valid
                var invalidInputs = document.querySelectorAll("input.is-invalid");

                // Lặp qua từng input
                invalidInputs.forEach(function (input) {
                    input.addEventListener("input", function () {
                        input.classList.remove("is-invalid");
                    });
                });
            });
        </script>

        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
    </body>
</html>
