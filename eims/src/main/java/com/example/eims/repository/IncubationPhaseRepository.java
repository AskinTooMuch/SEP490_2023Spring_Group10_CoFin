/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 05/03/2023    1.0        ChucNV      First Deploy<br>
 */
package com.example.eims.repository;

import com.example.eims.entity.IncubationPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IncubationPhaseRepository extends JpaRepository<IncubationPhase, Long> {
    Optional<IncubationPhase> findByIncubationPhaseId(Long incubationPhaseId);
    @Query(value = "SELECT ip.* FROM eims.egg_batch eb " +
            "JOIN eims.breed b ON eb.breed_id = b.breed_id " +
            "JOIN eims.specie s ON b.specie_id = s.specie_id " +
            "JOIN eims.incubation_phase ip ON s.specie_id = ip.specie_id " +
            "WHERE eb.egg_batch_id = ?1", nativeQuery = true)
    List<IncubationPhase> getListIncubationPhaseForEggBatch(Long eggProductId);
}
