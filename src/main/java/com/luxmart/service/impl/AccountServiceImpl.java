package com.luxmart.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxmart.model.PrimaryAccount;
import com.luxmart.model.PrimaryTransaction;
import com.luxmart.model.SavingsAccount;
import com.luxmart.model.SavingsTransaction;
import com.luxmart.model.User;
import com.luxmart.service.AccountService;
import com.luxmart.service.TransactionService;
import com.luxmart.service.UserService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	// base number for the accountNumber
	private static int nextAccountNumber = 11223145;

	@Autowired
	UserService userService;
	
	@Autowired
	TransactionService transactionService;
	
	// create a primary account for the new user
	@Override
	public PrimaryAccount createPrimaryAccount() {
		// initialize a new primaryAccount
		PrimaryAccount primaryAccount = new PrimaryAccount();
		// set the balance of the newly create account to 0
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        //assign an account number to the new account
        primaryAccount.setAccountNumber(accountGen());

        
        // return the model object
        return primaryAccount;
	}

	// create a savings account for the new user
	@Override
	public SavingsAccount createSavingsAccount() {
		// initialize a new savingsAccount
		SavingsAccount savingsAccount = new SavingsAccount();
		// set the balance of the newly create account to 0
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
      //assign an account number to the new account
        savingsAccount.setAccountNumber(accountGen());
        // return the model object
        return savingsAccount;
	}

	// increase the accountNumber
	  private int accountGen() {
	        return ++nextAccountNumber;
	    }
	  
	  
	  // do a deposit
	  public void deposit(String accountType, double amount, Principal principal) {
		 
		  // get the user for the current account
	        User user = userService.findByEmail(principal.getName());
	     
	        // check if the selected account is the primary account
	        if (accountType.equalsIgnoreCase("Primary")) {
	            PrimaryAccount primaryAccount = user.getPrimaryAccount();
	            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
	            userService.savePrimaryAccount(primaryAccount, principal);

	            
	            // get the current date to assign it to the transaction record
	            Date date = new Date();

	            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance());
	            transactionService.savePrimaryDepositTransaction(primaryTransaction, principal);
	            
	        } 
	     // check if the selected account is the savings account
	        else if (accountType.equalsIgnoreCase("Savings")) {
	            SavingsAccount savingsAccount = user.getSavingsAccount();
	            // sum the values  for the account balance
	            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
	            // save the changes to the account
	            userService.saveSavingsAccount(savingsAccount, principal);
//
	            Date date = new Date();
	            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance());
	            transactionService.saveSavingsDepositTransaction(savingsTransaction, principal);
	        }
	    }

	  public void withdraw(String accountType, double amount, Principal principal) {
		  // get the current user
	        User user = userService.findByEmail(principal.getName());

	        // check if the selected account is the primary account
	        if (accountType.equalsIgnoreCase("Primary")) {
	            PrimaryAccount primaryAccount = user.getPrimaryAccount();
	            // rest the values  for the account balance
	            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
	            // save the changes to the account
	            userService.savePrimaryAccount(primaryAccount, principal);

	            Date date = new Date();

	            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance());
	            transactionService.savePrimaryWithdrawTransaction(primaryTransaction, principal);
	        } else if (accountType.equalsIgnoreCase("Savings")) {
	            SavingsAccount savingsAccount = user.getSavingsAccount();
	            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
	            // save the changes to the account
	            userService.saveSavingsAccount(savingsAccount, principal);

	            Date date = new Date();
	            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance());
	            transactionService.saveSavingsWithdrawTransaction(savingsTransaction, principal);
	        }
	    }
}
