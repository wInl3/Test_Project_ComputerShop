<%-- 
    Document   : viewFeedback
    Created on : May 28, 2025, 9:48:28 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Feedback Tables</title>
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
                <jsp:param name="activeMenu" value="feedback"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Feedback Tables
                    </h1>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-dashboard"></i> Home/li>
                        <li>Feedback</li>
                        <li class="active">Feedback Tables</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <div class="row align-items-center">
                                        <!-- Optional: place search or filter tools here -->
                                    </div>
                                </div>

                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Feedback ID</th>
                                                <th>Full Name</th>
                                                <th>Review</th>
                                                <th>Admin Reply</th>
                                                <th>Category</th>
                                                <th>Rate</th>
                                                <th>Created At</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                                <th>Reply</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${not empty feedbacks}">
                                                    <c:forEach var="feedback" items="${feedbacks}">
                                                        <tr>
                                                            <td>${feedback.feedbackID}</td>
                                                            <td>${feedback.fullname}</td>
                                                            <td style="white-space: normal; word-break: break-word; max-width: 200px;">
                                                                <span class="feedback-content" title="${feedback.content}">
                                                                    <c:choose>
                                                                        <c:when test="${fn:length(feedback.content) > 50}">
                                                                            ${fn:substring(feedback.content, 0, 50)}... <i class="fa fa-info-circle"></i>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            ${feedback.content}
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </span>
                                                            </td>
                                                            <td style="white-space: normal; word-break: break-word; max-width: 200px;">
                                                                <span class="feedback-reply" title="${feedback.reply}">
                                                                    <c:choose>
                                                                        <c:when test="${not empty feedback.reply && fn:length(feedback.reply) > 50}">
                                                                            ${fn:substring(feedback.reply, 0, 50)}... <i class="fa fa-info-circle"></i>
                                                                        </c:when>
                                                                        <c:when test="${not empty feedback.reply}">
                                                                            ${feedback.reply}
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">Not replied</span></c:otherwise>
                                                                    </c:choose>
                                                                </span>
                                                            </td>
                                                            <td>${feedback.categoryName}</td>
                                                            <td>${feedback.rate}</td>
                                                            <td>${feedback.createdAt}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${feedback.status == 0}"> 
                                                                        <label class="label label-danger">Inactive</label>
                                                                    </c:when>
                                                                    <c:when test="${not empty feedback.reply}">
                                                                        <span class="label label-primary">Replied</span>
                                                                    </c:when>
                                                                    <c:when test="${feedback.status == 1}">
                                                                        <span class="label label-success">Not replied</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="label label-default">Unknown</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <c:if test="${feedback.status == 1 || feedback.status == 2}">
                                                                    <form action="${pageContext.request.contextPath}/admin/updateFeedbackStatus" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc chắn muốn thay đổi trạng thái feedback này?');">
                                                                        <input type="hidden" name="feedbackID" value="${feedback.feedbackID}" />
                                                                        <button type="submit" name="status" value="0" class="btn btn-xs btn-danger">Set Inactive</button>
                                                                    </form>
                                                                </c:if>
                                                                <c:if test="${feedback.status == 0}">
                                                                    <form action="${pageContext.request.contextPath}/admin/updateFeedbackStatus" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc chắn muốn thay đổi trạng thái feedback này?');">
                                                                        <input type="hidden" name="feedbackID" value="${feedback.feedbackID}" />
                                                                        <button type="submit" name="status" value="1" class="btn btn-xs btn-success">Set Active</button>
                                                                    </form>
                                                                </c:if>
                                                            </td>
                                                            <td>
                                                                <c:if test="${feedback.status == 1}">
                                                                    <a href="${pageContext.request.contextPath}/admin/replyFeedback?feedbackID=${feedback.feedbackID}" class="btn btn-xs btn-primary" onclick="return confirm('Bạn có chắc chắn muốn trả l�?i feedback này?');">Reply</a>
                                                                </c:if>
                                                                <c:if test="${feedback.status == 2}">
                                                                    <a href="${pageContext.request.contextPath}/admin/replyFeedback?feedbackID=${feedback.feedbackID}&edit=true" class="btn btn-xs btn-info" onclick="return confirm('Bạn có chắc chắn muốn sửa trả l�?i này?');">Edit</a>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td colspan="10" class="text-center text-muted">No feedback found.</td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
                    "autoWidth": true,
                    "stateSave": true
                });
            });
        </script>
        <script>
            $(function () {
                $('.sidebar-menu').tree();
            });
        </script>
        <!-- Tooltip cho nội dung dài -->
        <script>
            $(document).tooltip({
                selector: '.feedback-content, .feedback-reply',
                placement: 'top'
            });
        </script>
    </body>
</html>