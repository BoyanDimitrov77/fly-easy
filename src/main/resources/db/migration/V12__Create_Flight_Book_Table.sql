CREATE TABLE IF NOT EXISTS flight_book(
	id SERIAL,
	flight_id INTEGER NOT NULL,
	booker INTEGER  NOT NULL,
	payment_id INT NOT NULL,
	status VARCHAR(50) NOT NULL,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	PRIMARY KEY(id),
	CONSTRAINT FK_flight_book_flight FOREIGN KEY(flight_id) REFERENCES flight(id),
	CONSTRAINT FK_flight_book_user FOREIGN KEY(booker) REFERENCES users(id),
	CONSTRAINT FK_flight_book_payment FOREIGN KEY(payment_id) REFERENCES payment(id)
);