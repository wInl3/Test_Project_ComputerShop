/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.security.Timestamp;

/**
 *
 * @author User
 */
public class CommentLike {

    private int LikeID;
    private int CommentID;
    private int UserID;
    private Timestamp CreatedAt;

    public CommentLike() {
    }

    public CommentLike(int LikeID, int CommentID, int UserID, Timestamp CreatedAt) {
        this.LikeID = LikeID;
        this.CommentID = CommentID;
        this.UserID = UserID;
        this.CreatedAt = CreatedAt;
    }

    public int getLikeID() {
        return LikeID;
    }

    public void setLikeID(int LikeID) {
        this.LikeID = LikeID;
    }

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int CommentID) {
        this.CommentID = CommentID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    @Override
    public String toString() {
        return "CommentLike{" + "LikeID=" + LikeID + ", CommentID=" + CommentID + ", UserID=" + UserID + ", CreatedAt=" + CreatedAt + '}';
    }
    
}
