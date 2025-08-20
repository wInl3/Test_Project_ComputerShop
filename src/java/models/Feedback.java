package models;

public class Feedback {
    private int feedbackID;
    private int userID;
    private String content;
    private int orderItemID;
    private String createdAt;
    private int rate;
    private int status;
    private String fullname;
    private String reply;
    private int categoryID;
    private String categoryName;
    private String roleName;
    
    private OrderItems orderItems;

    // Constructor mặc định
    public Feedback() {
    }

    // Constructor đầy đủ cho JOIN lấy category và role
    public Feedback(int feedbackID, int userID, String content, int orderItemID, String createdAt, int rate, int status, String fullname, String reply, int categoryID, String categoryName, String roleName) {
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.content = content;
        this.orderItemID = orderItemID;
        this.createdAt = createdAt;
        this.rate = rate;
        this.status = status;
        this.fullname = fullname;
        this.reply = reply;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.roleName = roleName;
    }

    // Giữ nguyên tất cả constructor cũ (ví dụ):
    public Feedback(int feedbackID, int userID, String content, int orderItemID, String createdAt, int rate, int status, String fullname, String reply) {
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.content = content;
        this.orderItemID = orderItemID;
        this.createdAt = createdAt;
        this.rate = rate;
        this.status = status;
        this.fullname = fullname;
        this.reply = reply;
    }

    // Constructor cũ để không lỗi code cũ
    public Feedback(int userID, String content, int orderItemID, int rate) {
        this.userID = userID;
        this.content = content;
        this.orderItemID = orderItemID;
        this.rate = rate;
        this.status = 1;
    }

    // Getter/setter
    public int getFeedbackID() { return feedbackID; }
    public void setFeedbackID(int feedbackID) { this.feedbackID = feedbackID; }
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getOrderItemID() { return orderItemID; }
    public void setOrderItemID(int orderItemID) { this.orderItemID = orderItemID; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public int getRate() { return rate; }
    public void setRate(int rate) { this.rate = rate; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }
    public int getCategoryID() { return categoryID; }
    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public OrderItems getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItems orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackID=" + feedbackID +
                ", userID=" + userID +
                ", content='" + content + '\'' +
                ", orderItemID=" + orderItemID +
                ", createdAt='" + createdAt + '\'' +
                ", rate=" + rate +
                ", status=" + status +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}