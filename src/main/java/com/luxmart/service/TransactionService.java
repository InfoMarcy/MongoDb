package com.luxmart.service;

import java.security.Principal;
import java.util.List;

import com.luxmart.model.PrimaryAccount;
import com.luxmart.model.PrimaryTransaction;
import com.luxmart.model.Recipient;
import com.luxmart.model.SavingsAccount;
import com.luxmart.model.SavingsTransaction;

public interface TransactionService {
	List<PrimaryTransaction> findPrimaryTransactionList(String username);

    List<SavingsTransaction> findSavingsTransactionList(String username);

    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction , Principal principal);

    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction , Principal principal);
    
    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction, Principal principal);
    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction, Principal principal);
    
    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount, Principal principal) throws Exception;
    
    List<Recipient> findRecipientList(Principal principal);

    Recipient saveRecipient(Recipient recipient, Principal principal);

    Recipient findRecipientByName(String recipientName, Principal principal);

    void deleteRecipientByName(String recipientName, Principal principal);
    
    void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount, Principal principal);


}
