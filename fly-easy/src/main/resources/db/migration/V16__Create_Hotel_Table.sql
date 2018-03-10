CREATE TABLE IF NOT EXISTS hotel(
	id SERIAL,
	name VARCHAR(25) NOT NULL,
	location_id INTEGER NOT NULL,
	description VARCHAR(100) NULL,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	PRIMARY KEY(id),
	CONSTRAINT FK_hotel_location FOREIGN KEY (location_id) REFERENCES location(id)
	
);