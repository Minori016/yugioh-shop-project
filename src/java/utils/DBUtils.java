package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String DB_NAME = "YUGIOH_TCG_SHOP";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "Sa@12345";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // ✅ Sửa lại URL kết nối: KHÔNG còn \HOANGAN
        String url = "jdbc:sqlserver://localhost:1433;"   // hoặc "jdbc:sqlserver://LEGION5:1433;"
                   + "databaseName=" + DB_NAME + ";"
                   + "encrypt=true;"
                   + "trustServerCertificate=true;";  // Bắt buộc cho SQL Server 2022

        conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        return conn;
    }

    public static void main(String[] args) {
        try {
            Connection conn = DBUtils.getConnection();
            if (conn != null) {
                System.out.println("✅ Connected to: " + conn.getCatalog());
            } else {
                System.out.println("❌ Connection failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
