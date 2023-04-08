/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
 * 23/03/2023   2.0         DuongNH     Add test case<br>
 * 05/04/2023   2.1         DuongNH     Update test<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.facility.UpdateFacilityDTO;
import com.example.eims.entity.Facility;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("getAllFacilityUTCID01")
    void getAllFacilityUTCID01() {
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
        assertNotEquals(null,responseEntity.getBody());
    }

    @Test
    @DisplayName("getFacilityOfOwnerUTCID01")
    void getFacilityOfOwnerUTCID01() {
        // Set up
        Long userId = 1L;
        Facility facility = new Facility();
        facility.setUserId(userId);
        facility.setFacilityName("Chu Xuong Trung");
        facility.setFacilityAddress("Tỉnh Hải Dương");
        facility.setFacilityFoundDate(Date.valueOf("2019-02-17"));
        facility.setSubscriptionExpirationDate(Date.valueOf("2023-05-31"));
        facility.setHotline("0987654321");
        facility.setBusinessLicenseNumber("1234567890");
        facility.setStatus(1);

        // Define behaviour of repository
        when(facilityRepository.findByUserId(1L)).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.getFacilityOfOwner(userId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null,responseEntity.getBody());
    }

    @Test
    @DisplayName("getFacilityOfOwnerUTCID02")
    void getFacilityOfOwnerUTCID02() {
        // Set up
        Long userId = 0L;

        // Define behaviour of repository
        when(facilityRepository.findByUserId(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.getFacilityOfOwner(userId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID01")
    void showFormUpdateUTCID01() {
        // Set up
        Long facilityId = 1L;
        Facility facility = new Facility();
        facility.setUserId(1L);
        facility.setFacilityId(1L);
        facility.setFacilityName("Chu Xuong Trung");
        facility.setFacilityAddress("Tỉnh Hải Dương");
        facility.setFacilityFoundDate(Date.valueOf("2019-02-17"));
        facility.setSubscriptionExpirationDate(Date.valueOf("2023-05-31"));
        facility.setHotline("0987654321");
        facility.setBusinessLicenseNumber("1234567890");
        facility.setStatus(1);
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.getFromEntity(facility);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findByFacilityId(1L)).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.showFormUpdate(facilityId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(dto,responseEntity.getBody());
    }

    @Test
    @DisplayName("showFormUpdateUTCID02")
    void showFormUpdateUTCID02() {
        // Set up
        Long facilityId = 0L;
        // Define behaviour of repository
        when(facilityRepository.findByFacilityId(0L)).thenReturn(Optional.empty());

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.showFormUpdate(facilityId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID01")
    void updateFacilityUTCID01() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin cơ sở thành công",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID02")
    void updateFacilityUTCID02() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("ABC123");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin cơ sở thành công",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID03")
    void updateFacilityUTCID03() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên cơ sở không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID04")
    void updateFacilityUTCID04() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName(null);
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên cơ sở không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID05")
    void updateFacilityUTCID05() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ cơ sở không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID06")
    void updateFacilityUTCID06() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress(null);
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ cơ sở không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID07")
    void updateFacilityUTCID07() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2050-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày thành lập không hợp lệ",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID08")
    void updateFacilityUTCID08() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày thành lập không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID09")
    void updateFacilityUTCID09() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate(null);
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày thành lập không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID10")
    void updateFacilityUTCID10() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("");
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số đăng kí kinh doanh không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID11")
    void updateFacilityUTCID11() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber(null);
        dto.setHotline("0987654321");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số đăng kí kinh doanh không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID12")
    void updateFacilityUTCID12() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("098765432");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Hotline không hợp lệ",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID13")
    void updateFacilityUTCID13() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("abcdefgh");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Hotline không hợp lệ",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID14")
    void updateFacilityUTCID14() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline("");
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Hotline không được để trống",responseEntity.getBody());
    }

    @Test
    @DisplayName("updateFacilityUTCID15")
    void updateFacilityUTCID15() {
        // Set up
        Facility facility = new Facility();
        UpdateFacilityDTO dto = new UpdateFacilityDTO();
        dto.setUserId(1L);
        dto.setFacilityId(1L);
        dto.setFacilityName("Nguyễn Văn A");
        dto.setFacilityAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setFoundDate("2000-02-03");
        dto.setBusinessLicenseNumber("1234567890");
        dto.setHotline(null);
        dto.setStatus(1);
        // Define behaviour of repository
        when(facilityRepository.getStatusById(1L)).thenReturn(true);
        when(facilityRepository.findById(dto.getFacilityId())).thenReturn(Optional.of(facility));

        // Run service method
        ResponseEntity<?> responseEntity = facilityService.updateFacility(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Hotline không được để trống",responseEntity.getBody());
    }

}