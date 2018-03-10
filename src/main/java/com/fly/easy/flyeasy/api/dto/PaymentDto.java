package com.fly.easy.flyeasy.api.dto;

import java.math.BigDecimal;

import com.fly.easy.flyeasy.db.model.Payment;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {

	private long id;

	private UserDto user;

	private BigDecimal amount;

	private BigDecimal discount;

	private String status;

	public static PaymentDto of(Payment payment) {
		return FlyEasyApp.ofNullable(payment, p -> PaymentDto.builder().id(p.getId()).user(UserDto.of(p.getUser()))
				.amount(p.getAmount()).discount(p.getDiscount()).status(p.getStatus()).build());
	}
}
