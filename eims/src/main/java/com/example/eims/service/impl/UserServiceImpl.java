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
import com.example.eims.service.interfaces.IUserService;
import org.springframework.http.ResponseEntity;

public class UserServiceImpl implements IUserService {
    /**
     * Requesting user information and facility information with the requesting DTO consists of user phone number.
     *
     * @param userId
     * @return the details of a user and it's facility in the form of a UserDetailDTO
     */
    @Override
    public ResponseEntity<UserDetailDTO> sendUserDetail(Long userId) {
        return null;
    }

    /**
     * Get all user.
     *
     * @return
     */
    @Override
    public ResponseEntity<?> getAllUser() {
        return null;
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
        return null;
    }

    /**
     * Show form to update user's information.
     *
     * @param userId the id of the user
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long userId) {
        return null;
    }

    /**
     * Update a user.
     *
     * @param updateUserDTO contains the user's new name,date of birth, phone number, address, email, salary and status
     * @return
     */
    @Override
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO) {
        return null;
    }
}
