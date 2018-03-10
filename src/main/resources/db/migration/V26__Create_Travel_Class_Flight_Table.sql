CREATE TABLE IF NOT EXISTS travel_class_flight(
	flight_id INTEGER NOT NULL,
	travel_class_id INTEGER NOT NULL,
	 PRIMARY KEY(flight_id,travel_class_id),
	 CONSTRAINT 	FK_travel_class_flight_flight FOREIGN KEY(flight_id) REFERENCES flight(id),
	 CONSTRAINT FK_travel_class_flight_travel_class FOREIGN KEY (travel_class_id) REFERENCES travel_class(id)
);

CREATE INDEX FK_travel_class_flight_idx ON travel_class_flight (travel_class_id);