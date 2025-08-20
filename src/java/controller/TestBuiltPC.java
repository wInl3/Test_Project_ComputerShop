package controller;

import dal.CategoriesDAO;
import models.BuildPCView;
import java.util.List;

public class TestBuiltPC {
    public static void main(String[] args) {
        CategoriesDAO dao = new CategoriesDAO();
        List<BuildPCView> pcs = dao.getBuiltPCsForCustomer();

        if (pcs.isEmpty()) {
            System.out.println("⚠️ Không có Build PC nào được tìm thấy.");
        } else {
            for (BuildPCView pc : pcs) {
                System.out.println("🔧 Build PC #" + pc.getBuildPCID());
                System.out.printf("🧩 MainBoard: %s | Ảnh: %s\n", pc.getMainBoard(), pc.getImgMain());
                System.out.printf("🧠 CPU      : %s | Ảnh: %s\n", pc.getCpu(), pc.getImgCPU());
                System.out.printf("🎮 GPU      : %s | Ảnh: %s\n", pc.getGpu(), pc.getImgGPU());
                System.out.printf("💾 RAM      : %s | Ảnh: %s\n", pc.getRam(), pc.getImgRAM());
                System.out.printf("📀 SSD      : %s | Ảnh: %s\n", pc.getSsd(), pc.getImgSSD());
                System.out.printf("🖥️ Case     : %s | Ảnh: %s\n", pc.getPcCase(), pc.getImgCase());
                System.out.printf("💰 Tổng giá : %,d₫\n", pc.getPrice());
                System.out.println("📌 Trạng thái: " + (pc.getStatus() == 1 ? "Hoạt động" : "Ẩn"));
                System.out.println("---------------------------------------------------");
            }
        }
    }
}
