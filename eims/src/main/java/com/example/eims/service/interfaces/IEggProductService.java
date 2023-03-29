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

package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface IEggProductService {

    /**
     * Get all available egg product.
     *
     * @param facilityId the id of the facility
     * @return
     */
    public ResponseEntity<?> getAllAvailableProduct(Long facilityId);

    /**
     * Get all egg product of a phase.
     *
     * @param facilityId the id of the facility
     * @param phaseNumber the phase number of egg product
     * @return
     */
    public ResponseEntity<?> getAvailableProductByPhase(Long facilityId, int phaseNumber);
}
