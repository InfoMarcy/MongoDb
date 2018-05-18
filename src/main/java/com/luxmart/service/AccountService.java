package com.luxmart.service;

import java.security.Principal;

import com.luxmart.model.PrimaryAccount;
import com.luxmart.model.SavingsAccount;


public interface AccountService {
	PrimaryAccount  createPrimaryAccount();
	SavingsAccount createSavingsAccount();
	
	void deposit(String accountType, double amount, Principal principal);
	void withdraw(String accountType, double amount, Principal principal);

}
