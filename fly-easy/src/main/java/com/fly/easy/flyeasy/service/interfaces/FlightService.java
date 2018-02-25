package com.fly.easy.flyeasy.service.interfaces;

import java.util.List;

import com.fly.easy.flyeasy.api.dto.FlightDto;

public interface FlightService {

	FlightDto createFllight(FlightDto flightDto, long airlineId);
	
	List<FlightDto> findAllFlight();
	
	FlightDto getFlight(long flightId);
	
}
