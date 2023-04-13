/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 15/03/2023    1.0        DuongVV          First Deploy<br>
 * 13/04/2023    2.0         DuongVV         Add update locations egg batch<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.eggBatch.UpdateEggBatchDTO;
import com.example.eims.dto.eggBatch.UpdateLocationEggBatchDTO;
import com.example.eims.service.interfaces.IEggBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/eggBatch")
public class EggBatchController {
    @Autowired
    private IEggBatchService eggBatchService;

    /**
     * View an egg batch's details.
     *
     * @param eggBatchId the id of egg batch
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<?> getEggBatch(@RequestParam Long eggBatchId) {
        return eggBatchService.getEggBatch(eggBatchId);
    }

    /**
     * View list egg batch.
     *
     * @param facilityId the id of facility
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<?> viewListEggBatch(@RequestParam Long facilityId) {
        return eggBatchService.viewListEggBatch(facilityId);
    }

    /**
     * Update egg batch's status.
     *
     * @param updateEggBatchDTO
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateEggBatch(@RequestBody UpdateEggBatchDTO updateEggBatchDTO) {
        return eggBatchService.updateEggBatch(updateEggBatchDTO);
    }

    /**
     * Update egg batch's locations.
     *
     * @param updateLocationEggBatchDTO
     * @return
     */
    @PutMapping("/update/location")
    public ResponseEntity<?> updateLocationEggBatch(@RequestBody UpdateLocationEggBatchDTO updateLocationEggBatchDTO) {
        return eggBatchService.updateLocationEggBatch(updateLocationEggBatchDTO);
    }

    /**
     * Update egg batch's state.
     *
     * @param eggBatchId the id of egg batch
     * @param done       boolean value for state
     * @return
     */
    @PutMapping("/update/done")
    public ResponseEntity<?> updateEggBatch(@RequestParam Long eggBatchId, @RequestParam boolean done) {
        return eggBatchService.setDoneEggBatch(eggBatchId, done);
    }
}
