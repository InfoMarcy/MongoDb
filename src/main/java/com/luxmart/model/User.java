package com.luxmart.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.luxmart.model.security.Role;

import lombok.Data;

@Data
@Document(collection = "user")
public class User {

	@Id
	private String id;
	private String username;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String motherLastName;
	// index to speed the process of filtering in this case by pricePerNight
	@Indexed(direction = IndexDirection.ASCENDING)
	private String email;
	private String phone;
	private int age;
	private String sex;
	private String ocupation;
	private boolean enabled = true;

	private PrimaryAccount primaryAccount;
	private SavingsAccount savingsAccount;

	private List<Recipient> recipientList;
	private List<Appointment> appointmentList;
	private Role role;

	public User() {
		super();
		this.recipientList = new ArrayList<>();
		this.appointmentList = new ArrayList<>();
		this.role = Role.ROLE_USER;

	}

	
}
