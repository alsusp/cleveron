DROP TABLE IF EXISTS module;

CREATE TABLE module (
	id SERIAL,
	name VARCHAR(50) UNIQUE NOT NULL,
	parent_id INT,
	PRIMARY KEY (id),
	FOREIGN KEY (parent_id) REFERENCES module(id) ON DELETE RESTRICT
);
