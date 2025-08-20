<%-- 
    Document   : addNewUser
    Created on : Jun 3, 2025, 10:14:57 AM
    Author     : PC ASUS
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Advanced form elements</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.6 -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <!-- daterange picker -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/daterangepicker/daterangepicker.css">
        <!-- bootstrap datepicker -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/datepicker/datepicker3.css">
        <!-- iCheck for checkboxes and radio inputs -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/iCheck/all.css">
        <!-- Bootstrap Color Picker -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/colorpicker/bootstrap-colorpicker.min.css">
        <!-- Bootstrap time Picker -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/timepicker/bootstrap-timepicker.min.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/plugins/select2/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">    
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/custom.css">

        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">

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
                <jsp:param name="activeMenu" value="category"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>INSERT CATEGORY </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Category</a></li>
                        <li class="active">Insert Category</li>
                    </ol>
                </section>
                <!-- Main content -->
                <div class="box box-success">
                    <div class="box-body">
                        <c:if test="${not empty errors}">
                            <div class="alert alert-danger" style="font-weight:bold;">
                                <ul>
                                    <c:forEach var="err" items="${errors}">
                                        <li>${err}</li>
                                        </c:forEach>
                                </ul>
                            </div>
                        </c:if>

                        <form method="post" action="${ctx}/CateAdmin" enctype="multipart/form-data">
                            <input type="hidden" name="service" value="insert">
                            <input type="hidden" name="submit" value="submit">

                            <!-- Category Name -->
                            <div class="form-group">
                                <label for="categoryName">Category Name</label>
                                <input type="text" id="categoryName" name="categoryName"
                                       class="form-control"
                                       placeholder="Enter category name"
                                       value="${param.categoryName}">
                            </div>

                            <!-- Brand -->
                            <div class="form-group">
                                <label for="brandID">Brand</label>
                                <select id="brandID" name="brandID" class="form-control">
                                    <option value="">-- Select Brand --</option>
                                    <c:forEach var="b" items="${brands}">
                                        <option value="${b.brandID}"
                                                <c:if test="${param.brandID == b.brandID}">selected</c:if>>
                                            ${b.brandName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Component -->
                            <div class="form-group">
                                <label for="componentID">Component</label>
                                <select id="componentID" name="componentID" class="form-control">
                                    <option value="">-- Select Component --</option>
                                    <c:forEach var="c" items="${components}">
                                        <option value="${c.componentID}"
                                                <c:if test="${param.componentID == c.componentID}">selected</c:if>>
                                            ${c.componentName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Price -->
                            <div class="form-group">
                                <label for="price">Price</label>
                                <input type="text" id="price" name="price"
                                       class="form-control"
                                       value="${param.price}">
                            </div>

                            <!-- Description -->
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea id="description" name="description"
                                          class="form-control" rows="3">${param.description}</textarea>
                            </div>

                            <!-- Status -->
                            <div class="form-group">
                                <label for="status">Status</label>
                                <select id="status" name="status" class="form-control">
                                    <option value="1" <c:if test="${param.status == '1'}">selected</c:if>>Active</option>
                                    <option value="0" <c:if test="${param.status == '0'}">selected</c:if>>Inactive</option>
                                    <option value="2" <c:if test="${param.status == '2'}">selected</c:if>>On Sale</option>
                                    </select>
                                </div>

                                <!-- Image Upload -->
                                <div class="form-group">
                                    <label for="imageFile">Category Image</label>
                                    <input type="file" id="imageFile" name="imageFile" class="form-control" accept="">
                                </div>

                                <!-- Submit -->
                                <button type="submit" class="btn btn-primary">Insert Category</button>
                                <a href="${ctx}/CateAdmin?service=list" class="btn btn-default">Cancel</a>
                        </form>
                    </div>




                    <!-- /.box -->
                </div>
                <jsp:include page="../../components/footer.jsp" />
                <jsp:include page="../../components/control-sidebar.jsp" />
            </div>
            <!-- ./wrapper -->
            <!-- jQuery 2.2.3 -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
            <!-- Bootstrap 3.3.6 -->
            <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
            <!-- Select2 -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/select2/select2.full.min.js"></script>
            <!-- InputMask -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/input-mask/jquery.inputmask.js"></script>
            <script src="${ctx}/AdminLTE/AdminPages/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
            <script src="${ctx}/AdminLTE/AdminPages/plugins/input-mask/jquery.inputmask.extensions.js"></script>
            <!-- date-range-picker -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
            <script src="${ctx}/AdminLTE/AdminPages/plugins/daterangepicker/daterangepicker.js"></script>
            <!-- bootstrap datepicker -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/datepicker/bootstrap-datepicker.js"></script>
            <!-- bootstrap color picker -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
            <!-- bootstrap time picker -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/timepicker/bootstrap-timepicker.min.js"></script>
            <!-- SlimScroll 1.3.0 -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/slimScroll/jquery.slimscroll.min.js"></script>
            <!-- iCheck 1.0.1 -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/iCheck/icheck.min.js"></script>
            <!-- FastClick -->
            <script src="${ctx}/AdminLTE/AdminPages/plugins/fastclick/fastclick.js"></script>
            <!-- AdminLTE App -->
            <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
            <!-- AdminLTE for demo purposes -->
            <script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>
            <!-- Page script -->
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const inputs = document.querySelectorAll(".is-invalid");

                    inputs.forEach(input => {
                        input.addEventListener("input", function () {
                            // B? class 'is-invalid'
                            input.classList.remove("is-invalid");

                            // T�m ph?n t? <p> b�o l?i g?n input nh?t v� x�a
                            const errorMsg = input.parentElement.querySelector("p");
                            if (errorMsg) {
                                errorMsg.remove();
                            }
                        });
                    });
                });
            </script>
    </body>
</html>

