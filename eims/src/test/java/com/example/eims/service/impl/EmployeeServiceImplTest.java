/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 06/04/2023   1.0         DuongNH     First Deploy<br>
 * 06/04/2023   2.0         DuongNH     Add test<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.employee.CreateEmployeeDTO;
import com.example.eims.dto.employee.EmployeeDetailDTO;
import com.example.eims.dto.employee.EmployeeListItemDTO;
import com.example.eims.dto.employee.UpdateEmployeeDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.entity.Role;
import com.example.eims.entity.SalaryHistory;
import com.example.eims.entity.User;
import com.example.eims.entity.WorkIn;
import com.example.eims.repository.FacilityRepository;
import com.example.eims.repository.SalaryHistoryRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WorkInRepository workInRepository;
    @Mock
    private SalaryHistoryRepository salaryHistoryRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    FacilityRepository facilityRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    private final StringDealer stringDealer = new StringDealer();


    User convertCreateEmployeeDaoToUser(CreateEmployeeDTO createEmployeeDTO){
        User newEmployee = new User();
        newEmployee.setUsername(createEmployeeDTO.getEmployeeName());
        newEmployee.setDob(stringDealer.convertToDateAndFormat(createEmployeeDTO.getEmployeeDob()));
        newEmployee.setPhone(createEmployeeDTO.getEmployeePhone());
        newEmployee.setPassword(passwordEncoder.encode(createEmployeeDTO.getEmployeePassword()));
        newEmployee.setEmail(stringDealer.trimMax(createEmployeeDTO.getEmail()));
        newEmployee.setAddress(createEmployeeDTO.getEmployeeAddress());
        newEmployee.setSalary(createEmployeeDTO.getSalary());
        List roleList = new ArrayList<Role>();
        roleList.add(new Role(3, "ROLE_EMPLOYEE", true));
        newEmployee.setRoles(roleList);
        newEmployee.setStatus(2);
        return  newEmployee;
    }

    User convertUpdateEmployeeDaoToUser(UpdateEmployeeDTO updateEmployeeDTO){
        User updatedEmployee = new User();
        updatedEmployee.setUserId(updateEmployeeDTO.getEmployeeId());
        updatedEmployee.setUsername(updateEmployeeDTO.getEmployeeName());
        updatedEmployee.setDob(stringDealer.convertToDateAndFormat(updateEmployeeDTO.getEmployeeDob()));
        updatedEmployee.setPhone(updateEmployeeDTO.getEmployeePhone());
        updatedEmployee.setEmail(stringDealer.trimMax(updateEmployeeDTO.getEmail()));
        updatedEmployee.setAddress(updateEmployeeDTO.getEmployeeAddress());
        updatedEmployee.setSalary(updateEmployeeDTO.getSalary());
        updatedEmployee.setStatus(updateEmployeeDTO.getStatus());
        List roleList = new ArrayList<Role>();
        roleList.add(new Role(3, "ROLE_EMPLOYEE", true));
        updatedEmployee.setRoles(roleList);
        updatedEmployee.setStatus(updateEmployeeDTO.getStatus());
        return  updatedEmployee;
    }

    @Test
    @DisplayName("createNewEmployeeUTCID01")
    void createNewEmployeeUTCID01() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        User newEmployee = convertCreateEmployeeDaoToUser(dto);

        User returnEmployee = convertCreateEmployeeDaoToUser(dto);
        returnEmployee.setUserId(1L);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(userRepository.existsByPhone(dto.getEmployeePhone())).thenReturn(false);
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm nhân viên thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID02")
    void createNewEmployeeUTCID02() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("& HCm");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        User newEmployee = convertCreateEmployeeDaoToUser(dto);

        User returnEmployee = convertCreateEmployeeDaoToUser(dto);
        returnEmployee.setUserId(1L);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(userRepository.existsByPhone(dto.getEmployeePhone())).thenReturn(false);
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID03")
    void createNewEmployeeUTCID03() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID04")
    void createNewEmployeeUTCID04() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName(null);
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID05")
    void createNewEmployeeUTCID05() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày sinh không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID06")
    void createNewEmployeeUTCID06() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày sinh không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID07")
    void createNewEmployeeUTCID07() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("abcdefgh");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID08")
    void createNewEmployeeUTCID08() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID09")
    void createNewEmployeeUTCID09() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone(null);
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID10")
    void createNewEmployeeUTCID10() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("123456789");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mật khẩu không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID11")
    void createNewEmployeeUTCID11() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mật khẩu không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID12")
    void createNewEmployeeUTCID12() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword(null);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Mật khẩu không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID13")
    void createNewEmployeeUTCID13() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("a_b_c@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID14")
    void createNewEmployeeUTCID14() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("12@3tungdt@gmail.com"	);
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID15")
    void createNewEmployeeUTCID15() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("12@3tungdt@gmail.com"	);
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID16")
    void createNewEmployeeUTCID16() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        User newEmployee = convertCreateEmployeeDaoToUser(dto);

        User returnEmployee = convertCreateEmployeeDaoToUser(dto);
        returnEmployee.setUserId(1L);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(userRepository.existsByPhone(dto.getEmployeePhone())).thenReturn(false);
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm nhân viên thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID17")
    void createNewEmployeeUTCID17() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail(null);
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        User newEmployee = convertCreateEmployeeDaoToUser(dto);

        User returnEmployee = convertCreateEmployeeDaoToUser(dto);
        returnEmployee.setUserId(1L);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(userRepository.existsByPhone(dto.getEmployeePhone())).thenReturn(false);
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm nhân viên thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID18")
    void createNewEmployeeUTCID18() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("");
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID19")
    void createNewEmployeeUTCID19() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress(null);
        dto.setSalary(new BigDecimal("100.00"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID20")
    void createNewEmployeeUTCID20() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("0.00"));

        User newEmployee = convertCreateEmployeeDaoToUser(dto);

        User returnEmployee = convertCreateEmployeeDaoToUser(dto);
        returnEmployee.setUserId(1L);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(userRepository.existsByPhone(dto.getEmployeePhone())).thenReturn(false);
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm nhân viên thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("createNewEmployeeUTCID21")
    void createNewEmployeeUTCID21() {
        MockitoAnnotations.openMocks(this);
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setEmployeePassword("@User123");
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("-1"));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.createNewEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tiền lương không được bé hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID01")
    void updateEmployeeUTCID01() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        User newEmployee = convertUpdateEmployeeDaoToUser(dto);

        User returnEmployee = convertUpdateEmployeeDaoToUser(dto);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thánh công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID02")
    void updateEmployeeUTCID02() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("& HCm");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        User newEmployee = convertUpdateEmployeeDaoToUser(dto);

        User returnEmployee = convertUpdateEmployeeDaoToUser(dto);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID03")
    void updateEmployeeUTCID03() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID04")
    void updateEmployeeUTCID04() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName(null);
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tên không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID05")
    void updateEmployeeUTCID05() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày sinh không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID06")
    void updateEmployeeUTCID06() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob(null);
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Ngày sinh không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID07")
    void updateEmployeeUTCID07() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("abcdefgh");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID08")
    void updateEmployeeUTCID08() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("098765432");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID09")
    void updateEmployeeUTCID09() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID10")
    void updateEmployeeUTCID10() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone(null);
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Số điện thoại không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID11")
    void updateEmployeeUTCID11() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(2);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        User newEmployee = convertUpdateEmployeeDaoToUser(dto);

        User returnEmployee = convertUpdateEmployeeDaoToUser(dto);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thánh công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID12")
    void updateEmployeeUTCID12() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(1);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Trạng thái mới không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID13")
    void updateEmployeeUTCID13() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("a_b_c@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID14")
    void updateEmployeeUTCID14() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("12@3tungdt@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Email không đúng định dạng", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID15")
    void updateEmployeeUTCID15() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        User newEmployee = convertUpdateEmployeeDaoToUser(dto);

        User returnEmployee = convertUpdateEmployeeDaoToUser(dto);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thánh công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID16")
    void updateEmployeeUTCID16() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail(null);
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        User newEmployee = convertUpdateEmployeeDaoToUser(dto);

        User returnEmployee = convertUpdateEmployeeDaoToUser(dto);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thánh công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID17")
    void updateEmployeeUTCID17() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("");
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID18")
    void updateEmployeeUTCID18() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress(null);
        dto.setSalary(new BigDecimal("100.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Địa chỉ không được để trống", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID19")
    void updateEmployeeUTCID19() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com"	);
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("0.00"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        User newEmployee = convertUpdateEmployeeDaoToUser(dto);

        User returnEmployee = convertUpdateEmployeeDaoToUser(dto);

        SalaryHistory salaryHistory = new SalaryHistory();
        salaryHistory.setSalary(dto.getSalary());
        Date issueDate = Date.valueOf(LocalDate.now());
        salaryHistory.setIssueDate(issueDate);
        salaryHistory.setUserId(returnEmployee.getUserId());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        when(userRepository.save(newEmployee)).thenReturn(returnEmployee);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thánh công", responseEntity.getBody());
    }

    @Test
    @DisplayName("updateEmployeeUTCID20")
    void updateEmployeeUTCID20() {
        MockitoAnnotations.openMocks(this);
        UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
        dto.setEmployeeId(1L);
        dto.setFacilityId(1L);
        dto.setEmployeeName("Lê Văn Đ");
        dto.setEmployeeDob("2000-02-03");
        dto.setEmployeePhone("0987654321");
        dto.setStatus(0);
        dto.setEmail("tungduong71@gmail.com");
        dto.setEmployeeAddress("{\"city\":\"Tỉnh Hải Dương\",\"district\":\"Huyện Gia Lộc\",\"ward\":\"Xã Hoàng Diệu\",\"street\":\"Thôn Nghĩa Hy\"}");
        dto.setSalary(new BigDecimal("-1"));

        WorkIn workIn = new WorkIn();
        workIn.setUserId(dto.getEmployeeId());
        workIn.setFacilityId(dto.getFacilityId());
        workIn.setStatus(1);

        User oldEmployeeInfo = new User();
        oldEmployeeInfo.setUserId(dto.getEmployeeId());
        oldEmployeeInfo.setPhone("0987654321");
        oldEmployeeInfo.setSalary(dto.getSalary());

        // Define behaviour of repository
        when(workInRepository.findByUserId(1L)).thenReturn(Optional.of(workIn));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(oldEmployeeInfo));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.updateEmployee(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Tiền lương không được bé hơn 0", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteEmployeeUTCID01")
    void deleteEmployeeUTCID01() {
        Long employeeId = 1L;
        User oldEmployee = new User();
        oldEmployee.setUserId(employeeId);

        WorkIn workIn = new WorkIn();
        workIn.setUserId(employeeId);
        workIn.setFacilityId(1L);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(oldEmployee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.of(workIn));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.deleteEmployee(employeeId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Xoá nhân viên thành công", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteEmployeeUTCID02")
    void deleteEmployeeUTCID02() {
        Long employeeId = -1L;
        User oldEmployee = new User();
        oldEmployee.setUserId(employeeId);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.deleteEmployee(employeeId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhân viên không còn tồn tại trên hệ thống", responseEntity.getBody());
    }

    @Test
    @DisplayName("deleteEmployeeUTCID03")
    void deleteEmployeeUTCID03() {
        Long employeeId = 15L;
        User oldEmployee = new User();
        oldEmployee.setUserId(employeeId);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(oldEmployee));
        when(workInRepository.findByUserId(employeeId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.deleteEmployee(employeeId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhân viên không còn tồn tại trên hệ thống", responseEntity.getBody());
    }


    @Test
    @DisplayName("viewEmployeeByIdUTCID01")
    void viewEmployeeByIdUTCID01() {
        Long employeeId = 1L;
        User employee = new User();
        employee.setUserId(employeeId);

        EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
        employeeDetailDTO.getFromEntity(employee);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.of(employee));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.viewEmployeeById(employeeId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(employeeDetailDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("viewEmployeeByIdUTCID02")
    void viewEmployeeByIdUTCID02() {
        Long employeeId = -1L;
        User employee = new User();
        employee.setUserId(employeeId);

        // Define behaviour of repository
        when(userRepository.findByUserId(employeeId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.viewEmployeeById(employeeId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("viewAllEmployeeUTCID01")
    void viewAllEmployeeUTCID01() {
        Long facilityId = 1L;

        WorkIn workIn1 = new WorkIn();
        workIn1.setUserId(1L);
        workIn1.setFacilityId(1L);
        workIn1.setStatus(1);

        WorkIn workIn2 = new WorkIn();
        workIn2.setUserId(2L);
        workIn2.setFacilityId(1L);
        workIn2.setStatus(1);

        List<WorkIn> workInList = new ArrayList<>();
        workInList.add(workIn1);
        workInList.add(workIn2);

        User employee1 = new User();
        employee1.setUserId(1L);
        User employee2 = new User();
        employee2.setUserId(2L);

        EmployeeListItemDTO employeeDTO1 = new EmployeeListItemDTO();
        employeeDTO1.getFromUser(employee1);
        EmployeeListItemDTO employeeDTO2 = new EmployeeListItemDTO();
        employeeDTO2.getFromUser(employee2);

        List<EmployeeListItemDTO> employeeDetailDTOList = new ArrayList<>();
        employeeDetailDTOList.add(employeeDTO1);
        employeeDetailDTOList.add(employeeDTO2);

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(true);
        when(workInRepository.findAllByFacilityId(facilityId)).thenReturn(Optional.of(workInList));
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(employee1));
        when(userRepository.findByUserId(2L)).thenReturn(Optional.of(employee2));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.viewAllEmployee(facilityId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(employeeDetailDTOList, responseEntity.getBody());
    }

    @Test
    @DisplayName("viewAllEmployeeUTCID02")
    void viewAllEmployeeUTCID02() {
        Long facilityId = 2L;

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(true);
        when(workInRepository.findAllByFacilityId(facilityId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.viewAllEmployee(facilityId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("viewAllEmployeeUTCID03")
    void viewAllEmployeeUTCID03() {
        Long facilityId = -1L;

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.viewAllEmployee(facilityId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cơ sở không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchEmployeeByNameOrPhoneUTCID01")
    void searchEmployeeByNameOrPhoneUTCID01() {
        Long facilityId = 1L;
        String searchKey = "09123";

        User employee1 = new User();
        User employee2 = new User();

        List<User> listEmployee = new ArrayList<>();
        listEmployee.add(employee1);
        listEmployee.add(employee2);

        EmployeeListItemDTO employeeDTO1 = new EmployeeListItemDTO();
        employeeDTO1.getFromUser(employee1);
        EmployeeListItemDTO employeeDTO2 = new EmployeeListItemDTO();
        employeeDTO2.getFromUser(employee2);

        List<EmployeeListItemDTO> list = new ArrayList<>();
        list.add(employeeDTO1);
        list.add(employeeDTO2);

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(true);
        when(userRepository.searchEmployeeByPhoneOrName(facilityId,searchKey)).thenReturn(Optional.of(listEmployee));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.searchEmployeeByNameOrPhone(facilityId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(list, responseEntity.getBody());
    }

    @Test
    @DisplayName("searchEmployeeByNameOrPhoneUTCID02")
    void searchEmployeeByNameOrPhoneUTCID02() {
        Long facilityId = -1L;
        String searchKey = "09123";

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.searchEmployeeByNameOrPhone(facilityId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cơ sở không hợp lệ", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchEmployeeByNameOrPhoneUTCID03")
    void searchEmployeeByNameOrPhoneUTCID03() {
        Long facilityId = 1L;
        String searchKey = "Duong";

        User employee1 = new User();
        User employee2 = new User();

        List<User> listEmployee = new ArrayList<>();
        listEmployee.add(employee1);
        listEmployee.add(employee2);

        EmployeeListItemDTO employeeDTO1 = new EmployeeListItemDTO();
        employeeDTO1.getFromUser(employee1);
        EmployeeListItemDTO employeeDTO2 = new EmployeeListItemDTO();
        employeeDTO2.getFromUser(employee2);

        List<EmployeeListItemDTO> list = new ArrayList<>();
        list.add(employeeDTO1);
        list.add(employeeDTO2);

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(true);
        when(userRepository.searchEmployeeByPhoneOrName(facilityId,searchKey)).thenReturn(Optional.of(listEmployee));
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.searchEmployeeByNameOrPhone(facilityId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(list, responseEntity.getBody());
    }

    @Test
    @DisplayName("searchEmployeeByNameOrPhoneUTCID04")
    void searchEmployeeByNameOrPhoneUTCID04() {
        Long facilityId = -1L;
        String searchKey = "";

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.searchEmployeeByNameOrPhone(facilityId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhập từ khóa để tìm kiếm", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchEmployeeByNameOrPhoneUTCID05")
    void searchEmployeeByNameOrPhoneUTCID05() {
        Long facilityId = -1L;
        String searchKey = null;

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.searchEmployeeByNameOrPhone(facilityId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhập từ khóa để tìm kiếm", responseEntity.getBody());
    }

    @Test
    @DisplayName("searchEmployeeByNameOrPhoneUTCID06")
    void searchEmployeeByNameOrPhoneUTCID06() {
        Long facilityId = -1L;
        String searchKey = "adfadfadfasdfad";

        // Define behaviour of repository
        when(facilityRepository.existsByFacilityId(facilityId)).thenReturn(true);
        when(userRepository.searchEmployeeByPhoneOrName(facilityId,searchKey)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = employeeService.searchEmployeeByNameOrPhone(facilityId,searchKey);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Không tìm thấy nhân viên phù hợp", responseEntity.getBody());
    }
}