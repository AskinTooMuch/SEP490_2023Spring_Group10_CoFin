-- Check if database already exist. If yes then drop the database to ensure the script runs successfully with no variations.
DROP DATABASE IF EXISTS eims;
CREATE DATABASE eims;
USE eims;

-- Create all tables with no foreign keys.
CREATE TABLE userRole(
	roleId 		integer 	AUTO_INCREMENT PRIMARY KEY,
    roleName 	varchar(63) NOT NULL,
    status		boolean		NOT NULL
);

CREATE TABLE facility(
	facilityId					integer 	AUTO_INCREMENT PRIMARY KEY,
    userId						integer		NOT NULL,
    facilityName				varchar(63) NOT NULL,
    facilityAddress				varchar(63) NOT NULL,
    facilityFoundDate			date 		NOT NULL,
    subscriptionExpirationDate	datetime,
    hotline						varchar(15),
    status						boolean		NOT NULL
);

CREATE TABLE user(
	userId		integer 		AUTO_INCREMENT PRIMARY KEY,
    roleId		integer			NOT NULL,
    facilityId	integer,
    userName	varchar(63)		NOT NULL,
    phone		varchar(15)		NOT NULL,
    email		varchar(127),
    password	varchar(127)	NOT NULL,
    address		varchar(127),
	status		boolean			NOT NULL
);

CREATE TABLE specie(
	specieId	integer		AUTO_INCREMENT PRIMARY KEY,
    userId		integer		NOT NULL,
    specieName	varchar(63) NOT NULL,
    incubationPeriod	time,
    status		boolean		NOT NULL
);

CREATE TABLE eggType(
	eggTypeId			integer 	AUTO_INCREMENT PRIMARY KEY,
    specieId			integer,
    userId				integer		NOT NULL,
    eggTypeName			varchar(63)	NOT NULL,
    imageSrc			varchar(1027),
    status 				boolean		NOT NULL
);

CREATE TABLE incubationPhase(
	incubationPhaseId 	integer 	AUTO_INCREMENT PRIMARY KEY,
    specieId			integer		NOT NULL,
    phaseNumber			integer		NOT NULL,
    phasePeriod			time		NOT NULL,
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
    userId			integer			NOT NULL,
    supplierName	varchar(63)		NOT NULL,
    supplierPhone	varchar(15)		NOT NULL,
    supplierAddress	varchar(255)	NOT NULL,
    supplierMail	varchar(127),
    status			boolean		NOT NULL
);

CREATE TABLE customer(
	customerId		integer 	AUTO_INCREMENT PRIMARY KEY,
    userId			integer		NOT NULL,
    customerName	varchar(63)	NOT NULL,
    customerPhone	varchar(15),
    customerAddress	varchar(127),
    customerMail	varchar(127),
    status			boolean		NOT NULL
);

CREATE TABLE importReceipt(
	importId	integer			AUTO_INCREMENT PRIMARY KEY,
    supplierId	integer			NOT NULL,
    userId		integer			NOT NULL,	-- Importer
    facilityId	integer			NOT NULL,
    importDate	datetime		NOT NULL,
    total		decimal(15,2)	NOT NULL,
    status		boolean			NOT NULL
);

CREATE TABLE importDetail(
	importDetailId	integer			AUTO_INCREMENT PRIMARY KEY,
	importId		integer			NOT NULL,
    amount			integer 		NOT NULL,
    price			decimal(15,2)	NOT NULL,
    eggTypeId		integer			NOT NULL
);

CREATE TABLE exportReceipt(
	exportId	integer			AUTO_INCREMENT PRIMARY KEY,
    customerId	integer			NOT NULL,
    userId		integer			NOT NULL,	-- Exporter
    facilityId	integer			NOT NULL,
    exportDate	datetime		NOT NULL,
    total		decimal(15,2)	NOT NULL,
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
    facilityId		integer		NOT NULL,
    machineName		varchar(63)	NOT NULL,
    maxCapacity		integer		NOT NULL,
    curCapacity		integer		NOT NULL,
    addedDate		date		NOT NULL,
    active			boolean		NOT NULL,
    status			boolean		NOT NULL
);

CREATE TABLE eggProduct(
	productId			integer	AUTO_INCREMENT PRIMARY KEY,
	importDetailId		integer	NOT NULL,
    incubationPhaseId	integer	NOT NULL,
    incubationDate		datetime,
    amount				integer NOT NULL,
    curAmount			integer NOT NULL,
    status				boolean NOT NULL
);

CREATE TABLE eggLocation(
	eggId		integer	AUTO_INCREMENT PRIMARY KEY,
	productId		integer NOT NULL,
    machineId		integer NOT NULL,
    amount			integer NOT NULL,
    status			boolean NOT NULL
);

CREATE TABLE salary(
	salaryId	integer			AUTO_INCREMENT PRIMARY KEY,
	userId		integer			NOT NULL,
    baseSalary	decimal(15,2)	NOT NULL,
    bonus		decimal(15,2),
    fine		decimal(15,2),
	issueDate	date			NOT NULL,
    status		boolean 		NOT NULL
);

CREATE TABLE cost(
	costId 		integer 		AUTO_INCREMENT PRIMARY KEY,
    userId			integer			NOT NULL,
    facilityId		integer 		NOT NULL,
    costItem	varchar(63) 	NOT NULL,
    costAmount	decimal(15,2) 	NOT NULL,
    issueDate		datetime		NOT NULL,
    status 			boolean 		NOT NULL
);

CREATE TABLE subscription(
	subscriptionId 	integer 		AUTO_INCREMENT PRIMARY KEY,
    cost			decimal(15,2) 	NOT NULL,
    duration		time			NOT NULL,
    machineQuota	integer			NOT NULL,
    status 			boolean			NOT NULL
);

CREATE TABLE userSubsription(
    facilityId		integer		NOT NULL,
    subscriptionId	integer 	NOT NULL,
    subscribeDate	datetime	NOT NULL,
    status			boolean		NOT NULL
);

-- Add the foreign keys and references to created tables.
ALTER TABLE facility 
ADD FOREIGN KEY (userId) 		REFERENCES user(userId);

ALTER TABLE user
ADD FOREIGN KEY (roleId) 		REFERENCES userRole(roleId),
ADD	FOREIGN KEY (facilityId) 	REFERENCES facility(facilityId);

ALTER TABLE specie
ADD FOREIGN KEY (userId) 		REFERENCES user(userId);

ALTER TABLE eggType
ADD FOREIGN KEY (specieId) 		REFERENCES specie(specieId),
ADD FOREIGN KEY (userId) 		REFERENCES user(userId);

ALTER TABLE machineType
ADD FOREIGN KEY (userId) 		REFERENCES user(userId);

ALTER TABLE supplier
ADD FOREIGN KEY (userId) 		REFERENCES user(userId);

ALTER TABLE customer
ADD FOREIGN KEY (userId) 		REFERENCES user(userId);

ALTER TABLE importReceipt
ADD FOREIGN KEY (supplierId) 	REFERENCES supplier(supplierId),
ADD FOREIGN KEY (userId) 		REFERENCES user(userId),
ADD FOREIGN KEY (facilityId)	REFERENCES facility(facilityId);

ALTER TABLE importDetail
ADD FOREIGN KEY (importId)		REFERENCES importReceipt(importId),
ADD FOREIGN KEY (batchId) 		REFERENCES eggBatch(batchId),
ADD FOREIGN KEY (eggTypeId)		REFERENCES eggType(eggTypeId);

ALTER TABLE exportReceipt
ADD FOREIGN KEY (customerId) 	REFERENCES customer(customerId),
ADD FOREIGN KEY (userId) 		REFERENCES user(userId),
ADD FOREIGN KEY (facilityId)	REFERENCES facility(facilityId);

ALTER TABLE exportDetail
ADD FOREIGN KEY (exportId)		REFERENCES exportReceipt(exportId),
ADD FOREIGN KEY (batchId) 		REFERENCES eggBatch(batchId);

ALTER TABLE machine
ADD FOREIGN KEY (machineTypeId)	REFERENCES machineType(machineTypeId),
ADD FOREIGN KEY (facilityId)	REFERENCES facility(facilityId);

ALTER TABLE batchDetail
ADD FOREIGN KEY (batchId)		REFERENCES eggBatch(batchId),
ADD FOREIGN KEY (machineId)		REFERENCES machine(machineId);

ALTER TABLE salary
ADD FOREIGN KEY (userId)		REFERENCES user(userId);

ALTER TABLE spending
ADD FOREIGN KEY (userId)		REFERENCES user(userId),
ADD FOREIGN KEY (facilityId)	REFERENCES facility(facilityId);

ALTER TABLE userSubsription
ADD FOREIGN KEY (userId)		REFERENCES user(userId),
ADD FOREIGN KEY (subscriptionId)REFERENCES subscription(subscriptionId);