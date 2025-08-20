/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Comment {

    private int CommentID;
    private int Post_id;
    private int UserID;
    private String CommentText;
    private Timestamp CreatedAt;
    private Integer ParentCommentID;
    private String FullName;
    private List<Comment> replies = new ArrayList<>();

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public Comment() {
    }

    public Comment(int CommentID, int Post_id, int UserID, String CommentText, Timestamp CreatedAt, Integer ParentCommentID) {
        this.CommentID = CommentID;
        this.Post_id = Post_id;
        this.UserID = UserID;
        this.CommentText = CommentText;
        this.CreatedAt = CreatedAt;
        this.ParentCommentID = ParentCommentID;
    }

    public Comment(String FullName) {
        this.FullName = FullName;
    }

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int CommentID) {
        this.CommentID = CommentID;
    }

    public int getPost_id() {
        return Post_id;
    }

    public void setPost_id(int Post_id) {
        this.Post_id = Post_id;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String CommentText) {
        this.CommentText = CommentText;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public Integer getParentCommentID() {
        return ParentCommentID;
    }

    public void setParentCommentID(Integer ParentCommentID) {
        this.ParentCommentID = ParentCommentID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    @Override
    public String toString() {
        return "Comment{" + "CommentID=" + CommentID + ", Post_id=" + Post_id + ", UserID=" + UserID + ", CommentText=" + CommentText + ", CreatedAt=" + CreatedAt + ", ParentCommentID=" + ParentCommentID + ", FullName=" + FullName + '}';
    }

}
