CREATE TABLE IF NOT EXISTS comments_user_airline(
	id SERIAL,
	user_id INTEGER NOT NULL,
	airline_id INTEGER  NOT NULL,
	comment VARCHAR(100) NOT NULL,
	count_likes INTEGER DEFAULT 0,
	count_unlikes INTEGER DEFAULT 0,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	PRIMARY KEY(id),
	CONSTRAINT FK_comments_user_airline_user FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT FK_comments_user_airline_airline FOREIGN KEY (user_id) REFERENCES airline(id)
	
);