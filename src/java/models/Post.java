/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class Post {

    private int Post_id;
    private String Title;
    private String Author;
    private java.sql.Timestamp Updated_date;
    private String Content;
    private String bc_name;
    private int Bc_id;
    private String Thumbnail;
    private String Brief;
    private int Add_id;
    private int status;

    public Post() {
    }

    public Post(int Post_id, String Title, String Author, Timestamp Updated_date, String Content, String bc_name, int Bc_id, String Thumbnail, String Brief, int Add_id, int status) {
        this.Post_id = Post_id;
        this.Title = Title;
        this.Author = Author;
        this.Updated_date = Updated_date;
        this.Content = Content;
        this.bc_name = bc_name;
        this.Bc_id = Bc_id;
        this.Thumbnail = Thumbnail;
        this.Brief = Brief;
        this.Add_id = Add_id;
        this.status = status;
    }

    public Post(int Post_id, String Title, String Author, Timestamp Updated_date, String Content, String bc_name, int Bc_id, String Thumbnail, String Brief, int Add_id) {
        this.Post_id = Post_id;
        this.Title = Title;
        this.Author = Author;
        this.Updated_date = Updated_date;
        this.Content = Content;
        this.bc_name = bc_name;
        this.Bc_id = Bc_id;
        this.Thumbnail = Thumbnail;
        this.Brief = Brief;
        this.Add_id = Add_id;
    }

    public Post(int Post_id, String Title, String Author, Timestamp Updated_date, String Content, int Bc_id, String Thumbnail, String Brief) {
        this.Post_id = Post_id;
        this.Title = Title;
        this.Author = Author;
        this.Updated_date = Updated_date;
        this.Content = Content;
        this.Bc_id = Bc_id;
        this.Thumbnail = Thumbnail;
        this.Brief = Brief;
//        this.Add_id = Add_id;
    }

    public Post(String Title, String Author, Timestamp Updated_date, String Content, int Bc_id, String Thumbnail, String Brief, int Add_id, int status) {
        this.Title = Title;
        this.Author = Author;
        this.Updated_date = Updated_date;
        this.Content = Content;
        this.Bc_id = Bc_id;
        this.Thumbnail = Thumbnail;
        this.Brief = Brief;
        this.Add_id = Add_id;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBc_name() {
        return bc_name;
    }

    public void setBc_name(String bc_name) {
        this.bc_name = bc_name;
    }

    public int getPost_id() {
        return Post_id;
    }

    public void setPost_id(int Post_id) {
        this.Post_id = Post_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public Timestamp getUpdated_date() {
        return Updated_date;
    }

    public void setUpdated_date(Timestamp Updated_date) {
        this.Updated_date = Updated_date;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getBc_id() {
        return Bc_id;
    }

    public void setBc_id(int Bc_id) {
        this.Bc_id = Bc_id;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String Thumbnail) {
        this.Thumbnail = Thumbnail;
    }

    public String getBrief() {
        return Brief;
    }

    public void setBrief(String Brief) {
        this.Brief = Brief;
    }

    public int getAdd_id() {
        return Add_id;
    }

    public void setAdd_id(int Add_id) {
        this.Add_id = Add_id;
    }

    @Override
    public String toString() {
        return "Post{" + "Post_id=" + Post_id + ", Title=" + Title + ", Author=" + Author + ", Updated_date=" + Updated_date + ", Content=" + Content + ", bc_name=" + bc_name + ", Bc_id=" + Bc_id + ", Thumbnail=" + Thumbnail + ", Brief=" + Brief + ", Add_id=" + Add_id + ", status=" + status + '}';
    }

   

    

}
