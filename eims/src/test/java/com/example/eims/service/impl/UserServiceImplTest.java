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
import com.example.eims.entity.Facility;
import com.example.eims.entity.User;
import com.example.eims.service.interfaces.IUserService;
import com.example.eims.utils.StringDealer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private IUserService userService;
    private static StringDealer stringDealer = new StringDealer();
    private static PasswordEncoder passwordEncoder;
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

    }

    @Test
    void getAllUser() {
        ResponseEntity<?> responseEntity = userService.getAllUser();
        // 5 users
        List<User> userList = (List<User>) responseEntity.getBody();
        assertEquals(userList.size(), 4); /* Fail */
        //assertEquals(userList.size(), 5); /* Pass */
    }

    @Test
    void getAllUserPaging() {
        ResponseEntity<?> responseEntity = userService.getAllUserPaging(2,3, "ASC");
        // 5 users-> (page 2, size 3) -> return 2 pages
        Page<User> userPage = (Page<User>) responseEntity.getBody();
        assertEquals(userPage.getTotalPages(), 3); /* Fail */
        //assertEquals(userPage.getTotalPages(), 2); /* Pass */
    }

    @Test
    void showFormUpdate() {
        ResponseEntity<?> responseEntity = userService.showFormUpdate(2L);
        // name = Test Owner
        UpdateUserDTO updateUserDTO = (UpdateUserDTO) responseEntity.getBody();
        assertEquals(updateUserDTO.getUsername(), "Test Employee"); /* Fail */
        //assertEquals(user.getUsername(), "Test Owner"); /* Pass */
    }

    @Test
    void updateUser() {
        // Get user (showFromUpdate)
        ResponseEntity<?> responseEntity = userService.showFormUpdate(2L);
        UpdateUserDTO updateUserDTO = (UpdateUserDTO) responseEntity.getBody();
        // Set new name
        updateUserDTO.setUsername("Test Owner 2");

        ResponseEntity<?> responseEntity2 = userService.updateUser(updateUserDTO);
        // Assert
        //assertEquals(responseEntity2.getBody(), "Fail"); /* Fail */
        assertEquals(responseEntity2.getBody(), "User information updated"); /* Pass */
    }

    @Test
    void getAllUserByRole() {
        // 3 users with role_id = 2
        ResponseEntity<?> responseEntity = userService.getAllUserByRole(2L);
        List<User> userList = (List<User>) responseEntity.getBody();
        // Assert
        assertEquals(userList.size(), 2);   /* Fail */
        //assertEquals(userList.size(), 3);   /* Pass */
    }

    @Test
    void getAllUserByRoleAndStatus() {
        // 2 users with role_id = 2 and status = 1
        ResponseEntity<?> responseEntity = userService.getAllUserByRoleAndStatus(2L, 1);
        List<User> userList = (List<User>) responseEntity.getBody();
        // Assert
        assertEquals(userList.size(), 3); /* Fail */
        //assertEquals(userList.size(), 2); /* Pass */
    }
}