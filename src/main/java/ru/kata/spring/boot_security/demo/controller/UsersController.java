package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;


@Controller
public class UsersController {
//	private final UserServiceImp userServiceImp;
//
//	public UsersController(UserServiceImp userServiceImp) {
//		this.userServiceImp = userServiceImp;
//	}


//	@GetMapping("/user")
//	public String showUser(@AuthenticationPrincipal User user, @RequestParam(value = "name", required = false) String name, Model model) {
//		Set<String> roles = AuthorityUtils.authorityListToSet(user.getRoles());
//
//		if (roles.contains("ROLE_ADMIN") || user.getFirstName().equals(name)) {
//			model.addAttribute("userbyid", userServiceImp.getUserByName(name));
//			return "user/user";
//		} else {
//			return "user/notauth";
//		}
//	}

	@GetMapping(value = "/user")
	public String userPage(@AuthenticationPrincipal User userAuth, Model model) {
		model.addAttribute("usersAuth", userAuth);
		model.addAttribute("userIsAdmin", userAuth.getStringRoles().contains("ADMIN"));
		return "user";
	}
}