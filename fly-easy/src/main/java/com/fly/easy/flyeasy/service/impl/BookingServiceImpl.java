package com.fly.easy.flyeasy.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.BookingDto;
import com.fly.easy.flyeasy.api.dto.PassengerTicketDto;
import com.fly.easy.flyeasy.db.model.Bonus;
import com.fly.easy.flyeasy.db.model.Flight;
import com.fly.easy.flyeasy.db.model.FlightBook;
import com.fly.easy.flyeasy.db.model.FlightBookStatus;
import com.fly.easy.flyeasy.db.model.PassengerTicket;
import com.fly.easy.flyeasy.db.model.Payment;
import com.fly.easy.flyeasy.db.model.PaymentStatus;
import com.fly.easy.flyeasy.db.model.TravelClass;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.repository.FlightBookRepository;
import com.fly.easy.flyeasy.db.repository.FlightRepository;
import com.fly.easy.flyeasy.db.repository.PassengerTicketRepository;
import com.fly.easy.flyeasy.db.repository.PaymentRepository;
import com.fly.easy.flyeasy.db.repository.TravelClassRepository;
import com.fly.easy.flyeasy.db.repository.UserRepository;
import com.fly.easy.flyeasy.service.interfaces.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	private static final BigDecimal HUNDRED = new BigDecimal(100);

	private static final String[] SEATS_LETTER = { "A", "B", "C", "D", "E", "F" };

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightBookRepository flightBookRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PassengerTicketRepository passengerTicketRepository;

	@Autowired
	private TravelClassRepository travelClassRepository;

	@Override
	public BookingDto bookFlight(long flightId, long userId) {
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
		flightBook.setPayment(createPaymentRecord(user));
		flightBook.setStatus(FlightBookStatus.WAITING.toString());

		FlightBook saveFlightBook = flightBookRepository.saveAndFlush(flightBook);

		return BookingDto.of(saveFlightBook);
	}

	private Payment createPaymentRecord(User user) {
		Payment payment = new Payment();
		payment.setUser(user);
		payment.setStatus(PaymentStatus.PENDING.toString());
		payment.setAmount(BigDecimal.ZERO);
		payment.setDiscount(BigDecimal.ZERO);

		Payment savePayment = paymentRepository.saveAndFlush(payment);

		return savePayment;

	}

	@Override
	public Payment payBookedFlight(long paymentId, BigDecimal amount, long flightBookId, String bonusId) {
		Payment payment = paymentRepository.findOne(paymentId);

		if (payment == null) {
			throw new ApiException("Payment record not found");
		}

		FlightBook flightBook = flightBookRepository.findOne(flightBookId);

		BigDecimal totalAmountWithoutDiscount = flightBook.getFlight().getPrice()
				.multiply(new BigDecimal(flightBook.getPassengerTickets().size()));

		BigDecimal saveAmount = amount;

		if (bonusId != null) {
			Bonus bonus = payment.getUser().getBonuses().stream().filter(b -> b.getId() == Long.parseLong(bonusId))
					.findFirst().get();

			if (checkPaymentDiscount(totalAmountWithoutDiscount, amount, bonus.getPercent())) {
				BigDecimal calculateDiscountPrice = calculateDiscountPrice(totalAmountWithoutDiscount,
						bonus.getPercent());

				saveAmount = totalAmountWithoutDiscount.subtract(calculateDiscountPrice);
				payment.setAmount(saveAmount);
				payment.setDiscount(calculateDiscountPrice);

				// mark bonus as used
				bonus.setIsUsed(true);
				userRepository.saveAndFlush(payment.getUser());

			} else {
				throw new ApiException("Amount is not corrent");
			}

		} else {

			if (totalAmountWithoutDiscount.compareTo(saveAmount) == 0) {
				payment.setAmount(saveAmount);
			} else {
				throw new ApiException("Amount is not corrent");
			}

		}

		payment.setStatus(PaymentStatus.CONFIRMED.toString());
		Payment savePayment = paymentRepository.saveAndFlush(payment);

		return savePayment;
	}

	private BigDecimal calculateDiscountPrice(BigDecimal priceFlight, BigDecimal percent) {
		return priceFlight.multiply(percent).divide(HUNDRED);

	}

	private boolean checkPaymentDiscount(BigDecimal totalAmountWithoutDiscount, BigDecimal compareAmount,
			BigDecimal bonusPercent) {

		BigDecimal discountPrice = calculateDiscountPrice(totalAmountWithoutDiscount, bonusPercent);

		if (totalAmountWithoutDiscount.subtract(discountPrice).compareTo(compareAmount) == 0) {
			return true;
		}

		return false;
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
	public BookingDto addPassengerFlightBook(long flightBookId, long travelClassId,
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

			return passengerTicket;

		}).collect(Collectors.toList());

		List<PassengerTicket> savePassengerTickets = passengerTicketRepository.save(passengerTickets);

		flightBook.setPassengerTickets(savePassengerTickets);

		FlightBook saveFlightBookWithPassengerTicket = flightBookRepository.saveAndFlush(flightBook);

		return BookingDto.of(saveFlightBookWithPassengerTicket);
	}

	private String generateTicketNumber() {

		long ticketNumber;

		do {
			ticketNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;

		} while (passengerTicketRepository.findByTicketNumber(Long.toString(ticketNumber)) != null);

		return Long.toString(ticketNumber);

	}
}
