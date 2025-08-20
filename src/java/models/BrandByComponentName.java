package models;

public class BrandByComponentName {
    private int componentID;
    private String componentName;
    private String brandName;

    public BrandByComponentName() {}

    public BrandByComponentName(int componentID, String componentName, String brandName) {
        this.componentID = componentID;
        this.componentName = componentName;
        this.brandName = brandName;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
