<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Enter OTP | E-Shopper</title>
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
            .btn-disabled {
                opacity: 0.6;
                pointer-events: none;
                filter: grayscale(0.5);
            }
        </style>
    </head>
    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="activeHeader" value="no"/>
        </jsp:include>
        <div class="otp-container">
            <div class="otp-form-box">
                <h2>Enter OTP</h2>
                <form action="OTP" method="POST">
                    <label for="otp" class="form">Please enter the OTP sent to your email</label>
                    <input type="hidden" name="email" value="${email}" />
                    <input type="text" id="otp" name="otp" class="form-control" maxlength="6" required placeholder="------" autocomplete="off"/>
                    <div id="countdown" style="text-align:center;color:#f57c00;font-weight:bold;margin-bottom:10px;"></div>
                    <c:choose>
                        <c:when test="${error eq 'invalid'}">
                            <div class="alert alert-danger" id="error-message">The OTP you entered is incorrect. Please try again.</div>
                        </c:when>
                        <c:when test="${error eq 'expired'}">
                            <div class="alert alert-danger" id="error-message">OTP has expired. Please request a new one.</div>
                        </c:when>
                    </c:choose>

                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>
                    <c:if test="${not empty expirationTime}">
                        <input type="hidden" id="expirationTime" value="${expirationTime}" />
                    </c:if>
                    <div class="custom-between mt-3">
                        <button type="submit" class="btn-modern">Confirm OTP</button>
                        <a href="Login">Back to Login</a>
                    </div>
                </form>
                <div id="resend-otp-btn" style="display:none;text-align:center;margin-top:10px;">
                    <form action="User" method="POST" style="display:inline;">
                        <input type="hidden" name="service" value="sendOTP"/>
                        <input type="hidden" name="email" value="${email}"/>
                        <button type="submit" class="btn btn-warning">Resend OTP</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
        <script>
            // Lấy thời gian hết hạn từ server (milliseconds)
            let expirationTime = document.getElementById('expirationTime') ? parseInt(document.getElementById('expirationTime').value) : null;
            function getTimeLeft() {
                if (!expirationTime)
                    return 0;
                const now = new Date().getTime();
                return Math.max(0, Math.floor((expirationTime - now) / 1000));
            }
            const countdownEl = document.getElementById('countdown');
            const otpInput = document.getElementById('otp');
            const confirmBtn = document.querySelector('.btn-modern');
            const errorMessage = document.getElementById('error-message');
            function updateCountdown(timeLeft) {
                if (timeLeft > 0) {
                    const minutes = Math.floor(timeLeft / 60);
                    const seconds = timeLeft % 60;
                    const formattedTime =
                            (minutes < 10 ? '0' : '') + minutes + ':' +
                            (seconds < 10 ? '0' : '') + seconds;
                    countdownEl.textContent = 'OTP expires in ' + formattedTime;

                    if (confirmBtn)
                        confirmBtn.classList.remove('btn-disabled');
                    if (otpInput)
                        otpInput.disabled = false;
                    if (confirmBtn)
                        confirmBtn.disabled = false;
                    document.getElementById('resend-otp-btn').style.display = 'none';
                } else {
                    countdownEl.textContent = 'OTP has expired. Please request a new one!';
                    if (otpInput)
                        otpInput.disabled = true;
                    if (confirmBtn) {
                        confirmBtn.disabled = true;
                        confirmBtn.classList.add('btn-disabled');
                    }
                    document.getElementById('resend-otp-btn').style.display = 'block';
                    if (errorMessage && errorMessage.textContent.includes('incorrect')) {
                        errorMessage.style.display = 'none';
                    }
                }
            }

            // Initial call
            updateCountdown(getTimeLeft());
            const timer = setInterval(function () {
                let timeLeft = getTimeLeft();
                updateCountdown(timeLeft);
                if (timeLeft <= 0)
                    clearInterval(timer);
            }, 1000);
        </script>
    </body>
</html>
