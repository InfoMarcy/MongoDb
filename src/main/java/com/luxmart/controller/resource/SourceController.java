package com.luxmart.controller.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luxmart.model.PrimaryTransaction;
import com.luxmart.model.SavingsTransaction;
import com.luxmart.model.User;
import com.luxmart.service.TransactionService;
import com.luxmart.service.UserService;

@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('ADMIN')") // only ADMIN can see this content
public class SourceController {

	@Autowired
	UserService userService;
	
	@Autowired
	TransactionService transactionService;
	
	// return a list of users from mongoDb
	@RequestMapping(value="users", method = RequestMethod.GET)
	public Iterable<User> getUsers() {		
		Iterable<User> users= userService.findAll();
		return users;
	}
	

    @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
    public List<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username) {
        return transactionService.findPrimaryTransactionList(username);
    }

    @RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
    public List<SavingsTransaction> getSavingsTransactionList(@RequestParam("username") String username) {
        return transactionService.findSavingsTransactionList(username);
    }

    @RequestMapping("/user/{username}/enable")
    public void enableUser(@PathVariable("username") String username) {
        userService.enableUser(username);
    }

    @RequestMapping("/user/{username}/disable")
    public void diableUser(@PathVariable("username") String username) {
        userService.disableUser(username);
    }
	
	
	
			
}
