package web.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext(name = "entityManagerFactory")
    private EntityManager entityManager;


    @Override
    public void saveUser(String name, String lastName, int age, String email) {
        User user = new User(name, lastName, age, email);
        entityManager.persist(user);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void removeUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public void update(User userUpdate) {
        User userToBeUpdate = entityManager.find(User.class, userUpdate.getId());
        userToBeUpdate.setName(userUpdate.getName());
        userToBeUpdate.setLastName(userUpdate.getLastName());
        userToBeUpdate.setAge(userUpdate.getAge());
        userToBeUpdate.setEmail(userUpdate.getEmail());
        entityManager.unwrap(Session.class).update(userToBeUpdate);
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "SELECT u FROM User u";
        Query query = entityManager.createQuery(hql, User.class);
        return query.getResultList();
    }
}
