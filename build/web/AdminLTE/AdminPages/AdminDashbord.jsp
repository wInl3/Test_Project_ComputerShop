<%-- 
    Document   : AdminDashbord
    Created on : May 28, 2025, 10:03:33 AM
    Author     : Admin
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Dashboard</title>
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
        <!-- iCheck -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/iCheck/flat/blue.css">
        <!-- Morris chart -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/morris/morris.css">
        <!-- jvectormap -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
        <!-- Date Picker -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/datepicker/datepicker3.css">
        <!-- Daterange picker -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/daterangepicker/daterangepicker.css">
        <!-- bootstrap wysihtml5 - text editor -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
        <!-- DataTables CSS -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.css">

        <style>
            #pagination button {
                margin: 0 3px;
                min-width: 80px;
            }
        </style>



        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <jsp:include page="components/header.jsp" />
            <jsp:include page="components/sidebar.jsp">
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Dashboard
                        <small>Control panel</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">Dashboard</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <!-- /.row -->
                    <!-- Main row -->
                    <div class="row">
                        <!-- Left col -->
                        <section class="col-lg-6 connectedSortable">
                            <!-- Custom tabs (Charts with tabs)-->
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title text-red"><i class="fa fa-exclamation-circle"></i> Categories Need to Import</h3>
                                </div>
                                <div class="box-body">
                                    <table id="importTable" class="table table-bordered table-striped text-center">
                                        <thead class="bg-light-blue">
                                            <tr>
                                                <th class="text-center">Category ID</th>
                                                <th class="text-center">Category Name</th>
                                                <th class="text-center">Inventory</th>
                                                <th class="text-center">Queue</th>
                                                <th class="text-center">Import</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="category" items="${list}">
                                                <tr>
                                                    <td>${category.categoryID}</td>
                                                    <td>${category.categoryName}</td>
                                                    <td>${category.inventory}</td>
                                                    <td>${category.queue}</td>
                                                    <td>
                                                        <a href="${ctx}/Import?categoryID=${category.categoryID}"
                                                           class="btn btn-primary btn-sm">Import</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <div id="pagination" class="text-center" style="margin-top: 15px;"></div>
                                </div>
                            </div>

                        </section>
                        <!-- right col -->
                    </div>
                    <!-- /.row (main row) -->

                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <jsp:include page="components/footer.jsp" />
        </div>
        <!-- ./wrapper -->

        <!-- jQuery -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>

        <!-- DataTables -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datatables/dataTables.bootstrap.min.js"></script>

        <!-- jQuery 2.2.3 -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <!-- jQuery UI 1.11.4 -->
        <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
        <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
        <script>
            $.widget.bridge('uibutton', $.ui.button);
        </script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
        <!-- Morris.js charts -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/morris/morris.min.js"></script>
        <!-- Sparkline -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/sparkline/jquery.sparkline.min.js"></script>
        <!-- jvectormap -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
        <!-- jQuery Knob Chart -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/knob/jquery.knob.js"></script>
        <!-- daterangepicker -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
        <script src="${ctx}/AdminLTE/AdminPages/plugins/daterangepicker/daterangepicker.js"></script>
        <!-- datepicker -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/datepicker/bootstrap-datepicker.js"></script>
        <!-- Bootstrap WYSIHTML5 -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
        <!-- Slimscroll -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
        <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/pages/dashboard.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>
        <!-- Notification Updater -->
        <script src="${ctx}/AdminLTE/AdminPages/js/notification-updater.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const rowsPerPage = 5;
                const table = document.getElementById("importTable");
                const tbody = table.querySelector("tbody");
                const rows = Array.from(tbody.querySelectorAll("tr"));
                const totalPages = Math.ceil(rows.length / rowsPerPage);
                const pagination = document.getElementById("pagination");

                let currentPage = 1;

                function renderTablePage(page) {
                    tbody.innerHTML = "";
                    const start = (page - 1) * rowsPerPage;
                    const end = start + rowsPerPage;
                    rows.slice(start, end).forEach(row => tbody.appendChild(row));
                }

                function renderPagination() {
                    pagination.innerHTML = "";

                    const prev = document.createElement("button");
                    prev.innerText = "Previous";
                    prev.className = "btn btn-default btn-sm";
                    prev.disabled = currentPage === 1;
                    prev.onclick = () => {
                        if (currentPage > 1) {
                            currentPage--;
                            renderTablePage(currentPage);
                            renderPagination();
                        }
                    };

                    const next = document.createElement("button");
                    next.innerText = "Next";
                    next.className = "btn btn-default btn-sm";
                    next.disabled = currentPage === totalPages;
                    next.onclick = () => {
                        if (currentPage < totalPages) {
                            currentPage++;
                            renderTablePage(currentPage);
                            renderPagination();
                        }
                    };

                    const pageInfo = document.createElement("span");
                    pageInfo.innerHTML = ` Page ${currentPage} of ${totalPages} `;
                    pageInfo.style.margin = "0 10px";

                    pagination.appendChild(prev);
                    pagination.appendChild(pageInfo);
                    pagination.appendChild(next);
                }

                if (rows.length > 0) {
                    renderTablePage(currentPage);
                    renderPagination();
                } else {
                    pagination.innerHTML = "<p class='text-muted'>No data to display</p>";
                }
            });
        </script>

    </body>
</html>

