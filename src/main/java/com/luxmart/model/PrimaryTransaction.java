package com.luxmart.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class PrimaryTransaction {
	@Id
	private String id;
	private Date date;
	private String description;
	private String type;
	private String status;
	private Double amount;
	private BigDecimal availableBalance;
    
    
    
	public PrimaryTransaction() {
		super();
		this.id = UUID.randomUUID().toString();
	
	}
	public PrimaryTransaction(Date date, String description, String type, String status, Double amount,
			BigDecimal availableBalance) {
		super();
		this.date = date;
		this.description = description;
		this.type = type;
		this.status = status;
		this.amount = amount;
		this.availableBalance = availableBalance;
	}
	
	
}
