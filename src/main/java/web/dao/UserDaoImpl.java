package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastName VARCHAR(255) NOT NULL,"
                + "age INT NOT NULL,"
                + "email VARCHAR(255) NOT NULL"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3";
        entityManager.createNativeQuery(createTableSQL).executeUpdate();
    }

    @Transactional
    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        entityManager.createNativeQuery(dropTableSQL).executeUpdate();
    }

    @Transactional
    @Override
    public void saveUser(String name, String lastName, int age, String email) {
        User user = new User(name, lastName, age, email);
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        String hql = "SELECT u FROM User u";
        Query query = entityManager.createQuery(hql, User.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void cleanUsersTable() {
        String deleteAllRowsSQL = "DELETE FROM users";
        entityManager.createNativeQuery(deleteAllRowsSQL).executeUpdate();
    }
}
