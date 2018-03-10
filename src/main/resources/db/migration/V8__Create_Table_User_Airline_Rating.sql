CREATE TABLE IF NOT EXISTS user_airline_rating(
	user_id INTEGER NOT NULL,
	airline_id INTEGER NOT NULL,
	rating NUMERIC(2,1) DEFAULT 0,
	CONSTRAINT user_rating UNIQUE  (user_id,airline_id),
	CONSTRAINT FK_user_airline_rating_user FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT FK_user_airline_rating_airline FOREIGN KEY (airline_id) REFERENCES airline(id)
);