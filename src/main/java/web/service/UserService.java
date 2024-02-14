package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, int age, String email);

    public void add(User user);

    User getUserById(long id);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}