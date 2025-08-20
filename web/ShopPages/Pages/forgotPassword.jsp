<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Forgot Password | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css" rel="stylesheet">
        <style>
            .forgot-container {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 80vh;
            }
            .forgot-form-box {
                background: #fff;
                border-radius: 16px;
                box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.15);
                padding: 40px 32px 32px 32px;
                max-width: 400px;
                width: 100%;
                margin: 0 auto;
            }
            .forgot-form-box h2 {
                text-align: center;
                margin-bottom: 24px;
                color: #f57c00;
                font-weight: 700;
            }
            .forgot-form-box label {
                font-weight: 500;
                color: #333;
            }
            .forgot-form-box .form-control {
                border-radius: 8px;
                margin-bottom: 18px;
                border: 1px solid #e0e0e0;
                box-shadow: none;
            }
            .forgot-form-box .btn-modern {
                background: linear-gradient(90deg, #f6d365 0%, #fda085 100%);
                color: #fff;
                border: none;
                border-radius: 8px;
                padding: 10px 0;
                width: 100%;
                font-weight: 600;
                font-size: 16px;
                transition: background 0.3s;
            }
            .forgot-form-box .btn-modern:hover {
                background: linear-gradient(90deg, #fda085 0%, #f6d365 100%);
                color: #fff;
            }
            .forgot-form-box .custom-between {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .forgot-form-box a {
                color: #f57c00;
                text-decoration: underline;
                font-size: 14px;
            }
            .alert-danger, .alert-success {
                margin-bottom: 16px;
            }
            #email {
                margin-bottom: 30px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="activeHeader" value="no"/>
        </jsp:include>
        <div class="forgot-container">
            <div class="forgot-form-box">
                <h2>Forgot Password</h2>
                <form action="User" method="POST">
                    <label for="email" class="form">Enter your email address</label>
                    <input type="hidden" name="service" value="sendOTP"/>
                    <input type="email" id="email" name="email" class="form-control ${error == null ? '' : 'is-invalid'}" required placeholder="Email address" value="${email}"/>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <div class="custom-between mt-3">
                        <button type="submit" class="btn-modern">Send OTP</button>
                        <a href="Login">Back to Login</a>
                    </div>
                </form>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
    </body>
</html>
