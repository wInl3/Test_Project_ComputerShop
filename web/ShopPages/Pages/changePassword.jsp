<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Change Password | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <style>
            .change-container {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 80vh;
            }
            .change-form-box {
                background: #fff;
                border-radius: 16px;
                box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.15);
                padding: 40px 32px 32px 32px;
                max-width: 400px;
                width: 100%;
                margin: 0 auto;
            }
            .change-form-box h2 {
                text-align: center;
                margin-bottom: 24px;
                color: #f57c00;
                font-weight: 700;
            }
            .change-form-box label {
                font-weight: 500;
                color: #333;
            }
            .change-form-box .form-control {
                border-radius: 8px;
                margin-bottom: 18px;
                border: 1px solid #e0e0e0;
                box-shadow: none;
            }
            .change-form-box .btn-modern {
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
            .change-form-box .btn-modern:hover {
                background: linear-gradient(90deg, #fda085 0%, #f6d365 100%);
                color: #fff;
            }
            .alert-danger, .alert-success {
                margin-bottom: 16px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="activeHeader" value="no"/>
        </jsp:include>
        <div class="change-container">
            <div class="change-form-box">
                <h2>Change Password</h2>
                <form action="User" method="POST" id="changePasswordForm" >
                    <input type="hidden" name="service" value="changePassword"/>
                    <input type="hidden" name="email" value="${email}"/>
                    <label for="password" class="form">New Password</label>
                    <input type="password" id="password" class="form-control${error == 'Password must be at least 6 characters.' ? ' is-invalid' : ''}" required placeholder="Enter new password"/>
                    <c:if test="${error == 'Password must be at least 6 characters.'}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <label for="confirmPassword" class="form">Confirm Password</label>
                    <input type="password" id="confirmPassword" class="form-control${error == 'Passwords do not match.' ? ' is-invalid' : ''}" required placeholder="Confirm new password"/>
                    <c:if test="${error == 'Passwords do not match.'}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>
                    <button type="submit" class="btn-modern">Change Password</button>
                    <a href="Login">Back to Login</a>
                    <!-- Thêm input hidden để chứa password đã hash -->
                    <input type="hidden" name="hashedPassword" id="hashedPassword">
                </form>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("changePasswordForm");

                form.addEventListener("submit", async function (e) {
                    e.preventDefault(); // chặn submit mặc định

                    const passwordInput = document.getElementById("password");
                    const confirmPasswordInput = document.getElementById("confirmPassword");
                    const hashedInput = document.getElementById("hashedPassword");

                    const password = passwordInput.value;
                    const confirmPassword = confirmPasswordInput.value;

                    // ✅ Kiểm tra độ dài
                    if (password.length < 6) {
                        alert("Password must be at least 6 characters.");
                        return;
                    }

                    // ✅ Kiểm tra khớp
                    if (password !== confirmPassword) {
                        alert("Passwords do not match.");
                        return;
                    }

                    // ✅ Nếu hợp lệ, tiến hành hash
                    const encoder = new TextEncoder();
                    const data = encoder.encode(password);

                    const hashBuffer = await crypto.subtle.digest('SHA-256', data);
                    const hashArray = Array.from(new Uint8Array(hashBuffer));
                    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');

                    hashedInput.value = hashHex;

                    form.submit(); // submit lại form sau khi đã hash
                });
            });
        </script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
    </body>
</html>
