package pl.sda.dao;

import pl.sda.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    List<Person> findAll();

    Optional<Person> findById(int id);

    int create(Person person);

    int update(Person person);

    int createOrUpdate(Person person);

    void delete(int id);

    void deleteAll();

    List<Person> queryByFirstNameOrLastName(String name);

    Optional<Person> queryById(Integer id);

    List<Person> queryByCode(String code);

    Optional<Person> queryByFirstNameAndLastNameAndCode(String firstName, String lastName, String code);

    List<String> queryLastNameSorted();

    List<String> queryCitiesSortedWithLimit(Integer limit);
}
