package com.fly.easy.flyeasy.service.interfaces;

import java.math.BigDecimal;

import com.fly.easy.flyeasy.db.model.FlightBook;
import com.fly.easy.flyeasy.db.model.HotelBook;
import com.fly.easy.flyeasy.db.model.Payment;
import com.fly.easy.flyeasy.db.model.User;

public interface PaymentService {

	Payment createPaymentRecord(User user);
	
	FlightBook payBookedFlight(FlightBook flightBook,BigDecimal amount,String bonusId);
	
	HotelBook payHotelBook(HotelBook hotelBook,BigDecimal amount);
	
	
}
