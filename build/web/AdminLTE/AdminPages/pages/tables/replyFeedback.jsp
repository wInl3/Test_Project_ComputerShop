<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>AdminLTE 2 | Reply Feedback</title>
    <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
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
            <h1>
                Trả lời Feedback
                <small>Admin trả lời phản hồi của khách hàng</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="${ctx}/FeedBackAdmin"><i class="fa fa-dashboard"></i> Feedback Tables</a></li>
                <li class="active">Reply Feedback</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Trả lời Feedback</h3>
                        </div>
                        <div class="box-body">
                            <b>Người gửi:</b> ${feedback.fullname}<br/>
                            <b>Sản phẩm:</b>
                            <a href="${pageContext.request.contextPath}/productDetail?categoryID=${feedback.categoryID}" target="_blank">
                                ${feedback.categoryName}
                            </a><br/>
                            <b>Nội dung feedback:</b> ${feedback.content}<br/>
                            <b>Ngày gửi:</b> ${feedback.createdAt}<br/>
                        </div>
                        <div class="box-footer">
                            <form action="${pageContext.request.contextPath}/admin/replyFeedback" method="post">
                                <input type="hidden" name="feedbackID" value="${feedback.feedbackID}" />
                                <div class="form-group">
                                    <label for="reply">Nội dung trả lời:</label>
                                    <textarea name="reply" id="reply" class="form-control" rows="4" placeholder="Nhập nội dung trả lời...">${feedback.reply}</textarea>
                                </div>
                                <button type="submit" class="btn btn-success">Gửi trả lời</button>
                                <a href="${pageContext.request.contextPath}/FeedBackAdmin" class="btn btn-default" style="margin-left:10px;">Quay lại</a>
                                <c:if test="${not empty feedback.reply}">
                                    <button type="submit" name="deleteReply" value="true" class="btn btn-danger" style="margin-left:10px;"
                                        onclick="return confirm('Bạn có chắc muốn xóa trả lời này không?');">
                                        Xóa trả lời
                                    </button>
                                </c:if>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <jsp:include page="../../components/footer.jsp" />
    <jsp:include page="../../components/control-sidebar.jsp" />
</div>
<script src="${ctx}/AdminLTE/AdminPages/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/AdminLTE/AdminPages/dist/js/app.min.js"></script>
</body>
</html>