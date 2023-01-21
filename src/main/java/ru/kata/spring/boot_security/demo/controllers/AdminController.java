package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserServiceImp userServiceImp;
    private RoleServiceImp roleServiceImp;

    public AdminController(UserServiceImp userServiceImp, RoleServiceImp roleServiceImp) {
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("user", userServiceImp.getUserByUsername(principal.getName()));
        model.addAttribute("users", userServiceImp.getListOfUsers());
        return "admin/adminPage";
    }

    @GetMapping("/createUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleServiceImp.getListOfRoles());
        return "admin/newUser";
    }

    @PostMapping("/createNewUser")
    public String createUser(@ModelAttribute("user") User user) {
        List<Role> listRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            listRoles.add(roleServiceImp.getRoleByName(role.getName()));
        }
        user.setRoles(listRoles);
        userServiceImp.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userServiceImp.getUserById(id));
        model.addAttribute("listRoles", roleServiceImp.getListOfRoles());
        return "admin/editUser";
    }

    @PatchMapping("/editUser/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") int id) {
        List<Role> listRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            listRoles.add(roleServiceImp.getRoleByName(role.getName()));
        }
        user.setRoles(listRoles);
        userServiceImp.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userServiceImp.deleteUserById(userServiceImp.getUserById(id));
        return "redirect:/admin";
    }
}
