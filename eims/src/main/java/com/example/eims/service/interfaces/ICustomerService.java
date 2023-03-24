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

package com.example.eims.service.interfaces;

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import org.springframework.http.ResponseEntity;

public interface ICustomerService {
    /**
     * Get all of user's customers.
     *
     * @param userId the id of the Owner
     * @return list of Customers
     */
    public ResponseEntity<?> getAllCustomer(Long userId);

    /**
     * Get a customer.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    public ResponseEntity<?> getCustomer(Long customerId);

    /**
     * Create a customer of a user.
     *
     * @param createCustomerDTO contains the user's id, name, phone number and address of the customer
     * @return
     */
    public ResponseEntity<?> createCustomer(CreateCustomerDTO createCustomerDTO);

    /**
     * Show form to update a customer of a user.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    public ResponseEntity<?> showFormUpdate(Long customerId);

    /**
     * Update a customer of a user.
     *
     * @param updateCustomerDTO contains the new name, phone number and address, email and status of the customer
     * @return
     */
    public ResponseEntity<?> updateCustomer(UpdateCustomerDTO updateCustomerDTO);

    /**
     * Search customer of the user by their name or phone number.
     *
     * @param key    the search key (name or phone number)
     * @param userId the id of the Owner
     * @return list of customers match the key search item.
     */
    public ResponseEntity<?> searchCustomer(Long userId, String key);

    /**
     * Get all of user's customers with Paging.
     *
     * @param userId the id of the Owner
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Customers
     */
    public ResponseEntity<?> getAllCustomerPaging(Long userId, Integer page, Integer size, String sort);
}
