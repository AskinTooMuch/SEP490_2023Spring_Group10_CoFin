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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements IFacilityService {

    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final UserRepository userRepository;
    private final StringDealer stringDealer;

    public FacilityServiceImpl(FacilityRepository facilityRepository, UserRepository userRepository) {
        this.facilityRepository = facilityRepository;
        this.userRepository = userRepository;
        this.stringDealer = new StringDealer();
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
            // Check if facility is still running
            int facilityStatus = facilityRepository.getStatusById(facilityId) ? 1 : 0;
            if (facilityStatus == 0) { /* status = 0 (deactivated) */
                return new ResponseEntity<>("Cơ sở đã ngừng hoạt động", HttpStatus.BAD_REQUEST);
            }
            UpdateFacilityDTO updateFacilityDTO = new UpdateFacilityDTO();
            updateFacilityDTO.getFromEntity(facility.get());
            return new ResponseEntity<>(updateFacilityDTO, HttpStatus.OK);
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
            // Check if facility is still running
            int facilityStatus = facilityRepository.getStatusById(updateFacilityDTO.getFacilityId()) ? 1 : 0;
            if (facilityStatus == 0) { /* status = 0 (deactivated) */
                return new ResponseEntity<>("Cơ sở đã ngừng hoạt động", HttpStatus.BAD_REQUEST);
            }
            Facility facility = facilityOptional.get();
            // Facility name
            if (updateFacilityDTO.getFacilityName() == null || updateFacilityDTO.getFacilityName().equals("")) { /* Facility name empty */
                return new ResponseEntity<>("Tên cơ sở không được để trống", HttpStatus.BAD_REQUEST);
            }
            String name = stringDealer.trimMax(updateFacilityDTO.getFacilityName());
            // Found date
            if (updateFacilityDTO.getFoundDate() == null || updateFacilityDTO.getFoundDate().equals("")) { /* Found date is empty */
                return new ResponseEntity<>("Ngày thành lập không được để trống", HttpStatus.BAD_REQUEST);
            }
            String fDate = updateFacilityDTO.getFoundDate();
            Date foundDate = stringDealer.convertToDateAndFormat(fDate);
            if(foundDate.after(Date.valueOf(LocalDate.now()))){
                return new ResponseEntity<>("Ngày thành lập không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            // Hotline
            if (updateFacilityDTO.getHotline() == null || stringDealer.trimMax(updateFacilityDTO.getHotline()).equals("")) { /* Hotline empty */
                return new ResponseEntity<>("Hotline không được để trống", HttpStatus.BAD_REQUEST);
            }
            String hotline = stringDealer.trimMax(updateFacilityDTO.getHotline());
            if (!stringDealer.checkPhoneRegex(hotline)) { /* Hotline is not valid */
                return new ResponseEntity<>("Hotline không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            // Facility address
            if (updateFacilityDTO.getFacilityAddress() == null || stringDealer.trimMax(updateFacilityDTO.getFacilityAddress()).equals("")) { /* Facility address empty */
                return new ResponseEntity<>("Địa chỉ cơ sở không được để trống", HttpStatus.BAD_REQUEST);
            }
            String address = stringDealer.trimMax(updateFacilityDTO.getFacilityAddress());
            // Business license number
            if (updateFacilityDTO.getBusinessLicenseNumber() == null || stringDealer.trimMax(updateFacilityDTO.getBusinessLicenseNumber()).equals("")) { /* Business License Number empty */
                return new ResponseEntity<>("Số đăng kí kinh doanh không được để trống", HttpStatus.BAD_REQUEST);
            }
            String licenseNumber = stringDealer.trimMax(updateFacilityDTO.getBusinessLicenseNumber());
            // Status
            int status = updateFacilityDTO.getStatus();

            // Set attribute
            facility.setFacilityName(name);
            facility.setFacilityFoundDate(foundDate);
            facility.setHotline(hotline);
            facility.setFacilityAddress(address);
            facility.setBusinessLicenseNumber(licenseNumber);
            facility.setStatus(status);
            // Save
            facilityRepository.save(facility);
            return new ResponseEntity<>("Cập nhật thông tin cơ sở thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
