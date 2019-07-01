package pl.sda.configuration;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private final String url;
    private final String username;
    private final String password;

    private PropertyReader(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static PropertyReader loadConfiguration() throws IOException {
        Properties properties = new Properties();
        properties.load(PropertyReader.class.getClassLoader().getResourceAsStream("db.config.properties"));
        return new PropertyReader(properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
    }
}
