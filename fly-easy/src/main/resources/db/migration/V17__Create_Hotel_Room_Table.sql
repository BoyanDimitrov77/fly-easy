CREATE TABLE IF NOT EXISTS hotel_room(
	id SERIAL,
	hotel_id INTEGER NOT NULL,
	type_room VARCHAR(50) NOT NULL,
	price NUMERIC(10,2) DEFAULT 0,
	PRIMARY KEY(id),
	CONSTRAINT FK_hotel_room_hotel FOREIGN KEY(hotel_id) REFERENCES hotel(id)
);