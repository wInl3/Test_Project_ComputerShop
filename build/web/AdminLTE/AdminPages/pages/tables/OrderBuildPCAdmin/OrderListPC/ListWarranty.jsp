<%-- 
    Document   : insertProduct
    Created on : May 28, 2025, 9:48:28 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Data Tables</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.6 -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages//plugins/datatables/dataTables.bootstrap.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages//dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages//dist/css/skins/_all-skins.min.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <jsp:include page="../../../../components/header.jsp" />
            <jsp:include page="../../../../components/sidebar.jsp">
                <jsp:param name="activeMenu" value="orderbuildpc"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>



            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Order Warranty
                    </h1>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-dashboard"></i> Home</li>
                        <li>Order</li>
                        <li class="activ21e">view Order Warranty</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">

                                <!-- /.box-header -->
                                <div class="box-body">
                                    <c:if test="${not empty error}">
                                        <div class="alert alert-danger" style="font-weight:bold;">${error}</div>
                                    </c:if>
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Order ID</th>
                                                <th>Customer Name</th>                                                
                                                <th>Staff Name</th>
                                                <th>Order Date</th>
                                                <th>Product Type</th>
                                                <th>Total Amount</th>
                                                <th>View Product Warranty</th>
                                                <th>Complete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${not empty orders}">
                                                <c:forEach var="order" items="${orders}">
                                                    <tr>
                                                        <td>${order.orderID}</td>
                                                        <td>${order.customerName}</td>
                                                        <td>${order.staffName}</td>
                                                        <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${order.product_Type != null && order.product_Type == 0}">Category</c:when>
                                                                <c:otherwise>Build PC</c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${order.totalAmount}</td>
                                                        <td>
                                                            <a href="OrderBuildPC?service=warrantyActionFinish&orderID=${order.orderID}" 
                                                               class="btn btn-warning btn-sm">View</a>
                                                        </td>
                                                        <td>
                                                            <div class="text-center" >
                                                                <form action="OrderBuildPC" method="post" style="display: inline;">
                                                                    <input type="hidden" name="service" value="completeWarranty">
                                                                    <input type="hidden" name="orderID" value="${order.orderID}">
                                                                    <button type="submit" class="btn btn-success btn-sm">
                                                                        Complete
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- /.box-body -->
                </section>
                <!-- START: Pending Warranty Product Table -->
                <c:if test="${not empty selectPendingWarranty}">
                    <div class="box box-warning mt-4">
                        <div class="box-header with-border">
                            <h3 class="box-title">
                                <i class="fa fa-wrench"></i> Warranty Products
                            </h3>
                        </div>
                        <div class="box-body">
                            <table class="table table-bordered table-hover">
                                <thead class="thead-light">
                                    <tr>
                                        <th style="width: 70%">Product Code</th>
                                        <th class="text-center" style="width: 30%">Action</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="code" items="${selectPendingWarranty}">
                                        <tr>
                                            <td>${code.productCode}</td>
                                            <td class="text-center">
                                                <form action="OrderBuildPC" method="post" style="display: inline;">
                                                    <input type="hidden" name="service" value="warrantyActionFinish">
                                                    <input type="hidden" name="productCode" value="${code.productCode}">
                                                    <input type="hidden" name="orderID" value="${orderID}">
                                                    <button type="submit" class="btn btn-warning btn-sm">
                                                        Finish
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>


                            </table>
                        </div>
                    </div>





                </c:if>
                <!-- END -->




            </div>
            <!-- /.box -->

            <!-- /.content-wrapper -->
            <jsp:include page="../../../../components/footer.jsp" />
            <jsp:include page="../../../../components/control-sidebar.jsp" />
        </div>
        <!-- ./wrapper -->

        <!-- jQuery 2.2.3 -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
        <!-- DataTables -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.min.js"></script>
        <!-- SlimScroll -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>
        <!-- page script -->
        <script>
            $(function () {
                $("#example1").DataTable();
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
        <script>
            $(function () {
                $('.sidebar-menu').tree();
            });
        </script>
    </body>
</html>

