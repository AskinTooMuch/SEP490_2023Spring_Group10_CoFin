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

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import com.example.eims.service.interfaces.ICustomerService;
import org.springframework.http.ResponseEntity;

public class CustomerServiceImpl implements ICustomerService{
    /**
     * Get all of user's customers.
     *
     * @param userId the id of current logged-in user.
     * @return list of Customers
     */
    @Override
    public ResponseEntity<?> getAllCustomer(Long userId) {
        return null;
    }

    /**
     * Get a customer.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Override
    public ResponseEntity<?> getCustomer(Long customerId) {
        return null;
    }

    /**
     * Create a customer of a user.
     *
     * @param createCustomerDTO contains the user's id, name, phone number and address of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> createCustomer(CreateCustomerDTO createCustomerDTO) {
        return null;
    }

    /**
     * Show form to update a customer of a user.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long customerId) {
        return null;
    }

    /**
     * Update a customer of a user.
     *
     * @param customerId        the id of the customer
     * @param updateCustomerDTO contains the new name, phone number and address, email and status of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> updateCustomer(Long customerId, UpdateCustomerDTO updateCustomerDTO) {
        return null;
    }

    /**
     * Delete a customer of a user.
     *
     * @param customerId the id of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> deleteCustomer(Long customerId) {
        return null;
    }

    /**
     * Search customer of the user by their name or phone number.
     *
     * @param key    the search key (name or phone number)
     * @param userId the id of current logged-in user
     * @return list of customers match the key search item.
     */
    @Override
    public ResponseEntity<?> searchCustomer(String key, Long userId) {
        return null;
    }

    /**
     * Get all of user's customers with Paging.
     *
     * @param userId the id of current logged-in user.
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Customers
     */
    @Override
    public ResponseEntity<?> getAllCustomerPaging(Long userId, Integer page, Integer size, String sort) {
        return null;
    }
}
