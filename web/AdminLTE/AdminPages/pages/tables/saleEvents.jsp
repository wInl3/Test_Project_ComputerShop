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
        <title>Sale Events | Dashboard</title>
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

        <style>
            .form-container {
                margin-bottom: 20px;
                padding: 10px;
                display: flex;
                align-items: center;
            }

            .form-container label {
                margin-right: 10px;
                font-weight: normal;
            }

            .form-container select {
                padding: 5px;
                border: 1px solid #ccc;
                border-radius: 3px;
                margin-right: 10px;
                font-size: 14px;
                background-color: #fff;
            }

            .form-container input[type="submit"] {
                padding: 5px 10px;
                border: 1px solid #ccc;
                border-radius: 3px;
                background-color: #fff;
                cursor: pointer;
                font-size: 14px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
                background-color: #fff;
            }

            th, td {
                padding: 8px;
                text-align: left;
                border: 1px solid #ccc;
                font-size: 14px;
            }

            th {
                font-weight: bold;
                border-bottom: 2px solid #ccc;
            }

            tr {
                background-color: #fff;
            }

            .thumbnail img {
                max-width: 100px;
                max-height: 100px;
            }

            .status-btn, .update-btn {
                padding: 5px 10px;
                border: 1px solid #ccc;
                border-radius: 3px;
                background-color: #fff;
                cursor: pointer;
                font-size: 12px;
                margin-left: 5px;
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
                    <h1>Sale Events Table</h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Sale Events</a></li>
                        <li class="active">Sale Events Tables</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Sale Events | Total Events: ${fn:length(saleEvents)}</h3>
                                    <a href="${pageContext.request.contextPath}/addsale" class="btn btn-primary pull-right" style="margin-top: 10px;">
                                        Add Sale Events
                                    </a>
                                </div>

                                <div class="form-container">
                                    <form action="saleevents" method="get">
                                        <label for="categoryID"></label>
                                        <select name="categoryID" id="categoryID">
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.categoryID}" ${category.categoryID == selectedCategoryID ? 'selected' : ''}>
                                                    ${category.categoryName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit" style="background-color: #367FA9; color: white; border: none; padding: 6px 12px;">
                                            <i class="btn-primary"></i> View Events
                                        </button>
                                    </form>
                                </div>
                                <div class="box-body">
                                    <table>
                                        <tr>
                                            <th>Event ID</th>
                                            <th>Category</th>
                                            <th>Post Title</th>
                                            <th>Image</th>
                                            <th>Brand</th>
                                            <th>Original Price</th>
                                            <th>Discounted Price</th>
                                            <th>Start Date</th>
                                            <th>End Date</th>
                                            <th>Discount (%)</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                            <th>Approval</th>
                                        </tr>
                                        <c:choose>
                                            <c:when test="${empty saleEvents}">
                                                <tr><td colspan="9" style="text-align:center; font-style:italic;">No sale events available.</td></tr>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="event" items="${saleEvents}">
                                                    <tr>
                                                        <td>${event.eventID}</td>
                                                        <td>
                                                            <c:forEach var="category" items="${categories}">
                                                                <c:if test="${category.categoryID == event.categoryID}">
                                                                    ${category.categoryName}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                        <td>
                                                            <c:forEach var="post" items="${activePosts}">
                                                                <c:if test="${post.post_id == event.post_id}">
                                                                    ${post.title}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                        <td>
                                                            <c:if test="${not empty event.imgURL}">
                                                                <img src="${event.imgURL}" alt="Event Image" style="max-width: 80px; max-height: 80px;" />
                                                            </c:if>
                                                        </td>
                                                        <td>${event.brandName}</td>
                                                        <td><fmt:formatNumber value="${event.originalPrice}" type="currency" /></td>
                                                        <td><fmt:formatNumber value="${event.discountedPrice}" type="currency" /></td>

                                                        <td><fmt:formatDate value="${event.startDate}" pattern="yyyy-MM-dd"/></td>
                                                        <td><fmt:formatDate value="${event.endDate}" pattern="yyyy-MM-dd"/></td>
                                                        <td>${event.discountPercent}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${event.status == 0}">Out of Stock</c:when>
                                                                <c:when test="${event.status == 1}">Active</c:when>
                                                                <c:when test="${event.status == 2}">Pending</c:when>
                                                                <c:when test="${event.status == 3}">Ended</c:when>
                                                                <c:otherwise>Unknown</c:otherwise>
                                                            </c:choose>
                                                        </td>

                                                        <!-- New Action column -->
                                                        <td>
                                                            <a href="${ctx}/viewUser?userID=${event.createdBy}" style="color:#337ab7; display:block;">
                                                                ${event.createdByName}
                                                            </a>
                                                            <a href="${ctx}/updatesale?eventID=${event.eventID}" class="btn btn-sm btn-info">Update</a>
                                                            <c:if test="${event.status == 2}">
                                                                <form action="${ctx}/approveSaleEvent" method="post" style="display:inline;">
                                                                    <input type="hidden" name="eventID" value="${event.eventID}" />
                                                                    <button type="submit" class="btn btn-sm btn-success">Approve</button>
                                                                </form>
                                                            </c:if>
                                                        </td>

                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${event.approvedBy != null}">
                                                                    <a href="${ctx}/viewUser?userID=${event.approvedBy}" style="color:#337ab7;">
                                                                        ${event.approvedByName}
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span style="color:gray;">Waiting Approval</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </table>


                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </section>

    <!-- jQuery 2.2.3 -->
    <script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
    <!-- AdminLTE App -->
    <script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
</body>                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
</html>