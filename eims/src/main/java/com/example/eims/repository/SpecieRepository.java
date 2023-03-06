/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 22/02/2023    1.0        ChucNV      First Deploy<br>
 * 05/03/2023    2.0        ChucNV      Implement create new specie<br>
 */

package com.example.eims.repository;

import com.example.eims.dto.specie.NewSpecieDTO;
import com.example.eims.entity.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.annotation.Native;
import java.util.List;
import java.util.Optional;

public interface SpecieRepository extends JpaRepository<Specie, Long> {
    Optional<List<Specie>> findByUserId(Long userId);

    @Query(value = "CALL create_new_specie(?1, ?2, ?3, ?4, ?5, ?6)"
    , nativeQuery = true)
    Optional<?> createNewSpecie(Long userId, String specieName, int incubationPeriod, int embryoless, int diedEmbryo, int hatching);

    @Query(value = "CALL update_existing_specie(?1, ?2, ?3, ?4, ?5, ?6)"
            , nativeQuery = true)
    Optional<?> updateSpecie(Long specieId, String specieName, int incubationPeriod, int embryoless, int diedEmbryo, int hatching);

    @Query(value = "CALL deactivate_existing_specie(?1)"
            , nativeQuery = true)
    Optional<?> deactivateById(Long specieId);
}
