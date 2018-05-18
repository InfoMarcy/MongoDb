package com.luxmart.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Recipient {
	
@Id	
private String id;
public Recipient() {
	super();
	this.id = UUID.randomUUID().toString();
}

private String name;
private String email;
private String phone;
private String accountNumber;
private String description;



}
