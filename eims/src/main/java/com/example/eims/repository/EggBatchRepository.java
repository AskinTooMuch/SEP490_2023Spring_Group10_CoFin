/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 15/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.EggBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EggBatchRepository extends JpaRepository<EggBatch, Long> {
    Optional<List<EggBatch>> findByImportId(Long importId);
    Optional<EggBatch> findByEggBatchId(Long eggBatchId);
}
