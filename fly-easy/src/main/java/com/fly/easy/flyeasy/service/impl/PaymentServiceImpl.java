package com.fly.easy.flyeasy.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.db.model.Bonus;
import com.fly.easy.flyeasy.db.model.BookStatus;
import com.fly.easy.flyeasy.db.model.FlightBook;
import com.fly.easy.flyeasy.db.model.HotelBook;
import com.fly.easy.flyeasy.db.model.Payment;
import com.fly.easy.flyeasy.db.model.PaymentStatus;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.repository.PaymentRepository;
import com.fly.easy.flyeasy.db.repository.UserRepository;
import com.fly.easy.flyeasy.service.interfaces.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	private static final BigDecimal HUNDRED = new BigDecimal(100);
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Payment createPaymentRecord(User user) {
		Payment payment = new Payment();
		payment.setUser(user);
		payment.setStatus(PaymentStatus.PENDING.toString());
		payment.setAmount(BigDecimal.ZERO);
		payment.setDiscount(BigDecimal.ZERO);
		payment.setTimestamp(new Date());

		Payment savePayment = paymentRepository.saveAndFlush(payment);

		return savePayment;
	}

	@Override
	public HotelBook payHotelBook(HotelBook hotelBook, BigDecimal amount) {
		
		Payment payment = paymentRepository.findOne(hotelBook.getPayment().getId());
		
		if(payment == null){
			throw new ApiException("Hotel book - payment not found");
		}
		
		BigDecimal hotelRoomPrice = hotelBook.getHotelRoom().getPrice();

		if(hotelRoomPrice.compareTo(amount)!= 0){
			throw new ApiException("Amount is not correnct");
		}
		payment.setAmount(amount);
		payment.setStatus(PaymentStatus.CONFIRMED.toString());
		
		hotelBook.setStatus(BookStatus.CONFIRMED.toString());
		
		return hotelBook;
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

	@Override
	public FlightBook payBookedFlight(FlightBook flightBook, BigDecimal amount, String bonusId) {
		
		Payment payment = flightBook.getPayment();
		
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
		paymentRepository.saveAndFlush(payment);
		
		flightBook.setStatus(BookStatus.CONFIRMED.toString());
		
		
		return flightBook;
	}
	
	
}
