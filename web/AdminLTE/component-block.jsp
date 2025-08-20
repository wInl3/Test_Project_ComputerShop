<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    models.Categories sel = (models.Categories) request.getAttribute("selectedComponent");
%>
<div class="list-group-item" id="component-wrapper-<%= sel.getComponentID() %>">
    <div class="d-flex align-items-center">
        <img src="${ctx}/ShopPages/Pages/images/anhproduct/${sel.componentID}.jpg" class="component-image" />
        <div>
            <div class="component-title">${sel.componentName}
                <div class="selected-item">
                    <div class="component-meta">${sel.categoryName} - ${sel.brandName}</div>
                    <a href="#" class="btn btn-sm btn-outline-danger btn-remove mt-2"
                       data-delete-url="${ctx}/BuildPC?service=remove&componentID=${sel.componentID}">Delete</a>
                </div>
            </div>
        </div>
    </div>
    <div class="text-end">
        <div class="component-price">
            <fmt:formatNumber value="${sel.price}" type="number" groupingUsed="true"/>₫
        </div>
        <button type="button" class="btn btn-sm btn-primary mt-2 open-component-modal"
                data-component-id="${sel.componentID}" data-component-name="${sel.componentName}">
            Changes
        </button>
    </div>
</div>
