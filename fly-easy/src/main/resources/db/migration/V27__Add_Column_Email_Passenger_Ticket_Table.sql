ALTER TABLE `passenger_ticket`
MODIFY COLUMN `ticket_number` VARCHAR(13) NOT NULL,
ADD COLUMN `email` VARCHAR(64) NOT NULL AFTER `personal_identification_number`;