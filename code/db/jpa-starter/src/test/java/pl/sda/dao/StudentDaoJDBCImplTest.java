package pl.sda.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pl.sda.TestUtil;
import pl.sda.configuration.JdbcConnectionManager;
import pl.sda.configuration.PropertyReader;

class StudentDaoJDBCImplTest extends StudentDaoTest {

    private JdbcConnectionManager jdbcConnectionManager;

    @BeforeEach
    void setUp() throws Exception {
        jdbcConnectionManager = new JdbcConnectionManager(PropertyReader.loadConfiguration());
        sut = new StudentDaoJDBCImpl(jdbcConnectionManager);
        TestUtil.setUpDatabase(jdbcConnectionManager, "sda.sql");
    }

    @AfterEach
    void tearDown() throws Exception {
        TestUtil.cleanUpDatabase(jdbcConnectionManager);
    }
}
