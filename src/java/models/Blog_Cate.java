/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author User
 */
public class Blog_Cate {
    private int Bc_id;
    private String Bc_name;

    public Blog_Cate() {
    }

    public Blog_Cate(int Bc_id, String Bc_name) {
        this.Bc_id = Bc_id;
        this.Bc_name = Bc_name;
    }

    public int getBc_id() {
        return Bc_id;
    }

    public void setBc_id(int Bc_id) {
        this.Bc_id = Bc_id;
    }

    public String getBc_name() {
        return Bc_name;
    }

    public void setBc_name(String Bc_name) {
        this.Bc_name = Bc_name;
    }

    @Override
    public String toString() {
        return "Blog_Cate{" + "Bc_id=" + Bc_id + ", Bc_name=" + Bc_name + '}';
    }
    
        }


   