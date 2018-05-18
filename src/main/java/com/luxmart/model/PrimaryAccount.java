package com.luxmart.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PrimaryAccount {

	private int accountNumber;
	private BigDecimal accountBalance;
	
//	@JsonIgnore
	private List<PrimaryTransaction> PrimaryTransactionList;

	public PrimaryAccount() {
		super();
		this.PrimaryTransactionList = new ArrayList<>();
	}

}
