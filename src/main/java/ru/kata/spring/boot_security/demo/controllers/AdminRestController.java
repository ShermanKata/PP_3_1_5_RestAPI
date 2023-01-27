package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AdminRestController {

    private UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getListOfUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable ("id") int id) {
        return userService.getUserById(id);
    }
}
