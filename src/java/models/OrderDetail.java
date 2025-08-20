
package models;

public class OrderDetail {
    private int OrderDetailID;
    private int OrderItemID;
    private int ProductID;
    private Products product;
    private WarrantyDetails warranty;
    private int WarrantyDetailID; 
    private int UnitPrice;
    private int WarrantyPrice;
    private int Status;

    public OrderDetail() {
    }

    public OrderDetail(int OrderDetailID, int OrderItemID, int ProductID, int WarrantyDetailID, int UnitPrice, int WarrantyPrice, int Status) {
        this.OrderDetailID = OrderDetailID;
        this.OrderItemID = OrderItemID;
        this.ProductID = ProductID;
        this.WarrantyDetailID = WarrantyDetailID;
        this.UnitPrice = UnitPrice;
        this.WarrantyPrice = WarrantyPrice;
        this.Status = Status;
    }

    public OrderDetail(int OrderItemID, int ProductID, int WarrantyDetailID, int UnitPrice, int WarrantyPrice, int Status) {
        this.OrderItemID = OrderItemID;
        this.ProductID = ProductID;
        this.WarrantyDetailID = WarrantyDetailID;
        this.UnitPrice = UnitPrice;
        this.WarrantyPrice = WarrantyPrice;
        this.Status = Status;
    }

    public int getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(int OrderDetailID) {
        this.OrderDetailID = OrderDetailID;
    }

    public int getOrderItemID() {
        return OrderItemID;
    }

    public void setOrderItemID(int OrderItemID) {
        this.OrderItemID = OrderItemID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public int getWarrantyDetailID() {
        return WarrantyDetailID;
    }

    public void setWarrantyDetailID(int WarrantyDetailID) {
        this.WarrantyDetailID = WarrantyDetailID;
    }

    public int getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(int UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public int getWarrantyPrice() {
        return WarrantyPrice;
    }

    public void setWarrantyPrice(int WarrantyPrice) {
        this.WarrantyPrice = WarrantyPrice;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public WarrantyDetails getWarranty() {
        return warranty;
    }

    public void setWarranty(WarrantyDetails warranty) {
        this.warranty = warranty;
    }
}
