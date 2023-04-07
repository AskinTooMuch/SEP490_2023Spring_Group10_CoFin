/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 04/04/2023    1.0        ChucNV      First Deploy<br>
 * 05/04/2023    2.0        ChucNV      Add get discount<br>
 * 05/04/2023    2.0        ChucNV      Add flipStatusSubscription and getRunningMachineByFacility <br>
 * 05/04/2023    2.0        ChucNV      Add getValidSubscription <br>
 */
package com.example.eims.repository;

import com.example.eims.entity.UserSubscription;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    @Query(value = "SELECT COALESCE(FLOOR(ROUND((SELECT S.cost*0.3*(datediff(US.expire_date, now())/S.duration) \n" +
            "FROM subscription S JOIN user_subscription US ON S.subscription_id = US.subscription_id\n" +
            "WHERE US.facility_id = ?1 AND US.status = 1\n" +
            "LIMIT 1), -3)), 0) AS Discount", nativeQuery = true)
    float getDiscountByFacility(Long facilityId);

    @Query(value = "SELECT COUNT(machine_id) AS RunningMachine\n" +
            "FROM machine\n" +
            "WHERE facility_id = ?1 AND active = 1;", nativeQuery = true)
    int getRunningMachineByFacility(Long facilityId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_subscription\n" +
            "SET status = false\n" +
            "WHERE facility_id = ?1 AND us_id != ?2", nativeQuery = true)
    void flipStatusSubscription(Long facilityId, Long usId);

    @Query(value = "SELECT COALESCE((SELECT us_id FROM user_subscription US JOIN subscription S ON US.subscription_id WHERE US.status = 1 AND facility_id = ?1\n" +
            "AND S.machine_quota >= (SELECT COUNT(machine_id) AS RunningMachine FROM machine WHERE facility_id = ?1 AND active = 1 LIMIT 1) ORDER BY subscribe_date DESC LIMIT 1), 0) AS us_id; ", nativeQuery = true)
    Long getValidSubscription(Long facilityId);

    Optional<UserSubscription> getUserSubscriptionByFacilityIdAndStatus(Long facilityId, boolean status);
}
