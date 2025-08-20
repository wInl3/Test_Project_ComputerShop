<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <style>
            .error {
                color: red;
                font-weight: bold;
            }
            .message {
                color: green;
                font-weight: bold;
            }
            .feedback-item {
                margin-bottom: 20px;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            .feedback-actions a {
                margin-right: 10px;
            }
            .review-form {
                max-width: 600px;
                margin: 20px 0;
            }
            .review-form label {
                display: inline-block;
                width: 100px;
                font-weight: bold;
            }
            .review-form textarea, .review-form select {
                width: 100%;
                margin-bottom: 15px;
                padding: 8px;
            }
            .back-button {
                font-size: 18px;
                border-radius: 8px;
                text-decoration: none;
                display: inline-block;
                margin-top: 20px;
                margin-bottom: 20px;
                border: 1px #000 solid;
            }
        </style>
    </head>
    <body>
        <header id="header">
            <!-- Nội dung header giữ nguyên -->
        </header>
        <!-- Hiển thị thông báo lỗi/thành công -->
        <c:if test="${not empty message}">
            <p class="message">${message}</p>
            <%
                request.getSession().removeAttribute("message");
            %>
        </c:if>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
            <%
                request.getSession().removeAttribute("error");
            %>
        </c:if>
        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <a href="javascript:history.back()" class="btn btn-secondary back-button">Back</a>
                        <c:if test="${sessionScope.user != null}">   

                            <!-- Danh sách feedback -->
                            <c:choose>
                                <c:when test="${not empty feedbackList}">
                                    <c:forEach var="fb" items="${feedbackList}">
                                        <div class="feedback-item">
                                            <p><strong>Date:</strong> ${fb.createdAt}</p>
                                            <p><strong>Rate:</strong> ${fb.rate} sao</p>
                                            <p>${fb.content}</p>
                                            <div class="feedback-actions">
                                                <c:if test="${sessionScope.user != null && (sessionScope.user.userId == fb.userID || sessionScope.user.role.roleID == 1)}">
                                                    <a href="${pageContext.request.contextPath}/feedback?action=edit&id=${fb.feedbackID}" class="btn btn-primary btn-sm">Edit</a>
                                                    <!--<a href="${pageContext.request.contextPath}/feedback?action=delete&id=${fb.feedbackID}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa feedback này?')">Xóa</a>-->
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <h3>Write Your Review</h3>
                                    <div class="card-left">
                                        <img src="${pageContext.request.contextPath}/ShopPages/Pages/images/cart/two.png" alt="Product">
                                        <h4 class="category-name">${item.category.categoryName}</h4>
                                    </div>                 
                                    <!-- Form gửi feedback mới -->
                                    <form id="submitFeedbackForm" action="${pageContext.request.contextPath}/submitFeedback" method="post" class="review-form">
                                        <!-- Sửa lại tên trường cho đúng -->
                                        <input type="hidden" name="orderItemID" value="${item.orderItemID}">
                                        <div class="form-group">
                                            <label>Rating:</label>
                                            <select name="rate" required>
                                                <option value="">Chọn đánh giá</option>
                                                <option value="5">5 sao</option>
                                                <option value="4">4 sao</option>
                                                <option value="3">3 sao</option>
                                                <option value="2">2 sao</option>
                                                <option value="1">1 sao</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Content:</label>
                                            <textarea name="content" rows="5" required></textarea>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${sessionScope.user == null}">
                            <p>Vui lòng <a href="${pageContext.request.contextPath}/Login">đăng nhập</a> để gửi feedback.</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </section>

        <footer id="footer">
            <!-- Nội dung footer giữ nguyên -->
        </footer>

        <script>
            document.getElementById('submitFeedbackForm')?.addEventListener('submit', function (event) {
                const content = document.querySelector('textarea[name="content"]').value.trim();
                const rating = document.querySelector('select[name="rating"]').value;
                if (!content) {
                    event.preventDefault();
                    alert('Nội dung không được để trống.');
                } else if (content.length > 500) {
                    event.preventDefault();
                    alert('Nội dung không được vượt quá 500 ký tự.');
                } else if (!rating) {
                    event.preventDefault();
                    alert('Vui lòng chọn đánh giá.');
                }
            });
        </script>

        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>