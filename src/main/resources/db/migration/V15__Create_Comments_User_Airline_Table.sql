CREATE TABLE IF NOT EXISTS `comments_user_airline`(
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`airline_id` INT (11) NOT NULL,
	`comment` VARCHAR(100) NOT NULL,
	`count_likes` INT(10) DEFAULT 0,
	`count_unlikes` INT(10) DEFAULT 0,
	`timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`),
	CONSTRAINT `FK_comments_user_airline_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
	CONSTRAINT `FK_comments_user_airline_airline` FOREIGN KEY (`user_id`) REFERENCES `airline`(`id`)
	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
