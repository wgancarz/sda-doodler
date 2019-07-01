package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.domain.Person;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class PersonDaoHibernateImpl implements PersonDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDaoHibernateImpl.class);

    private static final SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        final Metadata metadata = new MetadataSources(registry)
                .buildMetadata();

        sessionFactory = metadata.buildSessionFactory();
    }

    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.openSession();
        List<Person> people = session.createQuery("FROM Person", Person.class).list();
        session.close();
        return people;
    }

    @Override
    public Optional<Person> findById(int id) {
        Session session = sessionFactory.openSession();
        Person person = session.get(Person.class, id);
        session.close();
        return Optional.ofNullable(person);
    }

    @Override
    public int create(Person person) {
        runInTransaction(session -> session.save(person));
        return person.getId();
    }

    @Override
    public int update(Person person) {
        runInTransaction(session -> session.update(person));
        return person.getId();
    }

    @Override
    public int createOrUpdate(Person person) {
        runInTransaction(session -> session.saveOrUpdate(person));
        return person.getId();
    }

    @Override
    public void delete(int id) {
        runInTransaction(session -> {
            Person person = session.get(Person.class, id);
            if (person != null) {
                session.delete(person);
            }
        });
    }

    @Override
    public void deleteAll() {
        runInTransaction(session -> {
            List<Person> people = session.createQuery("FROM Person", Person.class).list();
            people.forEach(session::delete);
        });
    }

    @Override
    public List<Person> queryByFirstNameOrLastName(String name) {
        Session session = sessionFactory.openSession();
        Query<Person> query = session.createQuery("FROM Person p WHERE p.firstName = :name OR p.lastName = :name", Person.class);
        query.setParameter("name", name);
        List<Person> people = query.list();
        session.close();
        return people;
    }

    @Override
    public Optional<Person> queryById(Integer id) {
        Session session = sessionFactory.openSession();
        Query<Person> query = session.createQuery("FROM Person WHERE id = :id", Person.class);
        query.setParameter("id", id);
        Person person = query.getSingleResult();
        session.close();
        return Optional.ofNullable(person);
    }

    @Override
    public List<Person> queryByCode(String code) {
        Session session = sessionFactory.openSession();
        Query<Person> query = session.createQuery("FROM Person WHERE address.code = :code", Person.class);
        query.setParameter("code", code);
        List<Person> people = query.list();
        session.close();
        return people;
    }

    @Override
    public Optional<Person> queryByFirstNameAndLastNameAndCode(String firstName, String lastName, String code) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
        Root<Person> root = criteria.from(Person.class);

        Predicate firstNamePredicate = builder.equal(root.get("firstName"), firstName);
        Predicate lastNamePredicate = builder.equal(root.get("lastName"), lastName);
        Predicate codePredicate = builder.equal(root.get("address").get("code"), code);

        criteria.select(root)
                .where(builder.and(firstNamePredicate, lastNamePredicate, codePredicate));

        Query<Person> query = session.createQuery(criteria);
        Person person = query.getSingleResult();
        session.close();
        return Optional.ofNullable(person);
    }

    @Override
    public List<String> queryLastNameSorted() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Person> root = criteria.from(Person.class);

        criteria.select(root.get("lastName"))
                .orderBy(builder.asc(root.get("lastName")));

        List<String> people = session.createQuery(criteria).list();
        session.close();
        return people;
    }

    @Override
    public List<String> queryCitiesSortedWithLimit(Integer limit) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Person> root = criteria.from(Person.class);
        Path<String> city = root.get("address").get("city");

        criteria.select(city)
                .orderBy(builder.asc(city));

        Query<String> query = session.createQuery(criteria);
        query.setMaxResults(limit);
        List<String> people = query.list();

        session.close();
        return people;
    }

    private void runInTransaction(Consumer<Session> action) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            action.accept(session);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.error(e.getMessage());
        }

        session.close();
    }
}
