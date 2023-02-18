-- Version 0.3.0: Add data
-- V0.2.0: Add foreign key constraint
-- V0.2.1: Change attribute name, re-route foreign key constraint
-- V0.2.2: Add CHECK constraint
-- Last update: 11/02/2023
-- Script for generating EIMS - Eggs Incubating Management System.
-- Check if database already exist. If yes then drop the database to ensure the script runs successfully with no variations.
DROP DATABASE IF EXISTS eims;
CREATE DATABASE eims;
USE eims;

-- Create all tables with no foreign keys.
CREATE TABLE userRole(
	role_id 		integer 	AUTO_INCREMENT PRIMARY KEY,
    roleName 	varchar(63) NOT NULL,
    status		boolean		NOT NULL
);

CREATE TABLE facility(
	facility_id					integer 	AUTO_INCREMENT PRIMARY KEY,
    user_id						integer		NOT NULL,
    facilityName				varchar(63) NOT NULL,
    facilityAddress				varchar(63) NOT NULL,
    facilityFoundDate			date 		NOT NULL,
    subscriptionExpirationDate	datetime,
    hotline						varchar(15)	NOT NULL,
    status						boolean		NOT NULL
);

CREATE TABLE user(
	user_id		integer 		AUTO_INCREMENT PRIMARY KEY,
    role_id		integer			NOT NULL,
    facility_id	integer,
    userName	varchar(63)		NOT NULL,
    dob			date			NOT NULL,
    phone		varchar(15)		NOT NULL,
    email		varchar(127),
    salary		decimal(15,2),
    password	varchar(127)	NOT NULL,
    address		varchar(127),
	status		boolean			NOT NULL
);

CREATE TABLE specie(
	specieId	integer		AUTO_INCREMENT PRIMARY KEY,
    user_id		integer		NOT NULL,
    specieName	varchar(63) NOT NULL,
    incubationPeriod	integer,
    status		boolean		NOT NULL
);

CREATE TABLE breed(
	breedId			integer 	AUTO_INCREMENT PRIMARY KEY,
    specieId			integer,
    user_id				integer		NOT NULL,
    breedName			varchar(63)	NOT NULL,
    averageWeight		double		NOT NULL,
    commonDisease		varchar(255),
    growthTime			time		NOT NULL,
    imageSrc			varchar(1027),
    status 				boolean		NOT NULL
);

CREATE TABLE incubationPhase(
	incubationPhaseId 	integer 	AUTO_INCREMENT PRIMARY KEY,
    specieId			integer		NOT NULL,
    phaseNumber			integer		NOT NULL,
    phasePeriod			integer		NOT NULL,
    phaseDescription	varchar(255),		
    status				boolean 	NOT NULL
);

CREATE TABLE machineType(
	machineTypeId	integer		AUTO_INCREMENT PRIMARY KEY,
    machineTypeName	varchar(63)	NOT NULL,
    description		varchar(255),
    status			boolean		NOT NULL
);

CREATE TABLE supplier(
	supplierId		integer 		AUTO_INCREMENT PRIMARY KEY,
    user_id			integer			NOT NULL,
    supplierName	varchar(63)		NOT NULL,
    supplierPhone	varchar(15)		NOT NULL,
    supplierAddress	varchar(255)	NOT NULL,
    supplierMail	varchar(127),
    status			boolean		NOT NULL
);

CREATE TABLE customer(
	customerId		integer 	AUTO_INCREMENT PRIMARY KEY,
    user_id			integer		NOT NULL,
    customerName	varchar(63)	NOT NULL,
    customerPhone	varchar(15),
    customerAddress	varchar(127),
    customerMail	varchar(127),
    status			boolean		NOT NULL
);

CREATE TABLE importReceipt(
	importId	integer			AUTO_INCREMENT PRIMARY KEY,
    supplierId	integer			NOT NULL,
    user_id		integer			NOT NULL,	-- Importer
    facility_id	integer			NOT NULL,
    importDate	datetime		NOT NULL,
    total		decimal(15,2)	NOT NULL,
    paid		decimal(15,2)	NOT NULL,
    status		boolean			NOT NULL
);

CREATE TABLE eggBatch(
	eggBatchId	integer			AUTO_INCREMENT PRIMARY KEY,
	importId		integer			NOT NULL,
	breedId		integer			NOT NULL,
    amount			integer 		NOT NULL,
    price			decimal(15,2)	NOT NULL
);

CREATE TABLE exportReceipt(
	exportId	integer			AUTO_INCREMENT PRIMARY KEY,
    customerId	integer			NOT NULL,
    user_id		integer			NOT NULL,	-- Exporter
    facility_id	integer			NOT NULL,
    exportDate	datetime		NOT NULL,
    total		decimal(15,2)	NOT NULL,
    paid		decimal(15,2)	NOT NULL,
    status		boolean			NOT NULL
);

CREATE TABLE exportDetail(
	exportId		integer			NOT NULL,
    productId		integer			NOT NULL,
    price			decimal(15,2)	NOT NULL,
    vaccinePrice	decimal(15,2),
    amount			integer			NOT NULL
);

CREATE TABLE machine(
	machineId		integer		AUTO_INCREMENT PRIMARY KEY,
    machineTypeId	integer 	NOT NULL,
    facility_id		integer		NOT NULL,
    machineName		varchar(63)	NOT NULL,
    maxCapacity		integer		NOT NULL,
    curCapacity		integer		NOT NULL,
    addedDate		date		NOT NULL,
    active			boolean		NOT NULL,
    status			boolean		NOT NULL
);

CREATE TABLE eggProduct(
	productId			integer	AUTO_INCREMENT PRIMARY KEY,
	eggBatchId		integer	NOT NULL,
    incubationPhaseId	integer	NOT NULL,
    incubationDate		datetime,
    amount				integer NOT NULL,
    curAmount			integer NOT NULL,
    status				boolean NOT NULL
);

CREATE TABLE eggLocation(
	eggId		integer	AUTO_INCREMENT PRIMARY KEY,
	productId	integer NOT NULL,
    machineId	integer NOT NULL,
    amount		integer NOT NULL,
    status		boolean NOT NULL
);

CREATE TABLE salary(
	salaryId	integer			AUTO_INCREMENT PRIMARY KEY,
	user_id		integer			NOT NULL,
    baseSalary	decimal(15,2)	NOT NULL,
    bonus		decimal(15,2),
    fine		decimal(15,2),
	issueDate	date			NOT NULL,
    note		varchar(255),
    status		boolean 		NOT NULL
);

CREATE TABLE cost(
	costId 		integer 		AUTO_INCREMENT PRIMARY KEY,
    user_id		integer			NOT NULL,
    facility_id	integer 		NOT NULL,
    costItem	varchar(63) 	NOT NULL,
    costAmount	decimal(15,2) 	NOT NULL,
    issueDate	datetime		NOT NULL,
    note		varchar(255),
    status 		boolean 		NOT NULL
);

CREATE TABLE subscription(
	subscriptionId 	integer 		AUTO_INCREMENT PRIMARY KEY,
    cost			decimal(15,2) 	NOT NULL,
    duration		integer			NOT NULL,
    machineQuota	integer			NOT NULL,
    status 			boolean			NOT NULL
);

CREATE TABLE userSubsription(
    facility_id		integer		NOT NULL,
    subscriptionId	integer 	NOT NULL,
    subscribeDate	datetime	NOT NULL,
    status			boolean		NOT NULL
);

-- Add the foreign keys and references to created tables.
ALTER TABLE facility 
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id);

ALTER TABLE user
ADD FOREIGN KEY (role_id) 		REFERENCES userRole(role_id),
ADD	FOREIGN KEY (facility_id) 	REFERENCES facility(facility_id),
ADD CHECK (salary >= 0);

ALTER TABLE specie
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD CHECK (incubationPeriod > 0);

ALTER TABLE breed
ADD FOREIGN KEY (specieId) 		REFERENCES specie(specieId),
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD CHECK (averageWeight > 0),
ADD CHECK (growthTime > 0);

ALTER TABLE incubationPhase
ADD FOREIGN KEY (specieId)		REFERENCES specie(specieId);

ALTER TABLE supplier
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id);

ALTER TABLE customer
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id);

ALTER TABLE importReceipt
ADD FOREIGN KEY (supplierId) 	REFERENCES supplier(supplierId),
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD CHECK (total >= 0),
ADD CHECK (paid >= 0);

ALTER TABLE eggBatch
ADD FOREIGN KEY (importId)		REFERENCES importReceipt(importId),
ADD FOREIGN KEY (breedId)		REFERENCES breed(breedId),
ADD CHECK (amount > 0),
ADD CHECK (price > 0);

ALTER TABLE exportReceipt
ADD FOREIGN KEY (customerId) 	REFERENCES customer(customerId),
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD CHECK (total >= 0),
ADD CHECK (paid >= 0);

ALTER TABLE exportDetail
ADD FOREIGN KEY (exportId)		REFERENCES exportReceipt(exportId),
ADD FOREIGN KEY (productId) 	REFERENCES eggProduct(productId),
ADD CHECK (price > 0),
ADD CHECK (vaccinePrice >= 0),
ADD CHECK (amount > 0);

ALTER TABLE machine
ADD FOREIGN KEY (machineTypeId)	REFERENCES machineType(machineTypeId),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD CHECK (maxCapacity >= 0),
ADD CHECK (curCapacity >= 0);

ALTER TABLE eggProduct
ADD FOREIGN KEY (eggBatchId)	REFERENCES eggBatch(eggBatchId),
ADD FOREIGN KEY (incubationPhaseId)	REFERENCES incubationPhase(incubationPhaseId);

ALTER TABLE eggLocation
ADD FOREIGN KEY (productId)		REFERENCES eggProduct(productId),
ADD FOREIGN KEY (machineId)		REFERENCES machine(machineId),
ADD CHECK (amount > 0);

ALTER TABLE salary
ADD FOREIGN KEY (user_id)		REFERENCES user(user_id),
ADD CHECK (baseSalary > 0),
ADD CHECK (bonus >= 0),
ADD CHECK (fine >= 0);

ALTER TABLE cost
ADD FOREIGN KEY (user_id)		REFERENCES user(user_id),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD CHECK (costAmount >= 0);

ALTER TABLE subscription
ADD CHECK (cost >= 0),
ADD CHECK (duration >= 0),
ADD CHECK (machineQuota >= 0);

ALTER TABLE userSubsription
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD FOREIGN KEY (subscriptionId)REFERENCES subscription(subscriptionId);

-- Insert Data into tables
-- userRole
INSERT INTO userRole (role_id, roleName, status)
VALUES 	(1, 'User', 1),
		(2, 'Owner', 1),
		(3, 'Employee', 1),
		(4, 'Moderator', 1),
		(5, 'Admin', 0);
-- user
INSERT INTO user(user_id, role_id, userName, dob, phone, password, status)
VALUES 	(1, '5', 'Default Data pack', '2001-12-16', '0969696696', 'a', 0);

-- specie
INSERT INTO specie(specieId, user_id, specieName, incubationPeriod, status)
VALUES 	(1, 1, 'Gà', 22, 1),
		(2, 1, 'Vịt', 31, 1),
		(3, 1, 'Ngan', 36, 1);

-- incubationPhase
INSERT INTO incubationPhase(specieId, phaseNumber, phasePeriod, phaseDescription, status)
VALUES 	(1, 0, 0,'Trứng vỡ/dập', 1),
		(1, 1, 0,'Trứng đang ấp', 1),
		(1, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(1, 3, 13,'Trứng loãng/tàu, phôi chết non', 1),
		(1, 4, 14,'Trứng lộn', 1),
		(1, 5, 19,'Trứng đang nở', 1),
		(1, 6, 21,'Trứng tắc', 1),
		(1, 7, 21,'Con nở', 1),
		(1, 8, 21,'Con đực', 1),
		(1, 9, 21,'Con cái', 1),
		(2, 0, 0,'Trứng vỡ/dập', 1),
        (2, 1, 0,'Trứng đang ấp', 1),
		(2, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(2, 3, 17,'Trứng loãng/tàu, phôi chết non', 1),
		(2, 4, 18,'Trứng lộn', 1),
		(2, 5, 28,'Trứng đang nở', 1),
		(2, 6, 30,'Trứng tắc', 1),
		(2, 7, 30,'Con nở', 1),
		(2, 8, 30,'Con đực', 1),
		(2, 9, 30,'Con cái', 1),
		(3, 0, 0,'Trứng vỡ/dập', 1),
        (3, 1, 0,'Trứng đang ấp', 1),
		(3, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(3, 3, 17,'Trứng loãng/tàu, phôi chết non', 1),
		(3, 4, 18,'Trứng lộn', 1),
		(3, 5, 32,'Trứng đang nở', 1),
		(3, 6, 35,'Trứng tắc', 1),
		(3, 7, 35,'Con nở', 1),
		(3, 8, 35,'Con đực', 1),
		(3, 9, 35,'Con cái', 1);
-- machineType
INSERT INTO machineType(machineTypeId, machineTypeName, description, status)
VALUES 	(1, 'Máy ấp', 'Máy dùng cho giai đoạn vừa mới ấp cho tới khi sắp nở, nhiệt cao, sức chứa cao', 1),
		(2, 'Máy nở', 'Máy dùng cho giai đoạn từ trứng lộn đến khi nở ra con, nhiệt thấp hơn, sức chứa thấp hơn', 1);

-- subscription
INSERT INTO subscription(cost, duration, machineQuota, status)
VALUES 	(0, 30, 5, 1),
		(500000, 30, 5, 1),
		(1300000, 90, 5, 1),
		(2400000, 180, 10, 1),
		(4000000, 365, 15, 1);