package controllerAdmin;

import dalAdmin.BuildPCAdminDAO;
import java.util.Arrays;
import models.BuildPCView;
import java.util.List;

public class TestBuildPC2 {

 
      public static void main(String[] args) {
        // Danh sách categoryID (giả sử đã tồn tại)
        List<Integer> categoryIDs = Arrays.asList(9, 18, 37, 47, 52, 65);

        // Danh sách warrantyID tương ứng (có thể = 0 nếu không chọn)
        List<Integer> warrantyIDs = Arrays.asList(201, 0, 203, 0, 0, 206);

        int userID = 1; // Giả sử user có ID = 1

        // Gọi DAO để insert
        BuildPCAdminDAO dao = new BuildPCAdminDAO();

        boolean success = dao.insertBuildPC(categoryIDs, warrantyIDs, userID);

        if (success) {
            System.out.println("✅ Insert Build PC thành công.");
        } else {
            System.out.println("❌ Insert thất bại.");
        }
    }
}
