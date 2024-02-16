package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(String name, String lastName, int age, String email);

    public void add(User user);

    User getUserById(Long id);

    void removeUserById(Long id);

    public void update(Long id, User userUpdate);

    List<User> getAllUsers();


}
