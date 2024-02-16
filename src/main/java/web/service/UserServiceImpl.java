package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void saveUser(String name, String lastName, int age, String email) {
        userDao.saveUser(name, lastName, age, email);
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public User removeUserById(long id) {
        userDao.removeUserById(id);
        return null;
    }

    @Transactional
    @Override
    public void update(long id, User userUpdate) {
        userDao.update(id, userUpdate);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


}
