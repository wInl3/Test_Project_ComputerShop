<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Blog Detail | E-Shopper</title>
        <link href="${ctx}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${ctx}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="${ctx}/ShopPages/Pages/js/html5shiv.js"></script>
        <script src="${ctx}/ShopPages/Pages/js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="${ctx}/ShopPages/Pages/images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${ctx}/ShopPages/Pages/images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            .post-title {
                font-size: 2em;
                color: #333;
                margin-bottom: 10px;
            }
            .post-meta {
                color: #777;
                font-size: 0.9em;
                margin-bottom: 20px;
                display: flex !important;
                justify-content: space-between !important;
                align-items: center;
                width: 100%;
                gap: 0;
            }
            .post-meta .post-date {
                text-align: left;
                min-width: 120px;
            }
            .post-meta .post-author {
                text-align: right;
                margin-left: auto;
                min-width: 120px;
            }
            .post-thumbnail {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-bottom: 20px;
                width: 100%;
                max-width: 500px;
                height: auto;
                max-height: 350px;
                overflow: hidden;
            }
            .post-thumbnail img {
                width: 100%;
                height: auto;
                object-fit: cover;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            }
            .post-brief {
                font-style: italic;
                color: #555;
                margin-bottom: 20px;
                font-size: 1.1em;
            }
            .post-content {
                line-height: 1.6;
                color: #444;
                margin-bottom: 20px;
            }
            .comments-section {
                margin-top: 30px;
            }
            .comment {
                border-top: 1px solid #ddd;
                padding: 10px 0;
            }
            .comment-meta {
                color: #777;
                font-size: 0.9em;
            }
            .comment-content {
                margin-top: 5px;
                color: #444;
            }
            .comment-form {
                margin-top: 20px;
            }
            .comment-form input, .comment-form textarea {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .comment-form button {
                padding: 10px 20px;
                background: #fe980f;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            .comment-form button:hover {
                background: #e68a00;
            }
            .back-link {
                display: inline-block;
                margin-top: 20px;
                text-decoration: none;
                color: #fe980f;
                font-weight: bold;
            }
            .back-link:hover {
                text-decoration: underline;
            }
            .blog-list {
                display: grid;
                grid-template-rows: repeat(5, auto);
                row-gap: 12px;
                margin-top: 10px;
            }

            .blog-card {
                padding: 14px;
                background-color: #f0f0f0;
                border: 1px solid #ddd;
                border-radius: 6px;
                box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            }
            .blog-card a {
                font-family: 'Arial', sans-serif;
                font-size: 13px;
                font-weight: normal;
                text-decoration: none;
                color: #000;
                display: block;
            }


            .title {
                font-size: 28px;
                text-align: center;
                margin-top: 30px;
                margin-bottom: 20px;
                font-weight: bold;
                color: #333;
            }

            .post-title {
                font-size: 36px;
                font-weight: 700;
                margin-bottom: 10px;
                color: #222;
                text-align: center;
            }

            .post-meta {
                text-align: center;
                font-size: 14px;
                color: #777;
                margin-bottom: 30px;
            }

            .post-meta span {
                margin: 0 10px;
            }

            .post-thumbnail {
                display: block;
                max-width: 100%;
                height: auto;
                margin: 0 auto 30px auto;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }

            .post-content {
                font-size: 18px;
                line-height: 1.7;
                color: #444;
                max-width: 800px;
                margin: 0 auto;
            }

            .post-content p {
                margin-bottom: 20px;
            }

        </style>
        <script>
            function toggleReplyForm(commentID) {
                const form = document.getElementById("reply-form-" + commentID);
                if (form) {
                    form.style.display = (form.style.display === "none") ? "block" : "none";
                }
            }

            function likeComment(commentID) {
                alert("You liked a comment" + commentID);

            }
        </script>

    </head>
    <body>
        <jsp:include page="/ShopPages/Pages/components/header.jsp">
            <jsp:param name="activePage" value="blog" />
        </jsp:include>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <h2>Blog Categories</h2>
                            <div>
                                <ul>
                                    <li><a href="blogdetail" style="color: black">All</a></li> 
                                        <c:forEach var="cat" items="${blog_categories}">
                                        <li>
                                            <a href="blogc?Bc_id=${cat.bc_id}" style="color: black;">${cat.bc_name}</a>
                                        </li>
                                    </c:forEach>

                                </ul>
                                <h2 style="padding-top: 20px">Latest Blogs</h2>
                                <ul>
                                    <div class="blog-list">
                                        <c:forEach items="${top5Posts}" var="p">

                                            <li>
                                                <div class="blog-card">
                                                    <a <a href="blogdetail?Post_id=${p.post_id}" style="color: black">
                                                        >
                                                        ${p.title} - <fmt:formatDate value="${p.updated_date}" pattern="dd/MM/yyyy"/>
                                                    </a>
                                                </div>
                                            </li>

                                        </c:forEach>

                                        <a href="blogc" class="btn btn-primary">VIEW ALL BLOGS</a>

                                    </div>
                                </ul>
                            </div>


                        </div>
                    </div>
                    <div class="col-sm-9">
                        <div class="blog-post-area">
                            <!-- Breadcrumb navigation -->
                            <nav aria-label="breadcrumb" style="margin-bottom: 18px;">
                                <ol class="breadcrumb" style="background: none; padding: 0; margin: 0;">
                                    <li class="breadcrumb-item"><a href="${ctx}/blogc" style="color: #fe980f;">Blog</a></li>
                                        <c:forEach var="cat" items="${blog_categories}">
                                            <c:if test="${cat.bc_id == post.bc_id}">
                                            <li class="breadcrumb-item">
                                                <a href="${ctx}/blogc?Bc_id=${cat.bc_id}" style="color: #fe980f;">${cat.bc_name}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                    <li class="breadcrumb-item active" aria-current="page" style="color: #333;">${post.title}</li>
                                </ol>
                            </nav>

                            <h2 class="title text-center">Blog Details</h2>
                            <h1 class="post-title">${post.title}</h1>
                            <div class="post-meta">
                                <span class="post-date"><fmt:formatDate value="${post.updated_date}" pattern="dd/MM/yyyy HH:mm"/></span>
                                <span class="post-author">${post.author}</span>
                            </div>

                            <c:if test="${not empty post.thumbnail}">
                                <img src="${post.thumbnail}" alt="Post Thumbnail" class="post-thumbnail">
                            </c:if>

                            <div class="post-content">
                                <p>${post.brief}</p>
                                <p>${post.content}</p>
                            </div>


                            <!-- Comments Section -->
                            <div class="comments-section">
                                <h3>Comments</h3>



                                <!-- Hàm đệ quy hiển thị bình luận -->
                                <c:forEach var="comment" items="${comments}">
                                    <div style="margin-left: ${comment.parentCommentID == null ? 0 : 40}px; border-left: 1px solid #ccc; padding-left: 10px; margin-top: 10px;">

                                        <p><strong>${comment.fullName}</strong> | ${comment.createdAt}</p>
                                        <p>${comment.commentText}</p>

                                        <!-- Nút Like và Reply -->
                                        <div style="margin-bottom: 10px;">
                                            <button type="button" onclick="likeComment(${comment.commentID})">👍 Like</button>
                                            <button type="button" onclick="toggleReplyForm(${comment.commentID})">💬 Reply</button>
                                        </div>

                                        <!-- Form trả lời, ẩn mặc định -->
                                        <div id="reply-form-${comment.commentID}" style="display:none;">
                                            <form action="${ctx}/blogdetail" method="post">
                                                <input type="hidden" name="Post_id" value="${post.post_id}" />
                                                <input type="hidden" name="Bc_id" value="${param.Bc_id}" />
                                                <input type="hidden" name="ParentCommentID" value="${comment.commentID}" />
                                                <textarea name="comment_text" rows="2" cols="50" placeholder="Reply..."></textarea><br>
                                                <button type="submit">Reply</button>
                                            </form>
                                        </div>


                                        <!-- Đệ quy replies -->
                                        <!-- Lặp lại reply con -->
                                        <c:if test="${not empty comment.replies}">
                                            <div class="replies" style="margin-left: 30px;">
                                                <jsp:include page="nested_comment.jsp">
                                                    <jsp:param name="comments" value="${comment.replies}" />
                                                </jsp:include>
                                            </div>
                                        </c:if>
                                    </div>
                                </c:forEach>


                                <script>
                                    function toggleReplyForm(commentID) {
                                        const form = document.getElementById("reply-form-" + commentID);
                                        form.style.display = form.style.display === "none" ? "block" : "none";
                                    }
                                </script>


                                <!-- Comment Form -->
                                <div class="comment-form">
                                    <h3>Add a Comment</h3>
                                    <form action="${pageContext.request.contextPath}/blogdetail" method="post">
                                        <input type="hidden" name="Post_id" value="${post.post_id}" />
                                        <textarea name="comment_text" placeholder="Your Comment" rows="4" required></textarea>
                                        <button type="submit">Submit Comment</button>
                                    </form>

                                </div>
                            </div>


                            <a href="${ctx}/blogc" class="back-link">Back to Blog List</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <jsp:include page="/ShopPages/Pages/components/footer.jsp" />

    <script src="${ctx}/ShopPages/Pages/js/jquery.js"></script>
    <script src="${ctx}/ShopPages/Pages/js/price-range.js"></script>
    <script src="${ctx}/ShopPages/Pages/js/jquery.scrollUp.min.js"></script>
    <script src="${ctx}/ShopPages/Pages/js/bootstrap.min.js"></script>
    <script src="${ctx}/ShopPages/Pages/js/jquery.prettyPhoto.js"></script>
    <script src="${ctx}/ShopPages/Pages/js/main.js"></script>
</body>
</html>