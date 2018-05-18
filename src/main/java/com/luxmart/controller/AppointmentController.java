package com.luxmart.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luxmart.model.Appointment;
import com.luxmart.service.AppointmentService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	    @Autowired
	    private AppointmentService appointmentService;

	    // get the appointment page
	    @RequestMapping(value = "/create",method = RequestMethod.GET)
	    public String createAppointment(Model model) {
	    	// create a new instance of the appointment class
	        Appointment appointment = new Appointment();
	        // add the appointment to the model
	        model.addAttribute("appointment", appointment);
	        //set an empty date variable 
	        model.addAttribute("dateString", "");

	        // return the view
	        return "appointment";
	    }

	    
	    // save the appointments
	    @RequestMapping(value = "/create",method = RequestMethod.POST)
	    public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment, @ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {
           // format the date
	        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	        // get the current date
	        Date d1 = format1.parse( date );
	        // set the appointment date
	        appointment.setDate(d1);

	        // create the appointment
	        appointmentService.createAppointment(appointment, principal);

	        // redirect to the main page
	        return "redirect:/userFront";
	    }
}
