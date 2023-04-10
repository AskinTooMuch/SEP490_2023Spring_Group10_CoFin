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

import com.example.eims.dto.payroll.CreatePayrollDTO;
import com.example.eims.dto.payroll.UpdatePayrollDTO;
import com.example.eims.entity.Payroll;
import com.example.eims.entity.User;
import com.example.eims.entity.WorkIn;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.PayrollRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.WorkInRepository;
import com.example.eims.utils.StringDealer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PayrollServiceImplTest {

    private final StringDealer stringDealer = new StringDealer();

    @Mock
    PayrollRepository payrollRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    WorkInRepository workInRepository;
    @Mock
    FacilityRepository facilityRepository;
    @InjectMocks
    PayrollServiceImpl payrollService;

    @Test
    @DisplayName("getAllPayrollUTCID01")
    void getAllPayrollUTCID01() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = 1L;

        User employee1 = new User();
        employee1.setUserId(2L);
        User employee2 = new User();
        employee2.setUserId(3L);

        Payroll payroll1 = new Payroll();
        payroll1.setOwnerId(ownerId);
        payroll1.setEmployeeId(employee1.getUserId());
        payroll1.setIssueDate(Date.valueOf("2020-02-01"));
        Payroll payroll2 = new Payroll();
        payroll2.setOwnerId(ownerId);
        payroll2.setEmployeeId(employee2.getUserId());
        payroll2.setIssueDate(Date.valueOf("2020-02-01"));

        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll1);
        payrollList.add(payroll2);

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(payrollRepository.findAllByOwnerId(ownerId)).thenReturn(Optional.of(payrollList));
        when(userRepository.findByUserId(2L)).thenReturn(Optional.of(employee1));
        when(userRepository.findByUserId(3L)).thenReturn(Optional.of(employee2));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.getAllPayroll(ownerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals("Tài khoản không hợp lệ", responseEntity.getBody());
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllPayrollUTCID02")
    void getAllPayrollUTCID02() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = 5L;

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(payrollRepository.findAllByOwnerId(ownerId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.getAllPayroll(ownerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllPayrollUTCID03")
    void getAllPayrollUTCID03() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = -1L;

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.getAllPayroll(ownerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tài khoản không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("getPayrollByIDUTCID01")
    void getPayrollByIDUTCID01() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);
        payroll.setEmployeeId(1L);
        payroll.setIssueDate(Date.valueOf("2020-02-01"));

        User employee = new User();
        employee.setUserId(1L);

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(employee));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.getAllPayroll(payrollId);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getPayrollByIDUTCID02")
    void getPayrollByIDUTCID02() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = -1L;

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.getAllPayroll(payrollId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tài khoản không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID01")
    void createPayrollUTCID01() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm tiền lương thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID02")
    void createPayrollUTCID02() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhân viên không tồn tại", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID03")
    void createPayrollUTCID03() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khoản tiền không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID04")
    void createPayrollUTCID04() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem(null);
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khoản tiền không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID05")
    void createPayrollUTCID05() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("0"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số tiền phải lớn hơn 0.01", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID06")
    void createPayrollUTCID06() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("-100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số tiền phải lớn hơn 0.01", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID07")
    void createPayrollUTCID07() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("3000-01-01");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày trả không được sau hôm nay", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID08")
    void createPayrollUTCID08() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("");
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày trả không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID09")
    void createPayrollUTCID09() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate(null);
        dto.setNote("Tra tien thang 1");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày trả không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID10")
    void createPayrollUTCID10() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("");
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm tiền lương thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createPayrollUTCID11")
    void createPayrollUTCID11() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employee.getUserId());
        workIn.setFacilityId(1L);
        workIn.setStatus(1);

        CreatePayrollDTO dto = new CreatePayrollDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote(null);
        dto.setOwnerId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        when(userRepository.existsById(dto.getOwnerId())).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.createPayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm tiền lương thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID01")
    void updatePayrollUTCID01() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật tiền lương thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID02")
    void updatePayrollUTCID02() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = -1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khoản tiền lương không tồn tại", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID03")
    void updatePayrollUTCID03() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId =1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khoản tiền không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID04")
    void updatePayrollUTCID04() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId =1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem(null);
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khoản tiền không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID05")
    void updatePayrollUTCID05() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("0"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số tiền phải lớn hơn 0.01", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID06")
    void updatePayrollUTCID06() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("-100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số tiền phải lớn hơn 0.01", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID07")
    void updatePayrollUTCID07() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("3000-01-01");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày trả không được sau hôm nay", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID08")
    void updatePayrollUTCID08() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("");
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày trả không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID09")
    void updatePayrollUTCID09() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate(null);
        dto.setNote("Tra tien thang 1");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày trả không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID10")
    void updatePayrollUTCID10() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote("");

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật tiền lương thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updatePayrollUTCID11")
    void updatePayrollUTCID11() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long payrollId = 1L;

        Payroll payroll = new Payroll();
        payroll.setPayrollId(payrollId);

        UpdatePayrollDTO dto = new UpdatePayrollDTO();
        dto.setPayrollId(payrollId);
        dto.setPayrollItem("Lương tháng 1");
        dto.setPayrollAmount(new BigDecimal("100"));
        dto.setIssueDate("2023-01-01");
        dto.setNote(null);

        // Define behaviour of repository
        when(payrollRepository.findByPayrollId(payrollId)).thenReturn(Optional.of(payroll));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.updatePayroll(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật tiền lương thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchPayrollUTCID01")
    void searchPayrollUTCID01() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = 1L;
        String searchKey = "09123";

        User employee1 = new User();
        employee1.setUserId(2L);
        User employee2 = new User();
        employee2.setUserId(3L);

        Payroll payroll1 = new Payroll();
        payroll1.setOwnerId(ownerId);
        payroll1.setEmployeeId(employee1.getUserId());
        payroll1.setIssueDate(Date.valueOf("2020-02-01"));
        Payroll payroll2 = new Payroll();
        payroll2.setOwnerId(ownerId);
        payroll2.setEmployeeId(employee2.getUserId());
        payroll2.setIssueDate(Date.valueOf("2020-02-01"));

        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll1);
        payrollList.add(payroll2);

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(payrollRepository.searchPayroll(ownerId,searchKey)).thenReturn(Optional.of(payrollList));
        when(userRepository.findByUserId(2L)).thenReturn(Optional.of(employee1));
        when(userRepository.findByUserId(3L)).thenReturn(Optional.of(employee2));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.searchPayroll(ownerId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("searchPayrollUTCID02")
    void searchPayrollUTCID02() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = -1L;
        String searchKey = "09123";

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.searchPayroll(ownerId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tài khoản không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchPayrollUTCID03")
    void searchPayrollUTCID03() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = 1L;
        String searchKey = "Duong";

        User employee1 = new User();
        employee1.setUserId(2L);
        User employee2 = new User();
        employee2.setUserId(3L);

        Payroll payroll1 = new Payroll();
        payroll1.setOwnerId(ownerId);
        payroll1.setEmployeeId(employee1.getUserId());
        payroll1.setIssueDate(Date.valueOf("2020-02-01"));
        Payroll payroll2 = new Payroll();
        payroll2.setOwnerId(ownerId);
        payroll2.setEmployeeId(employee2.getUserId());
        payroll2.setIssueDate(Date.valueOf("2020-02-01"));

        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll1);
        payrollList.add(payroll2);

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(payrollRepository.searchPayroll(ownerId,searchKey)).thenReturn(Optional.of(payrollList));
        when(userRepository.findByUserId(2L)).thenReturn(Optional.of(employee1));
        when(userRepository.findByUserId(3L)).thenReturn(Optional.of(employee2));
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.searchPayroll(ownerId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("searchPayrollUTCID04")
    void searchPayrollUTCID04() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = 1L;
        String searchKey = "";

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.searchPayroll(ownerId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhập từ khóa để tìm kiếm", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchPayrollUTCID05")
    void searchPayrollUTCID05() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = 1L;
        String searchKey = null;

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.searchPayroll(ownerId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhập từ khóa để tìm kiếm", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchPayrollUTCID06")
    void searchPayrollUTCID06() {
        MockitoAnnotations.openMocks(this);
        //set up
        Long ownerId = 1L;
        String searchKey = "adfadfadfasdfad";

        // Define behaviour of repository
        when(userRepository.existsById(ownerId)).thenReturn(true);
        when(payrollRepository.searchPayroll(ownerId,searchKey)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = payrollService.searchPayroll(ownerId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy khoản tiền lương phù hợp", responseEntity.getBody());
    }
}