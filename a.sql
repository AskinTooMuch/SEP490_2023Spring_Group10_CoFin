USE eims;
DELIMITER //
CREATE PROCEDURE 'user_and_facility'(id integer) 
BEGIN
SELECT U.user_id, UR.role_name, U.username, U.dob, U.email, U.salary, U.address, U.status AS USER_STATUS, 
		F.facility_id, F.facility_name, F.facility_found_date, F.hotline, F.status AS FACILITY_STATUS, F.subscription_expiration_date,
        US.subscription_id

FROM user U JOIN user_role UR ON U.role_id = UR.role_id
		LEFT JOIN facility F ON U.user_id = F.user_id
        LEFT JOIN user_subsription US ON F.facility_id = US.facility_id
WHERE U.user_id = id;
END
DELIMITER ;

CALL user_and_facility(2) 

