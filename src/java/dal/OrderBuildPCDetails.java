/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author PC
 */
public class OrderBuildPCDetails {
    private int orderBuildPCDetailID;
    private int categoryID;
    private String categoryName;
    private int price;

    public int getOrderBuildPCDetailID() {
        return orderBuildPCDetailID;
    }

    public void setOrderBuildPCDetailID(int orderBuildPCDetailID) {
        this.orderBuildPCDetailID = orderBuildPCDetailID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}