///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package dalAdmin;
//
//import java.util.List;
//import models.BuildPCAdmin;
//
///**
// *
// * @author PC
// */
//public class TestBuildPC {
//    public static void main(String[] args) {
//        BuildPCAdminDAO dao = new BuildPCAdminDAO();
//        int testBuildPCID = 19; // ← đổi ID phù hợp với DB của bạn
//
//        List<BuildPCAdmin> list = dao.getBuildPCItemsByBuildPCID(testBuildPCID);
//
//        if (list.isEmpty()) {
//            System.out.println("⚠️ Không có linh kiện nào được tìm thấy cho BuildPCID = " + testBuildPCID);
//        } else {
//            for (BuildPCAdmin item : list) {
//                System.out.println("ComponentID: " + item.getComponentID());
//                System.out.println("CategoryID: " + item.getCateID());
//                System.out.println("CategoryName: " + item.getCateName());
//                System.out.println("BrandName: " + item.getBrandName());
//                System.out.println("Price: " + item.getPrice());
//                System.out.println("ImageURL: " + item.getImgURL());
//                System.out.println("-------------------------------");
//            }
//        }
//    }
//}
