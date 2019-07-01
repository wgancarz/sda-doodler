package pl.sda.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pl.sda.TestUtil;
import pl.sda.configuration.JdbcConnectionManager;
import pl.sda.configuration.PropertyReader;

class StudentDaoJPAImplTest extends StudentDaoTest {

    private JdbcConnectionManager jdbcConnectionManager;

    @BeforeEach
    void setUp() throws Exception {
        sut = new StudentDaoJPAImpl();
        jdbcConnectionManager = new JdbcConnectionManager(PropertyReader.loadConfiguration());
        TestUtil.setUpDatabase(jdbcConnectionManager, "sda-jpa.sql");
    }

    @AfterEach
    void tearDown() throws Exception {
        TestUtil.cleanUpDatabase(jdbcConnectionManager);
    }
}
