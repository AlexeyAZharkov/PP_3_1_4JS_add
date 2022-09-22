package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersRestController {

	private final UserServiceImp userServiceImp;

	public UsersRestController(UserServiceImp userServiceImp) {
		this.userServiceImp = userServiceImp;
	}

	@GetMapping(value = "/users")
	public List<User> showAllUsers() {
		List<User> allUsers = userServiceImp.listUsers();
		return allUsers;
	}

	@GetMapping(value = "/users/{id}")
	public User getUserById(@PathVariable("id") Long id) {
		User userById = userServiceImp.getUserById(id);
		System.out.println(userById);
		return userById;
	}
}