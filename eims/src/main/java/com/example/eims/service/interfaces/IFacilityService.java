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

package com.example.eims.service.interfaces;

import com.example.eims.dto.facility.CreateFacilityDTO;
import com.example.eims.dto.facility.UpdateFacilityDTO;
import org.springframework.http.ResponseEntity;

public interface IFacilityService {
    /**
     * Get all facilities.
     *
     * @return list of Facilities
     */
    public ResponseEntity<?> getAllFacility();

    /**
     * Get the facility of a user.
     *
     * @param userId the id of the user
     * @return a facility
     */
    public ResponseEntity<?> getFacilityOfOwner(Long userId);

    /**
     * Create a facility of a user.
     *
     * @param createFacilityDTO contains the name, address, found date, subscription's expiration date, hotline and
     * status of the facility
     * @return
     */
    public ResponseEntity<?> createFacility(CreateFacilityDTO createFacilityDTO);

    /**
     * Show form to update the facility of a user.
     *
     * @param facilityId the id of the user
     * @return
     */
    public ResponseEntity<?> showFormUpdate(Long facilityId);

    /**
     * Update a facility of a user.
     *
     * @param facilityId the id of facility
     * @param updateFacilityDTO contains the new name, address, found date, subscription's expiration date,
     * @return
     */
    public ResponseEntity<?> updateFacility(Long facilityId, UpdateFacilityDTO updateFacilityDTO);

    /**
     * Delete a facility of a user.
     *
     * @param facilityId the id of the facility
     * @return
     */
    public ResponseEntity<?> deleteFacility(Long facilityId);
}
