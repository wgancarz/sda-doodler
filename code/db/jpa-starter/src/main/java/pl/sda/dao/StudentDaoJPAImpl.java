package pl.sda.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class StudentDaoJPAImpl implements StudentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDaoJPAImpl.class);

    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("jpa-dao");//TODO Create EntityManagerFactory using Persistence. This enable you to create EntityManagers.
    }

    //TODO remember to close all entityManagers

    @Override
    public List<Student> findAll() {
        //TODO EntityManagerFactory creates entityManager
        EntityManager entityManager = emf.createEntityManager();
        //TODO EntityManager enable us to create JPQL queries and execute it
        List<Student> students = entityManager.createQuery("from Student", Student.class).getResultList();
        entityManager.close();
        return students;
    }

    @Override
    public Optional<Student> findById(int id) {
        EntityManager entityManager = emf.createEntityManager();
        //TODO find method enable us to find item by it's ID, we can also to it with query (from Student where id = :id)
        Student student = entityManager.find(Student.class, id);
        entityManager.close();
        return Optional.ofNullable(student);
    }

    @Override
    public int create(Student student) {
        //TODO persist creates new item
        runInTransaction(entityManager -> entityManager.persist(student));
        return student.getId();
    }

    @Override
    public int update(Student student) {
        //TODO merge updates existing item
        runInTransaction(entityManager -> entityManager.merge(student));
        return student.getId();
    }

    @Override
    public void delete(int id) {
        runInTransaction(entityManager -> {
            Student student = entityManager.find(Student.class, id);
            //TODO remove marks item to be removed
            entityManager.remove(student);
        });
    }

    @Override
    public void deleteAll() {
        runInTransaction(entityManager -> {
            List<Student> students = entityManager.createQuery("FROM Student", Student.class).getResultList();
            //TODO we remove item one by one, it can also be done with query (All in one)
            students.forEach(entityManager::remove);
        });
    }

    private void runInTransaction(Consumer<EntityManager> action) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            //TODO We are going to change some data in DB so we open transaction
            transaction.begin();

            action.accept(entityManager);

            //TODO Transaction has to be committed
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.error(e.getMessage());
        }

        entityManager.close();
    }
}
