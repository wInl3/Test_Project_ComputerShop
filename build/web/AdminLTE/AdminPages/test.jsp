<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Product Test</title>
</head>
<body>
    <h2>Update Product</h2>
    <form action="ProductServlet" method="post">
        <input type="hidden" name="service" value="updateProduct">
        <input type="hidden" name="submit" value="submit">

        <label>Product ID:</label>
        <input type="number" name="product_id" value="${product.productID}" required><br><br>

        <label>Name:</label>
        <input type="text" name="name" value="${product.name}" required><br><br>

        <label>Description:</label>
        <textarea name="description">${product.description}</textarea><br><br>

        <label>Brand:</label>
        <input type="text" name="brand" value="${product.brand}" required><br><br>

        <label>Price:</label>
        <input type="number" step="0.01" name="price" value="${product.price}" required><br><br>

        <label>Quantity:</label>
        <input type="number" name="quantity" value="${product.quantity}" required><br><br>

        <label>Warranty (months):</label>
        <input type="number" name="warranty" value="${product.warrantyPeriod}" required><br><br>

        <label>Created At:</label>
        <input type="date" name="created_at" value="${product.createdAt}"><br><br>

        <label>Category ID:</label>
        <input type="number" name="category_id" value="${product.categoryID}" required><br><br>

        <label>Status:</label>
        <select name="status">
            <option value="1" ${product.status == 1 ? 'selected' : ''}>Active</option>
            <option value="0" ${product.status == 0 ? 'selected' : ''}>Inactive</option>
        </select><br><br>

        <button type="submit">Update Product</button>
    </form>
</body>
</html>
