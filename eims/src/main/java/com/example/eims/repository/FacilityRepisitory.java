package com.example.eims.repository;

import com.example.eims.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface FacilityRepisitory extends JpaRepository<Facility, Long> {
    Optional<Facility> findByUserId(Long userId);
}
