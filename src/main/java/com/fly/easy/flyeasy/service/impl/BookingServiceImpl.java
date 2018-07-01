package com.fly.easy.flyeasy.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.FlightBookingDto;
import com.fly.easy.flyeasy.api.dto.PassengerTicketDto;
import com.fly.easy.flyeasy.db.model.BookStatus;
import com.fly.easy.flyeasy.db.model.Flight;
import com.fly.easy.flyeasy.db.model.FlightBook;
import com.fly.easy.flyeasy.db.model.PassengerTicket;
import com.fly.easy.flyeasy.db.model.TravelClass;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.repository.FlightBookRepository;
import com.fly.easy.flyeasy.db.repository.FlightRepository;
import com.fly.easy.flyeasy.db.repository.PassengerTicketRepository;
import com.fly.easy.flyeasy.db.repository.TravelClassRepository;
import com.fly.easy.flyeasy.db.repository.UserRepository;
import com.fly.easy.flyeasy.service.interfaces.BookingService;
import com.fly.easy.flyeasy.service.interfaces.PaymentService;

@Service
public class BookingServiceImpl implements BookingService {

	private static final String[] SEATS_LETTER = { "A", "B", "C", "D", "E", "F" };

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightBookRepository flightBookRepository;

	@Autowired
	private PassengerTicketRepository passengerTicketRepository;

	@Autowired
	private TravelClassRepository travelClassRepository;

	@Autowired
	private PaymentService paymentService;

	@Override
	public FlightBookingDto bookFlight(long flightId, long userId) {
		Flight flight = flightRepository.findOne(flightId);

		if (flight == null) {
			throw new ApiException("Flight not found");
		}

		User user = userRepository.findOne(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		FlightBook flightBook = new FlightBook();
		flightBook.setFlight(flight);
		flightBook.setBooker(user);
		flightBook.setPayment(paymentService.createPaymentRecord(user));
		flightBook.setStatus(BookStatus.WAITING.toString());
		flightBook.setTimestamp(new Date());

		FlightBook saveFlightBook = flightBookRepository.saveAndFlush(flightBook);

		return FlightBookingDto.of(saveFlightBook);
	}

	@Override
	public FlightBookingDto payBookedFlight(BigDecimal amount, long flightBookId, String bonusId, long travelClassId) {
		FlightBook flightBook = flightBookRepository.findOne(flightBookId);

		if(flightBook == null){
			throw new ApiException("Flight book not found");
		}
		TravelClass travelClass = travelClassRepository.findOne(travelClassId);

		if(travelClass == null){
			throw new ApiException("Travel class not found");
		}

		FlightBook flightB = paymentService.payBookedFlight(flightBook, amount, bonusId, travelClass);

		flightB.setStatus(BookStatus.CONFIRMED.toString());

		FlightBook saveBookFlight = flightBookRepository.saveAndFlush(flightB);

		return FlightBookingDto.of(saveBookFlight);
	}

	private String generateSeatNum(long travelClassId) {

		TravelClass travelClass = travelClassRepository.findOne(travelClassId);

		if (travelClass == null) {
			throw new ApiException("Travel class not found");
		}

		int maxSeats = travelClass.getMaxSeats();

		int currentCounterSeats = travelClass.getCounterSeats();

		StringBuilder seatNumber = new StringBuilder();

		if (currentCounterSeats <= maxSeats) {
			currentCounterSeats++;
			int result = currentCounterSeats % SEATS_LETTER.length;

			if (result == 0) {
				seatNumber.append(currentCounterSeats);
				seatNumber.append(SEATS_LETTER[5]);
			} else {
				seatNumber.append(currentCounterSeats);
				seatNumber.append(SEATS_LETTER[result - 1]);
			}
		}

		travelClass.setCounterSeats(currentCounterSeats);

		return seatNumber.toString();
	}

	@Override
	public FlightBookingDto addPassengerFlightBook(long flightBookId, long travelClassId,
			List<PassengerTicketDto> passengerTicketDtos) {

		FlightBook flightBook = flightBookRepository.findOne(flightBookId);

		if (flightBook == null) {
			throw new ApiException("Flight book not found");
		}

		List<PassengerTicket> passengerTickets = passengerTicketDtos.stream().map(passengerTicketDto -> {
			PassengerTicket passengerTicket = new PassengerTicket();
			passengerTicket.setPassengerName(passengerTicketDto.getPassengerName());
			passengerTicket.setPersonalIdentificationNumber(passengerTicketDto.getIdentificationNumber());
			passengerTicket.setEmail(passengerTicketDto.getEmail());
			passengerTicket.setTicketNumber(generateTicketNumber());
			passengerTicket.setBoardSeatNumber(generateSeatNum(travelClassId));
			passengerTicket.setTimestamp(new Date());

			return passengerTicket;

		}).collect(Collectors.toList());

		List<PassengerTicket> savePassengerTickets = passengerTicketRepository.save(passengerTickets);

		flightBook.setPassengerTickets(savePassengerTickets);

		FlightBook saveFlightBookWithPassengerTicket = flightBookRepository.saveAndFlush(flightBook);

		return FlightBookingDto.of(saveFlightBookWithPassengerTicket);
	}

	private String generateTicketNumber() {

		long ticketNumber;

		do {
			ticketNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;

		} while (passengerTicketRepository.findByTicketNumber(Long.toString(ticketNumber)) != null);

		return Long.toString(ticketNumber);

	}
}
