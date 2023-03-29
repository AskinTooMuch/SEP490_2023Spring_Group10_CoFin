/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 28/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.service.interfaces.IEggProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/eggProduct")
public class EggProductController {
    @Autowired
    private IEggProductService eggProductService;

    /**
     * Get all available egg product.
     *
     * @param facilityId the id of the facility
     * @return
     */
    @GetMapping("/allAvailable")
    public ResponseEntity<?> getAllAvailableProduct(@RequestParam Long facilityId){
        return  eggProductService.getAllAvailableProduct(facilityId);
    }

    /**
     * Get all egg product of a phase.
     *
     * @param facilityId the id of the facility
     * @param phaseNumber the phase number of egg product
     * @return
     */
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableProductByPhase(@RequestParam Long facilityId, @RequestParam int phaseNumber){
        return eggProductService.getAvailableProductByPhase(facilityId, phaseNumber);
    }

}
