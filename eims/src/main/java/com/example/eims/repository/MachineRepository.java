/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 17/02/2023    1.0        DuongVV          First Deploy<br>
 * 28/02/2023    2.0        DuongVV          Add Paging<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.crypto.Mac;
import java.util.List;
import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    Optional<List<Machine>> findByFacilityId(Long facilityId);
    Optional<Machine> findByMachineId(Long machineId);
    boolean existsByMachineId(Long machineId);
    Page<Machine> findAllByFacilityId(Long facilityId, Pageable pageable);
    @Query(value = "SELECT * FROM eims.machine WHERE max_capacity > cur_capacity AND facility_id = ?1",
            nativeQuery = true)
    Optional<List<Machine>> findAllNotFull(Long facilityId);
    @Query(value = "SELECT IFNULL(SUM(amount), 0) FROM eims.egg_location WHERE machine = ?1  GROUP BY machine_id",
            nativeQuery = true)
    int getCurrentAmount(Long machineId);
}
