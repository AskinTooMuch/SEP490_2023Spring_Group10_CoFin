/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.facility.CreateFacilityDTO;
import com.example.eims.dto.facility.UpdateFacilityDTO;
import com.example.eims.service.interfaces.IFacilityService;
import org.springframework.http.ResponseEntity;

public class FacilityServiceImpl implements IFacilityService {
    /**
     * Get all facilities.
     *
     * @return list of Facilities
     */
    @Override
    public ResponseEntity<?> getAllFacility() {
        return null;
    }

    /**
     * Get the facility of a user.
     *
     * @param userId the id of the user
     * @return a facility
     */
    @Override
    public ResponseEntity<?> getFacilityOfOwner(Long userId) {
        return null;
    }

    /**
     * Create a facility of a user.
     *
     * @param createFacilityDTO contains the name, address, found date, subscription's expiration date, hotline and
     *                          status of the facility
     * @return
     */
    @Override
    public ResponseEntity<?> createFacility(CreateFacilityDTO createFacilityDTO) {
        return null;
    }

    /**
     * Show form to update the facility of a user.
     *
     * @param facilityId the id of the user
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long facilityId) {
        return null;
    }

    /**
     * Update a facility of a user.
     *
     * @param facilityId        the id of facility
     * @param updateFacilityDTO contains the new name, address, found date, subscription's expiration date,
     * @return
     */
    @Override
    public ResponseEntity<?> updateFacility(Long facilityId, UpdateFacilityDTO updateFacilityDTO) {
        return null;
    }

    /**
     * Delete a facility of a user.
     *
     * @param facilityId the id of the facility
     * @return
     */
    @Override
    public ResponseEntity<?> deleteFacility(Long facilityId) {
        return null;
    }
}
