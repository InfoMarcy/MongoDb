package com.luxmart.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxmart.model.Appointment;
import com.luxmart.model.User;
import com.luxmart.service.AppointmentService;
import com.luxmart.service.UserService;

@Service("appointmentService")
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	UserService userService;

	// create appointment
	@Override
	public void createAppointment(Appointment appointment, Principal principal) {
		// get the current user
		User user = userService.findByEmail(principal.getName());
		// set the username for the appointment
		appointment.setUsername(user.getUsername());
		// add the appointment to the user
		user.getAppointmentList().add(appointment);

		// save the appointment
		userService.save(user);
	}

	// get the list of all the appointments
	@Override
	public List<Appointment> getAppointmentList() {

		// find all users
		List<User> users = (List<User>) userService.findAll();
		// create a new arrayList of appointments
		List<Appointment> appointments = new ArrayList<>();

		// iterate over the list of all the users
		for (Iterator<User> user = users.iterator(); user.hasNext();) {
			// get the user from the forEach
			User getUser = user.next();

			// iterate over the AppointmentList
			for (Appointment item : getUser.getAppointmentList()) {

				// check if the class is not empty
				if (item != null) {
					appointments.add(item);
				}

			}

		}

		return appointments;
	}

	// confirm an appointment
	@Override
	public Appointment getAppointmentById(String username, String id) {
		User user = userService.findByUsername(username);

		// iterate over the Appointments
		for (Appointment item : user.getAppointmentList()) {
			// check if the appointment is the one we are looking for
			if (item.getId().toString().equals(id)) {
				item.setConfirmed(true);

				user.getAppointmentList().remove(item);
				user.getAppointmentList().add(item);

				userService.save(user);
			}

		}

		return null;
	}

}
