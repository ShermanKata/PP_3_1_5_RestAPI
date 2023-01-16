package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

@Controller
public class AdminController {
    private UserServiceImp userServiceImp;

    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("users", userServiceImp.getListOfUsers());
        return "admin/adminPage";
    }


    @GetMapping("admin/createUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/newUser";
    }

    @PostMapping("admin/createNewUser")
    public String createUser(@ModelAttribute("user") User user) {
        System.out.println(1);
        userServiceImp.addUser(user);
        System.out.println(2);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userServiceImp.getUserById(id));
        return "admin/editUser";
    }

    @PatchMapping("/admin/editUser/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") int id) {
        userServiceImp.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userServiceImp.deleteUserById(userServiceImp.getUserById(id));
        return "redirect:/admin";
    }
}
