package com.luxmart.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Appointment {
@Id
public String id;
private Date date;
private String location;
private String description;
private boolean confirmed;
private String username;

public Appointment() {
	super();
	this.id = UUID.randomUUID().toString();
}

}
