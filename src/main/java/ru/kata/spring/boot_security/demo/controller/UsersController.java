package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
public class UsersController {

//	@GetMapping(value = "/user")
//	public String userPage(@AuthenticationPrincipal User userAuth, Model model) {
//		model.addAttribute("usersAuth", userAuth);
//		model.addAttribute("userIsAdmin", userAuth.getStringRoles().contains("ADMIN"));
//		return "user";
//	}
}