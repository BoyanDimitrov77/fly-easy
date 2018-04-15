package com.fly.easy.flyeasy.api.endpoint;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fly.easy.flyeasy.api.dto.FlightBookingDto;
import com.fly.easy.flyeasy.api.dto.HotelBookingDto;
import com.fly.easy.flyeasy.api.dto.PassengerTicketDto;
import com.fly.easy.flyeasy.service.interfaces.BookingService;
import com.fly.easy.flyeasy.service.interfaces.HotelService;
import com.fly.easy.flyeasy.util.UserUtil;

@RestController
@RequestMapping(value = "booking", produces = "application/json")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private HotelService hotelService;

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/{flightId}")
	@Transactional
	public ResponseEntity<FlightBookingDto> bookFlight(@PathVariable("flightId") long flightId,
			SecurityContextHolder contex) {

		FlightBookingDto bookFlight = bookingService.bookFlight(flightId, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(bookFlight, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/passengers/{flightBookId}/{travelClassId}")
	@Transactional
	public ResponseEntity<FlightBookingDto> addPassengerToFlightBook(@PathVariable("flightBookId") long flightBookId,
			@PathVariable("travelClassId") long travelClassId,
			@RequestBody List<PassengerTicketDto> PassengerTicketDtos) {

		FlightBookingDto dto = bookingService.addPassengerFlightBook(flightBookId, travelClassId, PassengerTicketDtos);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/payment")
	@Transactional
	public ResponseEntity<FlightBookingDto> payBookedFlight(@RequestParam(value = "amount") String amount,
			@RequestParam(value = "flightBookId") String flightBookId,
			@RequestParam(value = "bonusId", required = false) String bonusId,
			@RequestParam(value = "travelClassId")String travelClassId) {

		FlightBookingDto payBookedFlight = bookingService.payBookedFlight(new BigDecimal(amount),
				Long.parseLong(flightBookId), bonusId,Long.parseLong(travelClassId));

		return new ResponseEntity<>(payBookedFlight, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookHotel/{hoteRoomId}")
	@Transactional
	public ResponseEntity<HotelBookingDto> bookHotel(@PathVariable("hoteRoomId") long hotelRoomId,
			SecurityContextHolder context) {

		HotelBookingDto dto = hotelService.bookHotel(hotelRoomId, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookHotel/payment")
	@Transactional
	public ResponseEntity<HotelBookingDto> payHotel(@RequestParam(value = "hotelBookId") String hotelBookId,
			@RequestParam(value = "amount") String amount) {

		HotelBookingDto dto = hotelService.payHotel(Long.parseLong(hotelBookId), new BigDecimal(amount));

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

}
