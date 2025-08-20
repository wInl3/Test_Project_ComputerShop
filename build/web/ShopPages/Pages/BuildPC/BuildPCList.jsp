<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
    .card-wrapper {
        display: flex;
        flex-direction: column;
        height: 100%;
        border: 1px solid #ccc;
        border-radius: 6px;
        padding: 10px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        background-color: #fff;
        min-height:700px;
    }

    .card-wrapper img {
        width: 100%;
        height: 380px;
        object-fit: cover;
        border: 1px solid #ccc;
        margin-bottom: 10px;
    }

    .product-description {
        font-size: 90%;
        max-height: 54px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        margin-bottom: 10px;
    }

    .card-footer {
        margin-top: auto;
    }
</style>

<form id="filterForm" method="get" action="${ctx}/BuildPC" onsubmit="return false;"
      style="display: flex; flex-direction: column; gap: 10px; margin-bottom: 20px;">
    <input type="hidden" name="service" value="filter" />
    <input type="hidden" name="ajax" value="true" />
    <input type="hidden" name="componentID" value="${componentID}" />

    <input type="text" name="keyword" value="${param.keyword}" placeholder="Search by name..." class="form-control" />

    <label for="brandSelect"><strong>Brand:</strong></label>
    <select id="brandSelect" name="brand" class="form-control">
        <option value="">-- All brands --</option>
        <c:forEach var="b" items="${brands}">
            <option value="${b.brandName}" <c:if test="${paDram.brand == b.brandName}">selected</c:if>>
                ${b.brandName}
            </option>
        </c:forEach>
    </select>

    <div style="display: flex; gap: 10px;">
        <input type="text" name="minPrice" value="${param.minPrice}" placeholder="Min price" class="form-control" style="width: 120px;" />
        <input type="text" name="maxPrice" value="${param.maxPrice}" placeholder="Max price" class="form-control" style="width: 120px;" />
    </div>

    <button type="submit" class="btn btn-sm btn-primary">Lọc</button>
</form>

<!-- DANH SÁCH SẢN PHẨM -->
<div class="container-fluid">
    <div class="row">
        <c:forEach var="p" items="${products}" varStatus="loop">
            <div class="col-md-6 mb-3 d-flex">
                <div class="card-wrapper w-100">
                    <img src="${ctx}/ShopPages/Pages/images/CatePicture/${p.imgURL}" alt="${p.categoryName}"  "/>

                    <div>
                        <strong>${p.categoryName}</strong> - ${p.brandName}
                        <div><strong>Description:</strong></div>
                        <div class="product-description" title="${p.description}">${p.description}</div>

                        <div>Price:
                            <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>₫
                        </div>

                        <c:if test="${not empty warrantyMap[p.categoryID]}">
                            <div style="margin-top: 5px;">
                                <strong>Choose Warranty:</strong>
                                <c:set var="minPeriod" value="999" />
                                <c:forEach var="w" items="${warrantyMap[p.categoryID]}">
                                    <c:if test="${w.status == 1 && w.warrantyPeriod lt minPeriod}">
                                        <c:set var="minPeriod" value="${w.warrantyPeriod}" />
                                    </c:if>
                                </c:forEach>
                                <c:forEach var="w" items="${warrantyMap[p.categoryID]}" varStatus="status">
                                    <div style="margin-left: 10px;">
                                        <input type="radio"
                                               name="warranty-${p.categoryID}"
                                               id="w-${p.categoryID}-${status.index}"
                                               value="${w.warrantyDetailID}"
                                               <c:if test="${w.status == 1 && w.warrantyPeriod == minPeriod}">checked="checked"</c:if>
                                               <c:if test="${w.status != 1}">disabled</c:if> />
                                        <label for="w-${p.categoryID}-${status.index}">
                                            ${w.warrantyPeriod} month -
                                            <fmt:formatNumber value="${w.price}" type="number" groupingUsed="true"/>₫
                                            <c:if test="${w.status != 1}">
                                                <span style="color:red;">(Inactive)</span>
                                            </c:if>
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                    </div>

                    <div class="card-footer d-flex justify-content-between mt-3">
                        <a href="${ctx}/CategoriesController?service=detail&categoryID=${p.categoryID}"
                           class="btn btn-info btn-sm" target="_blank">View Detail</a>
                        <button class="btn btn-success btn-sm"
                                onclick="chooseProductWithWarranty(${componentID}, ${p.categoryID}, '${fn:escapeXml(p.categoryName)}', '${fn:escapeXml(p.brandName)}', ${p.price}, '${fn:escapeXml(p.imgURL)}', ${p.categoryID})">
                            Chọn
                        </button>
                    </div>
                </div>
            </div>


        </c:forEach>
    </div>
</div>


<c:if test="${totalPages > 1}">
    <nav>
        <ul class="pagination">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="${i == currentPage ? 'active' : ''}">
                    <a href="javascript:void(0);" class="page-link" data-page="${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>
