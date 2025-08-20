/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author PC ASUS
 */
public class CustomerInfo {
    private int CustomerInfoID;
    private int UserID;
    private String Address;

    public CustomerInfo() {
    }

    public CustomerInfo(int CustomerInfoID, int UserID, String Address) {
        this.CustomerInfoID = CustomerInfoID;
        this.UserID = UserID;
        this.Address = Address;
    }

    public int getCustomerInfoID() {
        return CustomerInfoID;
    }

    public void setCustomerInfoID(int CustomerInfoID) {
        this.CustomerInfoID = CustomerInfoID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" + "CustomerInfoID=" + CustomerInfoID + ", UserID=" + UserID + ", Address=" + Address + '}';
    }
}
