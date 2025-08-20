package models;

public class Categories {

    private int categoryID;
    private String categoryName;

    private int brandComID;
    private String brandName;
    private String componentName;

    private int componentID;
    private int brandID;

    private int quantity;
    private int inventory;
    private int price;
    private int queue;

    private String description;
    private int status;
    private String imgURL;

    private int warrantyDetailID;
    private String warrantyDesc;
    private int warrantyPrice;
private int warrantyPeriod;
    // Constructors
    public Categories() {
    }

 
    public Categories(int categoryID, String categoryName, int brandComID, int quantity, int price, String description, int status, String imgURL) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.brandComID = brandComID;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.status = status;
        this.imgURL = imgURL;
    }

    public Categories(int categoryID, String categoryName, int brandComID, String brandName, String componentName, int quantity, int inventory, int price, String description, int status, String imgURL) {
        this(categoryID, categoryName, brandComID, quantity, price, description, status, imgURL);
        this.brandName = brandName;
        this.componentName = componentName;
        this.inventory = inventory;
    }

    public Categories(int categoryID, String categoryName, int brandComID, String brandName, String componentName, int componentID, int brandID, int quantity, int inventory, int price, int queue, String description, int status, String imgURL, int warrantyDetailID, String warrantyDesc, int warrantyPrice) {
        this(categoryID, categoryName, brandComID, brandName, componentName, quantity, inventory, price, description, status, imgURL);
        this.componentID = componentID;
        this.brandID = brandID;
        this.queue = queue;
        this.warrantyDetailID = warrantyDetailID;
        this.warrantyDesc = warrantyDesc;
        this.warrantyPrice = warrantyPrice;
    }


    public Categories(int categoryID, String categoryName, int componentID, int brandID, String brandName, int quantity, int price, String description, int status) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.componentID = componentID;
        this.brandID = brandID;
        this.brandName = brandName;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.status = status;
    }

    public Categories(String categoryName, int brandComID, int quantity, int price, String description, int status, String imgURL) {
        this.categoryName = categoryName;
        this.brandComID = brandComID;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.status = status;
        this.imgURL = imgURL;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and Setters
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

    public int getBrandComID() {
        return brandComID;
    }

    public void setBrandComID(int brandComID) {
        this.brandComID = brandComID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getWarrantyDetailID() {
        return warrantyDetailID;
    }

    public void setWarrantyDetailID(int warrantyDetailID) {
        this.warrantyDetailID = warrantyDetailID;
    }

    public String getWarrantyDesc() {
        return warrantyDesc;
    }

    public void setWarrantyDesc(String warrantyDesc) {
        this.warrantyDesc = warrantyDesc;
    }

    public int getWarrantyPrice() {
        return warrantyPrice;
    }

    public void setWarrantyPrice(int warrantyPrice) {
        this.warrantyPrice = warrantyPrice;
    }
}
