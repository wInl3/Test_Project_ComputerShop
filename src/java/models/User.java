/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.OrderPreparementDAO;
import dal.ShippingDAO;

/**
 *
 * @author PC ASUS
 */
public class User {

    private int userId;
    private Role role;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String password;
    private String createdAt;
    private int status;
    private CustomerInfo customerInfo;
    private StaffInfo staffInfo;

    public User() {
    }

    // Constructor cơ bản với các thông tin chính
    public User(String fullname, String email, String phoneNumber, String password, Role role) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.status = 1; // Mặc định status là active
    }

    // Constructor đầy đủ cho việc load từ database
    public User(int userId, Role role, String fullname, String email, String phoneNumber,
            String password, String createdAt, int status, CustomerInfo customerInfo, StaffInfo staffInfo) {
        this.userId = userId;
        this.role = role;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.createdAt = createdAt;
        this.status = status;
        this.customerInfo = customerInfo;
        this.staffInfo = staffInfo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }    // Phương thức hỗ trợ kiểm tra vai trò

    public boolean isCustomer() {
        return role != null && role.getRoleID() == 3; // Customer role
    }

    public boolean isStaff() {
        return role != null && (role.getRoleID() == 2 || role.getRoleID() == 4); // Staff role
    }

    public boolean isAdmin() {
        return role != null && role.getRoleID() == 1; // Admin role
    }

    public boolean isActive() {
        return status == 1;
    }
    
    public int countNumberOfOrders() {
        return (new OrderPreparementDAO()).countOrderPreparementByID(userId);
    }
    
    public int countNumberOfOrders1Month() {
        return (new OrderPreparementDAO()).countOrder1MonthByID(userId);
    }
    
    public int countNumberOfShips1Month() {
        return (new ShippingDAO()).countShips1MonthByID(userId);
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", role=" + role + ", fullname=" + fullname + ", email=" + email
                + ", phoneNumber=" + phoneNumber + ", password=" + password + ", createdAt=" + createdAt + ", status=" + status
                + ", customerInfo=" + customerInfo + ", staffInfo=" + staffInfo + '}';
    }
}
