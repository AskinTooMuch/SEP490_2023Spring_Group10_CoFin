/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 03/03/2023   3.1         DuongVV     Add view/approve registration<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.user.UpdateUserDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.entity.User;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.interfaces.IUserService;
import com.example.eims.utils.StringDealer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager em;
    private final StringDealer stringDealer;
    public UserServiceImpl(UserRepository userRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.em = em;
        this.stringDealer = new StringDealer();
    }


    /**
     * Requesting user information and facility information with the requesting DTO consists of user phone number.
     *
     * @param userId
     * @return the details of a user and it's facility in the form of a UserDetailDTO
     */
    @Override
    public ResponseEntity<UserDetailDTO> sendUserDetail(Long userId) {
        Query q = em.createNamedQuery("GetUserDetail");
        q.setParameter(1, userId);
        UserDetailDTO userDetailDTO = (UserDetailDTO) q.getSingleResult();
        System.out.println(userDetailDTO);
        return new ResponseEntity<>(userDetailDTO, HttpStatus.OK);
    }

    /**
     * Get all user.
     *
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUser() {
        // Retrieve users
        List<User> userList = userRepository.findAll();
        // Return
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /**
     * Get all user with Paging.
     *
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUserPaging(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("userId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("userId").descending();
        }
        // Get all users with Paging
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

    /**
     * Show form to update user's information.
     *
     * @param userId the id of the user
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long userId) {
        // Retrieve user
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            UpdateUserDTO updateUserDTO = new UpdateUserDTO();
            updateUserDTO.getFromEntity(user.get());
            // Return
            return new ResponseEntity<>(updateUserDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a user.
     *
     * @param updateUserDTO contains the user's new name,date of birth, phone number, address, email, salary and status
     * @return
     */
    @Override
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO) {
        // Check if Owner's account is still activated
        Long userId = updateUserDTO.getUserId();
        int accountStatus = (userRepository.getStatusByUserId(userId)? 1:0);
        if (accountStatus == 0) { /* status = 0 (deactivated) */
            return new ResponseEntity<>("Activate your account first", HttpStatus.BAD_REQUEST);
        }
        // Retrieve user's new information
        Optional<User> userOptional = userRepository.findById(updateUserDTO.getUserId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            // Check input
            // Name
            String name = stringDealer.trimMax(updateUserDTO.getUsername());
            if (name.equals("")){ /* Name is empty */
                return new ResponseEntity<>("User name", HttpStatus.BAD_REQUEST);
            }
            // Date of birth
            String sDate = stringDealer.trimMax(updateUserDTO.getDob());
            if (sDate.equals("")){ /* Date of birth is empty */
                return new ResponseEntity<>("Date of birth", HttpStatus.BAD_REQUEST);
            }
            // Email
            String email = stringDealer.trimMax(updateUserDTO.getEmail());
            if (!stringDealer.checkEmailRegex(email)){ /* Email is not valid*/
                return new ResponseEntity<>("Email", HttpStatus.BAD_REQUEST);
            }
            // Address
            String address = stringDealer.trimMax(updateUserDTO.getAddress());
            if (address.equals("")){ /* Address is empty */
                return new ResponseEntity<>("Address", HttpStatus.BAD_REQUEST);
            }
            Date dob = stringDealer.convertToDateAndFormat(sDate);

            // Set attribute
            user.setUsername(name);
            user.setDob(dob);
            user.setAddress(address);
            user.setEmail(email);
            // Save
            userRepository.save(user);
            return new ResponseEntity<>("User information updated!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * View list of users with same role.
     *
     * @param roleId the id of user's role
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUserByRole(int roleId) {
        Optional<List<User>> userList = userRepository.findAllByRoleId(roleId);
        if (userList.isPresent()) {
            return new ResponseEntity<>(userList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * View list of users with same role and status.
     *
     * @param roleId the id of user's role
     * @param status the status of user
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUserByRoleAndStatus(int roleId, int status) {
        Optional<List<User>> userList = userRepository.findAllByRoleIdAndStatus(roleId, status);
        if (userList.isPresent()) {
            return new ResponseEntity<>(userList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
