CREATE TABLE IF NOT EXISTS passenger_ticket(
	id SERIAL,
	passenger_name VARCHAR(100) NOT NULL,
    personal_identification_number VARCHAR(25) NOT NULL,
	ticket_number VARCHAR(100) NOT NULL,
	board_seat_no VARCHAR(10) NOT NULL,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	PRIMARY KEY (id)
);