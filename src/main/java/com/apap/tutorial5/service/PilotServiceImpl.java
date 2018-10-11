package com.apap.tutorial5.service;

import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.PilotDB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PilotServiceImpl implements PilotService {
	@Autowired
	private PilotDB pilotDB;
	
	@Override
	public List<PilotModel> getAllPilot() {
		return pilotDB.findAll();
	}

	@Override
	public void addPilot(PilotModel pilot) {
		pilotDB.save(pilot);	
	}

	@Override
	public void deletePilot(PilotModel pilot) {
		pilotDB.delete(pilot);
	}
	
	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		return pilotDB.findByLicenseNumber(licenseNumber);
	}
	
	@Override
	public PilotModel getPilotDetailById(String id) {
		for(int i = 0; i < pilotDB.findAll().size(); i++) {
			if(pilotDB.findAll().get(i).getId() == Integer.parseInt(id)) {
				return pilotDB.findAll().get(i);
			}
		}
		return null;
	}

//	@Override
//	public boolean hasPilot(PilotModel pilot) {
//		for(int i = 0; i < pilotDB.findAll().size(); i++) {
//			if(pilotDB.findAll().get(i).getLicenseNumber().equalsIgnoreCase(pilot.getLicenseNumber())) {
//				return true;
//			}
//		}
//		return false;
//	}
}
