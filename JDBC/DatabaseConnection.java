package day0805.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/team_project_1";
    private static final String USER = "jw";
    private static final String PASSWORD = "0922";

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
