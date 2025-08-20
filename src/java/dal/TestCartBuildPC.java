/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import models.CartBuildPC;
import java.util.List;

public class TestCartBuildPC {

    public static void main(String[] args) {
        CartBuildPCDAO dao = new CartBuildPCDAO();
        
        int userID = 1;  // Giả sử tài khoản có userID = 1 có dữ liệu trong Cart_Build_PC

        List<CartBuildPC> cartList = dao.getCartPCView(userID);

        for (CartBuildPC pc : cartList) {
            System.out.println("CartBuildPCID: " + pc.getCartBuildPCID());
            System.out.println("Mainboard: " + pc.getMainBoard());
            System.out.println("CPU: " + pc.getCpu());
            System.out.println("GPU: " + pc.getGpu());
            System.out.println("RAM: " + pc.getRam());
            System.out.println("SSD: " + pc.getSsd());
            System.out.println("Case: " + pc.getPcCase());
            System.out.println("Price: " + pc.getPrice());
            System.out.println("Status: " + pc.getStatus());
            System.out.println("--------------------------------------");
        }

        if (cartList.isEmpty()) {
            System.out.println("Giỏ hàng rỗng hoặc sai UserID.");
        }
    }
}