package com.example.eims.repository;

import com.example.eims.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    List<Machine> findByFacilityId(Long facilityId);
}
