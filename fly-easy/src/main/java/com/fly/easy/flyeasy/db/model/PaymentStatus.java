package com.fly.easy.flyeasy.db.model;

public enum PaymentStatus {

	PENDING("PENDING"), CONFIRMED("CONFIRMED");

	private final String paymentStatus;

	PaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return paymentStatus;
	}
}
