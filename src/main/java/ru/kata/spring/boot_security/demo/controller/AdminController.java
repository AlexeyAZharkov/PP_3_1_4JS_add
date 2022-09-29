package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "admin";
    }
}
