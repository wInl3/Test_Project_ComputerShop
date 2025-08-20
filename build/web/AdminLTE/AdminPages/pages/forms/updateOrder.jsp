<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Update User</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/custom.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <jsp:include page="../../components/header.jsp" />
            <jsp:include page="../../components/sidebar.jsp">
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>UPDATE Oder</h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Order</a></li>
                        <li class="active">Update Order</li>
                    </ol>
                </section>
                <div class="box box-primary">
                    <div class="box-body">

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" style="font-weight:bold;">${error}</div>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/OrderAdmin">
                            <input type="hidden" name="service" value="update"/>
                            <input type="hidden" name="submit" value="submit"/>
                            <input type="hidden" name="orderID" value="${param.orderID != null ? param.orderID : order.orderID}"/>

                            <!-- Customer Name -->
                            <div class="form-group">
                                <label for="fullName">Customer Name</label>
                                <input type="text" class="form-control" readonly
                                       value="${order.fullName}" />
                            </div>

                            <!-- Order Date -->
                            <div class="form-group">
                                <label for="orderDate">Order Date</label>
                                <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd HH:mm" var="formattedDate"/>
                                <input type="text" class="form-control" readonly
                                       value="${formattedDate}" />
                            </div>

                            <!-- Product Type -->
                            <div class="form-group">
                                <label for="productType">Product Type</label>
                                <input type="text" class="form-control" readonly
                                       value="<c:choose>
                                           <c:when test='${order.product_Type == 0}'>Category</c:when>
                                           <c:when test='${order.product_Type == 1}'>Build PC</c:when>
                                           <c:otherwise>Unknown</c:otherwise>
                                       </c:choose>" />
                            </div>

                            <!-- Address -->
                            <div class="form-group">
                                <label for="address">Address</label>
                                <input type="text" class="form-control" readonly
                                       value="${order.address}" />
                            </div>

                            <!-- Total Amount -->
                            <div class="form-group">
                                <label for="totalAmount">Total Amount</label>
                                <input type="text" class="form-control" readonly
                                       value="${order.totalAmount}" />
                            </div>

                            <!-- Status -->
                            <div class="form-group">
                                <label for="status">Status</label>
                                <select id="status" name="newStatus" class="form-control">
                                    <option value="0" <c:if test="${param.newStatus == '0' || (empty param.newStatus and order.status == 0)}">selected</c:if>>Canceled</option>
                                    <option value="1" <c:if test="${param.newStatus == '1' || (empty param.newStatus and order.status == 1)}">selected</c:if>>Processing</option>
                                    <option value="2" <c:if test="${param.newStatus == '2' || (empty param.newStatus and order.status == 2)}">selected</c:if>>Shipping</option>
                                    <option value="3" <c:if test="${param.newStatus == '3' || (empty param.newStatus and order.status == 3)}">selected</c:if>>Completed</option>
                                    <option value="4" <c:if test="${param.newStatus == '4' || (empty param.newStatus and order.status == 3)}">selected</c:if>>Return</option>
                                    </select>
                                </div>

                                <!-- Submit -->
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                <a href="${pageContext.request.contextPath}/OrderAdmin" class="btn btn-default">Cancel</a>
                        </form>
                    </div>
                </div>

            </div>
            <jsp:include page="../../components/footer.jsp" />
            <jsp:include page="../../components/control-sidebar.jsp" />
        </div>
        <!-- jQuery 2.2.3 -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
        <!-- AdminLTE App -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>
        <script>
            $(function () {
                $('.sidebar-menu').tree();
            });
        </script>
        <style>
            .is-invalid {
                border: 2px solid #dc3545 !important;
                background-color: #f8d7da !important;
            }
        </style>
    </body>
</html>
