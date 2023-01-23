package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    void deleteUserById(int id);

    void updateUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    List<User> getListOfUsers();
}
