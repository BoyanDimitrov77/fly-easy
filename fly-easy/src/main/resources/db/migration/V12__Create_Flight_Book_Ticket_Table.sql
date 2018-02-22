CREATE TABLE IF NOT EXISTS `flight_book_ticket`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`flight_id` INT(11) NOT NULL,
	`booker` INT (11) NOT NULL,
	`payment_id` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
	UNIQUE KEY `booker_and_flight` (`flight_id`,`booker`),
	CONSTRAINT `FK_flight_book_ticket_flight` FOREIGN KEY(`flight_id`) REFERENCES `flight`(`id`),
	CONSTRAINT `FK_flight_book_ticket_user` FOREIGN KEY(`booker`) REFERENCES `user`(`id`),
	CONSTRAINT `FK_flight_book_ticket_payment` FOREIGN KEY(`payment_id`) REFERENCES `payment`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;