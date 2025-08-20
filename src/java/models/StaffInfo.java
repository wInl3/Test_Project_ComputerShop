/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author PC ASUS
 */
public class StaffInfo {
    private int StaffInfoID;
    private int UserID;
    private String StartedDate;
    private String EndDate;

    public StaffInfo() {
    }

    public StaffInfo(int StaffInfoID, int UserID, String StartedDate, String EndDate) {
        this.StaffInfoID = StaffInfoID;
        this.UserID = UserID;
        this.StartedDate = StartedDate;
        this.EndDate = EndDate;
    }

    public int getStaffInfoID() {
        return StaffInfoID;
    }

    public void setStaffInfoID(int StaffInfoID) {
        this.StaffInfoID = StaffInfoID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getStartedDate() {
        return StartedDate;
    }

    public void setStartedDate(String StartedDate) {
        this.StartedDate = StartedDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }
    
    

    @Override
    public String toString() {
        return "StaffInfo{" + "StaffInfoID=" + StaffInfoID + ", UserID=" + UserID + ", StartedDate=" + StartedDate + ", EndDate=" + EndDate + '}';
    }
}
