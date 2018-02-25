package com.fly.easy.flyeasy.api.endpoint;

import java.util.List;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fly.easy.flyeasy.api.dto.FlightDto;
import com.fly.easy.flyeasy.service.interfaces.FlightService;

@RestController
@RequestMapping(value = "flight", produces = "application/json")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@RequestMapping(method = RequestMethod.POST, value = "/createFlightRecord/{airlineId}")
	@Transactional
	public ResponseEntity<FlightDto> createFlightRecord(@RequestBody FlightDto flightDto,
			@PathVariable("airlineId") long airlineId) {

		FlightDto dto = flightService.createFllight(flightDto, airlineId);

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public List<FlightDto> getAllFlights() {

		return flightService.findAllFlight();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{flightId}")
	public ResponseEntity<FlightDto> getFlight(@PathVariable("flightId") long flightId) {

		FlightDto dto = flightService.getFlight(flightId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
