package day0805;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private DBUtil() {}
    private static final DBUtil instance = new DBUtil();
    public static DBUtil getInstance() {
        return instance;
    }
    public Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/day0805/db.properties")) {
            properties.load(input);
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }

}
