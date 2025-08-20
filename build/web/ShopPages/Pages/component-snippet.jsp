<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:set var="sel" value="${selected}" />
<c:set var="compId" value="${sel.componentID}" />
<div class="list-group-item" id="component-wrapper-${compId}">
    <div class="d-flex align-items-center">
        <img src="${ctx}/ShopPages/Pages/images/anhproduct/${compId}.jpg" alt="${sel.categoryName}" class="component-image" />
        <div>
            <div class="component-title">${sel.componentName}
                <div class="selected-item">
                    <div class="component-meta">${sel.categoryName} - ${sel.brandName}</div>
                    <a href="#" class="btn btn-sm btn-outline-danger btn-remove mt-2" 
                       data-delete-url="${ctx}/BuildPC?service=remove&componentID=${compId}">Delete</a>
                </div>
            </div>
        </div>
    </div>
    <div class="text-end">
        <div class="component-price">
            <fmt:formatNumber value="${sel.price}" type="number" groupingUsed="true"/>₫
        </div>
        <button type="button" class="btn btn-sm btn-primary mt-2 open-component-modal" 
                data-component-id="${compId}" data-component-name="${sel.componentName}">
            Change
        </button>
    </div>
</div>
