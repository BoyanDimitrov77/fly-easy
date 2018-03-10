CREATE TABLE IF NOT EXISTS verification_token(
   id SERIAL,
  token VARCHAR(64) NOT NULL,
  user_id INTEGER NOT NULL,
  expiry_date TIMESTAMP(0) with time zone NOT NULL,
  verified BOOLEAN NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT ix_verification_token UNIQUE  (user_id),
  CONSTRAINT FK_Verification_Token_User FOREIGN KEY (user_id) REFERENCES users (id)
	);