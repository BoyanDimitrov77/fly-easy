CREATE TABLE IF NOT EXISTS `user_airline_rating`(
	`user_id` INT(11) NOT NULL,
	`airline_id` INT(11) NOT NULL,
	`rating` DECIMAL(2,1) DEFAULT 0,
	UNIQUE KEY `user_rating` (`user_id`,`airline_id`),
	CONSTRAINT `FK_user_airline_rating_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
	CONSTRAINT `FK_user_airline_rating_airline` FOREIGN KEY (`airline_id`) REFERENCES `airline`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;