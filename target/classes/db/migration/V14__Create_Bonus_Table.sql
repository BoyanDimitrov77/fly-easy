CREATE TABLE IF NOT EXISTS bonus(
	id SERIAL,
	user_id INTEGER  NOT NULL,
	airline_id INTEGER NOT NULL,
	amount NUMERIC(10,2) DEFAULT 0,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	PRIMARY KEY(id),
	CONSTRAINT FK_bonus_user FOREIGN KEY(user_id) REFERENCES users(id),
	CONSTRAINT FK_bonus_airline FOREIGN KEY(airline_id) REFERENCES airline(id)
);