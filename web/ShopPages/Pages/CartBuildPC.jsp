<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cart | E-Shopper</title>

        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css?v=1.0.9" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="/ShopPages/Pages/components/header.jsp" />

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">Cart - Build PC</li>
                    </ol>
                </div>

                <h2 class="mb-4 cartTableTitle">Your Build PC Cart</h2>

                <div class="table-responsive cart_info scrollable-table">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td><input type="checkbox" id="checkAll" onchange="toggleAll(this)" class="ml-3 mr-3"/></td>
                                <td style="display: none;">Cart ID</td>
                                <td>MainBoard</td>
                                <td>CPU</td>
                                <td>GPU</td>
                                <td>RAM</td>
                                <td>SSD</td>
                                <td>Case</td>
                                <td>Price</td>
                                <td>Delete</td>
                            </tr>
                        </thead>

                        <c:if test="${not empty cartBuildPC}">
                            <tbody>
                                <c:forEach var="pc" items="${cartBuildPC}">
                                    <tr>
                                        <td><input type="checkbox" class="select-item ml-3 mr-3" value="${pc.cartBuildPCID}"/></td>
                                        <td style="display: none;">${pc.cartBuildPCID}</td>
                                        <td>${pc.mainBoard}</td>
                                        <td>${pc.cpu}</td>
                                        <td>${pc.gpu}</td>
                                        <td>${pc.ram}</td>
                                        <td>${pc.ssd}</td>
                                        <td>${pc.pcCase}</td>
                                        <td><fmt:formatNumber value="${pc.price}" type="number" /> VND</td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/CardBuildPc" onsubmit="return confirm('Bạn có chắc chắn muốn xóa cấu hình PC này không?');">
                                                <input type="hidden" name="service" value="deleteCartBuildPC" />
                                                <input type="hidden" name="id" value="${pc.cartBuildPCID}" />
                                                <button type="submit" class="btn btn-danger btn-sm">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                            </form>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </c:if>
                    </table>

                    <c:if test="${empty cartBuildPC}">
                        <center class="mb-4 fs-3">
                            <p>No PC builds in cart. Please build your PC at:</p>
                            <a href="BuildPCController" class="btn btn-success">Build PC</a>
                        </center>
                    </c:if>
                </div>

                <div class="mt-3 mb-3 text-right fs-4">
                    Total price of selected builds:
                    <span id="selected-total" style="color: orange; font-weight: bold;">0 VND</span>
                </div>

                <div class="mt-3 mb-3 text-right fs-4">
                    Deposit amount (20%):
                    <span id="deposit-amount" style="color: green; font-weight: bold;">0 VND</span>
                </div>

                <div class="mt-3 mb-3 text-right">
                    <button class="btn btn-success" onclick="submitOrder()" style="min-width: 200px;">DEPOSIT</button>
                </div>
            </div>
        </section>

        <script>
            function updateTotal() {
                let total = 0;
                document.querySelectorAll(".select-item:checked").forEach(cb => {
                    const row = cb.closest("tr");
                    const priceText = row?.querySelector("td:nth-child(9)")?.innerText.replace(/[^\d]/g, '');
                    if (priceText)
                        total += parseInt(priceText);
                });
                document.getElementById("selected-total").innerText = total + " VND";
                document.getElementById("deposit-amount").innerText = Math.floor(total * 0.2) + " VND";

            }

            function toggleAll(master) {
                document.querySelectorAll(".select-item").forEach(cb => cb.checked = master.checked);
                updateTotal();
            }

            document.addEventListener("DOMContentLoaded", () => {
                const items = document.querySelectorAll(".select-item");
                const checkAll = document.getElementById("checkAll");

                items.forEach(cb => cb.addEventListener("change", () => {
                        checkAll.checked = [...items].every(c => c.checked);
                        updateTotal();
                    }));
            });

            function submitOrder() {
                const selected = document.querySelectorAll(".select-item:checked");
                if (selected.length === 0) {
                    alert("Please select at least one Build PC to place a deposit.");
                    return;
                }

                let total = 0;
                selected.forEach(cb => {
                    const row = cb.closest("tr");
                    const priceText = row?.querySelector("td:nth-child(9)")?.innerText.replace(/[^\d]/g, '');
                    if (priceText)
                        total += parseInt(priceText);
                });

                const deposit = Math.floor(total * 0.2);
                const ids = Array.from(selected).map(cb => cb.value);

                const form = document.createElement("form");
                form.method = "POST";
                form.action = "ShopPages/Pages/confirmVnPayOrder.jsp";

                const idsInput = document.createElement("input");
                idsInput.name = "ids";
                idsInput.value = ids.join(","); // CHÍNH XÁC: biến thành chuỗi "1,2,3"
                form.appendChild(idsInput);

                const amountInput = document.createElement("input");
                amountInput.name = "amount";
                amountInput.value = deposit.toString().replace(/[^\d]/g, "");
                form.appendChild(amountInput);

                document.body.appendChild(form);
                form.submit();
                document.body.removeChild(form);
            }

        </script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/main.js"></script>

    </body>
</html>
