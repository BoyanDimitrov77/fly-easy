package com.fly.easy.flyeasy.db.model;

public enum FlightBookStatus {

	WAITING("WAITING"), CONFIRMED("CONFIRMED"), CANCELLED("CANCELLED");

	private final String flightBookStatus;

	FlightBookStatus(String flightBookStatus) {
		this.flightBookStatus = flightBookStatus;
	}

	@Override
	public String toString() {
		return flightBookStatus;
	}

}
