package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder; // Поместил сюда, т.к. иначе была ошибка: The dependencies of some of the beans in the application context form a cycle

	public UsersRestController(UserServiceImp userServiceImp, PasswordEncoder passwordEncoder) {
		this.userServiceImp = userServiceImp;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping(value = "/users")
	public List<User> showAllUsers(Model model, @AuthenticationPrincipal User userAuth) {
		List<User> allUsers = userServiceImp.listUsers();
		model.addAttribute("usersAuth", userAuth);
		return allUsers;
	}

	@GetMapping(value = "/users/{id}")
	public User getUserById(@PathVariable("id") Long id) {
		User userById = userServiceImp.getUserById(id);
		return userById;
	}

	@PostMapping("/users")
	public User addNewUser(@RequestBody User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		userServiceImp.addUser(newUser);
		return newUser;
	}

	@PutMapping("/users")
	public User updateUser(@RequestBody User updatedUser) {
		if (updatedUser.getRole() != null && updatedUser.getRole().equals("ADMIN")) {
			updatedUser.addRoleForm(new Role(1L, "ROLE_ADMIN"));
			updatedUser.setRole("ADMIN");
		} else if (updatedUser.getRole() != null && updatedUser.getRole().equals("USER")) {
			updatedUser.addRoleForm(new Role(2L, "ROLE_USER"));
			updatedUser.setRole("USER");
		}
		if (updatedUser.getRole() == null && updatedUser.getStringRoles().equals("ADMIN")) {
			updatedUser.addRoleForm(new Role(1L, "ROLE_ADMIN"));
			updatedUser.setRole("ADMIN");
		} else if (updatedUser.getRole() == null && updatedUser.getStringRoles().equals("USER")) {
			updatedUser.addRoleForm(new Role(2L, "ROLE_USER"));
			updatedUser.setRole("USER");
		}
		String oldPassword = updatedUser.getPassword();
		// Условие - костыль, чтобы не шифровать уже зашифрованный пароль (который не менялся в форме)
		if (!oldPassword.startsWith("$2a$10$")) {
			updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
		}
		userServiceImp.updateUser(updatedUser.getId(), updatedUser);
		return updatedUser;
	}

	@DeleteMapping(value = "/users/{id}")
	public String deleteUserById(@PathVariable("id") Long id) {
		System.out.println(id);
		userServiceImp.deleteUser(id);
		return "User with id = " + id + " was deleted !";
	}
}