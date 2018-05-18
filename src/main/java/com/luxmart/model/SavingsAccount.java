package com.luxmart.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SavingsAccount {

	private int accountNumber;
	private BigDecimal accountBalance;
	
	private List<SavingsTransaction> SavingsTransactionList;


	public SavingsAccount() {
		super();
		this.SavingsTransactionList = new ArrayList<>();
	}

	
}
