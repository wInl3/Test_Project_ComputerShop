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
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>


            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Order Items Tables
                    </h1>
                   
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">

                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-hover">
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
                                                        <td>${item.categoryName}</td>
                                                        <td>${item.quantity}</td>
                                                        <td>${item.price}</td>
                                                        
                                                        <td>                                                                       
                                                                    <form action="OrderItemAdmin" method="get" style="display:inline;">
                                                                        <input type="hidden" name="service" value="viewProductsWait" />
                                                                        <input type="hidden" name="orderItemID" value="${item.orderItemID}" />
                                                                        <input type="hidden" name="orderID" value="${param.orderID}" />
                                                                        <button type="submit" class="btn btn-info btn-sm">View</button>
                                                                    </form>
                                                        </td>
                                                    </tr>                                                  
                                                </c:forEach>
                                            </c:if>
                                        </tbody>
                                    </table>                                    
                                </div>
                                </table>

                                <!-- Action buttons -->
                                <div class="text-center" style="margin-top: 20px;">
                                    <!-- Back button -->
                                    <form action="OrderAdminCate" method="get" style="display:inline;">
                                        <input type="hidden" name="service" value="listWaitShip" />
                                        <button type="submit" class="btn btn-default">Back</button>
                                    </form>

                                    <!-- Finish button -->
                                    <form action="OrderAdminCate" method="post" style="display: inline; margin-left: 10px;">
                                        <input type="hidden" name="service" value="updateStatusShip">
                                        <input type="hidden" name="status" value="4">
                                        <input type="hidden" name="orderID" value="${param.orderID}">
                                        <button type="submit" class="btn btn-success">Receive Order</button>
                                    </form>
                                </div>

                                <!-- Error message -->
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger text-center" style="margin-top: 15px;">
                                        ${error}
                                    </div>
                                </c:if>


                            </div>

                        </div>
                    </div>

                    <!-- /.box-body -->
                </section>
                <c:if test="${not empty products}">
                    <div class="box box-primary" style="margin-top: 30px;">
                        <div class="box-header with-border">
                            <h3 class="box-title">Product Codes for Order Item ID: ${selectedOrderItemID}</h3>
                        </div>
                        <div class="box-body">
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Product Code</th>
                                        <th>Warranty Period</th>
                                        <th>Description</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="p" items="${products}">
                                        <tr>
                                            <td>${p.productCode}</td>
                                            <td>${p.warrantyPeriod}</td>
                                            <td style="white-space: normal; word-break: break-word; max-width: 200px;">${p.warrantyDescription}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>

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

