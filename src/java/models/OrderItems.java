package models;

import java.util.ArrayList;

public class OrderItems {

    private Categories category;
    private int OrderItemID;
    private int OrderID;
    private int CategoryID;
    private int Quantity;
    private int Price;
    private String orderCode;
    private String categoryName;
    private int inventory;
    private int queue;
    private ArrayList<OrderDetail> orderDetailList;
    private boolean completed = false; // Mặc định là false để tránh null

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public OrderItems() {
    }

    public OrderItems(Categories category, int OrderItemID, int OrderID, int CategoryID, int Quantity, int Price, String orderCode, String categoryName, int inventory, int queue, ArrayList<OrderDetail> orderDetailList) {
        this.category = category;
        this.OrderItemID = OrderItemID;
        this.OrderID = OrderID;
        this.CategoryID = CategoryID;
        this.Quantity = Quantity;
        this.Price = Price;
        this.orderCode = orderCode;
        this.categoryName = categoryName;
        this.inventory = inventory;
        this.queue = queue;
        this.orderDetailList = orderDetailList;
    }

    public OrderItems(int OrderItemID, int OrderID, int CategoryID, int Quantity, int Price, String orderCode, String categoryName, int inventory, int queue, ArrayList<OrderDetail> orderDetailList) {
        this.OrderItemID = OrderItemID;
        this.OrderID = OrderID;
        this.CategoryID = CategoryID;
        this.Quantity = Quantity;
        this.Price = Price;
        this.orderCode = orderCode;
        this.categoryName = categoryName;
        this.inventory = inventory;
        this.queue = queue;
        this.orderDetailList = orderDetailList;
    }

    public OrderItems(Categories category, int OrderItemID, int OrderID, int CategoryID, int Quantity, int Price, ArrayList<OrderDetail> orderDetailList) {
        this.category = category;
        this.OrderItemID = OrderItemID;
        this.OrderID = OrderID;
        this.CategoryID = CategoryID;
        this.Quantity = Quantity;
        this.Price = Price;
        this.orderDetailList = orderDetailList;
    }

    public OrderItems(Categories category, int OrderItemID, int OrderID, int CategoryID, int Quantity, int Price) {
        this.category = category;
        this.OrderItemID = OrderItemID;
        this.OrderID = OrderID;
        this.CategoryID = CategoryID;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public OrderItems(int OrderItemID, int OrderID, int CategoryID, int Quantity, int Price) {
        this.OrderItemID = OrderItemID;
        this.OrderID = OrderID;
        this.CategoryID = CategoryID;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public OrderItems(int OrderID, int CategoryID, int Quantity, int Price) {
        this.OrderID = OrderID;
        this.CategoryID = CategoryID;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public int getOrderItemID() {
        return OrderItemID;
    }

    public void setOrderItemID(int OrderItemID) {
        this.OrderItemID = OrderItemID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public ArrayList<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(ArrayList<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

}
