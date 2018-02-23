CREATE TABLE IF NOT EXISTS `hotel_book`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`hotel_id` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`payment_id` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
	UNIQUE KEY `hotel_user` (`hotel_id`,`user_id`),
	CONSTRAINT `FK_hotel_book_hotel` FOREIGN KEY (`hotel_id`) REFERENCES `hotel`(`id`),
	CONSTRAINT `FK_hotel_book_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
	CONSTRAINT `FK_hotel_book_payment` FOREIGN KEY (`payment_id`) REFERENCES `payment`(`id`)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;