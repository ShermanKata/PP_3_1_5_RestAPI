package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String startPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, Principal principal, User user) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getListOfUsers());
        model.addAttribute("listRoles", roleService.getListOfRoles());
        model.addAttribute("newUser", new User());
        return "adminPage";
    }

    @PostMapping("/createNewUser")
    public String createUser(@ModelAttribute("newUser") User user) {
        List<Role> listRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            listRoles.add(roleService.getRoleByName(role.getName()));
        }
        user.setRoles(listRoles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/editUser")
    public String updateUser(User user) {
        List<Role> listRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            listRoles.add(roleService.getRoleByName(role.getName()));
        }
        user.setRoles(listRoles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
