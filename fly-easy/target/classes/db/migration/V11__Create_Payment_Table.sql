CREATE TABLE IF NOT EXISTS payment(
	id SERIAL,
	user_id INTEGER  NOT NULL,
	amount NUMERIC (10,2) DEFAULT 0,
	status VARCHAR(50) NOT NULL,
	timestamp TIMESTAMP(0) with time zone NOT NULL DEFAULT NOW(),
	PRIMARY KEY (id),
	CONSTRAINT FK_payment_user FOREIGN KEY (user_id) REFERENCES users(id)
)	;