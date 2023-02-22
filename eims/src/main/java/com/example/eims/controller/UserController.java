/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/01/2023    1.0        ChucNV           First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.user.PhoneNumberDTO;
import com.example.eims.dto.user.UpdateUserDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.entity.Facility;
import com.example.eims.entity.User;
import com.example.eims.entity.UserRole;
import com.example.eims.repository.FacilityRepisitory;
import com.example.eims.repository.UserRepository;
import com.example.eims.repository.UserRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FacilityRepisitory facilityRepisitory;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * The API requesting user information and facility information with the requesting DTO consists of user phone number.
     * The API returns the details of a user and it's facility in the form of a UserDetailDTO
     * @param phoneNumberDTO
     * @return
     */
    @PostMapping("/details")
    //@Secured({"ROLE_OWNER", "ROLE_EMPLOYEE"})
    public ResponseEntity<UserDetailDTO> sendUserDetail(@RequestBody PhoneNumberDTO phoneNumberDTO){
        Query q = em.createNamedQuery("GetUserDetail");
        q.setParameter(1, phoneNumberDTO.getPhone());
        UserDetailDTO userDetailDTO = (UserDetailDTO) q.getSingleResult();
//
//        User user = userRepository.findByPhone(phoneNumberDTO.getPhone()).get();
//        Optional<Facility> facilityOpt = facilityRepisitory.findByUserId(user.getUserId());
//        //User information
//        UserDetailDTO userDetailDTO = new UserDetailDTO();
//        userDetailDTO.setUserId(user.getUserId());
//        UserRole userRole = new UserRole();
//        if (userRoleRepository.findByRoleId(user.getRoleId()).isPresent()){
//            userRole = userRoleRepository.findByRoleId(user.getRoleId()).get();
//        }
//        userDetailDTO.setUserRoleName(userRole.getRoleName());
//        userDetailDTO.setUsername(user.getUsername());
//        userDetailDTO.setUserDob(user.getDob());
//        userDetailDTO.setUserEmail(user.getEmail());
//        userDetailDTO.setUserSalary(user.getSalary());
//        userDetailDTO.setUserAddress(user.getAddress());
//        userDetailDTO.setUserStatus(user.isStatus());
//        //Facility information
//        if (facilityOpt.isPresent()) {
//            Facility facility = facilityOpt.get();
//            userDetailDTO.setFacilityId(facility.getFacilityId());
//            userDetailDTO.setFacilityName(facility.getFacilityName());
//            userDetailDTO.setFacilityAddress(facility.getFacilityAddress());
//            userDetailDTO.setFacilityFoundDate(facility.getFacilityFoundDate());
//            userDetailDTO.setFacilityStatus(facility.isStatus());
//
//        }

        return new ResponseEntity<>(userDetailDTO, HttpStatus.OK);
    }

    /**
     * API to update a user.
     * userId is the id of the user
     * The DTO contains the user's new name,date of birth, phone number, address, email, salary and status
     *
     * @param userId
     * @param updateUserDTO
     * @return
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, UpdateUserDTO updateUserDTO) {
        // Retrieve user's new information
        User user = userRepository.findById(userId).get();
        user.setUsername(updateUserDTO.getUsername());
        String sDate = updateUserDTO.getDob();
        Date date;
        try {
            date = new Date(
                    (new SimpleDateFormat("yyyy-MM-dd").parse(sDate)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        user.setDob(date);
        user.setPhone(updateUserDTO.getPhone());
        user.setAddress(updateUserDTO.getAddress());
        user.setEmail(updateUserDTO.getEmail());
        user.setStatus(updateUserDTO.getStatus());
        // Save
        userRepository.save(user);
        return new ResponseEntity<>("User information updated!", HttpStatus.OK);
    }
}
