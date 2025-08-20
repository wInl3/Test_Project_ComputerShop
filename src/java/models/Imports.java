package models;

import java.util.Date;

public class Imports {

    private int ImportID;
    private String ImportCode;
    private int CategoryID;
    private String CategoryName;
    private Date CreatAt;
    private int Quantity;
    private int price;

    public Imports(int ImportID, int CategoryID, String CategoryName, Date CreatAt, int Quantity, int price) {
        this.ImportID = ImportID;
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.CreatAt = CreatAt;
        this.Quantity = Quantity;
        this.price = price;
    }

    public Imports(int ImportID, int CategoryID, Date CreatAt, int Quantity, int price) {
        this.ImportID = ImportID;
        this.CategoryID = CategoryID;
        this.CreatAt = CreatAt;
        this.Quantity = Quantity;
        this.price = price;
    }

    public Imports(int CategoryID, Date CreatAt, int Quantity, int price) {
        this.CategoryID = CategoryID;
        this.CreatAt = CreatAt;
        this.Quantity = Quantity;
        this.price = price;
    }

    public Imports(int ImportID, String ImportCode, int CategoryID, String CategoryName, Date CreatAt, int Quantity, int price) {
        this.ImportID = ImportID;
        this.ImportCode = ImportCode;
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.CreatAt = CreatAt;
        this.Quantity = Quantity;
        this.price = price;
    }

    public Imports(String ImportCode, int CategoryID, String CategoryName, Date CreatAt, int Quantity, int price) {
        this.ImportCode = ImportCode;
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.CreatAt = CreatAt;
        this.Quantity = Quantity;
        this.price = price;
    }

    public Imports() {
    }

    public String getImportCode() {
        return ImportCode;
    }

    public void setImportCode(String ImportCode) {
        this.ImportCode = ImportCode;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public int getImportID() {
        return ImportID;
    }

    public void setImportID(int ImportID) {
        this.ImportID = ImportID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

    public Date getCreatAt() {
        return CreatAt;
    }

    public void setCreatAt(Date CreatAt) {
        this.CreatAt = CreatAt;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
