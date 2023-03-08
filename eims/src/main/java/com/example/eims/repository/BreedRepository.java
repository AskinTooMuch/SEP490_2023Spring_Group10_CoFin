/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 07/03/2023    1.0        ChucNV           First Deploy<br>
 */
package com.example.eims.repository;

import com.example.eims.entity.Breed;
import com.example.eims.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    Optional<List<Breed>> findBySpecieId(Long specieId);
    Optional<List<Breed>> findByUserId(Long userId);
}
