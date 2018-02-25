package com.fly.easy.flyeasy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.FlightDto;
import com.fly.easy.flyeasy.db.model.Airline;
import com.fly.easy.flyeasy.db.model.Flight;
import com.fly.easy.flyeasy.db.repository.AirlineRepository;
import com.fly.easy.flyeasy.db.repository.FlightRepository;
import com.fly.easy.flyeasy.service.interfaces.FlightService;
import com.fly.easy.flyeasy.service.interfaces.LocationService;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private LocationService locationService;

	@Override
	public FlightDto createFllight(FlightDto flightDto, long airlineId) {
		Airline airline = airlineRepository.findById(airlineId);

		if (airline == null) {
			throw new ApiException("Airline not found");
		}

		Flight flight = new Flight();
		flight.setAirline(airline);
		flight.setName(flightDto.getFlightName());
		flight.setLocationFrom(locationService.createLocation(flightDto.getLocationFrom().getName()));
		flight.setLocationTo(locationService.createLocation(flightDto.getLocationTo().getName()));
		flight.setDepartDate(flightDto.getDepartDate());
		flight.setArriveDate(flightDto.getArriveDate());
		flight.setPrice(flightDto.getPrice());
		flight.setSeats(flightDto.getSeats());
		flight.setTravelClass(flightDto.getTravelClass());

		Flight saveFlight = flightRepository.saveAndFlush(flight);

		return FlightDto.of(saveFlight);
	}

	@Override
	public List<FlightDto> findAllFlight() {
		return flightRepository.findAllFlights().stream().map(flight -> FlightDto.of(flight))
				.collect(Collectors.toList());
	}

	@Override
	public FlightDto getFlight(long flightId) {
		Flight flight = flightRepository.findById(flightId);
		if (flight == null) {
			throw new ApiException("Flight not found");
		}
		return FlightDto.of(flight);
	}

}
