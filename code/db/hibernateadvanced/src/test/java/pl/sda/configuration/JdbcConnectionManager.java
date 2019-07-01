package pl.sda.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionManager {
    private final PropertyReader propertyReader;

    public JdbcConnectionManager(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    public Connection getConnection() throws SQLException {
        String url = propertyReader.getUrl();
        String username = propertyReader.getUsername();
        String password = propertyReader.getPassword();
        return DriverManager.getConnection(url, username, password);
    }
}
