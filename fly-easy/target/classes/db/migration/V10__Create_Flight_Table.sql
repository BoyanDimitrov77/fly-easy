CREATE TABLE IF NOT EXISTS `flight`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`airline_id` INT(11) NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	`location_from_id` INT(11) NOT NULL,
	`location_to_id` INT(11) NOT NULL,
	`depart_date`TIMESTAMP NOT NULL,
	`arrive_date` TIMESTAMP NOT NULL,
	`price` DECIMAL(10,2) DEFAULT 0,
	`seat` INT(7) NOT NULL,
	`class` VARCHAR(50) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY(`id`),
	 CONSTRAINT `FK_flight_airline` FOREIGN KEY(`airline_id`) REFERENCES `airline`(`id`),
	 CONSTRAINT `FK_flight_location_form` FOREIGN KEY(`location_from_id`) REFERENCES `location`(`id`),
	 CONSTRAINT `FK_flight_location_to` FOREIGN KEY(`location_to_id`) REFERENCES `location`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;