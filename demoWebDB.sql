DROP DATABASE IF EXISTS usersdb;
CREATE DATABASE usersdb;
USE usersdb;

CREATE TABLE USER (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    email varchar(45) not null,
    full_name varchar(45),
    password  varchar(32) not null,
    enabled boolean
);

INSERT INTO user (email, full_name, password, enabled)
values ("test123@gmail.com", "Ngô Khá Bá", "123123123", true);