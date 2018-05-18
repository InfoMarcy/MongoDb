package com.luxmart.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxmart.model.PrimaryAccount;
import com.luxmart.model.PrimaryTransaction;
import com.luxmart.model.Recipient;
import com.luxmart.model.SavingsAccount;
import com.luxmart.model.SavingsTransaction;
import com.luxmart.model.User;
import com.luxmart.service.TransactionService;
import com.luxmart.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {


	@Autowired
	private UserService userService;

	
	
	

	public List<PrimaryTransaction> findPrimaryTransactionList(String username){
        User user = userService.findByUsername(username);
        List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();

        return primaryTransactionList;
    }

    public List<SavingsTransaction> findSavingsTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();

        return savingsTransactionList;
    }

    public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction, Principal principal) {
    	userService.savePrimaryTransaction(primaryTransaction, principal);
    }

    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction, Principal principal) {
    	userService.saveSavingsTransaction(savingsTransaction, principal);
    }
    
    public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction, Principal principal) {
    	userService.savePrimaryTransaction(primaryTransaction, principal);
    }

    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction, Principal principal) {
    	userService.saveSavingsTransaction(savingsTransaction, principal);
    }
    
    // transfer balance between savings and primary account
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount, Principal principal) throws Exception {
       // check if the transfer is from primary to savings account
    	if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
        	
        	// Subtract the amount from primary account
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            // add the amount to savings
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            
            // save the primaryAccount changes
            userService.savePrimaryAccount(primaryAccount, principal);
            // save the savingsAccount changes
            userService.saveSavingsAccount(savingsAccount, principal);
          
           // get the date for the new transaction
            Date date = new Date();
            // set the values for the primary transactionList
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance());
            // save the primaryTransaction and add it to the list
            userService.savePrimaryTransaction(primaryTransaction, principal);
            
            
            
            // set the values for the savings transactionList
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to "+transferTo+" from "+transferFrom, "Account", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance());
            //save the savings transactions
            userService.saveSavingsTransaction(savingsTransaction, principal);
        } 
    	// check if the transfer is from savings  to primary account
    	else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
    		//   add the transfer amount to primary
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
       //   Subtract the transfer amount to savings
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
         // save the primaryAccount changes
            userService.savePrimaryAccount(primaryAccount, principal);
         // save the savingsAccount changes
            userService.saveSavingsAccount(savingsAccount, principal);

            // get the date for the new transaction
            Date date = new Date();
            // set the values for the savings transactionList
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance());
            //save the savings transactions
            userService.saveSavingsTransaction(savingsTransaction, principal);
            
            // set the values for the savings transactionList
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to "+transferTo+" from "+transferFrom, "Account", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance());
            //save the savings transactions
            userService.savePrimaryTransaction(primaryTransaction, principal);
        } else { // otherwise throw an error
            throw new Exception("Invalid Transfer");
        }
    }
    
    // find the list of recipients
    public List<Recipient> findRecipientList(Principal principal) {
    	// get the current user
    	User user = userService.findByEmail(principal.getName());   	      
        
    	// get the list of recipients
        List<Recipient> recipientList = user.getRecipientList();

        
        // return the list of recipients
        return recipientList;
    }

    public Recipient saveRecipient(Recipient recipient, Principal principal) {
        return userService.saveRecipient(recipient, principal);
    }

    public Recipient findRecipientByName(String recipientName, Principal principal) {
        return userService.findRecipientByName(recipientName, principal);
    }

    public void deleteRecipientByName(String recipientName, Principal principal) {
    	userService.deleteRecipientByName(recipientName, principal);
    }
    
    
    // transfer balance from one person account to another
    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount, Principal principal) {
       // check if it is the primary account
    	if (accountType.equalsIgnoreCase("Primary")) {
    		// Subtract the balance from the primary account
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount))); 
            // save the transaction
            userService.savePrimaryAccount(primaryAccount, principal);
           
            // get the date for the transaction
            Date date = new Date();

            // add the values for the primary transaction record
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance());
            // save the primary transaction record
            userService.savePrimaryTransaction(primaryTransaction, principal);
        } 
    	// check if it is the savingsAccount
    	else if (accountType.equalsIgnoreCase("Savings")) {
    		// Subtract the balance from the savingsAccount
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            // save the transaction
             userService.saveSavingsAccount(savingsAccount, principal);
          // get the date for the transaction
            Date date = new Date();
            // add the values for the savings transaction record
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance());
            // save the savings transaction record 
            userService.saveSavingsTransaction(savingsTransaction, principal);
        }
    	
    	addBalanceToTransferredAccount(recipient, amount, principal);
    }

	public void addBalanceToTransferredAccount(Recipient recipient,String amount, Principal principal) {
		// get the user to transferto
		 User recipientUser = userService.findByEmail(recipient.getEmail());
		 // get the user that is making the transfer
		 User currentUser = userService.findByEmail(principal.getName());
		 // check if it is the primaryAccount		 
		 if(recipientUser.getPrimaryAccount().getAccountNumber() == Integer.parseInt(recipient.getAccountNumber()) ) {
			// Add the balance to the primary account
			 recipientUser.getPrimaryAccount().setAccountBalance(recipientUser.getPrimaryAccount().getAccountBalance().add(new BigDecimal(amount))); 
	            // save the transaction
	            userService.AddTransferToPrimaryAccount(recipientUser.getPrimaryAccount(), recipientUser , principal);
	            
	            
	            // get the date for the transaction
	            Date date = new Date();

	            // add the values for the primary transaction record
	            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, 
	            		"Transfered from "+ currentUser.getFirstName() +" " + currentUser.getLastName(), 
	            		"Transfer", "Finished", Double.parseDouble(amount), recipientUser.getPrimaryAccount().getAccountBalance());
	          
	            // save the primary transaction record 
	            recipientUser.getPrimaryAccount().getPrimaryTransactionList().add(primaryTransaction);
	            
	    		// save the update fields
	            userService.save(recipientUser);
	          
	            
		 } 
		// check if it is the primaryAccount	
		 else if(recipientUser.getSavingsAccount().getAccountNumber() == Integer.parseInt(recipient.getAccountNumber()) ) {
			// Add the balance to the primary account
			 recipientUser.getSavingsAccount().setAccountBalance(recipientUser.getSavingsAccount().getAccountBalance().add(new BigDecimal(amount))); 
	            // save the transaction
	            userService.AddTransferToSavingsAccount(recipientUser.getSavingsAccount(), recipientUser, principal);
	            
	            
	            // get the date for the transaction
	            Date date = new Date();

	            // add the values for the primary transaction record
	            SavingsTransaction savingsTransaction = new SavingsTransaction(date, 
	            		"Transfered from "+ currentUser.getFirstName() +" " + currentUser.getLastName(), 
	            		"Transfer", "Finished", Double.parseDouble(amount), recipientUser.getSavingsAccount().getAccountBalance());
	          
	            // save the primary transaction record 
	            recipientUser.getSavingsAccount().getSavingsTransactionList().add(savingsTransaction);
	            
	    		// save the update fields
	            userService.save(recipientUser);
		 }
		 else {
			System.out.println("The specify account does not exist Role Transaction Back");
		 }
	}




}
