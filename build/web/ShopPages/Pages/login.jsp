<%-- 
    Document   : login
    Created on : May 22, 2025, 9:18:17 AM
    Author     : PC ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--/header-->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Login | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css?v=1.0.11" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">

    </head><!--/head-->

    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="activeHeader" value="no"/>
        </jsp:include>

        <section id="login-section" class="position-container">
            <div class="container form-wrapper">
                <div class="row custom-center">
                    <div class="col-sm-4 col-sm-offset-1 ml-0">
                        <div class="login-form form-modern">
                            <h2>Login to your account</h2>

                            <c:if test="${not empty sessionScope.error}">
                                <div class="alert alert-danger text-center">${sessionScope.error}</div>
                                <c:remove var="error" scope="session"/>
                            </c:if>

                            <form action="Login" method="POST" id="loginForm">
                                <label class="form">Email</label>
                                <input type="text" name="email"
                                       placeholder="Enter email address"
                                       class="form-control ${error == 'Email is required!' || error == 'Email does not exist!' ? 'input-modern-invalid' : ''}"
                                       value="${not empty email ? email : ''}" required />

                                <c:if test="${error == 'Email is required!' || error == 'Email does not exist!'}">
                                    <p class="text-danger error-message">${error}</p>
                                </c:if>

                                <!-- Hidden field để chứa mật khẩu đã băm -->
                                <input type="hidden" name="hashedPassword" id="hashedPassword">

                                <label class="form">Password</label>
                                <input type="password"
                                       id="password"
                                       placeholder="Password"
                                       class="form-control ${error == 'Password is required!' || error == 'Incorrect password!' ? 'input-modern-invalid' : ''}"
                                       required value="${not empty password ? password : ''}" />

                                <c:if test="${error == 'Password is required!' || error == 'Incorrect password!'}">
                                    <p class="text-danger error-message">${error}</p>
                                </c:if>

                                <div class="d-flex justify-content-between align-items-end">
                                    <a href="${pageContext.request.contextPath}/User?service=forgotPassword" class="forgot-password-link">Forgot Password?</a>
                                </div>

                                <div class="custom-between mt-3">
                                    <button type="submit" id="loginButton" class="btn-modern">Login</button>
                                    <a href="SignUp">SignUp</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Chọn tất cả input có class is-invalid
                const invalidInputs = document.querySelectorAll("input.is-invalid");
                invalidInputs.forEach(function (input) {
                    input.addEventListener("input", function () {
                        // Xóa class is-invalid khỏi input
                        input.classList.remove("is-invalid");
                        // Ẩn tất cả phần tử có class .error-message (nếu có)
                        const errorMessages = document.querySelectorAll(".error-message");
                        errorMessages.forEach(function (el) {
                            el.style.display = "none";
                        });
                    });
                });
                // Chọn tất cả input có class input-modern-invalid
                const modernInvalidInputs = document.querySelectorAll("input.input-modern-invalid");
                modernInvalidInputs.forEach(function (input) {
                    input.addEventListener("input", function () {
                        // Xóa class input-modern-invalid khỏi input
                        input.classList.remove("input-modern-invalid");
                        // Ẩn tất cả phần tử có class .error-message
                        const errorMessages = input.parentElement.querySelectorAll(".error-message");
                        errorMessages.forEach(function (el) {
                            el.style.display = "none";
                        });
                    });
                });
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("loginForm");
                const passwordInput = document.getElementById("password");
                const hashedInput = document.getElementById("hashedPassword");

                form.addEventListener("submit", async function (e) {
                    e.preventDefault(); // chặn submit mặc định

                    const encoder = new TextEncoder();
                    const data = encoder.encode(passwordInput.value);
                    const hashBuffer = await crypto.subtle.digest('SHA-256', data);
                    const hashArray = Array.from(new Uint8Array(hashBuffer));
                    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');

                    hashedInput.value = hashHex;
                    passwordInput.value = ""; // tránh gửi mật khẩu gốc

                    form.submit(); // submit lại form sau khi đã hash
                });
            });
        </script>

<!--        <script>
            const password = '<%= request.getAttribute("password") %>';
            const userPassword = '<%= request.getAttribute("userPassword") %>';
            console.log("Password:", password);
            console.log("userPassword", userPassword);
        </script>-->
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/price-range.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/main.js"></script>
    </body>
</html>
