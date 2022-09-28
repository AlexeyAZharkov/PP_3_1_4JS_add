package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

@Controller
public class AuthController {
    private final UserServiceImp userServiceImp;

    public AuthController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

//    @GetMapping("/")
//    public String loginPage(@ModelAttribute("user") User user, @AuthenticationPrincipal User userAuth, Model model) {
//        model.addAttribute("allUsers", userServiceImp.listUsers());
//        model.addAttribute("usersAuth", userAuth);
//        model.addAttribute("userIsAdmin", userAuth.getStringRoles().contains("ADMIN"));
//    return "/admin";
//    }

    @GetMapping("/")
    public String loginPage() {
        return "/admin";
    }

//    @GetMapping("/")
//    public String loginPage() {
//        return "/login";
//    }
}
