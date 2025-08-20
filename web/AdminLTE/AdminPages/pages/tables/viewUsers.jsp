<%-- 
    Document   : insertProduct
    Created on : May 28, 2025, 9:48:28 PM
    Author     : Admin
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages//dist/css/custom.css?v=1.0.12">
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
                <jsp:param name="activeMenu" value="user"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>

            <!-- Content Wrapper -->
            <div class="content-wrapper">
                <!-- Content Header -->
                <section class="content-header">
                    <h1>
                        ${users.get(0).role.roleName} Management
                    </h1>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-dashboard"></i> Home</li>
                        <li>Users</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-body">
                                    <table id="example2" class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>User ID</th>
                                                <th>Full Name</th>
                                                <th>Email</th>
                                                <th>Phone</th>
                                                    <c:if test="${role == 'sale' || role == 'shipper'}"> 
                                                    <th>Started Date</th>
                                                    <th>End Date</th>
                                                    </c:if>
                                                    <c:if test="${role == 'sale'}"> 
                                                    <th>Orders this month</th>
                                                    </c:if>
                                                    <c:if test="${role == 'shipper'}"> 
                                                    <th>Ships this month</th>
                                                    </c:if>
                                                    <c:if test="${role == 'customer'}"> 
                                                    <th>Address</th>
                                                    </c:if>
                                                <th>Status</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${not empty users}">
                                                <c:forEach var="user" items="${users}">
                                                    <tr>
                                                        <td>${user.userId}</td>
                                                        <td>${user.fullname}</td>
                                                        <td>${user.email}</td>
                                                        <td>${user.phoneNumber}</td>
                                                        <c:if test="${role == 'sale' || role == 'shipper'}"> 
                                                            <td>${user.staffInfo.startedDate}</td>
                                                            <td>${user.staffInfo.endDate}</td>
                                                        </c:if>
                                                        <c:if test="${role == 'sale'}"> 
                                                            <td>${user.countNumberOfOrders1Month()}</td>
                                                        </c:if>
                                                        <c:if test="${role == 'shipper'}"> 
                                                            <td>${user.countNumberOfShips1Month()}</td>
                                                        </c:if>
                                                        <c:if test="${role == 'customer'}">
                                                            <td>${user.customerInfo != null ? user.customerInfo.address : ''}</td>
                                                        </c:if>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${user.status == 1}">
                                                                    <span class="label label-success">Active</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="label label-danger">Inactive</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <a href="${ctx}/Admin/user/update?userID=${user.userId}" class="btn btn-primary btn-sm">Update</a>
                                                            <a href="${ctx}/User?service=resetPassword&userID=${user.userId}&roleID=${user.role.roleID}"
                                                               onclick="return confirm(`Reset user's password?`);"
                                                               class="btn btn-danger btn-sm">Reset Password</a>
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
                </section>
            </div>

            <!-- /.content-wrapper -->
            <jsp:include page="../../components/footer.jsp" />

            <jsp:include page="../../components/control-sidebar.jsp" />
        </div>

        <!-- Toast Container -->
        <div id="toastContainer" style="
             position: fixed;
             top: 20px;
             right: 20px;
             z-index: 9999;
             min-width: 250px;
             ">
        </div>

        <!-- Toast style -->
        <style>
            .custom-toast {
                min-width: 250px;
                padding: 12px 20px;
                margin-bottom: 12px;
                border-radius: 6px;
                color: white;
                font-weight: bold;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
                opacity: 1;
                transition: opacity 0.5s ease;
            }

            .custom-toast.success {
                background-color: #28a745;
            }
            .custom-toast.error {
                background-color: #dc3545;
            }
            .custom-toast.warning {
                background-color: #ffc107;
                color: #333;
            }
        </style>

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
                                                                   function showToast(message, toastType) {
                                                                       const toast = document.createElement('div');
                                                                       toast.className = `custom-toast ${toastType}`;
                                                                       toast.textContent = message;
                                                                       console.log("Toast type:", toastType);
                                                                       document.getElementById('toastContainer').appendChild(toast);

                                                                       setTimeout(() => {
                                                                           toast.style.opacity = '0';
                                                                           setTimeout(() => toast.remove(), 500);
                                                                       }, 3000);
                                                                   }
        </script>


        <script>
            window.addEventListener('DOMContentLoaded', function () {
                const alertBox = document.getElementById("alertBox");
                if (alertBox) {
                    setTimeout(function () {
                        // Bootstrap 3: use jQuery .alert('close')
                        $(alertBox).alert('close');
                    }, 5000); // Tự biến mất sau 4 giây
                }
            });
        </script>
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
    <c:if test="${not empty toast}">
        <script>
            showToast("${toast}", "${toastType}");
        </script>
    </c:if>
    <%
        String toast = (String) session.getAttribute("toast");
        String toastType = (String) session.getAttribute("toastType");
        if (toast != null) {
            // Xoá sau khi hiển thị
            session.removeAttribute("toast");
            session.removeAttribute("toastType");
        }
    %>





</html>

