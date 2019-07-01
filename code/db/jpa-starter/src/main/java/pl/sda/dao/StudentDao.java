package pl.sda.dao;

import pl.sda.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    List<Student> findAll();

    Optional<Student> findById(int id);

    int create(Student student);

    int update(Student student);

    void delete(int id);

    void deleteAll();
}
