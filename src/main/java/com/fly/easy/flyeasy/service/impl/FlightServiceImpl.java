package com.fly.easy.flyeasy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.FlightBookingDto;
import com.fly.easy.flyeasy.api.dto.FlightDto;
import com.fly.easy.flyeasy.api.dto.SearchFilterDto;
import com.fly.easy.flyeasy.api.dto.TravelClassDto;
import com.fly.easy.flyeasy.db.model.Airline;
import com.fly.easy.flyeasy.db.model.Flight;
import com.fly.easy.flyeasy.db.model.FlightBook;
import com.fly.easy.flyeasy.db.model.TravelClass;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.repository.AirlineRepository;
import com.fly.easy.flyeasy.db.repository.FlightBookRepository;
import com.fly.easy.flyeasy.db.repository.FlightRepository;
import com.fly.easy.flyeasy.db.repository.UserRepository;
import com.fly.easy.flyeasy.service.interfaces.FlightService;
import com.fly.easy.flyeasy.service.interfaces.LocationService;
import com.fly.easy.flyeasy.util.SearchUtil;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private LocationService locationService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightBookRepository flightBookRepository;

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
		flight.setTravelClasses(createFlightTravelClasses(flightDto.getTravelClasses()));
		flight.setTimestamp(new Date());

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

	@Override
	public List<FlightDto> findFlightBetweenDates(Date fromDate, Date toDate) {

		return flightRepository.findAllFlightBetweenDates(fromDate, toDate).stream().map(flight -> FlightDto.of(flight))
				.collect(Collectors.toList());
	}

	@Override
	public List<FlightDto> getFlightsByPrice() {

		return flightRepository.findFlightsByPrice().stream().map(flight -> FlightDto.of(flight))
				.collect(Collectors.toList());
	}

	@Override
	public List<FlightDto> getFlightsByLocation(String locationFrom, String locationTo) {

		return flightRepository.findFlightsByLocation(locationFrom, locationTo).stream()
				.map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
	}

	@Override
	public List<FlightDto> getFlightsByAirlineRaiting() {

		return flightRepository.findFlightsByRatingAirline().stream().map(flight -> FlightDto.of(flight))
				.collect(Collectors.toList());
	}

	private List<TravelClass> createFlightTravelClasses(List<TravelClassDto> list) {
		return list.stream().map(c -> {
			TravelClass travelClass = new TravelClass();
			travelClass.setMaxSeats(c.getMaxSeats());
			travelClass.setTravelClass(c.getTravelClass());
			travelClass.setPrice(c.getPrice());

			return travelClass;
		}).collect(Collectors.toList());
	}

	@Override
	public List<FlightDto> searcFlight(SearchFilterDto searchFilterDto) {
		if (SearchUtil.checkAllFilter(searchFilterDto)) {
			return flightRepository
					.findFlightByAllRequirements(searchFilterDto.getLocationFrom(), searchFilterDto.getLocationTo(),
							searchFilterDto.getDate())
					.stream().map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		} else if (SearchUtil.checkFitlerWitPriceWithoutRating(searchFilterDto)) {
			return flightRepository
					.findFlightByLocationAndDateAndPriceWithoutRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutPrice(searchFilterDto)) {
			return flightRepository
					.findFlightByLocationAndDateAndRatingWithoutPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceAndRatingWithoutDate(searchFilterDto)) {
			return flightRepository
					.findFlightByLocationAndPriceAndRatingWithoutDate(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithPriceWithoutDateAndRating(searchFilterDto)) {
			return flightRepository
					.findFlightByLocationAndPriceWithoutDateAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterWithRatingWithoutDateAndPrice(searchFilterDto)) {
			return flightRepository
					.findFlightByLocationAndRatingWithoutDateAndPrice(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo())
					.stream().map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		} else if (SearchUtil.checkoFilterOnlyWithPrice(searchFilterDto)) {
			return flightRepository.findFlightsByPrice().stream().map(flight -> FlightDto.of(flight))
					.collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyRating(searchFilterDto)) {
			return flightRepository.findFlightsByRatingAirline().stream().map(flight -> FlightDto.of(flight))
					.collect(Collectors.toList());
		} else if (SearchUtil.checkFilterOnlyLocation(searchFilterDto)) {
			return flightRepository
					.findFlightsByLocation(searchFilterDto.getLocationFrom(), searchFilterDto.getLocationTo()).stream()
					.map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		} else if (SearchUtil.checkFilterLocationAndDateWithoutPriceAndRating(searchFilterDto)) {
			return flightRepository
					.findFlightByLocationAndDateWitoutPriceAndRating(searchFilterDto.getLocationFrom(),
							searchFilterDto.getLocationTo(), searchFilterDto.getDate())
					.stream().map(flight -> FlightDto.of(flight)).collect(Collectors.toList());
		}
		return flightRepository.findAllFlights().stream().map(flight -> FlightDto.of(flight))
				.collect(Collectors.toList());
	}

	@Override
	public List<FlightBookingDto> getMyFlights(long userId) {

		User user = userRepository.findOne(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		List<FlightBook> myFlights = flightBookRepository.getMyFlights(userId);

		return myFlights.stream().map(fb -> FlightBookingDto.of(fb)).collect(Collectors.toList());
	}

}
