ALTER TABLE `user`
ADD COLUMN `birth_date` DATE ,
ADD COLUMN `location_id` INT(11) ,
ADD CONSTRAINT `FK_user_location` FOREIGN KEY (`location_id`) REFERENCES `location`(`id`);
