/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class Warranties {
    private int WarrantyID;
    private int WarrantyPeriod ;
    private String Description;
    private int Status;

    public Warranties() {
    }

    public Warranties(int WarrantyID, int WarrantyPeriod, String Description, int Status) {
        this.WarrantyID = WarrantyID;
        this.WarrantyPeriod = WarrantyPeriod;
        this.Description = Description;
        this.Status = Status;
    }

    public Warranties(int WarrantyPeriod, String Description, int Status) {
        this.WarrantyPeriod = WarrantyPeriod;
        this.Description = Description;
        this.Status = Status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    
    

    public int getWarrantyID() {
        return WarrantyID;
    }

    public void setWarrantyID(int WarrantyID) {
        this.WarrantyID = WarrantyID;
    }

    public int getWarrantyPeriod() {
        return WarrantyPeriod;
    }

    public void setWarrantyPeriod(int WarrantyPeriod) {
        this.WarrantyPeriod = WarrantyPeriod;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
    
    
}
