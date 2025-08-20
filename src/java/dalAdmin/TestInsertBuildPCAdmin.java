//package dalAdmin;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class TestInsertBuildPCAdmin {
//
//    public static void main(String[] args) {
//
//        BuildPCAdminDAO dao = new BuildPCAdminDAO();
//
//        if (dao.isConnected()) {
//            System.out.println("Kết nối cơ sở dữ liệu thành công!");
//
//            // Danh sách 6 CategoryID cần hợp lệ trong bảng Categories
//            List<Integer> categoryIDs = Arrays.asList(3, 10, 5, 42, 1, 2);
//
//            int userID = 3; // ID người dùng phải tồn tại trong bảng Users
//
//            boolean success = dao.insertBuildPC(categoryIDs, userID);
//
//            if (success) {
//                System.out.println("Thêm Build PC thành công!");
//            } else {
//                System.out.println("Thêm Build PC thất bại!");
//            }
//
//        } else {
//            System.out.println("Không kết nối được database!");
//        }
//    }
//}
