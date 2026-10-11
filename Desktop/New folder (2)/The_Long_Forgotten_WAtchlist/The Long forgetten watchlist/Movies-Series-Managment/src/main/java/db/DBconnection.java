package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnection {

    public static Connection getConnection() throws SQLException {

        Properties properties = new Properties();

        try (FileInputStream file = new FileInputStream("db.personal")) {

            properties.load(file);

        } catch (IOException e) {
            throw new SQLException(
                "Database configuration file 'db.personal' not found.",
                e
            );
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        if (url == null || username == null || password == null) {
            throw new SQLException(
                "Database URL, username, or password is missing in db.personal."
            );
        }

        return DriverManager.getConnection(url, username, password);
    }
}