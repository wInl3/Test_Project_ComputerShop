<%-- Document : login Created on : May 22, 2025, 9:18:17 AM Author : PC ASUS
--%> <%@page contentType="text/html" pageEncoding="UTF-8"%> <%@taglib
    uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Sign Up | Computer Shop</title>
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css"
                rel="stylesheet"
                />
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css"
                rel="stylesheet"
                />
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/prettyPhoto.css"
                rel="stylesheet"
                />
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/price-range.css"
                rel="stylesheet"
                />
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/animate.css"
                rel="stylesheet"
                />
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css"
                rel="stylesheet"
                />
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/responsive.css"
                rel="stylesheet"
                />
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css?v=1.0.10"
                rel="stylesheet"
                />
            <!-- Toast CSS -->
            <link
                href="${pageContext.request.contextPath}/ShopPages/Pages/css/toastr.min.css"
                rel="stylesheet"
                />
            <style>
                .toast {
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    min-width: 200px;
                    padding: 15px;
                    border-radius: 4px;
                    color: white;
                    z-index: 1000;
                }
                .toast-success {
                    background-color: #28a745;
                }
                .toast-error {
                    background-color: #dc3545;
                }
                .form-hint {
                    font-size: 12px;
                    color: #666;
                    margin-top: 4px;
                }
            </style>
        </head>

        <body>
            <jsp:include page="components/header.jsp">
                <jsp:param name="activeHeader" value="no"/>
            </jsp:include>

            <section id="form" class="mt-0">
                <div class="container">
                    <div class="row custom-center">
                        <div class="col-sm-4">
                            <div class="signup-form form-modern">
                                <h2>New User Signup!</h2>
                                <form action="SignUp" method="POST" id="signupForm">
                                    <div class="form-group">
                                        <label class="form" for="email">Email</label>
                                        <input
                                            type="email"
                                            id="email"
                                            placeholder="Enter email address"
                                            class="form-control ${error == 'Email already exists!' || error == 'Email is required!' || error == 'Invalid email format!' ? 'input-modern-invalid' : ''}"
                                            name="email"
                                            required
                                            pattern="[a-zA-Z0-9+_.-]+@(.+)"
                                            value="${not empty error ? email : ''}"
                                            />
                                        <c:if
                                            test="${error == 'Email already exists!' || error == 'Email is required!' || error == 'Invalid email format!'}"
                                            >
                                            <p class="text-danger error-message">${error}</p>
                                        </c:if>
                                    </div>

                                    <div class="form-group">
                                        <label class="form" for="fullName">Full name</label>
                                        <input
                                            type="text"
                                            id="fullName"
                                            placeholder="Enter full name"
                                            class="form-control ${error == 'Full name is required!' ? 'input-modern-invalid' : ''}"
                                            name="fullName"
                                            required
                                            value="${not empty error ? fullName : ''}"
                                            />
                                        <c:if test="${error == 'Full name is required!'}">
                                            <p class="text-danger error-message">${error}</p>
                                        </c:if>
                                    </div>

                                    <div class="form-group">
                                        <label class="form" for="address">Address</label>
                                        <input
                                            type="text"
                                            id="address"
                                            placeholder="Enter address"
                                            class="form-control ${error == 'Address is required!' ? 'input-modern-invalid' : ''}"
                                            name="address"
                                            required
                                            value="${not empty error ? address : ''}"
                                            />
                                        <c:if test="${error == 'Address is required!'}">
                                            <p class="text-danger error-message">${error}</p>
                                        </c:if>
                                    </div>

                                    <div class="form-group">
                                        <label class="form" for="phoneNumber">Phone number</label>
                                        <input
                                            type="tel"
                                            id="phoneNumber"
                                            placeholder="Enter phone number (10 digits, starts with 0)"
                                            class="form-control ${(error == 'Phone number already exists!' || error == 'Phone number is required!' || error == 'Invalid phone number format!') ? 'input-modern-invalid' : ''}"
                                            name="phoneNumber"
                                            required
                                            pattern="0[0-9]{9}"
                                            value="${not empty error ? phoneNumber : ''}"
                                            />
                                        <span class="form-hint">Format: 0XXXXXXXXX (10 digits)</span>
                                        <c:if
                                            test="${error == 'Phone number already exists!' || error == 'Phone number is required!' || error == 'Invalid phone number format!'}"
                                            >
                                            <p class="text-danger error-message">${error}</p>
                                        </c:if>
                                    </div>

                                    <div class="form-group">
                                        <label class="form" for="password">Password</label>
                                        <input
                                            type="password"
                                            id="password"
                                            placeholder="Enter password"
                                            class="form-control ${error == 'Password is required!' ? 'input-modern-invalid' : ''}"
                                            name="password"
                                            required
                                            minlength="6"
                                            />
                                        <span class="form-hint">Minimum 6 characters</span>
                                        <c:if test="${error == 'Password is required!'}">
                                            <p class="text-danger error-message">${error}</p>
                                        </c:if>
                                    </div>

                                    <div class="form-group">
                                        <label class="form" for="confirmPassword">Confirm password</label>
                                        <input
                                            type="password"
                                            id="confirmPassword"
                                            placeholder="Confirm your password"
                                            class="form-control ${(error == 'Passwords do not match!' || error == 'Confirm password is required!') ? 'input-modern-invalid' : ''}"
                                            name="confirmPassword"
                                            required
                                            />
                                        <c:if
                                            test="${error == 'Passwords do not match!' || error == 'Confirm password is required!'}"
                                            >
                                            <p class="text-danger error-message">${error}</p>
                                        </c:if>
                                    </div>

                                    <div class="custom-between mt-5">
                                        <button type="submit" id="btn-signup" class="btn-modern">Sign Up</button>
                                        <a href="Login">Already have an account? Login</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Toast message -->
            <c:if test="${not empty toast}">
                <div class="toast ${toastType == 'success' ? 'toast-success' : 'toast-error'}" id="toast">
                    ${toast}
                </div>
            </c:if>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js"></script>
            <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
            <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
            <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.scrollUp.min.js"></script>
            <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.prettyPhoto.js"></script>
            <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/main.js"></script>

            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    // Handle form validation
                    const form = document.getElementById('signupForm');
                    const password = form.querySelector('input[name="password"]');
                    const confirmPassword = form.querySelector('input[name="confirmPassword"]');

                    form.addEventListener('submit', function (event) {
                        if (password.value !== confirmPassword.value) {
                            event.preventDefault();
                            confirmPassword.classList.add('input-modern-invalid');
                            const errorMessage = document.createElement('p');
                            errorMessage.className = 'text-danger error-message';
                            errorMessage.textContent = 'Passwords do not match!';
                            confirmPassword.parentNode.appendChild(errorMessage);
                        }
                    });

                    // Remove error messages on input
                    const inputs = document.querySelectorAll('input');
                    inputs.forEach((input) => {
                        input.addEventListener('input', function () {
                            this.classList.remove('input-modern-invalid');
                            const errorMessage = this.parentNode.querySelector('.error-message');
                            if (errorMessage) {
                                errorMessage.remove();
                            }
                        });
                    });

                    // Handle toast message
                    const toast = document.getElementById('toast');
                    if (toast) {
                        // Show toast
                        toast.style.display = 'block';

                        // Hide toast after 3 seconds
                        setTimeout(function () {
                            toast.style.opacity = '0';
                            toast.style.transition = 'opacity 0.5s ease-out';

                            // Remove from DOM after fade out
                            setTimeout(function () {
                                toast.remove();
                            }, 500);
                        }, 3000);
                    }
                });
            </script>
            <script>
                document.getElementById("signupForm").addEventListener("submit", function (e) {
                    // Lấy giá trị mật khẩu và xác nhận mật khẩu
                    const passwordInput = document.getElementById("password");
                    const confirmPasswordInput = document.getElementById("confirmPassword");

                    const password = passwordInput.value;
                    const confirmPassword = confirmPasswordInput.value;

                    // Kiểm tra xác nhận khớp
                    if (password !== confirmPassword) {
                        alert("Passwords do not match!");
                        e.preventDefault();
                        return;
                    }

                    // Mã hóa mật khẩu (SHA-256)
                    const hashedPassword = CryptoJS.SHA256(password).toString();

                    // Thay thế password bằng bản đã mã hóa (trước khi gửi đi)
                    passwordInput.value = hashedPassword;
                    confirmPasswordInput.value = hashedPassword;
                });
            </script>
        </body>
    </html>
