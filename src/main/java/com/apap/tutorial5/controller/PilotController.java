package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.PilotModel;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "APAP");
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("title", "Add Pilot");
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	public String viewPilot(@RequestParam(value="licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		model.addAttribute("listFlight", pilot.getPilotFlight());
		model.addAttribute("title", "View Pilot");
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/view/all")
	public String viewAll(Model model) {
		List<PilotModel> allPilot = pilotService.getAllPilot();
		model.addAttribute("allPilot", allPilot);
		model.addAttribute("title", "View All Pilot");
//		model.addAttribute("link", "/pilot/view/all");
		return "view-all-pilot";
	}
	
	@RequestMapping("/pilot/delete/id/{id}")
	public String deletePilot(@PathVariable Optional<String> id, Model model) {
		String errorMessage = "";
		model.addAttribute("title", "Delete Pilot");
		if(id.isPresent()) {
			PilotModel pilot = pilotService.getPilotDetailById(id.get());
			if(pilot != null) {
				pilotService.deletePilot(pilot);;
				String successMessage = "Pilot dengan nomor lisensi " + pilot.getLicenseNumber() + "  berhasil dihapus!";
				model.addAttribute("successMessage", successMessage);
				return "delete";
			}
			else{
				errorMessage = "Error! Pilot dengan nomor lisensi " + pilot.getLicenseNumber() + " tidak ditemukan";
				model.addAttribute("errorMessage", errorMessage);
				model.addAttribute("title", "Error");
				return "error";	
			}
		}
		else {
			errorMessage = "Error! Daftar Pilot kosong";
			model.addAttribute("errorMessage", errorMessage);
			model.addAttribute("title", "Error");
			return "error";
		}
	}
	
	@RequestMapping(value = "/pilot/update/license-number/{licenseNumber}", method = RequestMethod.GET)
	private String update(@PathVariable(value ="licenseNumber") String licenseNumber, Model model) {
		PilotModel newPilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", newPilot);
		model.addAttribute("title", "Update Pilot");
		return "updatePilot";
	}
	
	@RequestMapping(value = "/pilot/update", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PilotModel pilot) {
		PilotModel newPilot = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		newPilot.setFlyHour(pilot.getFlyHour());
		newPilot.setName(pilot.getName());
		pilotService.addPilot(newPilot);
		return "update";	
	}
	
//	@RequestMapping("/pilot/update/id/{id}")
//	public String updatePilot(@PathVariable Optional<String> id, Model model) {
//		PilotModel pilot = pilotService.getPilotDetailById(id.get());
//		model.addAttribute("pilot", pilot);
//		return "updatePilot";
//	}
//	
//	@RequestMapping(value = "/pilot/update", method = RequestMethod.POST)
//	private String updatePilotSubmit(@ModelAttribute PilotModel pilot) {
//		pilotService.updatePilot(pilot);
//		return "update";
//	}
}
