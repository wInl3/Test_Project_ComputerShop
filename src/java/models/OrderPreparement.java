/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author PC ASUS
 */
public class OrderPreparement {
    private User user;
    private OrderCate order;
    private String prepareTime;

    public OrderPreparement(User user, OrderCate order, String prepareTime) {
        this.user = user;
        this.order = order;
        this.prepareTime = prepareTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderCate getOrder() {
        return order;
    }

    public void setOrder(OrderCate order) {
        this.order = order;
    }

    public String getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }
    
    
}
