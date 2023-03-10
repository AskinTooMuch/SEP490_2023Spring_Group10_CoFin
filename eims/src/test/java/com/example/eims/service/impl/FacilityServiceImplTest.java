package com.example.eims.service.impl;

import com.example.eims.dto.facility.UpdateFacilityDTO;
import com.example.eims.entity.Facility;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class FacilityServiceImplTest {
    @Mock
    FacilityRepository facilityRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    FacilityServiceImpl facilityService;
    @Test
    void getAllFacility() {
        // Set up
        Facility facility1 = new Facility();
        Facility facility2 = new Facility();
        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility1);
        facilityList.add(facility2);

        // Define behaviour of repository
        when(facilityRepository.findAll()).thenReturn(facilityList);

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.getAllFacility();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(facilityList,responseEntity.getBody());
    }

    @Test
    void getFacilityOfOwner() {
        // Set up
        Facility facility = new Facility();

        // Define behaviour of repository
        when(facilityRepository.findByUserId(1L)).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.getFacilityOfOwner(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(facility,responseEntity.getBody());
    }

    @Test
    void showFormUpdate() {
        // Set up
        Facility facility = new Facility();
        facility.setUserId(1L);
        facility.setFacilityId(1L);
        facility.setFacilityName("name");
        facility.setFacilityAddress("address");
        facility.setFacilityFoundDate(new Date(9999899));
        facility.setBusinessLicenseNumber("999");
        facility.setHotline("0987654321");
        facility.setStatus(1);
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.getFromEntity(facility);
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(1L)).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.showFormUpdate(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(dto,responseEntity.getBody());
    }

    @Test
    void updateFacility() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("name");
        dto.setFacilityAddress("address");
        dto.setFoundDate("2000-01-01");
        dto.setBusinessLicenseNumber("999");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(1);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin cơ sở thành công",responseEntity.getBody());
    }
}