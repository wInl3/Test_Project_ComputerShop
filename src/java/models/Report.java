/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class Report {
    private int reportID;
    private User manager;
    private String title;
    private String content;
    private String createdAt;

    public Report() {
    }

    public Report(int reportID, User manager, String title, String content, String createdAt) {
        this.reportID = reportID;
        this.manager = manager;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Report{" + "reportID=" + reportID + ", manager=" + manager + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt + '}';
    }
}