/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
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

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager em;

    public UserServiceImpl(UserRepository userRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.em = em;
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
        User user = userRepository.findById(userId).get();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.getFromEntity(user);
        // Return
        return new ResponseEntity<>(updateUserDTO, HttpStatus.OK);
    }

    /**
     * Update a user.
     *
     * @param updateUserDTO contains the user's new name,date of birth, phone number, address, email, salary and status
     * @return
     */
    @Override
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO) {
        // Retrieve user's new information
        User user = userRepository.findById(updateUserDTO.getUserId()).get();
        user.setUsername(updateUserDTO.getUsername());

        StringDealer stringDealer = new StringDealer();
        String sDate = updateUserDTO.getDob();
        Date date = stringDealer.convertToDateAndFormat(sDate);
        user.setDob(date);

        //user.setPhone(updateUserDTO.getPhone());
        user.setAddress(updateUserDTO.getAddress());
        user.setEmail(updateUserDTO.getEmail());
        //user.setStatus(updateUserDTO.getStatus());
        // Save
        userRepository.save(user);
        return new ResponseEntity<>("User information updated!", HttpStatus.OK);
    }
}
