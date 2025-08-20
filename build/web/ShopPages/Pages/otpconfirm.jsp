<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OTP Confirmation | E-Shopper</title>
    <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
    <style>
        .otp-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 80vh;
        }
        .otp-form-box {
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.15);
            padding: 40px 32px 32px 32px;
            max-width: 400px;
            width: 100%;
            margin: 0 auto;
        }
        .otp-form-box h2 {
            text-align: center;
            margin-bottom: 24px;
            color: #f57c00;
            font-weight: 700;
        }
        .otp-form-box label {
            font-weight: 500;
            color: #333;
        }
        .otp-form-box .form-control {
            border-radius: 8px;
            margin-bottom: 18px;
            border: 1px solid #e0e0e0;
            box-shadow: none;
            text-align: center;
            font-size: 20px;
            letter-spacing: 8px;
        }
        .otp-form-box .btn-modern {
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
        .otp-form-box .btn-modern:hover {
            background: linear-gradient(90deg, #fda085 0%, #f6d365 100%);
            color: #fff;
        }
        .otp-form-box .custom-between {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .otp-form-box a {
            color: #f57c00;
            text-decoration: underline;
            font-size: 14px;
        }
        .alert-danger, .alert-success {
            margin-bottom: 16px;
        }
    </style>
</head>
<body>
    <%@ include file="components/header.jsp" %>
    <div class="otp-container">
        <div class="otp-form-box">
            <h2>OTP Confirmation</h2>
            <form action="OTPConfirm" method="POST">
                <label for="otp" class="form">Enter the OTP sent to your email</label>
                <input type="text" id="otp" name="otp" class="form-control" maxlength="6" required placeholder="------" autocomplete="off"/>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>
                <div class="custom-between mt-3">
                    <button type="submit" class="btn-modern">Confirm OTP</button>
                    <a href="Login">Back to Login</a>
                </div>
            </form>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
</body>
</html>
