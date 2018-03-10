ALTER TABLE users
ADD COLUMN birth_date DATE ,
ADD COLUMN location_id INTEGER ,
ADD CONSTRAINT FK_user_location FOREIGN KEY (location_id) REFERENCES location(id);