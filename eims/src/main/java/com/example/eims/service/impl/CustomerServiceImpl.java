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
import java.util.Optional;

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
        Optional<List<Customer>> customers = customerRepository.findByUserId(userId);
        if (customers.isPresent()) {
            return new ResponseEntity<>(customers.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
        // Check blank input
        if (createCustomerDTO.getName().equals("")) {
            return new ResponseEntity<>("Tên khách hàng không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (createCustomerDTO.getPhone().equals("")) {
            return new ResponseEntity<>("Số điện thoại khách hàng không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (createCustomerDTO.getAddress().equals("")) {
            return new ResponseEntity<>("Địa chỉ khách hàng không được để trống", HttpStatus.BAD_REQUEST);
        }
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
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a customer of a user.
     *
     * @param updateCustomerDTO contains the new name, phone number and address, email and status of the customer
     * @return
     */
    @Override
    public ResponseEntity<?> updateCustomer(UpdateCustomerDTO updateCustomerDTO) {
        // Check blank input
        if (updateCustomerDTO.getName().equals("")) {
            return new ResponseEntity<>("Tên khách hàng không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (updateCustomerDTO.getPhone().equals("")) {
            return new ResponseEntity<>("Số điện thoại khách hàng không được để trống", HttpStatus.BAD_REQUEST);
        }
        if (updateCustomerDTO.getAddress().equals("")) {
            return new ResponseEntity<>("Địa chỉ khách hàng không được để trống", HttpStatus.BAD_REQUEST);
        }

        Optional<Customer> customerOptional = customerRepository.findByCustomerId(updateCustomerDTO.getCustomerId());
        if (customerOptional.isPresent()) {
            // Retrieve customer's new information
            Customer customer = customerOptional.get();
            customer.setCustomerName(updateCustomerDTO.getName());
            customer.setCustomerPhone(updateCustomerDTO.getPhone());
            customer.setCustomerAddress(updateCustomerDTO.getAddress());
            customer.setCustomerMail(updateCustomerDTO.getMail());
            customer.setStatus(updateCustomerDTO.getStatus());
            // Save
            customerRepository.save(customer);
            return new ResponseEntity<>("Customer updated!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Search customer of the user by their name or phone number.
     *
     * @param userId the id of current logged-in user
     * @param key    the search key (name or phone number)
     * @return list of customers match the key search item.
     */
    @Override
    public ResponseEntity<?> searchCustomer(Long userId, String key) {
        // Trim spaces
        StringDealer stringDealer = new StringDealer();
        key = stringDealer.trimMax(key);
        // Search
        Optional<List<Customer>> customerList = customerRepository.searchByUsernameOrPhone(userId, key);
        if (customerList.isPresent()) {
            return new ResponseEntity<>(customerList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
