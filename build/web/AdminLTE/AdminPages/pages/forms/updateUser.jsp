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
                    <h1>UPDATE USER</h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">User</a></li>
                        <li class="active">Update User</li>
                    </ol>
                </section>
                <div class="box box-primary">
                    <div class="box-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" style="font-weight:bold;">${error}</div>
                        </c:if>
                        <form method="post" action="${ctx}/User">
                            <input type="hidden" name="service" value="updateUser">
                            <input type="hidden" name="userID" value="${user.userId}">
                            <input type="hidden" name="roleID" value="${user.role.roleID}">
                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" id="fullName" name="fullName" class="form-control ${error eq 'Full name is required!' ? ' is-invalid' : ''}" required value="${user.fullname}">
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="email" class="form-control ${error eq 'Email is required!' || error eq 'Email existed!' ? ' is-invalid' : ''}" required value="${user.email}">
                            </div>
                            <div class="form-group">
                                <label for="phoneNumber">Phone Number</label>
                                <input type="text" id="phoneNumber" name="phoneNumber" class="form-control ${error eq 'Phone number is required!' || error eq 'Invalid phone number' || error eq 'Phone number existed!' || error eq 'Phone number must be exactly 10 digits!' ? ' is-invalid' : ''}" required value="${user.phoneNumber}">
                            </div>
                            <c:if test="${user.role.roleID == 3}">
                                <div class="form-group">
                                    <label for="address">Address</label>
                                    <input type="text" id="address" name="address" class="form-control ${error eq 'Address is required!' ? ' is-invalid' : ''}" required value="${address != null ? address : user.customerInfo.address}">
                                </div>
                            </c:if>

                            <c:if test="${user.role.roleID == 2 || user.role.roleID == 4}">
                                <div class="form-group">
                                    <label for="address">Start Date</label>
                                    <input type="date" id="StartedDate" name="StartedDate" class="form-control" required value="${user.staffInfo.startedDate}">
                                </div>
                                <div class="form-group">
                                    <label for="address">End Date</label>
                                    <input type="date" id="EndDate" name="EndDate" class="form-control" value="${user.staffInfo.endDate != null ? user.staffInfo.endDate : ''}">
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label for="roleID">Role</label>
                                <input type="text" id="roleID" name="roleID" class="form-control" readonly 
                                       value="${user.role.roleName}">
                            </div>
                            <div class="form-group">
                                <label for="createdAt">Created At</label>
                                <input type="text" id="createdAt" name="createdAt" class="form-control" readonly 
                                       value="<fmt:parseDate value='${user.createdAt}' pattern='yyyy-MM-dd HH:mm:ss' var='createdDate'/><fmt:formatDate value='${createdDate}' pattern='yyyy-MM-dd'/>">
                            </div>
                            <div class="form-group">
                                <label for="status">Status</label>
                                <c:choose>
                                    <c:when test="${sessionScope.user ne null && sessionScope.user.userId eq user.userId}">
                                        <input type="text" class="form-control" value="${user.status == 1 ? 'Active' : 'Inactive'}" readonly />
                                        <input type="hidden" name="status" value="${user.status}" />
                                    </c:when>
                                    <c:otherwise>
                                        <select id="status" name="status" class="form-control">
                                            <option value="1" ${user.status == 1 ? 'selected' : ''}>Active</option>
                                            <option value="0" ${user.status == 0 ? 'selected' : ''}>Inactive</option>
                                        </select>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                            <c:if test="${user.role.roleID == 1}">
                                <a href="${ctx}/Admin/user?type=admin" class="btn btn-default">Cancel</a>
                            </c:if>
                            <c:if test="${user.role.roleID == 2}">
                                <a href="${ctx}/Admin/user?type=sale" class="btn btn-default">Cancel</a>
                            </c:if>
                            <c:if test="${user.role.roleID == 3}">
                                <a href="${ctx}/Admin/user?type=customer" class="btn btn-default">Cancel</a>
                            </c:if>
                            <c:if test="${user.role.roleID == 4}">
                                <a href="${ctx}/Admin/user?type=shipper" class="btn btn-default">Cancel</a>
                            </c:if>
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
