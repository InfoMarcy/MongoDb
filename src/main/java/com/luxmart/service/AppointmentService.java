package com.luxmart.service;

import java.security.Principal;
import java.util.List;

import com.luxmart.model.Appointment;

public interface AppointmentService {


	void createAppointment(Appointment appointment, Principal principal);
	List<Appointment> getAppointmentList();
	Appointment getAppointmentById(String username, String id);

}
