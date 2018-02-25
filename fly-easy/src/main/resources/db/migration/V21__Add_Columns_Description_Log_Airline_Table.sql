ALTER TABLE `airline`
ADD COLUMN `description` VARCHAR(100) NULL,
ADD COLUMN `logo` VARCHAR(36) DEFAULT NULL,
ADD CONSTRAINT `airline_logo` FOREIGN KEY (`logo`) REFERENCES picture(`id`);
