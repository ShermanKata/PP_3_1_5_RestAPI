package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
public class UserController {

    private UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/")
    public String getStart() {
        return "user/start";
    }

    @GetMapping("/user")
    public String getUserInfo(Model model, Principal principal) {
        model.addAttribute("user", userServiceImp.getUserByUsername(principal.getName()));
        return "user/user";
    }
}
