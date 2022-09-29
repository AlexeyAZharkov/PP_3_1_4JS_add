package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;


@Controller
@RequestMapping("")
public class AdminController {
    private final UserServiceImp userServiceImp;

    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping(value = "/admin")
    public String userPage(@ModelAttribute("user") User user, @AuthenticationPrincipal User userAuth, Model model) {
        model.addAttribute("allUsers", userServiceImp.listUsers());
        model.addAttribute("usersAuth", userAuth);
        model.addAttribute("userIsAdmin", userAuth.getStringRoles().contains("ADMIN"));
//        System.out.println("из админ  -- " + userAuth);
        return "admin";
    }

//    @PostMapping("/new")
//    public String create(@ModelAttribute("user") User user) {
//        userServiceImp.addUser(user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userServiceImp.deleteUser(id);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/edit")
//    public String editUser(@RequestParam(value = "id", required = false) Long id, Model model) {
//        model.addAttribute("user", userServiceImp.getUserById(id));
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/updateuser/{id}")
//    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
//        if (user.getRole() != null && user.getRole().equals("ADMIN")) {
//            user.addRoleForm(new Role(1L, "ROLE_ADMIN"));
//        } else if (user.getRole() != null && user.getRole().equals("USER")) {
//            user.addRoleForm(new Role(2L, "ROLE_USER"));
//        }
//        if (user.getRole() == null && userServiceImp.getUserById(id).getStringRoles().equals("ADMIN")) {
//            user.addRoleForm(new Role(1L, "ROLE_ADMIN"));
//        } else if (user.getRole() == null && userServiceImp.getUserById(id).getStringRoles().equals("USER")) {
//            user.addRoleForm(new Role(2L, "ROLE_USER"));
//        }
//        userServiceImp.updateUser(id, user);
//        return "redirect:/admin";
//    }
}
