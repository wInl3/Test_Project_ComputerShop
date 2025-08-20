<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Order Items</title>
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">
    </head>
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
                </section>
                <section class="content">
                    <div class="box">
                        <div class="box-body">
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Order Code</th>
                                        <th>Category Name</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                        <th>View Product</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${not empty items}">
                                        <c:forEach var="item" items="${items}">
                                            <tr>
                                                <td>${item.orderCode}</td>
                                                <td>${item.cateName}</td>
                                                <td>${item.quantity}</td>
                                                <td>${item.price}</td>
                                                <td>
                                                    ${item.productCode}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>

                        <div class="form-group" style="margin: 30px 40px 20px 40px;">
                            <label for="note"><strong>Note:</strong></label>
                            <textarea name="note" id="note" rows="4" class="form-control" style="resize: vertical; border-radius: 6px;"></textarea>
                        </div>

                        <div class="text-center" style="margin-bottom: 25px;">
                            <form action="OrderBuildPC" method="get" style="display:inline;">
                                <input type="hidden" name="service" value="listOnShipping" />
                                <button type="submit" class="btn btn-default btn-lg">Back</button>
                            </form>
                            <form action="OrderBuildPC" method="post" style="display:inline; margin-left: 10px;">
                                <input type="hidden" name="service" value="updateStatusShipFinish">
                                <input type="hidden" name="status" value="5">
                                <c:forEach var="item" items="${items}">
                                    <input type="hidden" name="itemIds" value="${item.orderBuildPcItemId}">
                                </c:forEach>
                                <input type="hidden" name="orderID" value="${orderID}" />
                                <button type="submit" class="btn btn-success btn-lg">Complete</button>
                            </form>
                        </div>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger text-center">${error}</div>
                        </c:if>
                    </div>


                </section>
            </div>
            <jsp:include page="../../../../components/footer.jsp" />
            <jsp:include page="../../../../components/control-sidebar.jsp" />
        </div>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.min.js"></script>
        <script>
            $(function () {
                $('#example2').DataTable({
                    "paging": true,
                    "lengthChange": true,
                    "searching": true,
                    "ordering": true,
                    "info": true,
                    "autoWidth": true
                });
            });
        </script>
    </body>
</html>
