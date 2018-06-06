CREATE TABLE IF NOT EXISTS `passenger_ticket`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`passenger_name` VARCHAR(100) NOT NULL,
    `personal_identification_number` VARCHAR(25) NOT NULL,
	`ticket_number` VARCHAR(100) NOT NULL,
	`board_seat_no` VARCHAR(10) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;