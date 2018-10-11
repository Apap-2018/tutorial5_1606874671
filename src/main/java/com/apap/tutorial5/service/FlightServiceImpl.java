package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.repository.FlightDB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightDB flightDB;
	
	@Override
	public void addFlight(FlightModel flight) {
		flightDB.save(flight);
	}

	@Override
	public void deleteFlightById(long id) {
		flightDB.deleteById(id);
	}

	@Override
	public FlightModel getFlightDetailById(String id) {
		for(int i = 0; i < flightDB.findAll().size(); i++) {
			if(flightDB.findAll().get(i).getId() == Integer.parseInt(id)) {
				return flightDB.findAll().get(i);
			}
		}
		return null;
	}

	@Override
	public List<FlightModel> getAllFlight() {
		return flightDB.findAll();
	}

}
