CREATE TABLE IF NOT EXISTS airports
(
	id BIGINT NOT NULL auto_increment,
	name VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS flights
(
	id BIGINT NOT NULL auto_increment,
	estimated_takeoff BIGINT NOT NULL,
	actual_takeoff BIGINT NOT NULL,
	airport_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (airport_id) REFERENCES airports(id)
);

CREATE TABLE IF NOT EXISTS skydivers
(
	id BIGINT NOT NULL auto_increment,
	username VARCHAR(50) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	estimated_jumps INTEGER NOT NULL,
	estimated_stay BIGINT NOT NULL,
	preferred_height INTEGER NOT NULL,
	available BOOLEAN DEFAULT FALSE,
	admin BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS flights_skydivers
(
	id BIGINT NOT NULL auto_increment,
	flight_id BIGINT NOT NULL,
	skydiver_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (flight_id) REFERENCES flights(id),
	FOREIGN KEY (skydiver_id) REFERENCES skydivers (id)
);