package models;

import java.sql.Date;


public class OrderInfo {
    private String orderCode;
    private String fullName;
    private String categoryName;
    private String productCode;
    private int warrantyPeriod;
    private String warrantyDescription;
    private Date start;
    private Date end;
    private int customerID;



    public OrderInfo(String orderCode, String fullName, String categoryName, String productCode, int warrantyPeriod, String warrantyDescription, Date start, Date end) {
        this.orderCode = orderCode;
        this.fullName = fullName;
        this.categoryName = categoryName;
        this.productCode = productCode;
        this.warrantyPeriod = warrantyPeriod;
        this.warrantyDescription = warrantyDescription;
        this.start = start;
        this.end = end;
    }

    public OrderInfo(String fullName, String categoryName, String productCode, int warrantyPeriod, String warrantyDescription, Date start, Date end) {
        this.fullName = fullName;
        this.categoryName = categoryName;
        this.productCode = productCode;
        this.warrantyPeriod = warrantyPeriod;
        this.warrantyDescription = warrantyDescription;
        this.start = start;
        this.end = end;
    }

    public OrderInfo() {
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyDescription() {
        return warrantyDescription;
    }

    public void setWarrantyDescription(String warrantyDescription) {
        this.warrantyDescription = warrantyDescription;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    
}
