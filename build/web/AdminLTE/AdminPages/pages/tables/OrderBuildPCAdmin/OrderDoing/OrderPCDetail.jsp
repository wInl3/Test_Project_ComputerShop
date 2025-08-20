<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE | Order Items</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

        <!-- Styles -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">
    </head>
    <%
        String orderIdParam = request.getParameter("orderID");
        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            int orderId = Integer.parseInt(orderIdParam);
            // tiếp tục xử lý
        } else {
            out.println("<h3 style='color:red;'>Thiếu hoặc sai tham số orderId!</h3>");
        }
    %>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <jsp:include page="../../../../components/header.jsp" />
            <jsp:include page="../../../../components/sidebar.jsp">
                <jsp:param name="activeMenu" value="orderbuildpc"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>

            <div class="content-wrapper">
                <section class="content-header">
                    <h1>Order Items Table</h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Order</a></li>
                        <li class="active">View Order Items</li>
                    </ol>
                </section>

                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">

                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Order Code</th>              
                                               <th>Category Name</th>
                                                <th>Quantity</th>
                                                <th>Warranty</th>
                                                <th>Price</th>
                                                <th>Inventory</th>
                                                <th>Queue</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${not empty items}">
                                                <c:forEach var="item" items="${items}">
                                                    <tr>
                                                        <td>${item.orderCode}</td>
                                                        <td>${item.cateName}</td>
                                                        <td>${item.quantity}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty item.warrantyDesc}">
                                                                    ${item.warrantyDesc}
                                                                </c:when>
                                                                <c:otherwise>No Have</c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫" /></td>
                                                        <td>${item.inventory}</td>
                                                        <td>${item.queue}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </tbody>
                                    </table>
                                    <div class="text-center" style="margin-top: 20px;">
                                        <form action="OrderBuildPC" method="post" style="display: inline;">
                                            <input type="hidden" name="service" value="updateStatus">
                                            <input type="hidden" name="status" value="3">
                                            <input type="hidden" name="orderID" value="${orderID}">
                                            <c:forEach var="item" items="${items}">
                                                <input type="hidden" name="itemIds" value="${item.orderBuildPcItemId}">
                                            </c:forEach>
                                            <button type="submit" class="btn btn-success" onclick="return confirm('Confirm accept this PC order?')">Receive</button>
                                        </form>
                                        <form action="OrderBuildPC" method="post" style="display: inline; margin-left: 10px;">
                                            <input type="hidden" name="service" value="updateStatus">
                                            <input type="hidden" name="status" value="0">
                                            <input type="hidden" name="orderID" value="${orderID}">
                                            <c:forEach var="item" items="${items}">
                                                <input type="hidden" name="itemIds" value="${item.orderBuildPcItemId}">
                                            </c:forEach>
                                            <button type="submit" class="btn btn-danger" onclick="return confirm('Confirm reject this PC order?')">Reject</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>

            <jsp:include page="../../../../components/footer.jsp" />
            <jsp:include page="../../../../components/control-sidebar.jsp" />

        </div>

        <!-- Scripts -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/fastclick/fastclick.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>
        <script>
                                                $(function () {
                                                    $('#example2').DataTable({
                                                        paging: true,
                                                        lengthChange: true,
                                                        searching: true,
                                                        ordering: true,
                                                        info: true,
                                                        autoWidth: true
                                                    });

                                                    $('.sidebar-menu').tree();
                                                });
        </script>           

    </body>
</html>
