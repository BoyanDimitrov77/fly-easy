package com.fly.easy.flyeasy.api.dto;

import java.util.List;

import com.fly.easy.flyeasy.db.model.FlightBook;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDto {

	private long id;

	private FlightDto flight;

	private UserDto booker;

	private PaymentDto payment;

	private String status;

	private List<PassengerTicketDto> passengerTickets;

	public static BookingDto of(FlightBook flightBook) {
		return FlyEasyApp.ofNullable(flightBook,
				fb -> BookingDto.builder().id(fb.getId()).flight(FlightDto.of(fb.getFlight()))
						.booker(UserDto.of(fb.getBooker())).payment(PaymentDto.of(fb.getPayment()))
						.passengerTickets(PassengerTicketDto.of(fb.getPassengerTickets())).status(fb.getStatus())
						.build());

	}

}
