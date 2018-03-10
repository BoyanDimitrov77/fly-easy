CREATE TABLE IF NOT EXISTS resources(
id VARCHAR(36) NOT NULL,
value VARCHAR(255) NOT NULL,
timestamp timestamp(0) with time zone NOT NULL DEFAULT NOW(),
PRIMARY KEY(id)
);