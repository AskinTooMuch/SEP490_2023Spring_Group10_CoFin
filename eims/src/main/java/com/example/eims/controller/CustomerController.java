/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import com.example.eims.entity.Customer;
import com.example.eims.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * API to get all of their customers.
     * userId is the id of current logged-in user.
     *
     * @param userId
     * @return list of Customers
     */
    @GetMapping("/all")
    public List<Customer> getAllCustomer(Long userId) {
        // Get all customers of the current User
        List<Customer> customerList = customerRepository.findByUserId(userId);
        return customerList;
    }

    /**
     * API to get a customer.
     * customerId is the id of the customer
     *
     * @param customerId
     * @return
     */
    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        // Get a customer of the current User
        Customer customer = customerRepository.findById(customerId).get();
        return customer;
    }

    /**
     * API to create a customer of a user.
     * createCustomerDTO contains the user's id, name, phone number and address of the customer
     *
     * @param createCustomerDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(CreateCustomerDTO createCustomerDTO) {
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

    /**
     * API to update a customer of a user.
     * customerId is the id of the customer
     * updateCustomerDTO contains the new name, phone number and address, email and status of the customer
     *
     * @param customerId
     * @param updateCustomerDTO
     * @return
     */
    @PutMapping("/update/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, UpdateCustomerDTO updateCustomerDTO) {
        // Retrieve customer's new information
        Customer customer = customerRepository.findByCustomerId(customerId);
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
     * API to delete a customer of a user.
     * customerId is the id of the customer
     *
     * @param customerId
     * @return
     */
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        // Delete
        customerRepository.deleteById(customerId);
        return new ResponseEntity<>("Customer deleted!", HttpStatus.OK);
    }
}
