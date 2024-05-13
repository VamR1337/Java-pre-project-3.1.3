package ru.kata.spring.boot_security.demo.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servis.RoleService;
import ru.kata.spring.boot_security.demo.servis.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

	private final RoleService roleService;
	private final UserService userService;

	@Autowired
	public AdminController(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}

	@GetMapping()
	public String showAllUsers(@ModelAttribute ("user") User user, Principal principal, Model model) {
		User authenticatedUser = userService.findByUsername(principal.getName());
		model.addAttribute ("authenticatedUser", authenticatedUser);
		model.addAttribute ("roleOfAuthenticatedUser", authenticatedUser.getRoles());
		model.addAttribute("users", userService.findAll());
		model.addAttribute( "AllRoles", roleService.findAll());
		return "admin-page";
	}

	@GetMapping("/user-profile/{id}")
	public String findUser(@PathVariable("id") Long id, Model model) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("AllRoles", user.getRoles());
		return "user-page";
	}

	@GetMapping("/edit/{id}")
	public String editUser(Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userService.findUserById(id));
		model.addAttribute("AllRoles", roleService.findAll());
		return "redirect:/admin";
	}

	@PatchMapping("/update/{id}")
	public String updateUser(@ModelAttribute("user") User updateUser, @PathVariable("id") Long id) {
		userService.updateUser(updateUser, id);
		return "redirect:/admin";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

	@GetMapping("/new")
	public String form_for_create_user(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("AllRoles", roleService.findAll());
		return "redirect:/admin";
	}

	@PostMapping("/create")
	public String saveNewUser(@ModelAttribute("user") User user) {
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}
}
