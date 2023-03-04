/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
<<<<<<< Updated upstream
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/01/2023    1.0        ChucNV           First Deploy<br>
 * 21/02/2023    1.1        DuongVV          Add Update<br>
 * 28/02/2023    2.0        ChucNV           Enable security<br>
 * 28/02/2023    2.1        DuongVV          Add Paging<br>
 * 02/03/2023    3.0        DuongVV          New code structure<br>
 * 03/03/2023    3.1        DuongVV          Add view/approve registration<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.user.UpdateUserDTO;
import com.example.eims.dto.user.UserDetailDTO;
import com.example.eims.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * Requesting user information and facility information with the requesting DTO consists of user phone number.
     *
     * @param userId
     * @return the details of a user and it's facility in the form of a UserDetailDTO
     */
    @GetMapping("/details")
    //@Secured({"ROLE_OWNER", "ROLE_EMPLOYEE"})
    public ResponseEntity<UserDetailDTO> sendUserDetail(@RequestParam Long userId){
        return userService.sendUserDetail(userId);
    }

    /**
     * Get all user.
     *
     * @param
     * @return
     */
    @GetMapping("/all")
    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    public ResponseEntity<?> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * Get all user with Paging.
     *
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return
     */
    @GetMapping("/allPaging")
    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    public ResponseEntity<?> getAllUserPaging(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                        @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return userService.getAllUserPaging(page, size, sort);
    }

    /**
     * Show form to update user's information.
     *
     * @param userId the id of the user
     * @return
     */
    @GetMapping("/update/get")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long userId) {
        return userService.showFormUpdate(userId);
    }

    /**
     * Update a user.
     *
     * @param updateUserDTO contains the user's new name,date of birth, phone number, address, email, salary and status
     * @return
     */
    @PutMapping("/update/save")
    @Secured({"ROLE_OWNER", "ROLE_EMPLOYEE", "ROLE_MANAGER", "ROLE_ADMIN"})
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUser(updateUserDTO);
    }

    /**
     * View list registration of owners.
     *
     * @return
     */
    @GetMapping("/registration")
    @Secured({"ROLE_MANAGER"})
    public ResponseEntity<?> viewListRegistration() {
        return userService.viewListRegistration();
    }

    /**
     * Approve or Decline owner's registration.
     *
     * @param userId id of the user
     * @param approval is the decision of approval (3-decline, 1-approve)
     * @return
     */
    @GetMapping("/registration/approve")
    @Secured({"ROLE_MANAGER"})
    public ResponseEntity<?> registrationApproval(@RequestParam Long userId,@RequestParam boolean approval) {
        return userService.registrationApproval(userId, approval);
    }
}
