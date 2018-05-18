package com.luxmart.service;

import java.security.Principal;

import com.luxmart.model.PrimaryAccount;
import com.luxmart.model.PrimaryTransaction;
import com.luxmart.model.Recipient;
import com.luxmart.model.SavingsAccount;
import com.luxmart.model.SavingsTransaction;
import com.luxmart.model.User;

public interface UserService {
	
	User findByUsername(String username);
	User findByEmail(String email);
	boolean checkUserExist(String username, String email);
	boolean checkUsernameExists(String username);
	boolean checkEmailExists(String email);

	User create(User user);
	
	void savePrimaryAccount(PrimaryAccount primaryAccount, Principal principal);
	void saveSavingsAccount(SavingsAccount savingsAccount, Principal principal);
	
	void savePrimaryTransaction(PrimaryTransaction primaryTransaction, Principal principal);
	void saveSavingsTransaction(SavingsTransaction savingsTransaction, Principal principal);
	
	// recipients
	Recipient saveRecipient(Recipient recipient, Principal principal);
	Recipient findRecipientByName(String recipientName, Principal principal);
	void deleteRecipientByName(String recipientName, Principal principal);

	void AddTransferToPrimaryAccount(PrimaryAccount primaryAccount, User recipientUser, Principal principal);
	void AddTransferToSavingsAccount(SavingsAccount savingsAccount, User recipientUser, Principal principal);
	void save(User user);
	Iterable<User> findAll();
	void enableUser(String username);
	void disableUser(String username);
	
	

}
