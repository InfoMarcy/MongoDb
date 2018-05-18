package com.luxmart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luxmart.model.User;
import com.luxmart.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	// get the user profile page
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Principal principal, Model model) {
		// get the current logged in user
		User user = userService.findByEmail(principal.getName());

		// add the user to the model
		model.addAttribute("user", user);

		// return the profile page
		return "profile";
	}

	// save the user profile changes
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String profilePost(@ModelAttribute("user") User newUser, Model model, Principal principal) {
		// get the current logged in user
		User user = userService.findByEmail(principal.getName());
		user.setUsername(newUser.getUsername());
		user.setFirstName(newUser.getFirstName());
		user.setMiddleName(newUser.getMiddleName());
		user.setLastName(newUser.getLastName());
		user.setMotherLastName(newUser.getMotherLastName());
		user.setEmail(newUser.getEmail());
		user.setPhone(newUser.getPhone());
        user.setUsername(newUser.getUsername());
		// add the user to the model
		model.addAttribute("user", user);

		// save the user
		userService.save(user);

		// return the view
		return "profile";
	}
}
