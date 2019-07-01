package pl.sda.dao;

import lombok.extern.java.Log;
import pl.sda.configuration.JdbcConnectionManager;
import pl.sda.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class StudentDaoJDBCImpl implements StudentDao {

    private static final String QUERY_ALL = "SELECT student_id, student_age, student_name FROM student";
    private static final String QUERY_BY_ID = "SELECT student_id, student_age, student_name FROM student WHERE student_id = ?";
    private static final String INSERT = "INSERT INTO student(student_age, student_name) VALUES(?,?)";
    private static final String UPDATE = "UPDATE student SET student_age = ?, student_name = ? WHERE student_id = ?";
    private static final String DELETE = "DELETE FROM student WHERE student_id = ?";
    private static final String DELETE_ALL = "DELETE FROM student";

    private final JdbcConnectionManager jdbcConnectionManager;

    public StudentDaoJDBCImpl(final JdbcConnectionManager jdbcConnectionManager) {
        this.jdbcConnectionManager = jdbcConnectionManager;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        try (Connection conn = jdbcConnectionManager.getConnection();
             Statement stm = conn.createStatement()) {

            ResultSet resultSet = stm.executeQuery(QUERY_ALL);
            while (resultSet.next()) {
                Optional<Student> student = mapFromResultSet(resultSet);
                student.ifPresent(students::add);
            }

        } catch (SQLException e) {
            log.warning(e.getMessage());
        }

        return students;
    }

    @Override
    public Optional<Student> findById(int id) {
        try (Connection conn = jdbcConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY_BY_ID)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapFromResultSet(rs);
            }
        } catch (SQLException ex) {
            log.warning(ex.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public int create(Student student) {
        try (Connection conn = jdbcConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, student.getAge());
            ps.setString(2, student.getName());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException ex) {
            log.warning(ex.getMessage());
        }

        throw new IllegalStateException("Unable to create student");
    }

    @Override
    public int update(Student student) {
        try (Connection conn = jdbcConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            ps.setInt(1, student.getAge());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 1) {
                return student.getId();
            }
        } catch (SQLException ex) {
            log.warning(ex.getMessage());
        }

        throw new IllegalStateException("Unable to update student");
    }

    @Override
    public void delete(int id) {
        try (Connection conn = jdbcConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            log.warning(ex.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try (Connection conn = jdbcConnectionManager.getConnection();
             Statement ps = conn.createStatement()) {

            ps.executeUpdate(DELETE_ALL);
        } catch (SQLException ex) {
            log.warning(ex.getMessage());
        }
    }

    private Optional<Student> mapFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("student_id");
        int age = rs.getInt("student_age");
        String name = rs.getString("student_name");

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        return Optional.of(student);
    }
}
