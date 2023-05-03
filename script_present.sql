
-- --------------------------------------------------------------------------
-- import receipt
INSERT INTO eims.import_receipt
(import_id, supplier_id, user_id, facility_id, import_date, total, paid, status)
VALUES 		(1,1,2,1,date_sub(now(),INTERVAL 7 HOUR),10000000,0,1),
			(2,1,2,1,date_sub(date_sub(now(),INTERVAL 7 HOUR),INTERVAL 3 DAY),10000000,0,1),
			(3,1,2,1,date_sub(date_sub(now(),INTERVAL 7 HOUR),INTERVAL 13 DAY),10000000,0,1),
			(4,1,2,1,date_sub(date_sub(now(),INTERVAL 7 HOUR),INTERVAL 14 DAY),10000000,0,1),
			(5,1,2,1,date_sub(date_sub(now(),INTERVAL 7 HOUR),INTERVAL 19 DAY),10000000,0,1),
			(6,1,2,1,date_sub(date_sub(now(),INTERVAL 7 HOUR),INTERVAL 21 DAY),10000000,0,1);
            
-- --------------------------------------------------------------------------   
-- egg batch
INSERT INTO eims.egg_batch
(egg_batch_id, import_id, breed_id, amount, price, need_action, date_action, status)
VALUES		(1,1,1,1000,100000,1,date_sub(now(),INTERVAL 7 HOUR),1),
			(2,2,1,1000,100000,1,date_sub(now(),INTERVAL 7 HOUR),1),
			(3,3,1,1000,100000,1,date_sub(now(),INTERVAL 7 HOUR),1),
			(4,4,1,1000,100000,1,date_sub(now(),INTERVAL 7 HOUR),1),
			(5,5,1,1000,100000,1,date_sub(now(),INTERVAL 7 HOUR),1),
			(6,6,1,1000,100000,1,date_sub(now(),INTERVAL 7 HOUR),1);

-- --------------------------------------------------------------------------
-- egg product
INSERT INTO eims.egg_product
(product_id, egg_batch_id, incubation_phase_id, incubation_date, amount, cur_amount, status)
VALUES
-- 1, day 0, update phase number 0
-- 2, day 3, update phase number 2
(1,2,1,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 3 DAY),100,100,1),
(2,2,2,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 3 DAY),100,100,1),
(3,2,3,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 3 DAY),800,800,1),
-- 3, day 13, update phase number 3
(4,3,1,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 13 DAY),100,100,1),
(5,3,2,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 13 DAY),100,100,1),
(6,3,3,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 13 DAY),800,800,1),
(7,3,4,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 10 DAY),0,0,1),   
-- 4, day 14, update phase number 4
(8,4,1,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 14 DAY),100,100,1),
(9,4,2,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 14 DAY),100,100,1),
(10,4,3,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 14 DAY),800,800,1),
(11,4,4,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 10 DAY),0,0,1),   
(12,4,5,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 1 DAY),0,0,1),              
-- 5, day 19, update phase number 5
(13,5,1,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 19 DAY),100,100,1),
(14,5,2,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 19 DAY),100,100,1),
(15,5,3,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 19 DAY),800,800,1),
(16,5,4,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 16 DAY),0,0,1),   
(17,5,5,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 6 DAY),0,0,1),  
(18,5,6,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 5 DAY),0,0,1),
-- 6, day 21, update phase number 6
(19,6,1,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 21 DAY),100,100,1),
(20,6,2,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 21 DAY),100,100,1),
(21,6,3,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 21 DAY),800,0,1),
(22,6,4,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 18 DAY),0,0,1),   
(23,6,5,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 8 DAY),0,0,1),  
(24,6,6,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 7 DAY),0,0,1),
(25,6,7,date_sub(date_sub(now(),INTERVAL 7 HOUR), INTERVAL 2 DAY),800,800,1);

-- --------------------------------------------------------------------------
-- egg location
INSERT INTO eims.egg_location
(egg_location_id, product_id, machine_id, amount, status)
VALUES 		(1,3,1,800,1),
			(2,6,1,800,1),
            (3,10,1,800,1),
            (4,15,1,800,1),
            (5,25,4,800,1);
            
-- --------------------------------------------------------------------------
-- machine
UPDATE eims.machine
SET cur_capacity = 3200,
active = 1
WHERE machine_id = 1;
-- ------------------
UPDATE eims.machine
SET cur_capacity = 800,
active = 1
WHERE machine_id = 4;


-- ----------------------------------------
SELECT * FROM eims.import_receipt;

