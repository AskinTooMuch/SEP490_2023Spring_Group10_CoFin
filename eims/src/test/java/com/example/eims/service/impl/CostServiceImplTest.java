 /*
  * Copyright (C) 2023, FPT University <br>
  * SEP490 - SEP490_G10 <br>
  * EIMS <br>
  * Eggs Incubating Management System <br>
  *
  * Record of change:<br>
  * DATE         Version     Author      DESCRIPTION<br>
  * 08/04/2023   1.0         DuongNH     First Deploy<br>
  * 08/04/2023   1.1         DuongNH     Add all test case<br>
  */
 package com.example.eims.service.impl;

import com.example.eims.dto.cost.CostDetailDTO;
import com.example.eims.dto.cost.CreateCostDTO;
import com.example.eims.dto.cost.UpdateCostDTO;
import com.example.eims.entity.Cost;
import com.example.eims.entity.Facility;
import com.example.eims.entity.Payroll;
import com.example.eims.entity.User;
import com.example.eims.repository.CostRepository;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

 @ExtendWith(MockitoExtension.class)
class CostServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CostRepository costRepository;

    @Mock
    FacilityRepository facilityRepository;

    @InjectMocks
    CostServiceImpl costService;

    @Test
    @DisplayName("getAllCostUTCID01")
    void getAllCostUTCID01() {
        //set up
        Long ownerId = 1L;

        Cost cost1 = new Cost();
        cost1.setIssueDate(Date.valueOf("2023-01-01"));
        Cost cost2 = new Cost();
        cost2.setIssueDate(Date.valueOf("2023-01-01"));

        List<Cost> costList = new ArrayList<>();
        costList.add(cost1);
        costList.add(cost2);

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(costRepository.findAllByUserId(ownerId)).thenReturn(Optional.of(costList));
        // Run service method
        ResponseEntity<?> responseEntity = costService.getAllCost(ownerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

     @Test
     @DisplayName("getAllCostUTCID02")
     void getAllCostUTCID02() {
         //set up
         Long ownerId = 5L;

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(costRepository.findAllByUserId(ownerId)).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = costService.getAllCost(ownerId);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals(null, responseEntity.getBody());
     }

     @Test
     @DisplayName("getAllCostUTCID03")
     void getAllCostUTCID03() {
         //set up
         Long ownerId = -1L;

         List<Cost> costList = new ArrayList<>();

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(false);
         // Run service method
         ResponseEntity<?> responseEntity = costService.getAllCost(ownerId);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tài khoản không hợp lệ", responseEntity.getBody());
     }

    @Test
    @DisplayName("getCostByIdUTCID01")
    void getCostByIdUTCID01() {
        //set up
        Long costId = 1L;

        Cost cost = new Cost();
        cost.setCostId(costId);
        cost.setIssueDate(Date.valueOf("2023-01-01"));

        CostDetailDTO dto = new CostDetailDTO();
        dto.getFromEntity(cost);

        // Define behaviour of repository
        when(costRepository.findById(costId)).thenReturn(Optional.of(cost));
        // Run service method
        ResponseEntity<?> responseEntity = costService.getCostById(costId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(dto, responseEntity.getBody());
    }

     @Test
     @DisplayName("getCostByIdUTCID02")
     void getCostByIdUTCID02() {
         //set up
         Long costId = 1L;

         // Define behaviour of repository
         when(costRepository.findById(costId)).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = costService.getCostById(costId);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals(null, responseEntity.getBody());
     }

    @Test
    @DisplayName("createCostUTCID01")
    void createCostUTCID01() {
        //set up
        Long ownerId = 1L;
        Long facilityId = 1L;

        Facility facility = new Facility();
        facility.setFacilityId(facilityId);

        CreateCostDTO dto = new CreateCostDTO();
        dto.setUserId(ownerId);
        dto.setFacilityId(facilityId);
        dto.setCostItem("Tiền điện");
        dto.setCostAmount(100F);
        dto.setPaidAmount(0F);
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
        // Run service method
        ResponseEntity<?> responseEntity = costService.createCost(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm chi phí thành công", responseEntity.getBody());
    }

     @Test
     @DisplayName("createCostUTCID02")
     void createCostUTCID02() {
         //set up
         Long ownerId = -1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);  
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(false);
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tài khoản không hợp lệ", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID03")
     void createCostUTCID03() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);  
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tên chi phí không thể để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID04")
     void createCostUTCID04() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem(null);
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);  
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tên chi phí không thể để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID05")
     void createCostUTCID05() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(0F);
         dto.setPaidAmount(0F); 
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tổng chi phí phải lớn hơn 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID06")
     void createCostUTCID06() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(-100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tổng chi phí phải lớn hơn 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID07")
     void createCostUTCID07() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Thêm chi phí thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID08")
     void createCostUTCID08() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote(null);

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Thêm chi phí thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID09")
     void createCostUTCID09() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(100F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Thêm chi phí thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID10")
     void createCostUTCID10() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(200F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số tiền đã thanh toán không được lớn hơn tổng chi phí", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID11")
     void createCostUTCID11() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(-100F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số tiền đã thanh toán không được bé hơn 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("createCostUTCID12")
     void createCostUTCID12() {
         //set up
         Long ownerId = 1L;
         Long facilityId = -1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         CreateCostDTO dto = new CreateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = costService.createCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Cơ sở hiện không được hoạt động", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID01")
     void updateCostUTCID01() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Cập nhật chi phí thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID02")
     void updateCostUTCID02() {
         //set up
         Long ownerId = -1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(false);
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tài khoản không hợp lệ", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID03")
     void updateCostUTCID03() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tên chi phí không thể để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID04")
     void updateCostUTCID04() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem(null);
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tên chi phí không thể để trống", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID05")
     void updateCostUTCID05() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(0F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tổng chi phí phải lớn hơn 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID06")
     void updateCostUTCID06() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(-100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tổng chi phí phải lớn hơn 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID07")
     void updateCostUTCID07() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Cập nhật chi phí thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID08")
     void updateCostUTCID08() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote(null);

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Cập nhật chi phí thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID09")
     void updateCostUTCID09() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(100F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Cập nhật chi phí thành công", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID10")
     void updateCostUTCID10() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(200F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số tiền đã thanh toán không được lớn hơn tổng chi phí", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID11")
     void updateCostUTCID11() {
         //set up
         Long ownerId = 1L;
         Long facilityId = 1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(-100F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.of(facility));
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Số tiền đã thanh toán không được bé hơn 0", responseEntity.getBody());
     }

     @Test
     @DisplayName("updateCostUTCID12")
     void updateCostUTCID12() {
         //set up
         Long ownerId = 1L;
         Long facilityId = -1L;

         Facility facility = new Facility();
         facility.setFacilityId(facilityId);

         UpdateCostDTO dto = new UpdateCostDTO();
         dto.setUserId(ownerId);
         dto.setFacilityId(facilityId);
         dto.setCostItem("Tiền điện");
         dto.setCostAmount(100F);
         dto.setPaidAmount(0F);
         dto.setNote("Tra tien thang 1");

         // Define behaviour of repository
         when(facilityRepository.findByFacilityId(facilityId)).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = costService.updateCost(dto);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Cơ sở hiện không được hoạt động", responseEntity.getBody());
     }

    @Test
    @DisplayName("searchCostByNameUTCID01")
    void searchCostByNameUTCID01() {
        //set up
        Long ownerId = 1L;
        String searchKey = "Tiền điện";

        Cost cost1 = new Cost();
        cost1.setIssueDate(Date.valueOf("2023-01-01"));
        Cost cost2 = new Cost();
        cost2.setIssueDate(Date.valueOf("2023-01-01"));

        List<Cost> list = new ArrayList<>();
        list.add(cost1);
        list.add(cost2);

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(costRepository.searchCostByName(ownerId,searchKey)).thenReturn(Optional.of(list));
        // Run service method
        ResponseEntity<?> responseEntity = costService.searchCostByName(ownerId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

     @Test
     @DisplayName("searchCostByNameUTCID02")
     void searchCostByNameUTCID02() {
         //set up
         Long ownerId = -1L;
         String searchKey = "Tiền điện";

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(false);
         // Run service method
         ResponseEntity<?> responseEntity = costService.searchCostByName(ownerId,searchKey);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Tài khoản không hợp lệ", responseEntity.getBody());
     }

     @Test
     @DisplayName("searchCostByNameUTCID03")
     void searchCostByNameUTCID03() {
         //set up
         Long ownerId = 1L;
         String searchKey = "";

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.searchCostByName(ownerId,searchKey);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Nhập từ khóa để tìm kiếm", responseEntity.getBody());
     }

     @Test
     @DisplayName("searchCostByNameUTCID04")
     void searchCostByNameUTCID04() {
         //set up
         Long ownerId = 1L;
         String searchKey = null;

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = costService.searchCostByName(ownerId,searchKey);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Nhập từ khóa để tìm kiếm", responseEntity.getBody());
     }

     @Test
     @DisplayName("searchCostByNameUTCID05")
     void searchCostByNameUTCID05() {
         //set up
         Long ownerId = 1L;
         String searchKey = "Tiền điện";

         // Define behaviour of repository
         when(userRepository.existsById(ownerId)).thenReturn(true);
         when(costRepository.searchCostByName(ownerId,searchKey)).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = costService.searchCostByName(ownerId,searchKey);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Không tìm thấy khoản chi phí phù hợp", responseEntity.getBody());
     }
}