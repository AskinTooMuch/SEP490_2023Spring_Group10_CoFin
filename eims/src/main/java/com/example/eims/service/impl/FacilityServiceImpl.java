/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 *
 */

package com.example.eims.service.impl;

import com.example.eims.dto.facility.CreateFacilityDTO;
import com.example.eims.dto.facility.UpdateFacilityDTO;
import com.example.eims.entity.Facility;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.IFacilityService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements IFacilityService {

    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final UserRepository userRepository;

    public FacilityServiceImpl(FacilityRepository facilityRepository, UserRepository userRepository) {
        this.facilityRepository = facilityRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all facilities.
     *
     * @return list of Facilities
     */
    @Override
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
    @Override
    public ResponseEntity<?> getFacilityOfOwner(Long userId) {
        // Get a facility of the current User
        Optional<Facility> facility = facilityRepository.findByUserId(userId);
        if (facility.isPresent()) {
            return new ResponseEntity<>(facility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
        // Check if Owner's account is active?
        if (userRepository.getStatusByUserId(createFacilityDTO.getUserId()) == 0){
            return new ResponseEntity<>("Activate your account first!", HttpStatus.BAD_REQUEST);
        } else {
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
    }

    /**
     * Show form to update the facility of a user.
     *
     * @param facilityId the id of the user
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long facilityId) {
        // Get a facility of the current User
        Optional<Facility> facility = facilityRepository.findByFacilityId(facilityId);
        if (facility.isPresent()) {
            return new ResponseEntity<>(facility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a facility of a user.
     *
     * @param updateFacilityDTO contains the new name, address, found date, subscription's expiration date,
     * @return
     */
    @Override
    public ResponseEntity<?> updateFacility(UpdateFacilityDTO updateFacilityDTO) {
        // Retrieve facility's new information and create new one
        Optional<Facility> facilityOptional = facilityRepository.findById(updateFacilityDTO.getFacilityId());
        if (facilityOptional.isPresent()) {
            Facility facility = facilityOptional.get();
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
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

}
