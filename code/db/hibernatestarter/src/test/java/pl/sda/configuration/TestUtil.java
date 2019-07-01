package pl.sda.configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class TestUtil {
    public static void setUpDatabase(JdbcConnectionManager jdbcConnectionManager) throws SQLException {
        executeOnDatabase(jdbcConnectionManager, "sda-setup.sql");
    }

    public static void cleanUpDatabase(JdbcConnectionManager jdbcConnectionManager) throws SQLException {
        executeOnDatabase(jdbcConnectionManager, "sda-cleanup.sql");
    }

    private static void executeOnDatabase(JdbcConnectionManager jdbcConnectionManager, String sql) throws SQLException {
        InputStream inputStream = TestUtil.class.getClassLoader().getResourceAsStream(sql);
        String sqlContent = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));

        try (Connection conn = jdbcConnectionManager.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sqlContent);
        }
    }
}
