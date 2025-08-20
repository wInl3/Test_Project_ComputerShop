<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Test Category</title>
</head>
<body>

<ul>
    <c:forEach var="cat" items="${categoryList}">
        <li>${cat.categoryName}</li>
    </c:forEach>
</ul>

</body>
</html>
