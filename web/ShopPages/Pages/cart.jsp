<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Cart | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/responsive.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/ShopPages/Pages/css/custom.css?v=1.0.20" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">

    </head>
    <body>
        <jsp:include page="/ShopPages/Pages/components/header.jsp" />

        <section class="container my-5">
            <h2 class="text-center mb-4">Your Shopping Cart</h2>
            <div class="table-responsive cart-table d-none d-md-block" style="max-height: 480px; overflow-y: auto;">
                <table class="table table-bordered">
                    <thead class="thead-light">
                        <tr>
                            <th><input type="checkbox" id="checkAll" onchange="toggleAll(this)"></th>
                            <th>Product</th>
                            <th>Details</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <c:choose>
                        <c:when test="${cart.size() > 0}">
                            <tbody id="cart-items-body" >
                                <!-- Các <tr> sẽ được load động -->
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        </table>
                        <center class="mb-4 fs-3">
                            <p>No items. Please add new products at </p>
                            <a href="CategoriesController?service=list" class="btn btn-success">Products</a>
                        </center>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="cart-actions mb-5">
                <div class="cart-total">
                    Selected Total: <span id="selected-total">0 VND</span>
                </div>
                <div>
                    <button class="btn btn-danger mr-2" onclick="deleteSelected()">Delete Selected</button>
                    <button class="btn btn-success btn-checkout" onclick="submitOrder()">Proceed to Checkout</button>
                </div>
            </div>
        </section>
        
        <!-- TOAST NOTIFICATION -->
        <c:if test="${not empty sessionScope.toast}">
            <div class="toast-container" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">
                <div class="alert alert-${sessionScope.toastType == 'error' ? 'danger' : 'success'} alert-dismissible" role="alert" id="toastMessage">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    ${sessionScope.toast}
                </div>
            </div>
            <c:remove var="toast" scope="session" />
            <c:remove var="toastType" scope="session" />
        </c:if>

        <jsp:include page="/ShopPages/Pages/components/footer.jsp" />

        <script>
            // Lấy giá trị từ server-side
            let offset = ${initialOffset};   // sẽ là 10 nếu đã load 10 item
            const limit = ${pageLimit};
            let loading = false;
            let endOfList = false;

            console.log(${cartItems});

            console.log("📌 Offset init:", offset, "Limit:", limit);

            document.addEventListener('DOMContentLoaded', function () {
                loadMoreCartItems(); // Gọi lần đầu

                const tableWrapper = document.querySelector('.cart-table');

                if (!tableWrapper) {
                    console.error("❌ Không tìm thấy .cart-table — kiểm tra selector hoặc class HTML.");
                    return;
                }

                console.log("✅ Đã gắn sự kiện scroll cho .cart-table");

                tableWrapper.addEventListener('scroll', function () {
                    const scrollTop = tableWrapper.scrollTop;
                    const clientHeight = tableWrapper.clientHeight;
                    const scrollHeight = tableWrapper.scrollHeight;

                    console.log({
                        scrollTop,
                        clientHeight,
                        scrollHeight
                    });

                    // Nếu kéo xuống gần cuối => load tiếp
                    if (scrollTop + clientHeight >= scrollHeight - 20) {
                        if (!loading && !endOfList) {
                            console.log("🔄 Đang kéo tới cuối => loadMoreCartItems()");
                            loadMoreCartItems();
                        }
                    }
                });
            });

            function loadMoreCartItems() {
                console.log(` Gọi API offset=` + offset + `&limit=` + limit);
                loading = true;

                fetch(`CartItemsLoader?offset=` + offset + `&limit=` + limit)
                        .then(res => res.text())
                        .then(html => {
                            const clean = html.trim();

                            if (clean.length === 0) {
                                endOfList = true;
                            } else {
                                // Đếm số <tr>
                                const temp = document.createElement('tbody');
                                temp.innerHTML = clean;
                                const rows = temp.querySelectorAll('tr').length;

                                if (rows < limit) {
                                    endOfList = true;
                                    console.log('✅ Hết dữ liệu — rows:', rows);
                                }

                                document.getElementById('cart-items-body').insertAdjacentHTML('beforeend', clean);
                                offset += limit;
                            }

                            loading = false;
                        })
                        .catch(err => {
                            console.error(err);
                            loading = false;
                        });
            }

        </script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/ShopPages/Pages/js/bootstrap.min.js"></script>

        <script>
            if (performance.getEntriesByType("navigation")[0].type === "back_forward") {
                location.href = "Cart"; // hoặc bất kỳ route nào gọi lại servlet giỏ hàng
            }

            let debounceTimer = null;

            function changeQty(button, delta) {
                const input = button.parentElement.querySelector('input');
                const oldValue = parseInt(input.value) || 1;
                let newValue = Math.max(0, oldValue + delta);
                input.dataset.oldValue = newValue;
                input.value = newValue;
                debounceTrigger(input);

                // ✅ Cập nhật lại tổng giá đã chọn
                updateSelectedTotal();
            }

            function debounceTrigger(input) {
                clearTimeout(debounceTimer);
                debounceTimer = setTimeout(() => {
                    validateAndUpdate(input);
                }, 50);
            }

            function validateAndUpdate(input) {
                const cartItemID = input.dataset.itemid;
                const price = parseFloat(input.dataset.price);
                const value = input.value.trim();
                const oldValue = parseInt(input.dataset.oldValue || "1");

                // ❌ Không hợp lệ: không phải số nguyên dương
                if (!/^\d+$/.test(value)) {
                    Swal.fire({
                        title: "Số lượng không hợp lệ!",
                        text: "Vui lòng nhập một số nguyên dương.",
                        icon: "error"
                    });
                    input.value = oldValue;
                    return;
                }

                const quantity = parseInt(value);

                // ❗ Nếu <= 0 → hỏi xoá
                if (quantity <= 0) {
                    comfirmDeletePopUp().then((confirmDelete) => {
                        if (confirmDelete) {
                            deleteCartItem(cartItemID, input);
                        } else {
                            // Huỷ xoá → phục hồi số lượng cũ
                            input.value = 1;
                            const lineTotalElem = input.closest('tr').querySelector('.cart_total');
                            const total = Number(price) * 1;
                            lineTotalElem.innerHTML = `<strong>` + total.toLocaleString('vi-VN', {maximumFractionDigits: 0}).replace(/\./g, ',') + ` VND </strong>`;
                            updateSelectedTotal();

                            // Cập nhật lại DB với oldValue
                            const params = new URLSearchParams();
                            params.append("cartItemID", cartItemID);
                            params.append("quantity", 1);

                            fetch('UpdateCartItem', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: params.toString()
                            }).then(res => res.text())
                                    .then(data => {
                                        if (data.trim() !== 'success') {
                                            Swal.fire("Lỗi", "Cập nhật thất bại", "error");
                                        }
                                    });
                        }
                    });
                } else {
                    const lineTotalElem = input.closest('tr').querySelector('.cart_total');
                    const total = Number(price) * quantity;
                    lineTotalElem.innerHTML = `<strong>` + total.toLocaleString('vi-VN', {maximumFractionDigits: 0}).replace(/\./g, ',') + ` VND </strong>`;


                    // Cập nhật lại DB với oldValue
                    const params = new URLSearchParams();
                    params.append("cartItemID", cartItemID);
                    params.append("quantity", input.value);

                    fetch('UpdateCartItem', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: params.toString()
                    }).then(res => res.text())
                            .then(data => {
                                if (data.trim() !== 'success') {
                                    Swal.fire("Lỗi", "Cập nhật thất bại", "error");
                                }
                            });
                }
            }

            function confirmDelete(button, cartItemID) {
                comfirmDeletePopUp().then((confirmDelete) => {
                    if (confirmDelete) {
                        deleteCartItem(cartItemID, button);
                    }
                });
            }

            function comfirmDeletePopUp() {
                return Swal.fire({
                    title: "Xoá sản phẩm?",
                    text: "Bạn có muốn bỏ sản phẩm này khỏi giỏ hàng không?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "Có, xoá!",
                    cancelButtonText: "Không"
                }).then((result) => result.isConfirmed);
            }

            function deleteCartItem(cartItemID, element) {
                const params = new URLSearchParams();
                params.append("cartItemID", cartItemID);

                fetch('DeleteCartItem', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: params.toString()
                })
                        .then(response => response.text())
                        .then(data => {
                            if (data.trim() === 'success') {
                                const row = element.closest('tr');
                                row.parentNode.removeChild(row);

// Sau khi xóa, kiểm tra còn sản phẩm nào không
                                const remainingItems = document.querySelectorAll('.cart-table tbody tr').length;
                                if (remainingItems === 0) {
                                    const cartInfoDiv = document.querySelector('.cart-table');
                                    cartInfoDiv.innerHTML = cartInfoDiv.innerHTML + `
        <center class="mb-4 fs-3">
            <p>No items. Please add new products at </p>
            <a href="CategoriesController?service=list" class="btn btn-success">Products</a>
        </center>
    `;

                                    // Đặt tổng giá về 0
                                    document.getElementById("selected-total").textContent = "0 VND";
                                }


                                // ✅ GỌI LẠI CẬP NHẬT TỔNG TIỀN SAU KHI XOÁ
                                updateSelectedTotal();

                            } else {
                                Swal.fire("Lỗi", "Xoá thất bại", "error");
                            }
                        });
            }
        </script>

        <script>
            function formatCurrency(number) {
                return Number(number).toLocaleString('vi-VN', {maximumFractionDigits: 0}).replace(/\./g, ',') + ' VND';
            }

            function updateSelectedTotal() {
                let total = 0;

                document.querySelectorAll('.select-item:checked').forEach(cb => {
                    const row = cb.closest('tr');
                    const price = parseFloat(row.querySelector('input[type="number"]').dataset.price);
                    const quantity = parseInt(row.querySelector('input[type="number"]').value);
                    total += price * quantity;
                });

                document.getElementById("selected-total").textContent = formatCurrency(total);
            }

// Gọi khi checkbox hoặc quantity thay đổi
            document.addEventListener('DOMContentLoaded', function () {
                document.getElementById('cart-items-body').addEventListener('change', function (e) {
                    if (e.target.classList.contains('select-item')) {
                        updateSelectedTotal();
                    }
                });

                document.querySelectorAll('.cart_quantity_input').forEach(input => {
                    input.addEventListener('change', updateSelectedTotal);
                    input.addEventListener('input', updateSelectedTotal);
                });

                document.getElementById('checkAll')?.addEventListener('change', updateSelectedTotal);
            });

            function toggleAll(checkbox) {
                const checkboxes = document.querySelectorAll('.select-item');
                checkboxes.forEach(cb => cb.checked = checkbox.checked);
            }

            function submitOrder() {
                const selected = [...document.querySelectorAll('.select-item:checked')]
                        .map(cb => cb.value);

                if (selected.length === 0) {
                    Swal.fire("Thông báo", "Vui lòng chọn ít nhất một sản phẩm để đặt hàng!", "info");
                    return;
                }

                // Gửi danh sách ID qua POST (hoặc Ajax)
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = 'Checkout';

                selected.forEach(id => {
                    const input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'selectedItems';
                    input.value = id;
                    form.appendChild(input);
                });

                document.body.appendChild(form);
                form.submit();
            }

            function deleteSelected() {
                const selectedCbs = [...document.querySelectorAll('.select-item:checked')];
                const ids = selectedCbs.map(cb => cb.value);

                if (ids.length === 0) {
                    Swal.fire("Thông báo", "Vui lòng chọn ít nhất một sản phẩm để xoá!", "info");
                    return;
                }

                Swal.fire({
                    title: "Xoá các sản phẩm đã chọn?",
                    text: `Bạn chắc chắn muốn xoá ${ids.length} sản phẩm?`,
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "Có, xoá!",
                    cancelButtonText: "Không"
                }).then(result => {
                    if (result.isConfirmed) {
                        const params = new URLSearchParams();
                        params.append("selectedItems", ids.join(","));

                        fetch('DeleteSelectedCartItems', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            body: params.toString()
                        }).then(res => res.text())
                                .then(data => {
                                    if (data.trim() === 'success') {
                                        // 🔥 Không querySelector lại! Xoá thẳng từ cb đã có
                                        selectedCbs.forEach(cb => {
                                            const row = cb.closest('tr');
                                            if (row)
                                                row.remove();
                                        });

                                        updateSelectedTotal();

                                        const remainingItems = document.querySelectorAll('.select-item').length;
                                        if (remainingItems === 0) {
                                            const cartTableDiv = document.querySelector('.cart-table');
                                            cartTableDiv.innerHTML += `
                            <center class="mb-4 fs-3">
                                <p>No items. Please add new products at </p>
                                <a href="CategoriesController?service=list" class="btn btn-success">Products</a>
                            </center>
                        `;
                                            document.getElementById("selected-total").textContent = "0 VND";
                                        }

                                    } else {
                                        Swal.fire("Lỗi", "Xoá thất bại", "error");
                                    }
                                });
                    }
                });
            }

        </script>
    </body>
</html>
