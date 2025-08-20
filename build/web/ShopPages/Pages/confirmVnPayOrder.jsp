<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("Login");
        return;
    }

    String cartIDs = request.getParameter("ids");
    String amountStr = request.getParameter("amount");
    int amount = 0;

    try {
        if (amountStr != null) {
            amountStr = amountStr.replaceAll("[^\\d]", "");
            amount = Integer.parseInt(amountStr);
        }
    } catch (Exception e) {
        amount = 0;
    }

    String fullName = user.getFullname();
    String phone = user.getPhoneNumber();
    String address = (user.getCustomerInfo() != null && user.getCustomerInfo().getAddress() != null)
            ? user.getCustomerInfo().getAddress() : "";
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Confirm Build PC Order | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css" rel="stylesheet">
    </head>
    <body>

                <jsp:include page="/ShopPages/Pages/components/header.jsp" />

        <div class="container mt-4">
            <div class="breadcrumbs">
                <ol class="breadcrumb mb-1">
                    <li><a href="${pageContext.request.contextPath}/BuildPC">Build PC</a></li>
                    <li class="active">Order Confirmation</li>
                </ol>
            </div>

            <h2 class="text-center mb-4">Confirm Your Order</h2>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">
                    ${requestScope.error}
                </div>
            </c:if>

            <div class="panel panel-default">
                <div class="panel-heading"><strong>Deposit Amount</strong></div>
                <div class="panel-body">
                    <h4><strong><%= amount %></strong> VND</h4>
                </div>
            </div>

            <form id="orderForm" class="checkout-form" action="<%= request.getContextPath() %>/ConfirmOrderBuildPC" method="post" onsubmit="return handleSubmit()">
                <div class="row">
                    <div class="col-md-6">
                        <h4>Receiver's Information</h4>
                        <div class="form-group">
                            <label>Full Name:</label>
                            <input type="text" name="fullname" class="form-control" value="<%= fullName %>" required>
                        </div>
                        <div class="form-group">
                            <label>Phone:</label>
                            <input type="text" name="phone" class="form-control" value="<%= phone %>" required>
                        </div>
                        <div class="form-group">
                            <label>Address:</label>
                            <input type="text" name="address" class="form-control" value="<%= address %>" required>
                        </div>
                        <div class="form-group">
                            <label>Note:</label>
                            <textarea name="note" class="form-control" rows="3"></textarea>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <h4>Payment Method</h4>
                        <div class="radio">
                            <label><input type="radio" name="methodOption" value="vnpay" checked> Pay with VNPay</label>
                        </div>
                        <div class="radio">
                            <label><input type="radio" name="methodOption" value="cod"> Cash on Delivery</label>
                        </div>

                        <input type="hidden" name="cartIDs" value="<%= cartIDs %>">
                        <input type="hidden" name="amount" value="<%= amount %>">
                        <input type="hidden" id="paymentMethodInput" name="paymentMethod" value="vnpay">
                        <input type="hidden" id="bankCodeInput" name="bankCode" value="">
                       

                        <button type="submit" class="btn btn-primary btn-lg mt-3">Confirm Order</button>
                    </div>
                         
                </div>
            </form>
                         <div class="text-left mb-3">
                            <a href="${pageContext.request.contextPath}/CardBuildPc" class="btn btn-outline-secondary">
                                <i class="fa fa-arrow-left"></i> Back to Build PC Cart
                            </a>
                        </div>
        </div>

        <!-- VNPay Modal -->
        <div id="vnpayModal" style="display:none; position:fixed; z-index:9999; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.6);">
            <div style="background:#fff; padding:20px; margin:15% auto; width:300px; text-align:center;">
                <h4>Redirecting to VNPay...</h4>
                <img src="${pageContext.request.contextPath}/assets/images/loading.gif" width="80" alt="Loading...">
                <p>Please wait...</p>
            </div>
        </div>

        <jsp:include page="/ShopPages/Pages/components/footer.jsp" />

        <script>
            function handleSubmit() {
                const method = document.querySelector('input[name="methodOption"]:checked').value;
                document.getElementById("paymentMethodInput").value = method;

                if (method === "vnpay") {
                    document.getElementById("vnpayModal").style.display = "block";
                }
                return true;
            }
        </script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
    </body>
</html>
