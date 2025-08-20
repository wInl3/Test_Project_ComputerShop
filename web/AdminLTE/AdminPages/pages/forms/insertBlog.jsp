<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Insert Blog</title>
        <meta content="width=device-width, initial-scale=1" name="viewport">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/AdminLTE.min.css">
        <link rel="stylesheet" href="${ctx}/AdminLTE/AdminPages/dist/css/skins/_all-skins.min.css">
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <jsp:include page="../../components/header.jsp"/>
            <jsp:include page="../../components/sidebar.jsp">
                <jsp:param name="activeMenu" value="blog"/>
                <jsp:param name="ctx" value="${ctx}" />
            </jsp:include>

            <div class="content-wrapper">
                <section class="content-header">
                    <h1>Insert Blog</h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Blog</a></li>
                        <li class="active">Insert Blog</li>
                    </ol>
                </section>

                <section class="content">
                    <div class="box box-success">
                        <div class="box-body">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>

                            <form method="post" action="${ctx}/blogadd" enctype="multipart/form-data" onsubmit="return validateForm()">

                                <input type="hidden" name="service" value="insert">
                                <input type="hidden" name="submit" value="submit">

                                <!-- Title -->
                                <div class="form-group">
                                    <label for="title">Blog Title</label>
                                    <input type="text" id="title" name="title" class="form-control" placeholder="Enter blog title" required value="${param.title}">
                                    <p id="title-error" class="text-danger small"></p>
                                </div>

                                <!-- Author -->
                                <div class="form-group">
                                    <label for="author">Author</label>
                                    <input type="text" id="author" name="author" class="form-control" placeholder="Enter author name" required value="${param.author}">
                                    <p id="author-error" class="text-danger small"></p>
                                </div>

                                <!-- Updated Date -->
                                <div class="form-group">
                                    <label for="updated_date">Updated Date</label>
                                    <input type="date" id="updated_date" name="updated_date" class="form-control" required value="${param.updated_date}">
                                </div>

                                <!-- Content -->
                                <div class="form-group">
                                    <label for="content">Content</label>
                                    <textarea id="content" name="content" rows="8" class="form-control" placeholder="Enter blog content" required>${param.content}</textarea>
                                    <p id="content-error" class="text-danger small"></p>
                                </div>

                                <!-- Brief -->
                                <div class="form-group">
                                    <label for="brief">Brief</label>
                                    <input type="text" id="brief" name="brief" class="form-control" placeholder="Enter short description" required value="${param.brief}">
                                    <p id="brief-error" class="text-danger small"></p>
                                </div>

                                <!-- Thumbnail -->
                                <div class="form-group">
                                    <label for="thumbnail">Thumbnail</label>
                                    <input type="text" id="thumbnail" name="thumbnail" class="form-control" placeholder="thumbnail.jpg">
                                    <p id="thumbnail-error" class="text-danger small"></p>
                                </div>

                                <!-- Category -->
                                <div class="form-group">
                                    <label for="bc_id">Category</label>
                                    <select id="bc_id" name="bc_id" class="form-control" required>
                                        <option value="">Choose Category</option>
                                        <c:forEach var="cat" items="${blog_categories}">
                                            <option value="${cat.bc_id}" ${param.bc_id == cat.bc_id ? 'selected' : ''}>${cat.bc_name}</option>
                                        </c:forEach>
                                    </select>
                                    <p id="bc_id-error" class="text-danger small"></p>
                                </div>

                                <!-- Author ID -->
                                <div class="form-group">
                                    <label for="add_id">Author ID</label>
                                    <input type="number" id="add_id" name="add_id" class="form-control"
                                           value="${sessionScope.user != null ? sessionScope.user.userId : ''}">
                                </div>

                                <!-- Status -->
                                <div class="form-group">
                                    <label>Status</label>
                                    <select class="form-control" name="status" required>
                                        <option value="1" ${param.status == '1' ? 'selected' : ''}>Active</option>
                                        <option value="0" ${param.status == '0' ? 'selected' : ''}>Inactive</option>
                                    </select>
                                </div>

                                <!-- Submit -->
                                <button type="submit" class="btn btn-primary">Add Blog</button>
                            </form>
                        </div>
                    </div>
                </section>
            </div>

            <jsp:include page="../../components/footer.jsp"/>
            <jsp:include page="../../components/control-sidebar.jsp"/>

        </div>

        <!-- Scripts -->
        <script>
            function validateForm() {
                let isValid = true;

                const title = document.getElementById('title').value.trim();
                const author = document.getElementById('author').value.trim();
                const brief = document.getElementById('brief').value.trim();
                const content = document.getElementById('content').value.trim();
                const bc_id = document.getElementById('bc_id').value;
                const thumbnail = document.getElementById('thumbnail').value;

                document.getElementById('title-error').textContent = '';
                document.getElementById('author-error').textContent = '';
                document.getElementById('brief-error').textContent = '';
                document.getElementById('content-error').textContent = '';
                document.getElementById('bc_id-error').textContent = '';
                document.getElementById('thumbnail-error').textContent = '';

                if (title.length < 5) {
                    document.getElementById('title-error').textContent = 'Title must be at least 5 characters';
                    isValid = false;
                }
                if (author.length < 2) {
                    document.getElementById('author-error').textContent = 'Author must be at least 2 characters';
                    isValid = false;
                }
                if (brief.length < 10) {
                    document.getElementById('brief-error').textContent = 'Brief must be at least 10 characters';
                    isValid = false;
                }
                if (content.length < 20) {
                    document.getElementById('content-error').textContent = 'Content must be at least 20 characters';
                    isValid = false;
                }
                if (!bc_id) {
                    document.getElementById('bc_id-error').textContent = 'Please select a category';
                    isValid = false;
                }
                if (thumbnail && !thumbnail.match(/\.(jpg|jpeg|png|gif)$/i)) {
                    document.getElementById('thumbnail-error').textContent = 'Thumbnail must be jpg, jpeg, png or gif';
                    isValid = false;
                }

                return isValid;
            }
        </script>

    </body>
</html>
