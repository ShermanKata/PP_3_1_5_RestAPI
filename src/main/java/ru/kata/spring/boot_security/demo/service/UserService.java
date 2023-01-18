package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    User getUserById(int id);
    List<User> getListOfUsers();
    void addUser(User user);
    void deleteUserById(User user);
    void updateUser(User user);
}
