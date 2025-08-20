/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author PC ASUS
 */
public class OTP {
    private int otpId;
    private String email;
    private String otpCode;
    private Date expirationTime;
    private Date createdAt;
    private boolean isUsed;

    public OTP() {}

    public OTP(int otpId, String email, String otpCode, Date expirationTime, Date createdAt, boolean isUsed) {
        this.otpId = otpId;
        this.email = email;
        this.otpCode = otpCode;
        this.expirationTime = expirationTime;
        this.createdAt = createdAt;
        this.isUsed = isUsed;
    }

    public int getOtpId() {
        return otpId;
    }
    public void setOtpId(int otpId) {
        this.otpId = otpId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getOtpCode() {
        return otpCode;
    }
    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
    public Date getExpirationTime() {
        return expirationTime;
    }
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public boolean isUsed() {
        return isUsed;
    }
    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
