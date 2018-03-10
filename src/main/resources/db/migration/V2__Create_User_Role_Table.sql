CREATE TABLE IF NOT EXISTS user_role(
	user_id INTEGER NOT NULL,
	role VARCHAR(50) NOT NULL,
	CONSTRAINT auth_user UNIQUE  (user_id,role),
	CONSTRAINT FK_User_User_Role FOREIGN KEY (user_id) REFERENCES users(id)
	);