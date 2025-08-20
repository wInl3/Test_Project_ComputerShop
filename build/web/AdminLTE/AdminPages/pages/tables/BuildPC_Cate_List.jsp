<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
    .product-card {
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        border: 1px solid #ddd;
        padding: 12px;
        border-radius: 8px;
        background-color: #fff;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }

    .product-card img {
        width: 100%;
        height: 160px;
        object-fit: cover;
        border: 1px solid #ccc;
        margin-bottom: 10px;
    }

    .product-info {
        font-size: 14px;
    }

    .product-warranty {
        font-size: 13px;
        margin-top: 8px;
    }

    .product-actions {
        margin-top: auto;
    }
    .product-card {
        min-height: 520px; /* chỉnh tùy ý */
    }
</style>

<form id="filterForm"
      action="${ctx}/BuildPC_ListCate"
      method="get"
      style="display: flex; flex-direction: column; gap: 10px; margin-bottom: 20px;">
    <input type="hidden" name="service" value="filter" />
    <input type="hidden" name="ajax" value="true" />
    <input type="hidden" name="componentID" value="${param.componentID}" />

    <input type="text" name="keyword" value="${param.keyword}" placeholder="Tìm theo tên..." class="form-control" />

    <label for="brandSelect"><strong>Hãng:</strong></label>
    <select id="brandSelect" name="brand" class="form-control">
        <option value="">-- Tất cả hãng --</option>
        <c:forEach var="b" items="${brands}">
            <option value="${b.brandName}" <c:if test="${param.brand == b.brandName}">selected</c:if>>${b.brandName}</option>
        </c:forEach>
    </select>

    <div style="display: flex; gap: 10px;">
        <input type="number" name="minPrice" value="${param.minPrice}" placeholder="Min price" class="form-control" style="width: 120px;" />
        <input type="number" name="maxPrice" value="${param.maxPrice}" placeholder="Max price" class="form-control" style="width: 120px;" />
    </div>

    <button type="submit" class="btn btn-sm btn-primary">Lọc</button>
</form>

<!-- DANH SÁCH SẢN PHẨM -->
<div class="container-fluid">
    <div class="row">
        <c:forEach var="p" items="${products}" varStatus="loop">
            <div class="col-md-6 col-lg-4 mb-4 d-flex">
                <div class="product-card w-100">
                    <img src="${pageContext.request.contextPath}/ShopPages/Pages/images/CatePicture/${p.imgURL}" alt="${p.categoryName}" />

                    <div class="product-info">
                        <div><strong>${p.categoryName}</strong> - ${p.brandName}</div>
                        <div>Giá: <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>₫</div>

                        <c:if test="${not empty warrantyMap[p.categoryID]}">
                            <div class="product-warranty">
                                <strong>Chọn bảo hành:</strong>
                                <c:forEach var="w" items="${warrantyMap[p.categoryID]}" varStatus="status">
                                    <div style="margin-left: 10px;">
                                        <input type="radio"
                                               name="warranty-${p.categoryID}"
                                               id="w-${p.categoryID}-${status.index}"
                                               value="${w.warrantyDetailID}"
                                               <c:if test="${w.status != 1}">disabled</c:if> />
                                        <label for="w-${p.categoryID}-${status.index}">
                                            <span class="warranty-desc">${w.description}</span> -
                                            ${w.warrantyPeriod} tháng -
                                            <span class="warranty-price"><fmt:formatNumber value="${w.price}" type="number" groupingUsed="true"/></span>₫
                                            <c:if test="${w.status != 1}">
                                                <span style="color:red;">(Ngừng áp dụng)</span>
                                            </c:if>
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>

                    <div class="product-actions text-center mt-3">
                        <button class="btn btn-sm btn-success"
                                onclick="chooseProductWithWarranty(
                                ${componentID},
                                ${p.categoryID},
                        '${fn:escapeXml(p.categoryName)}',
                        '${fn:escapeXml(p.brandName)}',
                                ${p.price},
                        '${fn:escapeXml(p.imgURL)}',
                                ${p.categoryID})">
                            Chọn
                        </button>


                    </div>
                </div>
            </div>

        </c:forEach>
    </div>
</div>

<!-- PHÂN TRANG -->
<c:if test="${totalPages > 1}">
    <nav>
        <ul class="pagination d-flex justify-content-center flex-wrap" style="gap: 5px;">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="javascript:void(0);" data-page="${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>