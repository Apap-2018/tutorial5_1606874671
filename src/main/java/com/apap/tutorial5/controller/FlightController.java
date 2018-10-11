package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		pilot.getPilotFlight().add(flight);
		model.addAttribute("flight", flight);
		model.addAttribute("title", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping(value="/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		model.addAttribute("title", "Delete Flight");
		return "delete";
	}
	
//	@RequestMapping("/flight/delete/id/{id}")
//	public String deleteFlight(@PathVariable Optional<String> id, Model model) {
//		String errorMessage = "";
//		if(id.isPresent()) {
//			FlightModel flight = flightService.getFlightDetailById(id.get());
//			if(flight != null) {
//				flightService.deleteFlight(flight);
//				String successMessage = "Flight dengan ID " + id.get() + " berhasil dihapus!";
//				model.addAttribute("successMessage", successMessage);
//				return "delete";
//			}
//			else{
//				errorMessage = "Error! Flight dengan ID " + id.get() + " tidak ditemukan";
//				model.addAttribute("errorMessage", errorMessage);
//				return "error";	
//			}
//		}
//		else {
//			errorMessage = "Error! Daftar Flight kosong";
//			model.addAttribute("errorMessage", errorMessage);
//			return "error";
//		}
//	}
	
	@RequestMapping("/flight/view/all")
	public String viewAll(Model model) {
		List<FlightModel> allFlight = flightService.getAllFlight();
		model.addAttribute("allFlight", allFlight);
		model.addAttribute("title", "View All Flight");
		return "view-all-flight";
	}
	
	@RequestMapping(value = "/flight/update/id/{id}", method = RequestMethod.GET)
	private String update(@PathVariable(value ="id") String id, Model model) {
		FlightModel newFlight = flightService.getFlightDetailById(id);
		model.addAttribute("flight", newFlight);
		model.addAttribute("title", "Update Flight");
		return "updateFlight";
	}
	
	@RequestMapping(value = "/flight/update", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel flight) {
		FlightModel newFlight = flightService.getFlightDetailById("" + flight.getId());
		newFlight.setFlightNumber(flight.getFlightNumber());
		newFlight.setOrigin(flight.getOrigin());
		newFlight.setDestination(flight.getDestination());
		newFlight.setTime(flight.getTime());
		flightService.addFlight(newFlight);
		
		return "update";	
	}
}