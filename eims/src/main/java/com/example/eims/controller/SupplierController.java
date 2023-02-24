/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 * 23/02/2023    2.0        DuongVV          Add search<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.Customer;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * API to get all of their suppliers.
     * phone is the phone number of current logged-in user.
     *
     * @param phone
     * @return list of Suppliers
     */
    @GetMapping("/all")
    public List<Supplier> getAllSupplier(String phone) {
        // Get current user's id
        Long userId = Long.parseLong(userRepository.findIdByPhone(phone));
        // Get all suppliers of the current User
        List<Supplier> supplierList = supplierRepository.findByUserId(userId);
        return supplierList;
    }

/*    @GetMapping("/all")
    public List<Customer> getAllCustomer(Long userId) {
        // Get all customers of the current User
        List<Customer> customerList = customerRepository.findByUserId(userId);
        return customerList;
    }*/

    /**
     * API to get a supplier.
     * supplierId is the id of the supplier
     *
     * @param supplierId
     * @return
     */
    @GetMapping("/{supplierId}")
    public Supplier getSupplier(@PathVariable Long supplierId) {
        // Get a supplier
        Supplier supplier = supplierRepository.findById(supplierId).get();
        return supplier;
    }

    /**
     * API to create a supplier of a user.
     * newSupplierDTO contains the user's id, name, phone number and address of the supplier
     *
     * @param createSupplierDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createSupplier(CreateSupplierDTO createSupplierDTO) {
        // Check phone number existed or not
        boolean existed = supplierRepository.existsBySupplierPhone(createSupplierDTO.getSupplierPhone());
        if (existed) { /* if phone number existed */
            return new ResponseEntity<>("Supplier's phone existed!", HttpStatus.OK);
        } else { /* if phone number not existed */
            // Retrieve supplier information and create new supplier
            Supplier supplier = new Supplier();
            supplier.setUserId(createSupplierDTO.getUserId());
            supplier.setSupplierName(createSupplierDTO.getSupplierName());
            supplier.setSupplierPhone(createSupplierDTO.getSupplierPhone());
            supplier.setSupplierAddress(createSupplierDTO.getSupplierAddress());
            supplier.setSupplierMail(createSupplierDTO.getSupplierMail());
            supplier.setStatus(1);
            // Save
            supplierRepository.save(supplier);
            return new ResponseEntity<>("Supplier created!", HttpStatus.OK);
        }
    }

    /**
     * API to update a supplier of a user.
     * supplierId is the id of the supplier
     * newSupplierDTO contains the user's id, new name, phone number and address of the supplier
     *
     * @param updateSupplierDTO
     * @param supplierId
     * @return
     */
    @PutMapping("/update/{supplierId}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long supplierId, UpdateSupplierDTO updateSupplierDTO) {
        // Retrieve supplier's new information
        Supplier supplier = supplierRepository.findBySupplierId(supplierId);
        supplier.setSupplierName(updateSupplierDTO.getSupplierName());
        supplier.setSupplierPhone(updateSupplierDTO.getSupplierPhone());
        supplier.setSupplierAddress(updateSupplierDTO.getSupplierAddress());
        supplier.setSupplierMail(updateSupplierDTO.getSupplierMail());
        supplier.setStatus(updateSupplierDTO.getStatus());
        // Save
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Supplier updated!", HttpStatus.OK);
    }

    /**
     * API to delete a supplier of a user.
     * supplierId is the id of the supplier
     *
     * @param supplierId
     * @return
     */
    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long supplierId) {
        // Delete
        supplierRepository.deleteById(supplierId);
        return new ResponseEntity<>("Supplier deleted!", HttpStatus.OK);
    }

    /**
     * API to search supplier of the user by their name or phone number.
     * key is the search key (name or phone number)
     * userId is the id of current logged-in user
     *
     * @param key
     * @param userId
     * @return list of suppliers
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchSupplier(@RequestParam String key, Long userId) {
        // Trim spaces
        StringDealer stringDealer = new StringDealer();
        key = stringDealer.trimMax(key);
        // Search
        List<Supplier> supplierList = supplierRepository.searchByUsernameOrPhone(userId, key);
        return new ResponseEntity<>(supplierList, HttpStatus.OK);
    }
}
