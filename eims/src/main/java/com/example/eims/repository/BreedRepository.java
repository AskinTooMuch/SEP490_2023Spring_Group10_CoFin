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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    Optional<List<Breed>> findBySpecieId(Long specieId);
    Optional<List<Breed>> findByUserId(Long userId);
    Optional<Breed> findByBreedId(Long breedId);
    @Query(value = "SELECT b.* FROM eims.egg_product ep " +
            "JOIN eims.egg_batch eb ON ep.egg_batch_id = eb.egg_batch_id " +
            "JOIN eims.breed b ON eb.breed_id = b.breed_id " +
            "WHERE ep.product_id = ?1", nativeQuery = true)
    Breed getBreedOfProduct(Long productId);
}
