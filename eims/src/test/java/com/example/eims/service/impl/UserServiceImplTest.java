/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 08/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.user.UpdateUserDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.entity.User;
import com.example.eims.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    EntityManager em;
    @Mock
    private Page<User> userPage;
    @InjectMocks
    UserServiceImpl userService;

 /*   @BeforeAll
    static void beforeAll() {
        //
        User user = new User();
        user.setUserId(99L);
        user.setRoleId(2L);
        user.setUsername("username");
        Date dob = stringDealer.convertToDateAndFormat("2000-01-01");
        user.setDob(dob);
        user.setPhone("0987654000");
        user.setEmail("test@test.test");
        user.setSalary(0.0F);
        String password = "test";
        user.setPassword(passwordEncoder.encode(password));
        user.setAddress("address");
        user.setStatus(1);

        //Facility
        Facility facility = new Facility();
        facility.setFacilityId(99L);
        facility.setUserId(99L);
        facility.setFacilityName("name");
        facility.setFacilityAddress("address");
        Date fDate = stringDealer.convertToDateAndFormat("2000-01-01");
        facility.setFacilityFoundDate(fDate);
        Date seDate = stringDealer.convertToDateAndFormat("2040-01-01");
        facility.setFacilityFoundDate(seDate);
        facility.setHotline("0987654000");
        facility.setBusinessLicenseNumber("123654");
        facility.setStatus(1);
    }*/

    @Test
    void sendUserDetail() {
        // Set up
        UserDetailDTO dto = new UserDetailDTO();
        dto.setUserId(2L);
        Query q = mock(Query.class);
        // Define behaviour of repository
        when(em.createNamedQuery("GetUserDetail")).thenReturn(q);
        when(q.getSingleResult()).thenReturn(dto);
        // Run service method
        ResponseEntity<UserDetailDTO> responseEntity = userService.sendUserDetail(2L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(2L,responseEntity.getBody().getUserId());
    }

    @Test
    void getAllUser() {
        // Set up
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        // Define behaviour of repository
        when(userRepository.findAll()).thenReturn(userList);
        // Run service method
        ResponseEntity<?> responseEntity = userService.getAllUser();
        List<User> list = (List<User>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(3, list.size());
    }

    @Test
    void getAllUserPaging() {
        // Set up
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("userId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        // Run service method
        ResponseEntity<?> responseEntity = userService.getAllUserPaging(1, 2, "ASC");
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(userPage, responseEntity.getBody());
    }

    @Test
    void showFormUpdate() {
        // Set up
        User user = new User();
        user.setUserId(1L);
        user.setUsername("aa");
        user.setDob(new Date(999999));
        user.setAddress("a@a.com");
        user.setAddress("address");
        // Define behaviour of repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = userService.showFormUpdate(1L);
        System.out.println(responseEntity.toString());
        UpdateUserDTO dto = (UpdateUserDTO) responseEntity.getBody();
        // Assert
        assertEquals("aa", dto.getUsername());
    }

    @DisplayName("aaaaaaaaaaa")
    @Test
    void updateUser() {
        // Set up
        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setUserId(1L);
        dto.setUsername("aa");
        dto.setDob("2000-01-01");
        dto.setEmail("a@a.com");
        dto.setAddress("address");
        User user = new User();
        user.setUserId(1L);
        int accountStatus = 1;
        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Run service method
        ResponseEntity<?> responseEntity = userService.updateUser(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("aa","Cập nhật thông tin thành công");
    }

}