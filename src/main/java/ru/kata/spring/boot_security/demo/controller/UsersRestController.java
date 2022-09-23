package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.model.Role;
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
		return userById;
	}

	@PostMapping("/users")
	public User addNewUser(@RequestBody User newUser) {
		userServiceImp.addUser(newUser);
		return newUser;
	}

	@PutMapping("/users")
	public User updateUser(@RequestBody User updatedUser) {
		if (updatedUser.getRole().equals("ADMIN")) {
			updatedUser.addRoleForm(new Role(1L, "ROLE_ADMIN"));
		} else if (updatedUser.getRole().equals("USER")) {
			updatedUser.addRoleForm(new Role(2L, "ROLE_USER"));
		}
		userServiceImp.updateUser(updatedUser.getId(), updatedUser);
		return updatedUser;
	}

	@DeleteMapping(value = "/users/{id}")
	public String deleteUserById(@PathVariable("id") Long id) {
		userServiceImp.deleteUser(id);
		return "User with id = " + id + " was deleted !";
	}
}