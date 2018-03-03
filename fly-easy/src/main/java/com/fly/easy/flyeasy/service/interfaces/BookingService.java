package com.fly.easy.flyeasy.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.fly.easy.flyeasy.api.dto.BookingDto;
import com.fly.easy.flyeasy.api.dto.PassengerTicketDto;
import com.fly.easy.flyeasy.db.model.Payment;

public interface BookingService {

	BookingDto bookFlight(long flightId, long userId);

	BookingDto addPassengerFlightBook(long flightBookId , long travelClassId,List<PassengerTicketDto> passengerTicketDtos);

	Payment payBookedFlight(long paymentId,BigDecimal amount, long flightBookId, String bonusId);

}
