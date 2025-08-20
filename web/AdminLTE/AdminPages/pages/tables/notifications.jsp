<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Notifications</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.6 -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">
        <!-- DataTables CSS -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.css">

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
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Notifications
                        <small>Manage all notifications</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="${ctx}/AdminDashbordServlet"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">Notifications</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">All Notifications</h3>
                                    <div class="box-tools pull-right">
                                        <a href="${ctx}/NotificationServlet?service=markAllAsRead" 
                                           class="btn btn-success btn-sm" onclick="return confirm('Mark all notifications as read?')">
                                            <i class="fa fa-check"></i> Mark All as Read
                                        </a>
                                    </div>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <c:if test="${not empty notifications}">
                                        <div class="table-responsive">
                                            <table id="notificationTable" class="table table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th width="5%">ID</th>
                                                        <th width="15%">Title</th>
                                                        <th width="15%">Sender</th>
                                                        <th width="10%">Status</th>
                                                        <th width="15%">Created At</th>
                                                        <th width="15%">Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="notification" items="${notifications}">
                                                        <tr class="${notification.isRead ? '' : 'unread-notification'}">
                                                            <td>${notification.notificationID}</td>
                                                            <td>
                                                                <strong>${notification.title}</strong>
                                                            </td>
                                                            <td>${notification.senderName}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${notification.isRead}">
                                                                        <span class="label label-success">Read</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="label label-warning">Unread</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <fmt:formatDate value="${notification.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                                            </td>
                                                            <td>
                                                                <a href="${ctx}/NotificationServlet?service=detail&id=${notification.notificationID}" class="btn btn-info btn-xs" title="View Detail"><i class="fa fa-eye"></i></a>
                                                                <form action="${ctx}/NotificationServlet" method="get" style="display:inline;">
                                                                    <input type="hidden" name="service" value="toggleRead"/>
                                                                    <input type="hidden" name="id" value="${notification.notificationID}"/>
                                                                    <button type="submit" class="btn btn-${notification.isRead ? 'warning' : 'success'} btn-sm">
                                                                        <i class="fa fa-${notification.isRead ? 'eye-slash' : 'eye'}"></i>
                                                                        ${notification.isRead ? 'Mark as Unread' : 'Mark as Read'}
                                                                    </button>
                                                                </form>
                                                                <a href="${ctx}/NotificationServlet?service=delete&notificationID=${notification.notificationID}" 
                                                                   class="btn btn-danger btn-xs" title="Delete" 
                                                                   onclick="return confirm('Are you sure you want to delete this notification?')">
                                                                    <i class="fa fa-trash"></i>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:if>
                                    <c:if test="${empty notifications}">
                                        <div class="text-center" style="padding: 50px;">
                                            <i class="fa fa-bell-o fa-3x text-muted"></i>
                                            <h3 class="text-muted">No notifications found</h3>
                                            <p class="text-muted">There are no notifications to display.</p>
                                        </div>
                                    </c:if>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            
            <jsp:include page="../../components/footer.jsp" />
            <jsp:include page="../../components/control-sidebar.jsp" />
            <div class="control-sidebar-bg"></div>
        </div>
        <!-- ./wrapper -->

        <!-- jQuery -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
        <!-- DataTables -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.min.js"></script>
        <!-- AdminLTE App -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
        <!-- SlimScroll -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>

        <style>
            .unread-notification {
                background-color: #f9f9f9;
                font-weight: bold;
            }
            .unread-notification td {
                border-color: #ddd;
            }
        </style>

        <script>
            $(function () {
                $('#notificationTable').DataTable({
                    "paging": true,
                    "lengthChange": true,
                    "searching": true,
                    "ordering": true,
                    "info": true,
                    "autoWidth": false,
                    "order": [[4, "desc"]], // Sort by Created At column descending
                    "pageLength": 25
                });
            });
            $(function () {
                $('.sidebar-menu').tree();
            });
        </script>
    </body>
</html> 