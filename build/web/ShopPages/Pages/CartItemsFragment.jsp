<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="cartItem" items="${cartItems}">
    <tr>
        <td><input type="checkbox" class="select-item" value="${cartItem.cartItemID}"></td>
        <td>
            <img src="${pageContext.request.contextPath}/ShopPages/Pages/images/CatePicture/${cartItem.category.imgURL}" alt="Product">
        </td>
        <td class="text-left">
            <strong>${cartItem.category.categoryName} ${cartItem.category.categoryID}</strong><br>
            Warranty: ${cartItem.warranty.warranty.warrantyPeriod} months<br>
            <small class="text-muted">(${cartItem.warranty.warranty.description})</small>
        </td>
        <td class="cart-price">
            <div>Product: <fmt:formatNumber value="${cartItem.category.price}" type="number" groupingUsed="true"/> VND</div>
            <div>Warranty: <fmt:formatNumber value="${cartItem.warranty.price}" type="number" groupingUsed="true"/> VND</div>
        </td>
        <td>
            <div class="cart-quantity-group">
                <button onclick="changeQty(this, -1)">-</button>
                <input type="number"
                       value="${cartItem.quantity}"
                       data-itemid="${cartItem.cartItemID}"
                       data-price="${cartItem.category.price + cartItem.warranty.price}"
                       oninput="changeQty(this, 0)"
                       onchange="debounceTrigger(this)">
                <button onclick="changeQty(this, 1)">+</button>
            </div>
        </td>
        <td class="cart_total">
            <strong><fmt:formatNumber value="${(cartItem.category.price + cartItem.warranty.price) * cartItem.quantity}" type="number" groupingUsed="true"/> VND</strong>
        </td>
        <td>
            <button class="cart-delete-btn" onclick="confirmDelete(this, ${cartItem.cartItemID})">
                X
            </button>
        </td>
    </tr>
</c:forEach>
