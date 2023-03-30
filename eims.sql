-- V0.11.1: Add event need_action, add egg_batch attribute
-- V0.11.0: Modify specie stored procedures
-- V0.10.0: Modify field lengths
-- V0.9.0: Modify the stored procedure user_and_facility
-- V0.8.2: Roll back to V0.8.0
-- V0.8.1: Update tables: customer, supplier (user_id -> facility_id)
-- V0.8.0: Modify the user - role relationship from many-one to many-many
-- V0.7.1: Import data on tables: registration, breed
-- V0.7.0: Import data on tables: User, Facility, Specie, incubation phase, supplier, customer, subscription user, 
-- V0.6.3: Minor audijustment at customer table (NOT NULL field and size)
-- V0.6.2: Minor audijustment at facility table and add data into machine table
-- V0.6.1: Fix stored procedure to work with user_id
-- V0.6.0: Add stored procedure user_and_facility
-- V0.5.0: Added tables: salary_history, registration, notification. Modified a few tables
-- V0.4.0: Change attribute and table name from camelCase to camel_case
-- V0.3.0: Add data
-- V0.2.0: Add foreign key constraint
-- V0.2.1: Change attribute _name, re-route foreign key constraint
-- V0.2.2: Add CHECK constraint
-- Last update: 29/03/2023
-- Script for generating EIMS - Eggs Incubating Management System.
-- Check if database already exist. If yes then drop the database to ensure the script runs successfully with no variations.
DROP DATABASE IF EXISTS eims;
CREATE DATABASE eims;
USE eims;

-- Create all tables with no foreign keys.
CREATE TABLE role(
	role_id 	integer 	AUTO_INCREMENT PRIMARY KEY,
    role_name 	varchar(15) NOT NULL,
    status		boolean		NOT NULL
);

CREATE TABLE facility(
	facility_id						integer 	AUTO_INCREMENT PRIMARY KEY,
    user_id							integer		NOT NULL,
    facility_name					varchar(50) NOT NULL,
    facility_address				varchar(511) NOT NULL,
    facility_found_date				date 		NOT NULL,
    subscription_expiration_date	datetime,
    hotline							varchar(11)	NOT NULL,
    business_license_number			varchar(10) NOT NULL UNIQUE,
    status							boolean		NOT NULL
);

CREATE TABLE user(
	user_id		integer 		AUTO_INCREMENT PRIMARY KEY,
    username	varchar(32)		NOT NULL,
    dob			date			NOT NULL,
    phone		varchar(11)		NOT NULL,
    email		varchar(64),
    salary		decimal(15,2),
    password	varchar(511)	NOT NULL, -- When entering check is max length 20, this length is for encoded passwords.
    address		varchar(511),
	status		boolean			NOT NULL
);

CREATE TABLE user_role(
	user_id		integer		NOT NULL,
    role_id		integer		NOT NULL,
    status		boolean		NOT NULL DEFAULT true
);

CREATE TABLE work_in(
	user_id		integer		NOT NULL,
    facility_id	integer		NOT NULL,
    status		boolean		NOT NULL
);

CREATE TABLE salary_history(
	salary_history_id	integer	AUTO_INCREMENT PRIMARY KEY,
    user_id				integer,
    salary				decimal(15,2),
    issue_date			date
);

CREATE TABLE specie(
	specie_id			integer		AUTO_INCREMENT PRIMARY KEY,
    user_id				integer		NOT NULL,
    specie_name			varchar(32) NOT NULL,
    incubation_period	integer,
    status				boolean		NOT NULL
);

CREATE TABLE breed(
	breed_id				integer 	AUTO_INCREMENT PRIMARY KEY,
    specie_id				integer,
    user_id					integer		NOT NULL,
    breed_name				varchar(32)	NOT NULL,
    average_weight_male		double,
    average_weight_female	double,
    common_disease			varchar(1000),
    growth_time				integer		NOT NULL,
    image_src				varchar(1027),
    status 					boolean		NOT NULL
);

CREATE TABLE incubation_phase(
	incubation_phase_id integer 	AUTO_INCREMENT PRIMARY KEY,
    specie_id			integer		NOT NULL,
    phase_number		integer		NOT NULL,
    phase_period		integer		NOT NULL,
    phase_description	varchar(255),		
    status				boolean 	NOT NULL
);

CREATE TABLE machine_type(
	machine_type_id		integer		AUTO_INCREMENT PRIMARY KEY,
    machine_type_name	varchar(32)	NOT NULL,
    description			varchar(255),
    status				boolean		NOT NULL
);

CREATE TABLE supplier(
	supplier_id			integer 		AUTO_INCREMENT PRIMARY KEY,
    user_id				integer			NOT NULL,
    supplier_name		varchar(32)		NOT NULL,
    facility_name	    varchar(50)		NOT NULL,
    supplier_phone		varchar(11)		NOT NULL,
    supplier_address	varchar(511)	NOT NULL,
    supplier_mail		varchar(64),
    status				boolean		NOT NULL
);

CREATE TABLE customer(
	customer_id			integer 	AUTO_INCREMENT PRIMARY KEY,
    user_id				integer		NOT NULL,
    customer_name		varchar(32)	NOT NULL,
    customer_phone		varchar(11) NOT NULL,
    customer_address	varchar(511) NOT NULL,
    customer_mail		varchar(64),
    status				boolean		NOT NULL
);

CREATE TABLE import_receipt(
	import_id	integer			AUTO_INCREMENT PRIMARY KEY,
    supplier_id	integer			NOT NULL,
    user_id		integer			NOT NULL,	-- Importer
    facility_id	integer			NOT NULL,
    import_date	datetime		NOT NULL,
    total		decimal(15,2)	NOT NULL,
    paid		decimal(15,2)	NOT NULL,
    status		boolean			NOT NULL
);

CREATE TABLE egg_batch(
	egg_batch_id	integer			AUTO_INCREMENT PRIMARY KEY,
	import_id		integer			NOT NULL,
	breed_id		integer			NOT NULL,
    amount			integer 		NOT NULL,
    price			decimal(15,2)	NOT NULL,
    need_action		boolean			NOT NULL,
    date_action		datetime		NOT NULL,
    status			boolean 		NOT NULL
);

CREATE TABLE export_receipt(
	export_id	integer			AUTO_INCREMENT PRIMARY KEY,
    customer_id	integer			NOT NULL,
    user_id		integer			NOT NULL,	-- Exporter
    facility_id	integer			NOT NULL,
    export_date	datetime		NOT NULL,
    total		decimal(15,2)	NOT NULL,
    paid		decimal(15,2)	NOT NULL,
    status		boolean			NOT NULL
);

CREATE TABLE export_detail(
	export_id		integer			NOT NULL,
    product_id		integer			NOT NULL,
    price			decimal(15,2)	NOT NULL,
    vaccine_price	decimal(15,2),
    amount			integer			NOT NULL
);

CREATE TABLE machine(
	machine_id		integer		AUTO_INCREMENT PRIMARY KEY,
    machine_type_id	integer 	NOT NULL,
    facility_id		integer		NOT NULL,
    machine_name	varchar(32)	NOT NULL,
    max_capacity	integer		NOT NULL,
    cur_capacity	integer		NOT NULL,
    added_date		date		NOT NULL,
    active			boolean		NOT NULL,
    status			boolean		NOT NULL
);

CREATE TABLE egg_product(
	product_id			integer	AUTO_INCREMENT PRIMARY KEY,
	egg_batch_id		integer	NOT NULL,
    incubation_phase_id	integer	NOT NULL,
    incubation_date		datetime,
    amount				integer NOT NULL,
    cur_amount			integer NOT NULL,
    status				boolean NOT NULL
);

CREATE TABLE egg_location(
	egg_location_id	integer	AUTO_INCREMENT PRIMARY KEY,
	product_id		integer NOT NULL,
    machine_id		integer NOT NULL,
    amount			integer NOT NULL,
    status			boolean NOT NULL
);

CREATE TABLE payroll(
	payroll_id		integer			AUTO_INCREMENT PRIMARY KEY,
    owner_id		integer			NOT NULL,
	employee_id		integer			NOT NULL,
    phone			varchar(11)		NOT NULL,
    payroll_item	varchar(50)		NOT NULL,
    payroll_amount	decimal(15,2)	NOT NULL,
	issue_date		date			NOT NULL,
    note			varchar(255),
    status			boolean 		NOT NULL
);

CREATE TABLE cost(
	cost_id 	integer 		AUTO_INCREMENT PRIMARY KEY,
    user_id		integer			NOT NULL,
    facility_id	integer 		NOT NULL,
    cost_item	varchar(50) 	NOT NULL,
    cost_amount	decimal(15,2) 	NOT NULL,
    paid_amount	decimal(15,2) 	NOT NULL,
    issue_date	date		NOT NULL,
    note		varchar(255),
    status 		boolean 		NOT NULL
);

CREATE TABLE subscription(
	subscription_id integer 		AUTO_INCREMENT PRIMARY KEY,
    cost			decimal(15,2) 	NOT NULL,
    duration		integer			NOT NULL,
    machine_quota	integer			NOT NULL,
    status 			boolean			NOT NULL
);

CREATE TABLE user_subsription(
    facility_id		integer		NOT NULL,
    subscription_id	integer 	NOT NULL,
    subscribe_date	datetime	NOT NULL,
    status			boolean		NOT NULL
);

CREATE TABLE registration(
	registration_id integer 	AUTO_INCREMENT PRIMARY KEY,
    user_id			integer 	NOT NULL,
    register_date	date		NOT NULL,
    status			smallint	NOT NULL	-- 0: Not viewed, 1: Rejected, 2: Approved
);

CREATE TABLE notification(
	notification_id		integer		AUTO_INCREMENT PRIMARY KEY,
    facility_id		    integer		NOT NULL,
    notification_brief	varchar(255),
    egg_batch_id		integer,
    date				date,
    status				boolean
);

CREATE TABLE otp(
	otp_id			integer			AUTO_INCREMENT PRIMARY KEY,
    phone_number	varchar(11)		NOT NULL,
    otp				varchar(10) 	DEFAULT "",
    time 			datetime,
    status			boolean
);


-- Add the foreign keys and references to created tables.
ALTER TABLE facility 
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id);

ALTER TABLE user
ADD CHECK (salary >= 0);

ALTER TABLE user_role
ADD FOREIGN KEY (user_id)		REFERENCES user(user_id),
ADD FOREIGN KEY (role_id)		REFERENCES role(role_id);

ALTER TABLE work_in
ADD FOREIGN KEY (user_id)		REFERENCES user(user_id),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id);

ALTER TABLE salary_history
ADD FOREIGN KEY (user_id)		REFERENCES user(user_id),
ADD CHECK (salary >= 0);

ALTER TABLE specie
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD CHECK (incubation_period > 0);

ALTER TABLE breed
ADD FOREIGN KEY (specie_id) 	REFERENCES specie(specie_id),
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD CHECK (average_weight_male > 0),
ADD CHECK (average_weight_female > 0),
ADD CHECK (growth_time > 0);

ALTER TABLE incubation_phase
ADD FOREIGN KEY (specie_id)		REFERENCES specie(specie_id);

ALTER TABLE supplier
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id);

ALTER TABLE customer
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id);

ALTER TABLE import_receipt
ADD FOREIGN KEY (supplier_id) 	REFERENCES supplier(supplier_id),
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD CHECK (total >= 0),
ADD CHECK (paid >= 0);

ALTER TABLE egg_batch
ADD FOREIGN KEY (import_id)		REFERENCES import_receipt(import_id),
ADD FOREIGN KEY (breed_id)		REFERENCES breed(breed_id),
ADD CHECK (amount > 0),
ADD CHECK (price > 0);

ALTER TABLE export_receipt
ADD FOREIGN KEY (customer_id) 	REFERENCES customer(customer_id),
ADD FOREIGN KEY (user_id) 		REFERENCES user(user_id),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD CHECK (total >= 0),
ADD CHECK (paid >= 0);

ALTER TABLE export_detail
ADD FOREIGN KEY (export_id)		REFERENCES export_receipt(export_id),
ADD FOREIGN KEY (product_id) 	REFERENCES egg_product(product_id),
ADD CHECK (price > 0),
ADD CHECK (vaccine_price >= 0),
ADD CHECK (amount > 0);

ALTER TABLE machine
ADD FOREIGN KEY (machine_type_id)	REFERENCES machine_type(machine_type_id),
ADD FOREIGN KEY (facility_id)		REFERENCES facility(facility_id),
ADD CHECK (max_capacity >= 0),
ADD CHECK (cur_capacity >= 0);

ALTER TABLE egg_product
ADD FOREIGN KEY (egg_batch_id)			REFERENCES egg_batch(egg_batch_id),
ADD FOREIGN KEY (incubation_phase_id)	REFERENCES incubation_phase(incubation_phase_id);

ALTER TABLE egg_location
ADD FOREIGN KEY (product_id)		REFERENCES egg_product(product_id),
ADD FOREIGN KEY (machine_id)		REFERENCES machine(machine_id),
ADD CHECK (amount > 0);

ALTER TABLE payroll
ADD FOREIGN KEY (owner_id)			REFERENCES user(user_id),
ADD FOREIGN KEY (employee_id)		REFERENCES user(user_id),
ADD CHECK (payroll_amount != 0);

ALTER TABLE cost
ADD FOREIGN KEY (user_id)		REFERENCES user(user_id),
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD CHECK (cost_amount >= 0);

ALTER TABLE subscription
ADD CHECK (cost >= 0),
ADD CHECK (duration >= 0),
ADD CHECK (machine_quota >= 0);

ALTER TABLE user_subsription
ADD FOREIGN KEY (facility_id)		REFERENCES facility(facility_id),
ADD FOREIGN KEY (subscription_id)	REFERENCES subscription(subscription_id);

ALTER TABLE registration
ADD FOREIGN KEY (user_id)		REFERENCES user(user_id);

ALTER TABLE notification
ADD FOREIGN KEY (facility_id)	REFERENCES facility(facility_id),
ADD FOREIGN KEY (egg_batch_id)	REFERENCES egg_batch(egg_batch_id);

-- Create Procedures:
-- Select user and facility of an user
DELIMITER //
CREATE PROCEDURE user_and_facility(uid integer) 
BEGIN
DECLARE fid integer DEFAULT 0;
-- SELECT the facility id and set to the variable fid. If at the end of the query fid == 0 then the user is a employee
SET fid = (SELECT facility_id
			FROM facility
			WHERE user_id = uid);
SELECT U.user_id, R.role_name, U.username, U.dob, U.email, U.salary, U.address, U.status AS USER_STATUS, 
		F.facility_id, F.facility_name, F.facility_address, F.facility_found_date, F.business_license_number,
        F.hotline, F.status AS FACILITY_STATUS, F.subscription_expiration_date, US.subscription_id, US.status AS SUB_STATUS
FROM user U JOIN user_role UR ON U.user_id = UR.user_id
		LEFT JOIN role R ON UR.role_id = R.role_id, 
		facility F
        LEFT JOIN user_subsription US ON F.facility_id = US.facility_id
		WHERE U.user_id = uid AND F.facility_id = (CASE
													WHEN fid != 0 THEN fid
													ELSE (SELECT facility_id FROM work_in WHERE user_id = uid)
												END)
ORDER BY US.subscribe_date DESC
LIMIT 1;
END //
DELIMITER ;


-- Add new specie and it's incubation phase
DELIMITER //
CREATE PROCEDURE create_new_specie(u_id integer, s_name varchar(63), i_period integer, embryoless integer, died_embryo integer, balut integer, hatching integer) 
BEGIN
	DECLARE s_id integer DEFAULT 0;
	INSERT INTO specie(user_id, specie_name, incubation_period, status) VALUE (u_id, s_name, i_period, 1);
    SET s_id = (SELECT specie_id 
				FROM specie 
                WHERE ((user_id = u_id) AND (specie_name = s_name) AND (incubation_period = i_period)) 
                ORDER BY specie_id DESC
                LIMIT 1);
    INSERT INTO incubation_phase(specie_id, phase_number, phase_period, phase_description, status)
	VALUES 	(s_id, -1, 0,'Trứng hư tổn, hao hụt', 1),
			(s_id, 0, 0,'Trứng vỡ/dập', 1),
			(s_id, 1, 0,'Trứng đang ấp', 1),
			(s_id, 2, embryoless,'Trứng trắng/tròn, trứng không có phôi', 1),
			(s_id, 3, died_embryo,'Trứng loãng/tàu, phôi chết non', 1),
			(s_id, 4, balut,'Trứng lộn', 1),
			(s_id, 5, hatching,'Trứng đang nở', 1),
			(s_id, 6, (i_period-1),'Trứng tắc', 1),
			(s_id, 7, (i_period-1),'Con nở', 1),
			(s_id, 8, (i_period-1),'Con đực', 1),
			(s_id, 9, (i_period-1),'Con cái', 1);
	SELECT * FROM specie WHERE specie_id = s_id;
END //
DELIMITER ;

-- Update specie and its phases
DELIMITER //
CREATE PROCEDURE update_existing_specie(s_id integer, s_name varchar(63), i_period integer, embryoless integer, died_embryo integer, balut integer, hatching integer) 
BEGIN
    UPDATE specie
	SET specie_name = s_name, incubation_period = i_period
	WHERE specie_id = s_id;
    
    UPDATE incubation_phase SET phase_period = embryoless WHERE specie_id = s_id AND phase_number = 2;
    UPDATE incubation_phase SET phase_period = died_embryo WHERE specie_id = s_id AND phase_number = 3;
    UPDATE incubation_phase SET phase_period = balut WHERE specie_id = s_id AND phase_number = 4;
    UPDATE incubation_phase SET phase_period = hatching WHERE specie_id = s_id AND phase_number = 5;
    UPDATE incubation_phase SET phase_period = i_period-1 WHERE specie_id = s_id AND phase_number >= 6;
	SELECT * FROM specie WHERE specie_id = s_id;
END //
DELIMITER ;

-- Deactivates specie and its phases
DELIMITER //
CREATE PROCEDURE deactivate_existing_specie(s_id integer) 
BEGIN
    UPDATE specie
	SET status = 0
	WHERE specie_id = s_id;
    UPDATE incubation_phase SET status = 0 WHERE specie_id = s_id;
    UPDATE breed SET status = 0 WHERE specie_id = s_id;
    SELECT * FROM specie WHERE specie_id = s_id;
END //
DELIMITER ;

-- Event set up
SET SQL_SAFE_UPDATES = 0; -- turn off safe update to update multiple rows without using primary key in WHERE clause
SET GLOBAL event_scheduler = ON;  -- enable event scheduler.
-- SELECT @@event_scheduler; -- check whether event scheduler is ON/OFF

-- Event update need_action
CREATE EVENT need_action  -- create event if not exist
ON SCHEDULE EVERY 24 HOUR -- run every day
STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY) -- the day after today
DO  -- process
UPDATE eims.egg_batch
SET  need_action = 1 
WHERE DATEDIFF(date_action, now()) = 0 -- date_action is the next day
OR date_action < now(); -- date_action is previous days (forget to update)

-- Create notification
-- After update
DELIMITER //
CREATE TRIGGER createNotificationAfterUpdate AFTER UPDATE ON eims.egg_batch
FOR EACH ROW
BEGIN
   IF (NEW.need_action <> OLD.need_action) THEN
		INSERT INTO eims.notification (facility_id, notification_brief, egg_batch_id, date,status)
		VALUES( (
		SELECT ir.facility_id FROM eims.egg_batch eb
		JOIN eims.import_receipt ir ON eb.import_id = ir.import_id
		WHERE eb.egg_batch_id = NEW.egg_batch_id
    ), "" , NEW.egg_batch_id , DATE(NEW.date_action), 1);
   END IF;
END;//
DELIMITER ;

-- After insert
DELIMITER //
CREATE TRIGGER createNotificationAfterInsert AFTER INSERT ON eims.egg_batch
FOR EACH ROW
BEGIN
		INSERT INTO eims.notification (facility_id, notification_brief, egg_batch_id, date,status)
		VALUES( (
		SELECT ir.facility_id FROM eims.egg_batch eb
		JOIN eims.import_receipt ir ON eb.import_id = ir.import_id
		WHERE eb.egg_batch_id = NEW.egg_batch_id
    ), "" , NEW.egg_batch_id , DATE(NEW.date_action), 1);
END;//
DELIMITER ;
-- Insert Data into tables
-- role
INSERT INTO role (role_id, role_name, status)
VALUES 	(1, 'ROLE_USER', 1),
		(2, 'ROLE_OWNER', 1),
		(3, 'ROLE_EMPLOYEE', 1),
		(4, 'ROLE_MODERATOR', 1),
		(5, 'ROLE_ADMIN', 0);
-- user
INSERT INTO user(user_id, username, dob, phone, email, salary, password, address, status)
VALUES 	(1,	'Nguyễn Chức', '2001-12-16', '0969044714',	'ownerchuc@gmail.com', 0, '$2a$10$Vp3h.q0WVd4LCt.jY4gbHe5bs2OKdQIa88oIFe7xR83m5UNRDaGPq',	'{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', 2),
		(2,	'Test Owner', '2001-01-01', '0987654322', 'owner@gmail.com', 0, '$2a$10$NpyM9vyE2zQ7bTdEA7nYAeoirBlK5SqI/7v23kVQd7nCZq9nI.oUu', '{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', 2),
		(3,	'Test Employee', '2001-01-01', '0987654323', 'employee@gmail.com', 0, '$2a$10$uRRgBmQCWBLgBsB4lImrguTBcHkx96MErC2fhvxmgUPDacoHPPr6W', '{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', 2),
		(4, 'Test Moderator', '2001-01-01', '0987654324', 'moderator@gmail.com', 0, '$2a$10$FNOLtGaY4coy0.CAHUxLpuBj9PIEO5J3/nqbORI8UmZuZd4eqARw2', '{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', 2),
        (5,	'Nguyễn Dương', '2001-01-01', '0852274855', 'ownerduong@gmail.com', 0, '$2a$10$UOEamoIHKWrb7BVNTOtZHebHBp6zPZ03STsKXDCZFD0BJusC9xFlO', '{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', 0);
		
INSERT INTO user_role (user_id, role_id, status) 
VALUES 	(1, 2, 1),
		(2, 2, 1),
		(3, 3, 1),
		(4, 4, 1),
		(5, 2, 1);

-- facility
INSERT INTO facility(facility_id, user_id, facility_name, facility_address, facility_found_date, subscription_expiration_date, hotline, business_license_number, status)
VALUES 	(1, 2, 'Lò trứng Tester', '{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', '1998-09-16', '2024-02-02', '0987654322', 'BE41030754', 1),
		(2, 1, 'Chu Xuong Trung', '{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', '2019-02-17', '2023-5-31', '0969044714', '4103012753', 1),
		(3, 5, 'ZTrung09', '{"city":"Tỉnh Hải Dương","district":"Huyện Gia Lộc","ward":"Xã Hoàng Diệu","street":"Thôn Nghĩa Hy"}', '2011-05-09', '2021-10-22', '0852274855', '7243012533', 0);
        
-- work_in
INSERT INTO work_in(user_id, facility_id, status)
VALUES (3, 1, 1);

-- supplier
INSERT INTO supplier(user_id, supplier_name, facility_name, supplier_phone, supplier_address, supplier_mail, status)
VALUES (2, 'Vũ Hồng Nam', 'Trang trại gà hòa phát', '0978456331', '{"city":"Tỉnh Hà Giang","district":"Huyện Quản Bạ","ward":"Xã Thái An","street":"Thôn 1"}', 'vunamhong@gmail.com', 1),
	   (2, 'Phạm Quang Việt', 'Gia cầm Việt Hương', '0978456332', '{"city":"Tỉnh Lào Cai","district":"Huyện Mường Khương","ward":"Xã La Pan Tẩn","street":"Thôn 7"}', 'viethuongeggsie@gmail.com', 1);
       
-- customer
INSERT INTO customer(user_id, customer_name, customer_phone, customer_address, customer_mail, status)
VALUES (2, 'Anh Tiến', '0978456341', '{"city":"Tỉnh Hà Giang","district":"Huyện Quản Bạ","ward":"Xã Thái An","street":"Thôn 3"}', 'tienchinngon@gmail.com', 1),
	   (2, 'Chị Hương Giang', '0978456342', '{"city":"Tỉnh Hà Giang","district":"Huyện Quản Bạ","ward":"Xã Thái An","street":"Thôn 5"}', 'huongchaykhongmui@gmail.com', 1);
        
        
-- specie
INSERT INTO specie(specie_id, user_id, specie_name, incubation_period, status)
VALUES 	(1, 1, 'Gà', 22, 1), (2, 1, 'Vịt', 31, 1), (3, 1, 'Ngan', 36, 1),
		(4, 2, 'Gà', 22, 1), (5, 2, 'Vịt', 31, 1), (6, 2, 'Ngan', 36, 1);

-- incubation_phase
INSERT INTO incubation_phase(specie_id, phase_number, phase_period, phase_description, status)
VALUES 	(1, -1, 0,'Trứng hư tổn, hao hụt', 1),
		(1, 0, 0,'Trứng vỡ/dập', 1),
		(1, 1, 0,'Trứng đang ấp', 1),
		(1, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(1, 3, 13,'Trứng loãng/tàu, phôi chết non', 1),
		(1, 4, 14,'Trứng lộn', 1),
		(1, 5, 19,'Trứng đang nở', 1),
		(1, 6, 21,'Trứng tắc', 1),
		(1, 7, 21,'Con nở', 1),
		(1, 8, 21,'Con đực', 1),
		(1, 9, 21,'Con cái', 1),
        (2, -1, 0,'Trứng hư tổn, hao hụt', 1),
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
        (3, -1, 0,'Trứng hư tổn, hao hụt', 1),
		(3, 0, 0,'Trứng vỡ/dập', 1),
        (3, 1, 0,'Trứng đang ấp', 1),
		(3, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(3, 3, 17,'Trứng loãng/tàu, phôi chết non', 1),
		(3, 4, 18,'Trứng lộn', 1),
		(3, 5, 32,'Trứng đang nở', 1),
		(3, 6, 35,'Trứng tắc', 1),
		(3, 7, 35,'Con nở', 1),
		(3, 8, 35,'Con đực', 1),
		(3, 9, 35,'Con cái', 1),
        (4, -1, 0,'Trứng hư tổn, hao hụt', 1),
        (4, 0, 0,'Trứng vỡ/dập', 1),
		(4, 1, 0,'Trứng đang ấp', 1),
		(4, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(4, 3, 13,'Trứng loãng/tàu, phôi chết non', 1),
		(4, 4, 14,'Trứng lộn', 1),
		(4, 5, 19,'Trứng đang nở', 1),
		(4, 6, 21,'Trứng tắc', 1),
		(4, 7, 21,'Con nở', 1),
		(4, 8, 21,'Con đực', 1),
		(4, 9, 21,'Con cái', 1),
        (5, -1, 0,'Trứng hư tổn, hao hụt', 1),
		(5, 0, 0,'Trứng vỡ/dập', 1),
        (5, 1, 0,'Trứng đang ấp', 1),
		(5, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(5, 3, 17,'Trứng loãng/tàu, phôi chết non', 1),
		(5, 4, 18,'Trứng lộn', 1),
		(5, 5, 28,'Trứng đang nở', 1),
		(5, 6, 30,'Trứng tắc', 1),
		(5, 7, 30,'Con nở', 1),
		(5, 8, 30,'Con đực', 1),
		(5, 9, 30,'Con cái', 1),
        (6, -1, 0,'Trứng hư tổn, hao hụt', 1),
		(6, 0, 0,'Trứng vỡ/dập', 1),
        (6, 1, 0,'Trứng đang ấp', 1),
		(6, 2, 3,'Trứng trắng/tròn, trứng không có phôi', 1),
		(6, 3, 17,'Trứng loãng/tàu, phôi chết non', 1),
		(6, 4, 18,'Trứng lộn', 1),
		(6, 5, 32,'Trứng đang nở', 1),
		(6, 6, 35,'Trứng tắc', 1),
		(6, 7, 35,'Con nở', 1),
		(6, 8, 35,'Con đực', 1),
		(6, 9, 35,'Con cái', 1);
        
-- breed
INSERT INTO breed(breed_id, specie_id, user_id, breed_name, average_weight_male, average_weight_female, common_disease, growth_time, image_src, status) 
VALUES	(1, 1, 2, "Gà Ri", 4.5, 4.0, "Marek","50","",1),
		(2, 1, 2, "Gà Lai chọi", 4.5, 4.0, "Marek","50","",1);
        
-- machine_type
INSERT INTO machine_type(machine_type_id, machine_type_name, description, status)
VALUES 	(1, 'Máy ấp', 'Máy dùng cho giai đoạn vừa mới ấp cho tới khi sắp nở, nhiệt cao, sức chứa cao', 1),
		(2, 'Máy nở', 'Máy dùng cho giai đoạn từ trứng lộn đến khi nở ra con, nhiệt thấp hơn, sức chứa thấp hơn', 1);
        
-- machine
INSERT INTO machine(machine_type_id, facility_id, machine_name, max_capacity, cur_capacity, added_date, active, status)
VALUES 	(1, 1, 'Máy ấp kho', '6000', '0', '2019-02-17', 0, 1),
		(1, 1, 'Máy ấp 2', '4000', '0', '2019-02-17', 0, 1),
		(2, 1, 'Máy nở nhỏ', '2000', '0', '2019-02-17', 0, 1),
        (2, 1, 'Máy nở to', '4000', '0', '2019-02-17', 0, 1);

-- subscription
INSERT INTO subscription(subscription_id, cost, duration, machine_quota, status)
VALUES 	(1, 0, 30, 5, 1),
		(2, 500000, 30, 5, 1),
		(3, 1300000, 90, 5, 1),
		(4, 2400000, 180, 10, 1),
		(5, 4000000, 365, 15, 1);
        
-- user_subscription
INSERT INTO user_subsription(facility_id, subscription_id, subscribe_date, status)
VALUES	('1', '3', '2022-05-31', 0),
		('1', '5', '2023-02-02', 1),
		('2', '4', '2021-04-25', 0);
       
-- registration
INSERT INTO registration(registration_id, user_id, register_date, status)
VALUES 	(1, 2, '2022-05-31', 2),
		(2, 1, '2022-05-31', 2),
		(3, 5, '2022-05-31', 0);
   

