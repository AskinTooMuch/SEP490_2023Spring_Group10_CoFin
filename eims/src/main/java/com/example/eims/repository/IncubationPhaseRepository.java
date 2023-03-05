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

public interface IncubationPhaseRepository extends JpaRepository<IncubationPhase, Long> {
}
