/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 * 02/03/2023    2.0        DuongVV          New code structure<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.facility.CreateFacilityDTO;
import com.example.eims.dto.facility.UpdateFacilityDTO;
import com.example.eims.service.interfaces.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/facility")
public class FacilityController {
    @Autowired
    private IFacilityService facilityService;

    /**
     * Get all facilities.
     *
     * @return list of Facilities
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllFacility() {
        return facilityService.getAllFacility();
    }

    /**
     * Get the facility of a user.
     *
     * @param userId the id of the user
     * @return a facility
     */
    @GetMapping("/get")
    public ResponseEntity<?> getFacilityOfOwner(@RequestParam Long userId) {
        return facilityService.getFacilityOfOwner(userId);
    }

    /**
     * Create a facility of a user.
     *
     * @param createFacilityDTO contains the name, address, found date, subscription's expiration date, hotline and
     * status of the facility
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createFacility(@RequestBody CreateFacilityDTO createFacilityDTO) {
        return facilityService.createFacility(createFacilityDTO);
    }

    /**
     * Show form to update the facility of a user.
     *
     * @param facilityId the id of the user
     * @return
     */
    @GetMapping("/update/get")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long facilityId) {
        return facilityService.showFormUpdate(facilityId);
    }

    /**
     * Update a facility of a user.
     *
     * @param facilityId the id of facility
     * @param updateFacilityDTO contains the new name, address, found date, subscription's expiration date,
     * @return
     */
    @PutMapping("/update/save")
    public ResponseEntity<?> updateFacility(@RequestParam Long facilityId, @RequestBody UpdateFacilityDTO updateFacilityDTO) {
        return facilityService.updateFacility(facilityId, updateFacilityDTO);
    }

    /**
     * Delete a facility of a user.
     *
     * @param facilityId the id of the facility
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFacility(@RequestParam Long facilityId) {
        return facilityService.deleteFacility(facilityId);
    }
}
