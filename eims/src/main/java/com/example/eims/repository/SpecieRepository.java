package com.example.eims.repository;

import com.example.eims.entity.Specie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecieRepository extends JpaRepository<Specie, Long> {
    Optional<List<Specie>> findByUserId(Long userId);
}
