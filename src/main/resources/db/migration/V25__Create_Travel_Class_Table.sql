CREATE TABLE IF NOT EXISTS travel_class(
	id SERIAL,
	max_seats INTEGER NOT NULL,
	count_seats INTEGER DEFAULT 0,
	travel_class VARCHAR(50) NOT NULL,
	PRIMARY KEY(id)
);