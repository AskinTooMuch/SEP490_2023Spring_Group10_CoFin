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
 */

package com.example.eims.controller;

import com.example.eims.dto.customer.CreateCustomerDTO;
import com.example.eims.dto.customer.UpdateCustomerDTO;
import com.example.eims.entity.Customer;
import com.example.eims.repository.CustomerRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * API to get all of user's customers.
     * userId is the id of current logged-in user.
     *
     * @param userId
     * @return list of Customers
     */
    @GetMapping("/all")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllCustomer(@RequestParam Long userId) {
        // Get all customers of the current User
        List<Customer> customerList = customerRepository.findByUserId(userId);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>("No customer found", HttpStatus.OK);
        }else {
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        }
    }

    /**
     * API to get a customer.
     * customerId is the id of the customer
     *
     * @param customerId
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/get")
    public ResponseEntity<?> getCustomer(@RequestParam Long customerId) {
        // Get a customer of the current User
        Customer customer = customerRepository.findByCustomerId(customerId).orElse(null);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No customer", HttpStatus.OK);
        }
    }

    /**
     * API to create a customer of a user.
     * createCustomerDTO contains the user's id, name, phone number and address of the customer
     *
     * @param createCustomerDTO
     * @return
     */
    @PostMapping("/create")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
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
     * API to show form to update a customer of a user.
     * customerId is the id of the customer
     *
     * @param customerId
     * @return
     */
    @GetMapping("/update/get")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long customerId) {
        // Get a customer of the current User
        Customer customer = customerRepository.findByCustomerId(customerId).orElse(null);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No customer", HttpStatus.OK);
        }
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
    @Secured({"ROLE_OWNER"})
    @PutMapping("/update/save")
    public ResponseEntity<?> updateCustomer(@RequestParam Long customerId, @RequestBody UpdateCustomerDTO updateCustomerDTO) {
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
     * API to delete a customer of a user.
     * customerId is the id of the customer
     *
     * @param customerId
     * @return
     */
    @Secured({"ROLE_OWNER"})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam Long customerId) {
        // Delete
        customerRepository.deleteById(customerId);
        return new ResponseEntity<>("Customer deleted!", HttpStatus.OK);
    }

    /**
     * API to search customer of the user by their name or phone number.
     * key is the search key (name or phone number)
     * userId is the id of current logged-in user
     *
     * @param key
     * @param userId
     * @return list of customers
     */
    @GetMapping("/search")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> searchCustomer(@RequestParam String key, @RequestBody Long userId) {
        // Trim spaces
        StringDealer stringDealer = new StringDealer();
        key = stringDealer.trimMax(key);
        // Search
        List<Customer> customerList = customerRepository.searchByUsernameOrPhone(userId, key);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }
}
