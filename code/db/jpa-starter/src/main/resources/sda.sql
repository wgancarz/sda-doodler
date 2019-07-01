DROP schema if exists jpa;
create schema jpa;
use jpa;

DROP table if exists student;
create table student
(
  student_id int auto_increment
    primary key,
  student_age int not null,
  student_name varchar(255) not null
);

INSERT INTO student(student_name, student_age) VALUES ('student 1', 20), ('student 2', 40), ('student 3', 60), ('student 4', 22), ('student 5', 16);