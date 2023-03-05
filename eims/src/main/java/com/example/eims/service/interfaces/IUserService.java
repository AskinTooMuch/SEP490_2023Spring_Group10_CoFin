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

package com.example.eims.service.interfaces;

import com.example.eims.dto.user.UpdateUserDTO;
import com.example.eims.dto.user.UserDetailDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    /**
     * Requesting user information and facility information with the requesting DTO consists of user phone number.
     *
     * @param userId
     * @return the details of a user and it's facility in the form of a UserDetailDTO
     */
    public ResponseEntity<UserDetailDTO> sendUserDetail(Long userId);

    /**
     * Get all user.
     *
     * @param
     * @return
     */
    public ResponseEntity<?> getAllUser();

    /**
     * Get all user with Paging.
     *
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return
     */
    public ResponseEntity<?> getAllUserPaging(Integer page, Integer size, String sort);

    /**
     * Show form to update user's information.
     *
     * @param userId the id of the user
     * @return
     */
    public ResponseEntity<?> showFormUpdate(Long userId);

    /**
     * Update a user.
     *
     * @param updateUserDTO contains the user's new name,date of birth, phone number, address, email, salary and status
     * @return
     */
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO);

    /**
     * View list of users with same role.
     *
     * @param roleId the id of user's role
     * @return
     */
    public ResponseEntity<?> getAllUserByRole(int roleId);

    /**
     * View list of users with same role and status.
     *
     * @param roleId the id of user's role
     * @param status the status of user
     * @return
     */
    public ResponseEntity<?> getAllUserByRoleAndStatus(int roleId, int status);
}
