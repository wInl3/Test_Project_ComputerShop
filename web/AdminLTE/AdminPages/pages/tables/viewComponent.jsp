


<%-- 
    Document   : insertCategory
    Created on : May 28, 2025, 9:48:28 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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

            <jsp:include page="../../components/header.jsp" />
            <jsp:include page="../../components/sidebar.jsp">
                <jsp:param name="activeMenu" value="component"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Component Tables
                    </h1>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-dashboard"></i> Home</li>
                        <li>Component/li>
                        <li class="active">view Component</li>
                    </ol>
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
                                                <th>Component ID</th>
                                                <th>Name</th>
                                                <th>Quantity</th>
                                                <th>Status</th>
                                                <th>View</th>
                                                <th>Action</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${not empty requestScope.data}">
                                                <c:forEach var="com" items="${requestScope.data}">
                                                    <tr>
                                                        <td>${com.componentID}</td>
                                                        <td>${com.componentName}</td>
                                                        <td>${com.quantity}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${com.status == 1}">
                                                                    <span class="label label-success">Active</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Disable</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <a href="BrandComAdmin?service=listbycom&componentID=${com.componentID}" 
                                                               class="btn btn-warning btn-sm">View</a>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${com.status == 1}">
                                                                    <a href="ComAdmin?service=toggleStatus&componentID=${com.componentID}"
                                                                       class="btn btn-danger btn-sm"
                                                                       onclick="return confirm('Are you sure you want to disable this component?');">
                                                                        Disable
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${com.status == 0}">
                                                                    <a href="ComAdmin?service=toggleStatus&componentID=${com.componentID}"
                                                                       class="btn btn-success btn-sm"
                                                                       onclick="return confirm('Are you sure you want to activate this component?');">
                                                                        Activate
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="text-muted">Unknown status</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>

                            </div>
                            <!-- /.box-body -->
                        </div>
                    </div>

                    <!-- /.box-body -->
            </div>
            <!-- /.box -->

            <!-- /.content-wrapper -->
            <jsp:include page="../../components/footer.jsp" />
            <jsp:include page="../../components/control-sidebar.jsp" />
            <!-- Add the sidebar's background. This div must be placed
                 immediately after the control sidebar -->
            <div class="control-sidebar-bg"></div>
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

