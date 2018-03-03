ALTER TABLE `bonus`
ADD COLUMN `expired_date` TIMESTAMP NOT NULL,
ADD COLUMN `is_used` BOOLEAN DEFAULT false,
CHANGE COLUMN `amount` `percent` DECIMAL(10,2) DEFAULT 0;