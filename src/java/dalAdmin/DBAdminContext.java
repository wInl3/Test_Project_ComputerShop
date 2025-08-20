package dalAdmin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBAdminContext {

    protected Connection connection;

    public DBAdminContext() {

        String user = "root"; // Thay đổi theo MySQL của bạn

        String pass = "123456"; 
        String url = "jdbc:mysql://localhost:3306/ComputerOnlineShop?useSSL=false&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBAdminContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getData(String sql) {
        ResultSet rs = null;
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = state.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(DBAdminContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
public void close() {
    try {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    public static void main(String[] args) {
        try {
            DBAdminContext dbContext = new DBAdminContext();
            if (dbContext.isConnected()) {
                System.out.println("Kết nối cơ sở dữ liệu thành công!");
            } else {
                System.out.println("Kết nối cơ sở dữ liệu thất bại.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}