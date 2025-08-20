package models;

public class WarrantyDetails {

    private int WarrantyDetailID;
    private int WarrantyID;
    private Warranties warranty;
    private int WarrantyPeriod;
    private String Description;
    private int BrandComID;
    private String BrandName;
    private String ComponentName;
    private int price;
    private int status;

    public WarrantyDetails() {
    }

    public WarrantyDetails(int WarrantyDetailID, int WarrantyID, Warranties warranty, int WarrantyPeriod, String Description, int BrandComID, String BrandName, String ComponentName, int price, int status) {
        this.WarrantyDetailID = WarrantyDetailID;
        this.WarrantyID = WarrantyID;
        this.warranty = warranty;
        this.WarrantyPeriod = WarrantyPeriod;
        this.Description = Description;
        this.BrandComID = BrandComID;
        this.BrandName = BrandName;
        this.ComponentName = ComponentName;
        this.price = price;
        this.status = status;
    }

    public WarrantyDetails(int WarrantyDetailID, int WarrantyID, int WarrantyPeriod, String Description, int BrandComID, String BrandName, String ComponentName, int price, int status) {
        this.WarrantyDetailID = WarrantyDetailID;
        this.WarrantyID = WarrantyID;
        this.WarrantyPeriod = WarrantyPeriod;
        this.Description = Description;
        this.BrandComID = BrandComID;
        this.BrandName = BrandName;
        this.ComponentName = ComponentName;
        this.price = price;
        this.status = status;
    }

    public WarrantyDetails(int WarrantyID, int WarrantyPeriod, String Description, int BrandComID, String BrandName, String ComponentName, int price, int status) {
        this.WarrantyID = WarrantyID;
        this.WarrantyPeriod = WarrantyPeriod;
        this.Description = Description;
        this.BrandComID = BrandComID;
        this.BrandName = BrandName;
        this.ComponentName = ComponentName;
        this.price = price;
        this.status = status;
    }

    public WarrantyDetails(int WarrantyDetailID, int WarrantyID, int BrandComID, String BrandName, String ComponentName, int price, int status) {
        this.WarrantyDetailID = WarrantyDetailID;
        this.WarrantyID = WarrantyID;
        this.BrandComID = BrandComID;
        this.BrandName = BrandName;
        this.ComponentName = ComponentName;
        this.price = price;
        this.status = status;
    }

    public WarrantyDetails(int WarrantyID, int BrandComID, String BrandName, String ComponentName, int price, int status) {
        this.WarrantyID = WarrantyID;
        this.BrandComID = BrandComID;
        this.BrandName = BrandName;
        this.ComponentName = ComponentName;
        this.price = price;
        this.status = status;
    }

    public WarrantyDetails(int WarrantyDetailID, int WarrantyID, int BrandComID, int price, int status) {
        this.WarrantyDetailID = WarrantyDetailID;
        this.WarrantyID = WarrantyID;
        this.BrandComID = BrandComID;
        this.price = price;
        this.status = status;
    }

    public WarrantyDetails(int WarrantyID, int BrandComID, String BrandName, int price, int status) {
        this.WarrantyID = WarrantyID;
        this.BrandComID = BrandComID;
        this.BrandName = BrandName;
        this.price = price;
        this.status = status;
    }

    public WarrantyDetails(int WarrantyID, int BrandComID, int price, int status) {
        this.WarrantyID = WarrantyID;
        this.BrandComID = BrandComID;
        this.price = price;
        this.status = status;
    }

    public Warranties getWarranty() {
        return warranty;
    }

    public void setWarranty(Warranties warranty) {
        this.warranty = warranty;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public String getComponentName() {
        return ComponentName;
    }

    public void setComponentName(String ComponentName) {
        this.ComponentName = ComponentName;
    }

    public int getWarrantyPeriod() {
        return WarrantyPeriod;
    }

    public void setWarrantyPeriod(int WarrantyPeriod) {
        this.WarrantyPeriod = WarrantyPeriod;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getWarrantyDetailID() {
        return WarrantyDetailID;
    }

    public void setWarrantyDetailID(int WarrantyDetailID) {
        this.WarrantyDetailID = WarrantyDetailID;
    }

    public int getWarrantyID() {
        return WarrantyID;
    }

    public void setWarrantyID(int WarrantyID) {
        this.WarrantyID = WarrantyID;
    }

    public int getBrandComID() {
        return BrandComID;
    }

    public void setBrandComID(int BrandComID) {
        this.BrandComID = BrandComID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
