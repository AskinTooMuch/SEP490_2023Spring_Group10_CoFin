/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 07/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.WorkIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkInRepository extends JpaRepository<WorkIn, Long> {
    Optional<WorkIn> findByUserId(Long userId);
    Optional<List<WorkIn>> findAllByFacilityId(Long facilityId);
    Optional<List<WorkIn>> findAllWorkInsByFacilityIdIn(Long[] facilityIdList);
}
