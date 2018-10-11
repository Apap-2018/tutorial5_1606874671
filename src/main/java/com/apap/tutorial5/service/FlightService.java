package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.FlightModel;

/**
 * FlightService
 * @author Muhammad Aulia Adil
 *
 */
public interface FlightService {
	
	void addFlight(FlightModel flight);
	void deleteFlightById(long id);
	FlightModel getFlightDetailById(String id);
	List<FlightModel> getAllFlight();
}
