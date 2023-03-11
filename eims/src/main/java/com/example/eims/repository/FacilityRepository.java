/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 17/02/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findByUserId(Long userId);
    Optional<Facility> findByFacilityId(Long facilityId);
    boolean existsByFacilityId(Long facilityId);
    @Query(value = "SELECT status FROM eims.facility where facility_id = ?1", nativeQuery = true)
    boolean getStatusById(Long facilityId);
    @Query(value = "SELECT facility_id FROM eims.facility where user_id = ?1", nativeQuery = true)
    Long getFacilityIdByUserId(Long userId);
}
