package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306//project1";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(AutoCloseable... closeables) {
        for(AutoCloseable closeble : closeables) {
            if(closeble != null) {
                try{
                    closeble.close();
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
