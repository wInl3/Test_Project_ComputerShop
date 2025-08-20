package models;

public class Shipping {

    private int ShippingID;
    private OrderCate order;
    private User Shipper;
    private String ShippingStatus;
    private String Shiptime;

    public Shipping(int ShippingID, OrderCate order, User Shipper, String ShippingStatus, String Shiptime) {
        this.ShippingID = ShippingID;
        this.order = order;
        this.Shipper = Shipper;
        this.ShippingStatus = ShippingStatus;
        this.Shiptime = Shiptime;
    }

    public int getShippingID() {
        return ShippingID;
    }

    public void setShippingID(int ShippingID) {
        this.ShippingID = ShippingID;
    }

    public OrderCate getOrder() {
        return order;
    }

    public void setOrder(OrderCate order) {
        this.order = order;
    }

    public User getShipper() {
        return Shipper;
    }

    public void setShipper(User Shipper) {
        this.Shipper = Shipper;
    }

    public String getShippingStatus() {
        return ShippingStatus;
    }

    public void setShippingStatus(String ShippingStatus) {
        this.ShippingStatus = ShippingStatus;
    }

    public String getShiptime() {
        return Shiptime;
    }

    public void setShiptime(String Shiptime) {
        this.Shiptime = Shiptime;
    }
}
