/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class Components {
    private int componentID;
    private String componentName;
    private int quantity;
    private int status;

    public Components() {
    }

    public Components(int componentID, String componentName, int quantity, int status) {
        this.componentID = componentID;
        this.componentName = componentName;
        this.quantity = quantity;
        this.status = status;
    }

    public Components(String componentName, int quantity, int status) {
        this.componentName = componentName;
        this.quantity = quantity;
        this.status = status;
    }
        
    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
