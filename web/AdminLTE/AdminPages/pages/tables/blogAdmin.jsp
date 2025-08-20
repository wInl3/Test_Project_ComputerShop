<%-- 
    Document   : AdminDashboard
    Created on : May 28, 2025, 10:03:33 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>BlogAdmin | Dashboard</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.6 -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins -->
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
        <!-- Bootstrap WYSIHTML5 -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
        <script type="text/javascript">
            function doDelete(Post_id) {
                if (confirm("Are you sure to delete Post with Post_id =" + Post_id)) {
                    window.location = "${pageContext.request.contextPath}/blogdelete?Post_id=" + Post_id;
                }
            }
        </script>

    </head>
    <body class="hold-transition skin-blue sidebar-mini">

        <div class="wrapper">




            <jsp:include page="../../components/header.jsp" />

            <jsp:include page="../../components/sidebar.jsp">
                <jsp:param name="activeMenu" value="blog"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>

            <div class="content-wrapper">
                <section class="content-header">
                    <h1>Blog Table</h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Blogs</a></li>
                        <li class="active">Blog Tables</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Manage Blogs | Total Blogs: <c:out value="${fn:length(requestScope.postlist)}" /></h3>

                                    <a href="${pageContext.request.contextPath}/blogadd" class="btn btn-primary pull-right" style="margin-top: 10px;">
                                        Add New Blog
                                    </a>


                                </div>
                                <div style="margin-left: 50px;">
                                    <form action="${ctx}/bloga" method="get" class="form-inline"
                                          style="display: flex; gap: 20px; max-width: 900px; flex-wrap: wrap;">

                                        <select name="sort" class="form-control" onchange="this.form.submit()">
                                            <option value="default" ${selectedSort == 'default' ? 'selected' : ''}>Default</option>
                                            <option value="A to Z" ${selectedSort == 'A to Z' ? 'selected' : ''}>A to Z</option>
                                            <option value="Z to A" ${selectedSort == 'Z to A' ? 'selected' : ''}>Z to A</option>
                                        </select>


                                        <select name="role" class="form-control" style="min-width: 150px;" onchange="this.form.submit()">
                                            <option value="all" ${selectedRole == null || selectedRole == 'all' ? 'selected' : ''}>All Roles</option>
                                            <option value="admin" ${selectedRole == 'admin' ? 'selected' : ''}>Admin</option>
                                            <option value="sale" ${selectedRole == 'sale' ? 'selected' : ''}>Sale</option>
                                        </select>


                                        <div style="display: flex; gap: 5px;">
                                            <input type="text" name="q" class="form-control" 
                                                   placeholder="Search by title..." style="min-width: 250px;" 
                                                   value="${param.q}" />

                                            <button type="submit" style="background-color: #367FA9; color: white; border: none; padding: 6px 12px;">
                                                <i class="fa fa-search"></i> Search
                                            </button>

                                        </div>
                                    </form>
                                </div>


                                <div class="box-body">
                                    <c:if test="${empty requestScope.postlist}">
                                        <p>No blog posts available.</p>
                                    </c:if>
                                    <c:if test="${not empty requestScope.postlist}">
                                        <table id="example2" class="table table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Title</th>
                                                    <th>Author</th>
                                                    <th>Updated Date</th>
                                                    <th>Content</th>
                                                    <th>Category</th>
                                                    <th>Thumbnail</th>
                                                    <th>Brief</th>
                                                    <th>Author ID</th>
                                                    <th>status</th>


                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="po" items="${requestScope.postlist}">
                                                    <tr>
                                                        <td>${po.post_id}</td>
                                                        <td><c:out value="${po.title}" /></td>
                                                        <td><c:out value="${po.author}" /></td>
                                                        <td><fmt:formatDate value="${po.updated_date}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
                                                        <td><c:out value="${fn:substring(po.content, 0, 50)}..." /></td>
                                                        <td>${po.bc_name}</td>

                                                        <td><img src="${po.thumbnail}" alt="Thumbnail" style="max-width: 100px;" onerror="this.src='${ctx}/AdminLTE/dist/img/default-thumbnail.jpg'" /></td>
                                                        <td><c:out value="${fn:substring(po.brief, 0, 50)}..." /></td>
                                                        <td>${po.add_id}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${po.status == 1}">

                                                                    <span class="btn btn-sm btn-primary">Active</span>

                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="btn btn-sm btn-primary">Disable</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>

                                                        <td>
                                                            <a href="${ctx}/blogupdate?post_id=${po.post_id}" class="btn btn-sm btn-primary">Update</a>

                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>


            <jsp:include page="../../components/footer.jsp" />

            <jsp:include page="../../components/control-sidebar.jsp" />0
            <div class="control-sidebar-bg"></div>
        </div>
        <!-- jQuery 2.2.3 -->

        <script src="${ctx}/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${ctx}/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
        <!-- DataTables -->
        <script src="${ctx}/AdminLTE/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="${ctx}/AdminLTE/plu.gins/datatables/dataTables.bootstrap.min.js"></script>
        <!-- SlimScroll -->
        <script src="${ctx}/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="${ctx}/AdminLTE/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="${ctx}/AdminLTE/dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="${ctx}/AdminLTE/dist/js/demo.js"></script>
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
                                                $('#example2').DataTable({
                                                    "paging": true,
                                                    "lengthChange": false,
                                                    "searching": true,
                                                    "ordering": true,
                                                    "info": true,
                                                    "autoWidth": false
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