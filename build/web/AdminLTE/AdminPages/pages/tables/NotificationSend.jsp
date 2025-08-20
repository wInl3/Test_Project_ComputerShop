<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Send Notification</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.css">
    <style>
        .checkbox-custom {
            width: 18px; height: 18px;
            accent-color: #007bff;
        }
        .checkbox-custom:checked {
            box-shadow: 0 0 0 2px #007bff;
        }
        .sendall-label {
            font-weight: bold;
            font-size: 16px;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <jsp:include page="../../components/header.jsp" />
    <jsp:include page="../../components/sidebar.jsp">
        <jsp:param name="ctx" value="${ctx}" />
    </jsp:include>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Send Notification
                <small>Send a notification to users</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="${ctx}/AdminDashbordServlet"><i class="fa fa-dashboard"></i> Home</a></li>
                <li><a href="${ctx}/NotificationServlet?service=list">Notifications</a></li>
                <li class="active">Send Notification</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">Send Notification</h3>
                        </div>
                        <div class="box-body">
                            <c:if test="${not empty successMessage}">
                                <div class="alert alert-success" role="alert">
                                    ${successMessage}
                                </div>
                            </c:if>
                            <form action="${ctx}/NotificationServlet" method="post">
                                <input type="hidden" name="service" value="send" />
                                <input type="hidden" name="senderID" value="${sessionScope.user.userId}" />
                                <div class="form-group">
                                    <label class="sendall-label">
                                        <input type="checkbox" id="sendAll" name="sendAll" value="true" class="checkbox-custom">
                                        <span id="sendAllText">Send to all users</span>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label>Select users to send notification:</label>
                                    <table id="userTable" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th><input type="checkbox" id="checkAll" class="checkbox-custom"></th>
                                                <th>Full Name</th>
                                                <th>Email</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="u" items="${userList}">
                                                <tr>
                                                    <td><input type="checkbox" name="userID" value="${u.userId}" class="user-checkbox checkbox-custom"></td>
                                                    <td>${u.fullname}</td>
                                                    <td>${u.email}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="form-group">
                                    <label for="title" style="font-size: 1.5em; font-weight: bold;">Title:</label>
                                    <input type="text" class="form-control" id="title" name="title" required style="font-size: 1.5em; height: 48px; font-weight: bold;" />
                                </div>
                                <div class="form-group">
                                    <label for="message" style="font-size: 1.2em; font-weight: bold;">Message:</label>
                                    <textarea class="form-control" id="message" name="message" required style="font-size: 1.2em; height: 80px;"></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Send</button>
                                <a href="${ctx}/NotificationServlet?service=list" class="btn btn-default">Cancel</a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="../../components/footer.jsp" />
    <jsp:include page="../../components/control-sidebar.jsp" />
    <div class="control-sidebar-bg"></div>
</div>
<script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/plugins/fastclick/fastclick.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>
<script>
    $(function () {
        $('#userTable').DataTable({
            "columnDefs": [
                { "orderable": false, "targets": 0 }
            ]
        });
        $('#checkAll').on('change', function() {
            $('.user-checkbox').prop('checked', this.checked);
        });
        $('#sendAll').on('change', function() {
            var checked = this.checked;
            $('.user-checkbox, #checkAll').prop('disabled', checked);
            if (checked) {
                $('.user-checkbox, #checkAll').prop('checked', true);
            } else {
                $('.user-checkbox, #checkAll').prop('checked', false);
            }
        });
        $('.sidebar-menu').tree();
    });
</script>
</body>
</html> 