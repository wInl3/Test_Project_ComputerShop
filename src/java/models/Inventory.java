/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author User
 */
public class Inventory {
    private int inventoryID;
    private int categoryID;
    private int quantity;
    private boolean isRestocking;

    public Inventory() {
    }

    public Inventory(int inventoryID, int categoryID, int quantity, boolean isRestocking) {
        this.inventoryID = inventoryID;
        this.categoryID = categoryID;
        this.quantity = quantity;
        this.isRestocking = isRestocking;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isIsRestocking() {
        return isRestocking;
    }

    public void setIsRestocking(boolean isRestocking) {
        this.isRestocking = isRestocking;
    }

    @Override
    public String toString() {
        return "Inventory{" + "inventoryID=" + inventoryID + ", categoryID=" + categoryID + ", quantity=" + quantity + ", isRestocking=" + isRestocking + '}';
    }

    
    
}
