package com.fly.easy.flyeasy.db.model;

public enum BookStatus {

	WAITING("WAITING"), CONFIRMED("CONFIRMED"), CANCELLED("CANCELLED");

	private final String bookStatus;

	BookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	@Override
	public String toString() {
		return bookStatus;
	}

}
