package com.fly.easy.flyeasy.api.endpoint;

import java.util.List;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fly.easy.flyeasy.api.dto.FilterDateDto;
import com.fly.easy.flyeasy.api.dto.FilterLocationDto;
import com.fly.easy.flyeasy.api.dto.FlightBookingDto;
import com.fly.easy.flyeasy.api.dto.FlightDto;
import com.fly.easy.flyeasy.api.dto.SearchFilterDto;
import com.fly.easy.flyeasy.service.interfaces.FlightService;
import com.fly.easy.flyeasy.util.UserUtil;

@RestController
@RequestMapping(value = "flight", produces = "application/json")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@RequestMapping(method = RequestMethod.POST, value = "/create/createFlightRecord/{airlineId}")
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

	@RequestMapping(method = RequestMethod.POST, value = "/allByDates")
	public List<FlightDto> getAllFlightByDates(@RequestBody FilterDateDto dto) {

		return flightService.findFlightBetweenDates(dto.getFromDate(), dto.getToDate());

	}

	@RequestMapping(method = RequestMethod.GET, value = "/allByPrice")
	public List<FlightDto> getFlightByPrice() {

		return flightService.getFlightsByPrice();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/allByLocation")
	public List<FlightDto> getFlightsByLocation(@RequestBody FilterLocationDto dto) {

		return flightService.getFlightsByLocation(dto.getLocationFrom(), dto.getLocationTo());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allByRaiting")
	public List<FlightDto> getFlightsByAirlineRaiting() {

		return flightService.getFlightsByAirlineRaiting();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/searchFlights")
	public List<FlightDto> getSearchedFlight(@RequestBody SearchFilterDto searchFilterDto) {
		return flightService.searcFlight(searchFilterDto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/myFlights")
	public List<FlightBookingDto> getMyFlights(SecurityContextHolder context) {

		return flightService.getMyFlights(UserUtil.gerUserFromContext().getId());
	}

}
