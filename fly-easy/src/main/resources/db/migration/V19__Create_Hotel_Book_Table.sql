CREATE TABLE IF NOT EXISTS hotel_book(
	id SERIAL,
	hotel_room_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	payment_id INTEGER NOT NULL,
	status VARCHAR(50) NOT NULL,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	PRIMARY KEY(id),
	CONSTRAINT FK_hotel_book_hotel_room FOREIGN KEY (hotel_room_id) REFERENCES hotel_room(id),
	CONSTRAINT FK_hotel_book_user FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT FK_hotel_book_payment FOREIGN KEY (payment_id) REFERENCES payment(id)

);