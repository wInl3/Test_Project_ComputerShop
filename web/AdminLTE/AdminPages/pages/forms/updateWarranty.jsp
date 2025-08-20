<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Update User</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/custom.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <jsp:include page="../../components/header.jsp" />
            <jsp:include page="../../components/sidebar.jsp">
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>UPDATE Warranty</h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Warranty</a></li>
                        <li class="active">Update Warranty</li>
                    </ol>
                </section>
                <div class="box box-primary">
                    <div class="box-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" style="font-weight:bold;">${error}</div>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/WarrantyAdmin">
                            <input type="hidden" name="service" value="update"/>
                            <input type="hidden" name="submit" value="submit"/>
                            <input type="hidden" name="warrantyID" value="${param.warrantyID != null ? param.warrantyID : warranty.warrantyID}"/>

                            <!-- Warranty Period -->
                            <div class="form-group">
                                <label for="warrantyPeriod">Warranty Period (months)</label>
                                <input type="text" id="warrantyPeriod" name="warrantyPeriod"
                                       class="form-control"
                                       placeholder="Enter warranty period in months"
                                       value="${param.warrantyPeriod != null ? param.warrantyPeriod : warranty.warrantyPeriod}" />
                            </div>

                            <!-- Description -->
                            <textarea id="description" name="description" rows="3" class="form-control"
                                      placeholder="Enter warranty description (max 100 characters)">${param.description != null ? param.description : warranty.description}</textarea>


                            <!-- Status -->
                            <div class="form-group">
                                <label for="status">Status</label>
                                <select id="status" name="status" class="form-control">
                                    <option value="1"
                                            <c:if test="${param.status == '1' || (empty param.status and warranty.status == 1)}">selected</c:if>>
                                                Active
                                            </option>
                                            <option value="0"
                                            <c:if test="${param.status == '0' || (empty param.status and warranty.status == 0)}">selected</c:if>>
                                                Inactive
                                            </option>
                                    </select>
                                </div>

                                <!-- Submit -->
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                <a href="${pageContext.request.contextPath}/WarrantyAdmin" class="btn btn-default">Cancel</a>
                        </form>
                    </div>
                </div>
            </div>
            <jsp:include page="../../components/footer.jsp" />
            <jsp:include page="../../components/control-sidebar.jsp" />
        </div>
        <!-- jQuery 2.2.3 -->
        <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
        <!-- AdminLTE App -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="${ctx}/AdminLTE/AdminPages/dist/js/demo.js"></script>
        <script>
            $(function () {
                $('.sidebar-menu').tree();
            });
        </script>
        <style>
            .is-invalid {
                border: 2px solid #dc3545 !important;
                background-color: #f8d7da !important;
            }
        </style>
    </body>
</html>
