package pl.sda.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
//TODO Student is an entity but in DB it is a table with name "student"
@Entity
@Table(name = "student")
public class Student implements Serializable {

    //TODO This should be Id, generated and unique column with name "student_id"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", unique = true)
    private int id;

    //TODO This should be not nullable column with name "student_name"
    @Column(name = "student_name", nullable = false)
    private String name;
    //TODO This should be not nullable column with name "student_age"
    @Column(name = "student_age", nullable = false)
    private int age;
}
