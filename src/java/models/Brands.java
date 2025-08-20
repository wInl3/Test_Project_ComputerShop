/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class Brands {

    private int brandID;
    private String brandName;
    private int Quantity;
    private int Status;

    public Brands() {
    }

    public Brands(int brandID, String brandName) {
        this.brandID = brandID;
        this.brandName = brandName;
    }

    public Brands(int brandID, String brandName, int Quantity, int Status) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.Quantity = Quantity;
        this.Status = Status;
    }

    public Brands(String brandName, int Quantity, int Status) {
        this.brandName = brandName;
        this.Quantity = Quantity;
        this.Status = Status;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Brands(String brandName) {
        this.brandName = brandName;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
