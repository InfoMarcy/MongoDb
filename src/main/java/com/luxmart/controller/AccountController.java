package com.luxmart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luxmart.model.PrimaryAccount;
import com.luxmart.model.PrimaryTransaction;
import com.luxmart.model.SavingsAccount;
import com.luxmart.model.SavingsTransaction;
import com.luxmart.model.User;
import com.luxmart.service.AccountService;
import com.luxmart.service.TransactionService;
import com.luxmart.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	// connect to the userService
	@Autowired 
	UserService userService;
	
	// connect to the accountService
	@Autowired 
	AccountService accountService;
	
	// connect to the transaction service
	@Autowired 
	TransactionService transactionService;
	
	
	// get the primary account page
	@RequestMapping("/primaryAccount")
	public String getPrimaryAccount(Model model, Principal principal) {
		
		// get the the current logged in user
		User user = userService.findByEmail(principal.getName());
		
		//get a list of all the transactions
	List<PrimaryTransaction> primaryTransactionList =  transactionService.findPrimaryTransactionList(user.getUsername());
						
		// get the primary account from the user class
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		//add the primary account to the model
		model.addAttribute("primaryAccount", primaryAccount);
		model.addAttribute("primaryTransactionList", primaryTransactionList);
		// return the primaryAccount page
		return "primaryAccount";
	}
	
	// get the savings account page
	@RequestMapping("/savingsAccount")
	public String getSavingsAccount(Model model, Principal principal) {
		// get the the current logged in user
		User user = userService.findByEmail(principal.getName());
		

		//get a list of all the transactions
	List<SavingsTransaction> savingsTransactionList =  transactionService.findSavingsTransactionList(user.getUsername());
			
			
		// get the savingsAccount from the user class
		SavingsAccount savingsAccount = user.getSavingsAccount();
		//add the savingsAccount to the model
		model.addAttribute("savingsTransactionList", savingsTransactionList);
		model.addAttribute("savingsAccount", savingsAccount);
		
		// return the savingsAccount page
		return "savingsAccount";
	}
	
	// get the deposit page
	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
	public String getDeposit(Model model) {
		// add the required fields to the model to be used on the page
		model.addAttribute("accountType", "");
		model.addAttribute("amount", "");
		
		// return the deposit view
		return "deposit";
	}
	
	// save a  deposit 
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
		public String postDeposit(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
	     // add the deposit arguments to the account service		
		accountService.deposit(accountType, Double.parseDouble(amount), principal);
			// return the deposit view
			return "redirect:/userFront";
		}
	
	
	// get the deposit page
		@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
		public String getWithdraw(Model model) {
			// add the required fields to the model to be used on the page
			model.addAttribute("accountType", "");
			model.addAttribute("amount", "");
			
			// return the deposit view
			return "withdraw";
		}
		
		// save a  deposit 
		@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
			public String postWithdraw(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
		     // add the deposit arguments to the account service		
			accountService.withdraw(accountType, Double.parseDouble(amount), principal);
				// return the deposit view
				return "redirect:/userFront";
			}

}
