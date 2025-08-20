package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBContext {

    private static Connection sharedConnection;

    protected Connection connection;

    public DBContext() {
        try {
            if (sharedConnection == null || sharedConnection.isClosed()) {
                String user = "root";
                String pass = "123456";
                String url = "jdbc:mysql://localhost:3306/ComputerOnlineShop?useSSL=false&serverTimezone=UTC";

                Class.forName("com.mysql.cj.jdbc.Driver");
                sharedConnection = DriverManager.getConnection(url, user, pass);
                System.out.println("✅ Kết nối MySQL được mở mới!");
            } else {
                System.out.println("✅ Dùng lại kết nối MySQL!");
            }

            this.connection = sharedConnection;

        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("❌ Lỗi kết nối MySQL: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public ResultSet getData(String sql) {
        ResultSet rs = null;
        try (Statement state = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println("❌ Lỗi khi thực thi truy vấn: " + ex.getMessage());
            ex.printStackTrace();
        }
        return rs;
    }

    public boolean isConnected() {
        try {
            return sharedConnection != null && !sharedConnection.isClosed();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        if (dbContext.isConnected()) {
            System.out.println("✅ Đã kết nối đến cơ sở dữ liệu MySQL.");
        } else {
            System.out.println("❌ Kết nối cơ sở dữ liệu thất bại.");
        }
    }
}
