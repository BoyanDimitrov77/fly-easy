package com.fly.easy.flyeasy.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.fly.easy.flyeasy.api.dto.FlightBookingDto;
import com.fly.easy.flyeasy.api.dto.PassengerTicketDto;

public interface BookingService {

	FlightBookingDto bookFlight(long flightId, long userId);

	FlightBookingDto addPassengerFlightBook(long flightBookId , long travelClassId,List<PassengerTicketDto> passengerTicketDtos);

	FlightBookingDto payBookedFlight(BigDecimal amount, long flightBookId, String bonusId, long travelClassId);

}
