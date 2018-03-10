CREATE TABLE IF NOT EXISTS flight(
	id SERIAL,
	airline_id INTEGER NOT NULL,
	name VARCHAR(100) NOT NULL,
	location_from_id INTEGER NOT NULL,
	location_to_id INTEGER NOT NULL,
	depart_date TIMESTAMP(0) NOT NULL,
	arrive_date TIMESTAMP(0) NOT NULL,
	price NUMERIC(10,2) DEFAULT 0,
	seats INTEGER NOT NULL,
	class VARCHAR(50) NOT NULL,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	 PRIMARY KEY(id),
	 CONSTRAINT FK_flight_airline FOREIGN KEY(airline_id) REFERENCES airline(id),
	 CONSTRAINT FK_flight_location_form FOREIGN KEY(location_from_id) REFERENCES location(id),
	 CONSTRAINT FK_flight_location_to FOREIGN KEY(location_to_id) REFERENCES location(id)
);