package com.example.eims.controller;

import com.example.eims.dto.NewCustomerDTO;
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
     * The userId is the id of current logged-in user.
     *
     * @param userId
     * @return list of Customer
     */
    @GetMapping("/all")
    public List<Customer> getAllCustomer(Long userId) {
        // Get all customers of the current User
        List<Customer> customerList = customerRepository.findByUserId(userId);
        return customerList;
    }

    /**
     * API to get a customer.
     * id is the id of the customer
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        // Get a customer of the current User
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    /**
     * API to create a customer of a user.
     * The DTO contains the user's id, name, phone number and address of the customer
     *
     * @param newCustomerDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createCustomer(NewCustomerDTO newCustomerDTO) {
        // Retrieve customer information and create new customer
        Customer customer = new Customer();
        customer.setUserId(newCustomerDTO.getUserId());
        customer.setName(newCustomerDTO.getName());
        customer.setPhone(newCustomerDTO.getPhone());
        customer.setAddress(newCustomerDTO.getAddress());
        // Save
        customerRepository.save(customer);
        return new ResponseEntity<>("Customer created!", HttpStatus.OK);
    }

    /**
     * API to update a customer of a user.
     * id is the id of the customer
     * The DTO contains the user's id, new name, phone number and address of the customer
     *
     * @param id, newCustomerDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, NewCustomerDTO newCustomerDTO) {
        // Retrieve customer's new information
        Customer customer = new Customer();
        customer.setUserId(newCustomerDTO.getUserId());
        customer.setName(newCustomerDTO.getName());
        customer.setPhone(newCustomerDTO.getPhone());
        customer.setAddress(newCustomerDTO.getAddress());
        // Save
        customerRepository.save(customer);
        return new ResponseEntity<>("Customer updated!", HttpStatus.OK);
    }

    /**
     * API to delete a customer of a user.
     * id is the id of the customer
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        // Delete
        customerRepository.deleteById(id);
        return new ResponseEntity<>("Customer deleted!", HttpStatus.OK);
    }
}
