
package models;


public class BrandComs {
    private int BrandComID;
    private int BrandID;
    private String BrandName;
    private int ComponentID;
    private String ComponentName;
    private int Quantity;

    public BrandComs(int BrandComID, int BrandID, int ComponentID, int Quantity) {
        this.BrandComID = BrandComID;
        this.BrandID = BrandID;
        this.ComponentID = ComponentID;
        this.Quantity = Quantity;
    }

    public BrandComs(int BrandComID, int BrandID, String BrandName, int ComponentID, String ComponentName, int Quantity) {
        this.BrandComID = BrandComID;
        this.BrandID = BrandID;
        this.BrandName = BrandName;
        this.ComponentID = ComponentID;
        this.ComponentName = ComponentName;
        this.Quantity = Quantity;
    }
    
    
    public BrandComs(int BrandID, int ComponentID, int Quantity) {
        this.BrandID = BrandID;
        this.ComponentID = ComponentID;
        this.Quantity = Quantity;
    }

    public BrandComs() {
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
    
    
    public int getBrandComID() {
        return BrandComID;
    }

    public void setBrandComID(int BrandComID) {
        this.BrandComID = BrandComID;
    }

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int BrandID) {
        this.BrandID = BrandID;
    }

    public int getComponentID() {
        return ComponentID;
    }

    public void setComponentID(int ComponentID) {
        this.ComponentID = ComponentID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
         
}
