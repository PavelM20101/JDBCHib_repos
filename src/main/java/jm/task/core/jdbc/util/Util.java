package jm.task.core.jdbc.util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Properties PROPERTIES = new Properties();
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    static {
        loadDriver();
        loadProperties();
    }

    public Util(){
    }
    private static void loadDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(PROPERTIES.getProperty(URL_KEY), PROPERTIES.getProperty(USERNAME_KEY), PROPERTIES.getProperty(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadProperties(){
        try(InputStream inputStream = Files.newInputStream(Paths.get(Util.class.getResource("/application.properties").toURI()))){
            PROPERTIES.load(inputStream);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
