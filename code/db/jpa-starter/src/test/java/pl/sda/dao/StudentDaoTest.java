package pl.sda.dao;

import org.junit.jupiter.api.Test;
import pl.sda.domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

abstract class StudentDaoTest {

    protected StudentDao sut;

    @Test
    void findAll() {
        List<Student> students = sut.findAll();

        assertThat(students).hasSize(5);

        Set<Integer> allAges = students.stream().map(Student::getAge).collect(Collectors.toSet());
        assertThat(allAges).containsExactlyInAnyOrder(20, 40, 60, 22, 16);
    }

    @Test
    void findById() {
        Student existingStudent = findOneOfStudents();
        int id = existingStudent.getId();

        Optional<Student> student = sut.findById(id);

        assertThat(student).isPresent();
        assertThat(student.get().getAge()).isEqualTo(22);
        assertThat(student.get().getName()).isEqualTo("student 4");
    }

    @Test
    void create() {
        Student student = new Student();
        student.setName("SDA Student");
        student.setAge(4);

        int id = sut.create(student);

        Optional<Student> createdStudent = sut.findById(id);
        assertThat(createdStudent).isPresent();
        assertThat(createdStudent.get().getAge()).isEqualTo(4);
        assertThat(createdStudent.get().getName()).isEqualTo("SDA Student");
    }

    @Test
    void update() {
        Student existingStudent = findOneOfStudents();
        existingStudent.setName("some new name");

        int updatedStudentId = sut.update(existingStudent);

        Optional<Student> updatedStudent = sut.findById(updatedStudentId);
        assertThat(updatedStudent).isPresent();
        assertThat(updatedStudent.get().getAge()).isEqualTo(22);
        assertThat(updatedStudent.get().getName()).isEqualTo("some new name");
    }

    @Test
    void delete() {
        Student existingStudent = findOneOfStudents();
        int id = existingStudent.getId();

        sut.delete(id);

        Optional<Student> removedStudent = sut.findById(id);
        assertThat(removedStudent).isNotPresent();
    }

    @Test
    void deleteAll() {
        sut.deleteAll();

        List<Student> students = sut.findAll();
        assertThat(students).isEmpty();
    }

    private Student findOneOfStudents() {
        Optional<Student> existingStudent = sut.findAll()
                .stream()
                .filter(student -> student.getAge() == 22)
                .findFirst();
        assertThat(existingStudent).isPresent();
        return existingStudent.get();
    }
}
