package models;

import java.util.ArrayList;
import java.util.Date;

public class OrderCate {

    private int OrderID;
    private String OrderCode;
    private int Product_Type;
    private int CustomerID;
    private String CustomerName;
    private String FullName;
    private Date OrderDate;
    private String Address;
    private String phoneNumber;
    private int TotalAmount;
    private int Status;
    private int PaymentStatusID;
    private int StaffID;
    private String StaffName;
    private String Note;
    private Order_Status orderStatus1;
    private Date PrepareTime;
    private int shipperID;
    private String shipperName;
    private String shipperPhone;
    private Date shipTime;
    private String shipNote;

    private int warrantyStaffID;
    private String warrantyStaffName;
    private Date warrantyAssignedDate;

    private ArrayList<OrderItems> orderItems;

    public OrderCate() {
    }

    public OrderCate(int OrderID, String OrderCode, int Product_Type, int CustomerID, String CustomerName, String FullName, Date OrderDate, String Address, String phoneNumber, int TotalAmount, int Status, int PaymentStatusID, int StaffID, String StaffName, String Note, Date PrepareTime, ArrayList<OrderItems> orderItems) {
        this.OrderID = OrderID;
        this.OrderCode = OrderCode;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.phoneNumber = phoneNumber;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
        this.PaymentStatusID = PaymentStatusID;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
        this.Note = Note;
        this.PrepareTime = PrepareTime;
        this.orderItems = orderItems;
    }

    public OrderCate(int OrderID, String OrderCode, int Product_Type, int CustomerID, String CustomerName, String FullName, Date OrderDate, String Address, String phoneNumber, int TotalAmount, int Status, int PaymentStatusID, int StaffID, String StaffName, String Note, Date PrepareTime, int shipperID, String shipperName, String shipperPhone, Date shipTime, int warrantyStaffID, String warrantyStaffName, Date warrantyAssignedDate) {
        this.OrderID = OrderID;
        this.OrderCode = OrderCode;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.phoneNumber = phoneNumber;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
        this.PaymentStatusID = PaymentStatusID;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
        this.Note = Note;
        this.PrepareTime = PrepareTime;
        this.shipperID = shipperID;
        this.shipperName = shipperName;
        this.shipperPhone = shipperPhone;
        this.shipTime = shipTime;
        this.warrantyStaffID = warrantyStaffID;
        this.warrantyStaffName = warrantyStaffName;
        this.warrantyAssignedDate = warrantyAssignedDate;
    }

    public OrderCate(String OrderCode, int Product_Type, int CustomerID, String CustomerName, String FullName, Date OrderDate, String Address, String phoneNumber, int TotalAmount, int Status, int PaymentStatusID, int StaffID, String StaffName, String Note, Date PrepareTime, int shipperID, String shipperName, String shipperPhone, Date shipTime, int warrantyStaffID, String warrantyStaffName, Date warrantyAssignedDate) {
        this.OrderCode = OrderCode;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.phoneNumber = phoneNumber;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
        this.PaymentStatusID = PaymentStatusID;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
        this.Note = Note;
        this.PrepareTime = PrepareTime;
        this.shipperID = shipperID;
        this.shipperName = shipperName;
        this.shipperPhone = shipperPhone;
        this.shipTime = shipTime;
        this.warrantyStaffID = warrantyStaffID;
        this.warrantyStaffName = warrantyStaffName;
        this.warrantyAssignedDate = warrantyAssignedDate;
    }
    

    public OrderCate(int OrderID, String OrderCode, int Product_Type, int CustomerID, String CustomerName, String FullName, Date OrderDate, String Address, String phoneNumber, int TotalAmount, int Status, int PaymentStatus, int StaffID, String StaffName, Date PrepareTime, ArrayList<OrderItems> orderItems) {
        this.OrderID = OrderID;
        this.OrderCode = OrderCode;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.phoneNumber = phoneNumber;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
        this.PaymentStatusID = PaymentStatus;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
        this.PrepareTime = PrepareTime;
        this.orderItems = orderItems;
    }

    public OrderCate(int OrderID, String OrderCode, int Product_Type, int CustomerID, String CustomerName, String FullName, Date OrderDate, String Address, String phoneNumber, int TotalAmount, int Status, int PaymentStatus, int StaffID, String StaffName, Date PrepareTime) {
        this.OrderID = OrderID;
        this.OrderCode = OrderCode;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.phoneNumber = phoneNumber;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
        this.PaymentStatusID = PaymentStatus;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
        this.PrepareTime = PrepareTime;
    }

    public OrderCate(int OrderID, String OrderCode, int Product_Type, int CustomerID, String FullName, Date OrderDate, String Address, String phoneNumber, int TotalAmount, int Status, int PaymentStatus, int StaffID, String StaffName) {
        this.OrderID = OrderID;
        this.OrderCode = OrderCode;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.phoneNumber = phoneNumber;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
        this.PaymentStatusID = PaymentStatus;
        this.StaffID = StaffID;
        this.StaffName = StaffName;
    }

    public OrderCate(int OrderID, String OrderCode, int Product_Type, int CustomerID, String FullName, Date OrderDate, String Address, String phoneNumber, int TotalAmount, int Status, int PaymentStatus) {
        this.OrderID = OrderID;
        this.OrderCode = OrderCode;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.phoneNumber = phoneNumber;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
        this.PaymentStatusID = PaymentStatus;
    }

    public OrderCate(int OrderID, int Product_Type, int CustomerID, Date OrderDate, String Address, int TotalAmount, int Status) {
        this.OrderID = OrderID;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
    }

    public OrderCate(int Product_Type, int CustomerID, Date OrderDate, String Address, int TotalAmount, int Status) {
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
    }

    public OrderCate(int OrderID, int Product_Type, int CustomerID, String FullName, Date OrderDate, String Address, int TotalAmount, int Status) {
        this.OrderID = OrderID;
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
    }

    public OrderCate(int Product_Type, int CustomerID, String FullName, Date OrderDate, String Address, int TotalAmount, int Status) {
        this.Product_Type = Product_Type;
        this.CustomerID = CustomerID;
        this.FullName = FullName;
        this.OrderDate = OrderDate;
        this.Address = Address;
        this.TotalAmount = TotalAmount;
        this.Status = Status;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public int getShipperID() {
        return shipperID;
    }

    public void setShipperID(int shipperID) {
        this.shipperID = shipperID;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public int getWarrantyStaffID() {
        return warrantyStaffID;
    }

    public void setWarrantyStaffID(int warrantyStaffID) {
        this.warrantyStaffID = warrantyStaffID;
    }

    public String getWarrantyStaffName() {
        return warrantyStaffName;
    }

    public void setWarrantyStaffName(String warrantyStaffName) {
        this.warrantyStaffName = warrantyStaffName;
    }

    public Date getWarrantyAssignedDate() {
        return warrantyAssignedDate;
    }

    public void setWarrantyAssignedDate(Date warrantyAssignedDate) {
        this.warrantyAssignedDate = warrantyAssignedDate;
    }

    public Date getPrepareTime() {
        return PrepareTime;
    }

    public void setPrepareTime(Date PrepareTime) {
        this.PrepareTime = PrepareTime;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int StaffID) {
        this.StaffID = StaffID;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String StaffName) {
        this.StaffName = StaffName;
    }

    public int getProduct_Type() {
        return Product_Type;
    }

    public void setProduct_Type(int Product_Type) {
        this.Product_Type = Product_Type;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public int getPaymentStatusID() {
        return PaymentStatusID;
    }

    public void setPaymentStatusID(int PaymentStatusID) {
        this.PaymentStatusID = PaymentStatusID;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public Order_Status getOrder_Status() {
        return orderStatus1;
    }

    public void setOrder_Status(Order_Status orderStatus1) {
        this.orderStatus1 = orderStatus1;
    }

    public String getShipNote() {
        return shipNote;
    }

    public void setShipNote(String shipNote) {
        this.shipNote = shipNote;
    }
    

}
