/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 18/02/2023   1.0         DuongVV     First Deploy<br>
 * 23/02/2023   2.0         DuongVV     Add search<br>
 * 28/02/2023   3.0         ChucNV      Enable Security<br>
 * 28/02/2023   3.1         DuongVV     Add paging<br>
 * 02/03/2023   4.0         DuongVV     New code structure<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import com.example.eims.service.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    /**
     * Get all of user's customers.
     *
     * @param userId the id of the Owner
     * @return list of Customers
     */
    @GetMapping("/all")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllCustomer(@RequestParam Long userId) {
        return customerService.getAllCustomer(userId);
    }

    /**
     * Get a customer.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/get")
    public ResponseEntity<?> getCustomer(@RequestParam Long customerId) {
        return customerService.getCustomer(customerId);
    }

    /**
     * Create a customer of a user.
     *
     * @param createCustomerDTO contains the user's id, name, phone number and address of the customer
     * @return
     */
    @PostMapping("/create")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        return customerService.createCustomer(createCustomerDTO);
    }


    /**
     * Show form to update a customer of a user.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/update/get")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long customerId) {
        return customerService.showFormUpdate(customerId);
    }

    /**
     * Update a customer of a user.
     *
     * @param updateCustomerDTO contains customer's id, new name, phone number and address, email and status of the customer
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @PutMapping("/update/save")
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO) {
        return customerService.updateCustomer(updateCustomerDTO);
    }

    /**
     * Search customer of the user by their name or phone number.
     *
     * @param userId the id of the Owner
     * @param key    the search key (name or phone number)
     * @return list of customers match the key search item.
     */
    @GetMapping("/search")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> searchCustomer(@RequestParam Long userId, @RequestParam String key) {
        return customerService.searchCustomer(userId, key);
    }

    /**
     * Get all of user's customers with Paging.
     *
     * @param userId the id of the Owner
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Customers
     */
    @GetMapping("/allPaging")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllCustomerPaging(@RequestParam(name = "userId") Long userId,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return customerService.getAllCustomerPaging(userId, page, size, sort);
    }

}
