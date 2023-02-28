/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.facility.CreateFacilityDTO;
import com.example.eims.dto.facility.UpdateFacilityDTO;
import com.example.eims.entity.Facility;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/facility")
public class FacilityController {
    @Autowired
    private FacilityRepository facilityRepository;

    /**
     * Get all facilities.
     *
     * @return list of Facilities
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllFacility() {
        // Get all facilities
        List<Facility> facilityList = facilityRepository.findAll();
        return new ResponseEntity<>(facilityList, HttpStatus.OK);
    }

    /**
     * Get the facility of a user.
     *
     * @param userId the id of the user
     * @return a facility
     */
    @GetMapping("/get")
    public ResponseEntity<?> getFacilityOfOwner(@RequestParam Long userId) {
        // Get a facility of the current User
        Facility facility = facilityRepository.findByUserId(userId).orElse(null);
        if (facility != null) {
            return new ResponseEntity<>(facility, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No facility", HttpStatus.OK);
        }
    }

    /**
     * Create a facility of a user.
     *
     * @param createFacilityDTO contains the name, address, found date, subscription's expiration date, hotline and
     *                          status of the facility
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createFacility(@RequestBody CreateFacilityDTO createFacilityDTO) {
        // Retrieve facility information and create new one
        Facility facility = new Facility();
        facility.setUserId(createFacilityDTO.getUserId());
        facility.setFacilityName(createFacilityDTO.getFacilityName());
        facility.setFacilityAddress(createFacilityDTO.getFacilityAddress());

        StringDealer stringDealer = new StringDealer();
        String fDate = createFacilityDTO.getFoundDate();
        String eDate = createFacilityDTO.getExpirationDate();
        Date foundDate = stringDealer.convertToDateAndFormat(fDate);
        Date expirationDate = stringDealer.convertToDateAndFormat(eDate);
        facility.setFacilityFoundDate(foundDate);
        facility.setSubscriptionExpirationDate(expirationDate);

        facility.setHotline(createFacilityDTO.getHotline());
        facility.setStatus(createFacilityDTO.getStatus());
        // Save
        facilityRepository.save(facility);
        return new ResponseEntity<>("Facility created!", HttpStatus.OK);
    }

    /**
     * Show form to update the facility of a user.
     *
     * @param facilityId the id of the user
     * @return
     */
    @GetMapping("/update/get")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long facilityId) {
        // Get a facility of the current User
        Facility facility = facilityRepository.findByFacilityId(facilityId).orElse(null);
        if (facility != null) {
            return new ResponseEntity<>(facility, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No facility", HttpStatus.OK);
        }
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
        // Retrieve facility's new information and create new one
        Facility facility = facilityRepository.findById(facilityId).get();
        facility.setFacilityName(updateFacilityDTO.getFacilityName());
        facility.setFacilityAddress(updateFacilityDTO.getFacilityAddress());

        StringDealer stringDealer = new StringDealer();
        String fDate = updateFacilityDTO.getFoundDate();
        String eDate = updateFacilityDTO.getExpirationDate();
        Date foundDate = stringDealer.convertToDateAndFormat(fDate);
        Date expirationDate = stringDealer.convertToDateAndFormat(eDate);
        System.out.println(foundDate);
        System.out.println(expirationDate);
        facility.setFacilityFoundDate(foundDate);
        facility.setSubscriptionExpirationDate(expirationDate);
        facility.setSubscriptionExpirationDate(expirationDate);
        facility.setHotline(updateFacilityDTO.getHotline());
        facility.setStatus(updateFacilityDTO.getStatus());
        // Save
        facilityRepository.save(facility);
        return new ResponseEntity<>("Facility updated!", HttpStatus.OK);
    }

    /**
     * Delete a facility of a user.
     *
     * @param facilityId the id of the facility
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFacility(@RequestParam Long facilityId) {
        // Delete
        facilityRepository.deleteById(facilityId);
        return new ResponseEntity<>("Facility deleted!", HttpStatus.OK);
    }
}
