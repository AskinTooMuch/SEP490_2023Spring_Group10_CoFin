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

package com.example.eims.service.interfaces;

import com.example.eims.dto.eggBatch.UpdateEggBatchDTO;
import org.springframework.http.ResponseEntity;

public interface IEggBatchService {

    /**
     * View an egg batch's details.
     *
     * @param eggBatchId the id of egg batch
     * @return
     */
    public ResponseEntity<?> getEggBatch(Long eggBatchId);

    /**
     * View list egg batch.
     *
     * @param facilityId the id of facility
     * @return
     */
    public ResponseEntity<?> viewListEggBatch(Long facilityId);

    /**
     * Update egg batch's status.
     *
     * @param updateEggBatchDTO
     * @return
     */
    public ResponseEntity<?> updateEggBatch(UpdateEggBatchDTO updateEggBatchDTO);

}
