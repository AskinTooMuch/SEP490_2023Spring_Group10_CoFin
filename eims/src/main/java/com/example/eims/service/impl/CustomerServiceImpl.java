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
import com.example.eims.entity.Customer;
import com.example.eims.repository.CustomerRepository;
import com.example.eims.service.interfaces.ICustomerService;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Get all of user's customers.
     *
     * @param userId the id of current logged-in user.
     * @return list of Customers
     */
    @Override
    public ResponseEntity<?> getAllCustomer(Long userId) {
        // Get all customers of the current User
        List<Customer> customerList = customerRepository.findByUserId(userId);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>("No customer found", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        }
    }

    /**
     * Get a customer.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Override
    public ResponseEntity<?> getCustomer(Long customerId) {
        // Get a customer of the current User
        Customer customer = customerRepository.findByCustomerId(customerId).orElse(null);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No customer", HttpStatus.OK);
        }
    }

    /**
     * Create a customer of a user.
     *
     * @param createCustomerDTO contains the user's id, name, phone number and address of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> createCustomer(CreateCustomerDTO createCustomerDTO) {
        // Check phone number existed or not
        boolean existed = customerRepository.existsByCustomerPhone(createCustomerDTO.getPhone());
        if (existed) { /* if phone number existed */
            return new ResponseEntity<>("Customer's phone existed!", HttpStatus.OK);
        } else { /* if phone number not existed */
            // Retrieve customer information and create new customer
            Customer customer = new Customer();
            customer.setUserId(createCustomerDTO.getUserId());
            customer.setCustomerName(createCustomerDTO.getName());
            customer.setCustomerPhone(createCustomerDTO.getPhone());
            customer.setCustomerAddress(createCustomerDTO.getAddress());
            customer.setCustomerMail(createCustomerDTO.getMail());
            customer.setStatus(1);
            // Save
            customerRepository.save(customer);
            return new ResponseEntity<>("Customer created!", HttpStatus.OK);
        }
    }

    /**
     * Show form to update a customer of a user.
     *
     * @param customerId the id of the customer
     * @return a customer
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long customerId) {
        // Get a customer of the current User
        Customer customer = customerRepository.findByCustomerId(customerId).orElse(null);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No customer", HttpStatus.OK);
        }
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
        // Retrieve customer's new information
        Customer customer = customerRepository.findByCustomerId(customerId).get();
        customer.setCustomerName(updateCustomerDTO.getName());
        customer.setCustomerPhone(updateCustomerDTO.getPhone());
        customer.setCustomerAddress(updateCustomerDTO.getAddress());
        customer.setCustomerMail(updateCustomerDTO.getMail());
        customer.setStatus(updateCustomerDTO.getStatus());
        // Save
        customerRepository.save(customer);
        return new ResponseEntity<>("Customer updated!", HttpStatus.OK);
    }

    /**
     * Delete a customer of a user.
     *
     * @param customerId the id of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> deleteCustomer(Long customerId) {
        // Delete
        customerRepository.deleteById(customerId);
        return new ResponseEntity<>("Customer deleted!", HttpStatus.OK);
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
        // Trim spaces
        StringDealer stringDealer = new StringDealer();
        key = stringDealer.trimMax(key);
        // Search
        List<Customer> customerList = customerRepository.searchByUsernameOrPhone(userId, key);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
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
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("customerId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("customerId").descending();
        }
        // Get all customers of the current User with Paging
        Page<Customer> customerPage = customerRepository.findAllByUserId(userId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(customerPage, HttpStatus.OK);
    }
}
