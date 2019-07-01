package pl.sda.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sda.configuration.JdbcConnectionManager;
import pl.sda.configuration.PropertyReader;
import pl.sda.configuration.TestUtil;
import pl.sda.domain.Address;
import pl.sda.domain.Person;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDaoHibernateImplTest {

    private PersonDao sut;
    private JdbcConnectionManager connectionManager;

    @BeforeEach
    void setUp() throws Exception {
        sut = new PersonDaoHibernateImpl();
        connectionManager = new JdbcConnectionManager(PropertyReader.loadConfiguration());
        TestUtil.setUpDatabase(connectionManager);
    }

    @AfterEach
    void tearDown() throws Exception {
        TestUtil.cleanUpDatabase(connectionManager);
    }

    @Test
    void findAll() {
        List<Person> people = sut.findAll();

        assertThat(people).hasSize(10);

        Set<String> uniqueCodes = people.stream().map(Person::getAddress).map(Address::getCode).collect(Collectors.toSet());
        assertThat(uniqueCodes).containsExactlyInAnyOrder("NH", "FL", "NY", "CA", "OR", "MS", "MI");
    }

    @Test
    void findById() {
        Person existingPerson = findOnePerson();
        Integer existingPersonId = existingPerson.getId();

        Optional<Person> foundPerson = sut.findById(existingPersonId);

        assertThat(foundPerson).isPresent();
        Person person = foundPerson.get();
        Address address = person.getAddress();

        assertThat(person.getFirstName()).isEqualTo("Felipe");
        assertThat(person.getLastName()).isEqualTo("Wexler");
        assertThat(address).isNotNull();
        assertThat(address.getStreet()).isEqualTo("Elliott Street");
        assertThat(address.getCity()).isEqualTo("Canaan");
        assertThat(address.getCode()).isEqualTo("NH");
    }

    @Test
    void create() {
        Address address = new Address("street", "city", "code");
        Person person = new Person(null, "name", "lastName", address);

        int createdId = sut.create(person);

        Optional<Person> foundPerson = sut.findById(createdId);
        assertThat(foundPerson).isPresent();
        Person createdPerson = foundPerson.get();
        Address createdAddress = createdPerson.getAddress();

        assertThat(createdPerson.getFirstName()).isEqualTo("name");
        assertThat(createdPerson.getLastName()).isEqualTo("lastName");
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getStreet()).isEqualTo("street");
        assertThat(createdAddress.getCity()).isEqualTo("city");
        assertThat(createdAddress.getCode()).isEqualTo("code");
    }

    @Test
    void update() {
        Person existingPerson = findOnePerson();
        existingPerson.setFirstName("UpdatedName");
        existingPerson.getAddress().setStreet("NewStreet");

        int updatedId = sut.update(existingPerson);

        Optional<Person> foundPerson = sut.findById(updatedId);
        assertThat(foundPerson).isPresent();
        Person updatedPerson = foundPerson.get();
        Address updatedAddress = updatedPerson.getAddress();

        assertThat(updatedPerson.getFirstName()).isEqualTo("UpdatedName");
        assertThat(updatedPerson.getLastName()).isEqualTo("Wexler");
        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getStreet()).isEqualTo("NewStreet");
        assertThat(updatedAddress.getCity()).isEqualTo("Canaan");
        assertThat(updatedAddress.getCode()).isEqualTo("NH");
    }

    @Test
    void createOrUpdate() {
        Address address = new Address("street", "city", "code");
        Person person = new Person(null, "name", "lastName", address);

        int createdId = sut.createOrUpdate(person);

        Optional<Person> foundPerson = sut.findById(createdId);
        assertThat(foundPerson).isPresent();
        Person createdPerson = foundPerson.get();
        Address createdAddress = createdPerson.getAddress();

        assertThat(createdPerson.getFirstName()).isEqualTo("name");
        assertThat(createdPerson.getLastName()).isEqualTo("lastName");
        assertThat(createdAddress).isNotNull();
        assertThat(createdAddress.getStreet()).isEqualTo("street");
        assertThat(createdAddress.getCity()).isEqualTo("city");
        assertThat(createdAddress.getCode()).isEqualTo("code");

        createdPerson.setFirstName("UpdatedName");
        createdPerson.getAddress().setStreet("NewStreet");

        int updatedId = sut.createOrUpdate(createdPerson);

        Optional<Person> foundUpdatedPerson = sut.findById(updatedId);
        assertThat(foundUpdatedPerson).isPresent();
        Person updatedPerson = foundUpdatedPerson.get();
        Address updatedAddress = updatedPerson.getAddress();

        assertThat(updatedPerson.getFirstName()).isEqualTo("UpdatedName");
        assertThat(updatedPerson.getLastName()).isEqualTo("lastName");
        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getStreet()).isEqualTo("NewStreet");
        assertThat(updatedAddress.getCity()).isEqualTo("city");
        assertThat(updatedAddress.getCode()).isEqualTo("code");
    }


    @Test
    void delete() {
        Person existingPerson = findOnePerson();
        Integer existingPersonId = existingPerson.getId();

        sut.delete(existingPersonId);

        Optional<Person> removedPerson = sut.findById(existingPersonId);
        assertThat(removedPerson).isNotPresent();
    }

    @Test
    void deleteAll() {
        sut.deleteAll();

        List<Person> people = sut.findAll();
        assertThat(people).isEmpty();
    }

    @Test
    void queryByFirstNameOrLastName() {
        List<Person> foundPeopleByFirstName = sut.queryByFirstNameOrLastName("Steven");

        assertThat(foundPeopleByFirstName).hasSize(1);
        assertThat(foundPeopleByFirstName.get(0).getFirstName()).isEqualTo("Steven");
        assertThat(foundPeopleByFirstName.get(0).getLastName()).isEqualTo("Singleton");

        List<Person> foundPeopleByLastName = sut.queryByFirstNameOrLastName("Singleton");

        assertThat(foundPeopleByLastName).hasSize(1);
        assertThat(foundPeopleByLastName.get(0).getFirstName()).isEqualTo("Steven");
        assertThat(foundPeopleByLastName.get(0).getLastName()).isEqualTo("Singleton");
    }

    @Test
    void queryById() {
        Person existingPerson = findOnePerson();
        Integer existingPersonId = existingPerson.getId();

        Optional<Person> foundPerson = sut.queryById(existingPersonId);

        assertThat(foundPerson).isPresent();
        Person person = foundPerson.get();
        Address address = person.getAddress();

        assertThat(person.getFirstName()).isEqualTo("Felipe");
        assertThat(person.getLastName()).isEqualTo("Wexler");
        assertThat(address).isNotNull();
        assertThat(address.getStreet()).isEqualTo("Elliott Street");
        assertThat(address.getCity()).isEqualTo("Canaan");
        assertThat(address.getCode()).isEqualTo("NH");
    }

    @Test
    void queryByCode() {
        String code = "FL";

        List<Person> people = sut.queryByCode(code);

        assertThat(people).hasSize(3);
        assertThat(people.stream().map(Person::getFirstName).collect(Collectors.toSet()))
                .containsExactlyInAnyOrder("Scott", "Evelyn", "Paul");
    }

    @Test
    void queryByFirstNameAndLastNameAndCode() {
        Optional<Person> foundPerson = sut.queryByFirstNameAndLastNameAndCode("Malvina", "Reed", "OR");

        assertThat(foundPerson).isPresent();
        Person person = foundPerson.get();
        Address address = person.getAddress();

        assertThat(person.getFirstName()).isEqualTo("Malvina");
        assertThat(person.getLastName()).isEqualTo("Reed");
        assertThat(address).isNotNull();
        assertThat(address.getStreet()).isEqualTo("Buena Vista Avenue");
        assertThat(address.getCity()).isEqualTo("Corvallis");
        assertThat(address.getCode()).isEqualTo("OR");
    }

    @Test
    void queryLastNameSorted() {
        List<String> sortedLastNames = sut.queryLastNameSorted();

        assertThat(sortedLastNames).hasSize(10);
        assertThat(sortedLastNames).containsExactly("Gates", "Harmon", "Lemen", "Lopez", "Mills", "Reed", "Singleton", "Sowers", "Stanley", "Wexler");
    }

    @Test
    void queryCitiesSortedWithLimit() {
        List<String> cities = sut.queryCitiesSortedWithLimit(3);

        assertThat(cities).hasSize(3);
        assertThat(cities).containsExactly("Aumsville", "Buffalo", "Canaan");
    }

    private Person findOnePerson() {
        final Optional<Person> existingPerson = sut.findAll()
                .stream()
                .filter(person -> person.getFirstName().equals("Felipe"))
                .findFirst();

        assertThat(existingPerson).isPresent();
        return existingPerson.get();
    }
}
