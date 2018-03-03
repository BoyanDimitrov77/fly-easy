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

import com.fly.easy.flyeasy.api.dto.BookingDto;
import com.fly.easy.flyeasy.api.dto.PassengerTicketDto;
import com.fly.easy.flyeasy.api.dto.PaymentDto;
import com.fly.easy.flyeasy.db.model.Payment;
import com.fly.easy.flyeasy.service.interfaces.BookingService;
import com.fly.easy.flyeasy.util.UserUtil;

@RestController
@RequestMapping(value = "booking", produces = "application/json")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/{flightId}")
	@Transactional
	public ResponseEntity<BookingDto> bookFlight(@PathVariable("flightId") long flightId,
			SecurityContextHolder contex) {

		BookingDto bookFlight = bookingService.bookFlight(flightId, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(bookFlight, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/passengers/{flightBookId}/{travelClassId}")
	@Transactional
	public ResponseEntity<BookingDto> addPassengerToFlightBook(@PathVariable("flightBookId") long flightBookId,
			@PathVariable("travelClassId") long travelClassId,
			@RequestBody List<PassengerTicketDto> PassengerTicketDtos) {

		BookingDto dto = bookingService.addPassengerFlightBook(flightBookId, travelClassId, PassengerTicketDtos);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/payment")
	@Transactional
	public ResponseEntity<PaymentDto> payBookedFlight(@RequestParam(value = "paymentId") String paymentId,
			@RequestParam(value = "amount") String amount, @RequestParam(value = "flightBookId") String flightBookId,
			@RequestParam(value = "bonusId", required = false) String bonusId) {

		Payment payBookedFlight = bookingService.payBookedFlight(Long.parseLong(paymentId), new BigDecimal(amount),
				Long.parseLong(flightBookId), bonusId);

		return new ResponseEntity<>(PaymentDto.of(payBookedFlight), HttpStatus.OK);
	}

}
