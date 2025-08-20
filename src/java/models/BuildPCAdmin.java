package models;

import java.util.Date;

public class BuildPCAdmin {

    // ===== Thông tin cơ bản =====
    private int buildPcId;
    private int buildPcItem;
    private int cateId;
    private String cateName;
    private int componentId;
    private Integer warrantyPrice;
    private String warrantyDesc;
    private Integer warrantyDetailId;
    private int price;
    private int status;
    private String brandName;

    private String imgUrl;
    private int queue;
    private int orderBuildPcItemId;
    private int inventory;
    private int quantity;

    // ===== Thông tin đơn hàng từ Orders =====
    private int orderId;
    private String orderCode;
    private int orderStatus;
    private Date orderDate;
    private int paymentStatusId;
    private int totalAmount;
    private String note;
    private String address;
    private String phoneNumber;
    private String customerName;
    

    // ===== Thông tin sản phẩm thực tế trong đơn =====
    private int orderBuildPcDetailId;
    private int orderBuildPcProductId;
    private int productId;
    private String productCode;
    private String productNote;
    private int productStatus;
    private Date warrantyStartDate;
    private Date warrantyEndDate;

    // ===== Thông tin Build_PC =====
    private int buildPcUserId;
    private int buildPcStatus;
    private int buildPcPrice;

    // ===== Brand - Component - BrandCom =====
    private int brandId;
    private int brandComId;
    private String componentName;

    // ===== Warranty gốc =====
    private int warrantyId;
    private int warrantyPeriod;

    // ===== Thông tin người dùng từ Users =====
    private int userId;
    private String fullName;
    private String email;
    private String userPhone;
    private int roleId;
    private int userStatus;
    private Date createdAt;
    private boolean completed;

    public BuildPCAdmin(int orderBuildPcItemId, int orderBuildPcDetailId, int quantity, int queue, int buildPcItem,
            int buildPcId, String orderCode, String fullName, Integer productId, String productCode,
            int cateId, String cateName, String imgUrl, int price, int status, int componentId,
            String brandName, int inventory, Integer warrantyDetailId, Integer warrantyPrice, String warrantyDesc) {
        this.orderBuildPcItemId = orderBuildPcItemId;
        this.orderBuildPcDetailId = orderBuildPcDetailId;
        this.quantity = quantity;
        this.queue = queue;
        this.buildPcItem = buildPcItem;
        this.buildPcId = buildPcId;
        this.orderCode = orderCode;
        this.fullName = fullName;
        this.productId = productId;
        this.productCode = productCode;
        this.cateId = cateId;
        this.cateName = cateName;
        this.imgUrl = imgUrl;
        this.price = price;
        this.status = status;
        this.componentId = componentId;
        this.brandName = brandName;
        this.inventory = inventory;
        this.warrantyDetailId = warrantyDetailId;
        this.warrantyPrice = warrantyPrice;
        this.warrantyDesc = warrantyDesc;
    }

    public BuildPCAdmin() {
    }

    public BuildPCAdmin(int cateId, String cateName, int componentId, Integer warrantyPrice, String warrantyDesc, Integer warrantyDetailId, int price, String brandName, String imgUrl) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.componentId = componentId;
        this.warrantyPrice = warrantyPrice;
        this.warrantyDesc = warrantyDesc;
        this.warrantyDetailId = warrantyDetailId;
        this.price = price;
        this.brandName = brandName;
        this.imgUrl = imgUrl;
    }

    public BuildPCAdmin(int aInt, int aInt0, String string, String string0, int aInt1, String string1, int warrantyDetailID, int warrantyPrice, String warrantyDesc) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getBuildPcId() {
        return buildPcId;
    }

    public void setBuildPcId(int buildPcId) {
        this.buildPcId = buildPcId;
    }

    public int getBuildPcItem() {
        return buildPcItem;
    }

    public void setBuildPcItem(int buildPcItem) {
        this.buildPcItem = buildPcItem;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public Integer getWarrantyPrice() {
        return warrantyPrice;
    }

    public void setWarrantyPrice(Integer warrantyPrice) {
        this.warrantyPrice = warrantyPrice;
    }

    public String getWarrantyDesc() {
        return warrantyDesc;
    }

    public void setWarrantyDesc(String warrantyDesc) {
        this.warrantyDesc = warrantyDesc;
    }

    public Integer getWarrantyDetailId() {
        return warrantyDetailId;
    }

    public void setWarrantyDetailId(Integer warrantyDetailId) {
        this.warrantyDetailId = warrantyDetailId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getOrderBuildPcItemId() {
        return orderBuildPcItemId;
    }

    public void setOrderBuildPcItemId(int orderBuildPcItemId) {
        this.orderBuildPcItemId = orderBuildPcItemId;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getOrderBuildPcDetailId() {
        return orderBuildPcDetailId;
    }

    public void setOrderBuildPcDetailId(int orderBuildPcDetailId) {
        this.orderBuildPcDetailId = orderBuildPcDetailId;
    }

    public int getOrderBuildPcProductId() {
        return orderBuildPcProductId;
    }

    public void setOrderBuildPcProductId(int orderBuildPcProductId) {
        this.orderBuildPcProductId = orderBuildPcProductId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductNote() {
        return productNote;
    }

    public void setProductNote(String productNote) {
        this.productNote = productNote;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public Date getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(Date warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public Date getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(Date warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public int getBuildPcUserId() {
        return buildPcUserId;
    }

    public void setBuildPcUserId(int buildPcUserId) {
        this.buildPcUserId = buildPcUserId;
    }

    public int getBuildPcStatus() {
        return buildPcStatus;
    }

    public void setBuildPcStatus(int buildPcStatus) {
        this.buildPcStatus = buildPcStatus;
    }

    public int getBuildPcPrice() {
        return buildPcPrice;
    }

    public void setBuildPcPrice(int buildPcPrice) {
        this.buildPcPrice = buildPcPrice;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getBrandComId() {
        return brandComId;
    }

    public void setBrandComId(int brandComId) {
        this.brandComId = brandComId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(int warrantyId) {
        this.warrantyId = warrantyId;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
