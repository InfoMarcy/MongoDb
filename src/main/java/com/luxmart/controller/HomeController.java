package com.luxmart.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luxmart.model.PrimaryAccount;
import com.luxmart.model.SavingsAccount;
import com.luxmart.model.User;
import com.luxmart.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	
	// get the home page
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		// redirect to index page
		return "redirect:/index";
	}

	// get the home page
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getIndex() {
		return "index";
	}
	
	// get the sign up page
		@RequestMapping(value = "/signup", method = RequestMethod.GET)
		public String getSignUpForm(Model model) {
			//create an user object
			User user = new User();
			// pass the user object to the view
			model.addAttribute("user", user);
			// return the view page
			return "signup";
		}
		
		
		
		// add new User
			@RequestMapping(value = "/signup", method = RequestMethod.POST)
			public String getSignUpForm(@Valid @ModelAttribute("user") User user, Model model) {
				
				// check if the user exist
				if(userService.checkUserExist(user.getUsername(), user.getPassword())) {
					
				// check if current email already exist
				if(userService.checkEmailExists(user.getEmail())) {	
					model.addAttribute("emailExists", true);
				}
				
				// check if current UserName already exist
				if(userService.checkUsernameExists(user.getUsername())) {
					model.addAttribute("usernameExists", true);
				}
				// return back the signup page
				return "signup";
				
				} else {
					// create new user
					userService.create(user);
                    // redirect to index page
					return "redirect:/";
				}
			}
			
// display the userFront page
@RequestMapping("/userFront")
public String getUserFront(Principal principal, Model model) {
	
System.out.println(principal.getName());
	// get the current logged in user
	User user = userService.findByEmail(principal.getName());
	// get the primary account
	PrimaryAccount primaryAccount = user.getPrimaryAccount();
	// get the savings account
	SavingsAccount savingsAccount = user.getSavingsAccount();
	
	// add the primary and savings account to the model and send it to the page
	model.addAttribute("primaryAccount", primaryAccount);
	model.addAttribute("savingsAccount", savingsAccount);
	
	
	// return the view
	return "userFront";
}
		
}
