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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/facility")
public class FacilityController {
    @Autowired
    private FacilityRepository facilityRepository;

    /**
     * API to get all facilities.
     *
     * @param
     * @return list of Facilities
     */
    @GetMapping("/all")
    public List<Facility> getAllFacility() {
        // Get all facilities
        List<Facility> facilityList = facilityRepository.findAll();
        return facilityList;
    }

    /**
     * API to get the facility of a user.
     * userId is the id of the user
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Facility getFacility(@PathVariable Long userId) {
        // Get a facility of the current User
        Facility facility = facilityRepository.findByUserId(userId).get();
        return facility;
    }

    /**
     * API to create a facility of a user.
     * The DTO contains the name, address, found date, subscription's expiration date,
     * hotline and status of the facility
     *
     * @param createFacilityDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createFacility(CreateFacilityDTO createFacilityDTO) {
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
     * API to create a facility of a user.
     * userId is the id of the user
     * updateFacilityDTO contains the new name, address, found date, subscription's expiration date,
     * hotline and status of the facility
     *
     * @param facilityId
     * @param updateFacilityDTO
     * @return
     */
    @PutMapping("/update/{facilityId}")
    public ResponseEntity<?> updateFacility(@PathVariable Long facilityId, UpdateFacilityDTO updateFacilityDTO) {
        // Retrieve facility's new information and create new one
        Facility facility = facilityRepository.findById(facilityId).get();
        facility.setFacilityName(updateFacilityDTO.getFacilityName());
        facility.setFacilityAddress(updateFacilityDTO.getFacilityAddress());

        StringDealer stringDealer = new StringDealer();
        String fDate = updateFacilityDTO.getFoundDate();
        String eDate = updateFacilityDTO.getExpirationDate();
        Date foundDate = stringDealer.convertToDateAndFormat(fDate);
        Date expirationDate = stringDealer.convertToDateAndFormat(eDate);
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
     * API to delete a facility of a user.
     * facilityId is the id of the facility
     *
     * @param facilityId
     * @return
     */
    @DeleteMapping("/delete/{facilityId}")
    public ResponseEntity<?> deleteFacility(@PathVariable Long facilityId) {
        // Delete
        facilityRepository.deleteById(facilityId);
        return new ResponseEntity<>("Facility deleted!", HttpStatus.OK);
    }
}
