CREATE TABLE IF NOT EXISTS `passenger_ticket`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`flight_book_ticket_id` INT(11) NOT NULL,
	`passenger_name` VARCHAR(100) NOT NULL,
	`ticket_number` VARCHAR(100) NOT NULL,
	`board_seat_no` VARCHAR(10) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_passenger_ticket_flight_book_ticket` FOREIGN KEY(`flight_book_ticket_id`) REFERENCES `flight_book_ticket`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;