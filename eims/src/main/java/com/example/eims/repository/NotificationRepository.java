/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 29/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<List<Notification>> findAllByFacilityIdAndDate(Long facilityId, Date date);
    Optional<List<Notification>> findAllByFacilityIdAndDateBefore(Long facilityId, Date date);
    @Query(value = "SELECT * FROM eims.notification WHERE facility_id = ?1 AND date <= ?2 ORDER BY date DESC LIMIT 5"
            , nativeQuery = true)
    Optional<List<Notification>> getTopNotification(Long facilityId, Date date);
}
