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
 * 26/02/2023   2.1         ChucNV      Fix create supplier<br>
 * 28/02/2023   3.0         ChucNV      Enable Security<br>
 * 28/02/2023   4.0         DuongVV     Add Paging<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.Customer;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.repository.UserRepository;
import com.example.eims.utils.StringDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ImportReceiptRepository importReceiptRepository;

    /**
     * Get all of their suppliers.
     *
     * @param userId the id of current logged-in user
     * @return list of Suppliers
     */
    @GetMapping("/all")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllSupplier(@RequestParam Long userId) {
        // Get all suppliers of the current User
        List<Supplier> supplierList = supplierRepository.findByUserId(userId);
        if (supplierList.isEmpty()) {
            return new ResponseEntity<>("No supplier found", HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(supplierList, HttpStatus.OK);
        }
    }

    /**
     * Get a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @GetMapping("/{supplierId}")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getSupplier(@PathVariable Long supplierId) {
        // Get a supplier
        Supplier supplier = supplierRepository.findBySupplierId(supplierId).orElse(null);
        if (supplier != null) {
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No supplier", HttpStatus.OK);
        }
    }

    /**
     * Create a supplier of a user.
     *
     * @param createSupplierDTO contains the user's id, name, phone number and address of the supplier
     * @return
     */
    @PostMapping("/create")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> createSupplier(@RequestBody CreateSupplierDTO createSupplierDTO) {
        System.out.println(createSupplierDTO);
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
     * Show form to update a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @GetMapping("/update/showForm/{supplierId}")
    public ResponseEntity<?> showFormUpdate(@PathVariable Long supplierId) {
        // Get a supplier
        Supplier supplier = supplierRepository.findBySupplierId(supplierId).orElse(null);
        if (supplier != null) {
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No supplier", HttpStatus.OK);
        }
    }

    /**
     * Update a supplier of a user.
     *
     * @param supplierId the id of the supplier
     * @param updateSupplierDTO contains the user's id, new name, phone number and address of the supplier
     * @return
     */
    @PutMapping("/update/{supplierId}")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> updateSupplier(@PathVariable Long supplierId, @RequestBody UpdateSupplierDTO updateSupplierDTO) {
        // Retrieve supplier's new information
        Supplier supplier = supplierRepository.findBySupplierId(supplierId).get();
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
     * Delete a supplier of a user.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @DeleteMapping("/delete/{supplierId}")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> deleteSupplier(@PathVariable Long supplierId) {
        // Delete
        supplierRepository.deleteById(supplierId);
        return new ResponseEntity<>("Supplier deleted!", HttpStatus.OK);
    }

    /**
     * Search supplier of the user by their name or phone number.
     *
     * @param key the search key (name or phone number)
     * @param userId the id of current logged-in user
     * @return list of suppliers
     */
    @GetMapping("/search")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> searchSupplier(@RequestParam String key, @RequestBody Long userId) {

        // Trim spaces
        StringDealer stringDealer = new StringDealer();
        key = stringDealer.trimMax(key);
        // Search
        List<Supplier> supplierList = supplierRepository.searchByUsernameOrPhone(userId, key);
        return new ResponseEntity<>(supplierList, HttpStatus.OK);
    }

    /**
     * Get all import bill from supplier.
     *
     * @param supplierId the id of supplier
     * @return list of import receipts
     */
    @GetMapping("/imports/{supplierId}")
    public ResponseEntity<?> viewImports(@PathVariable Long supplierId) {
        List<ImportReceipt> importReceiptList = importReceiptRepository.findBySupplierId(supplierId);
        return new ResponseEntity<>(importReceiptList, HttpStatus.OK);
    }

    /**
     * Get all of user's Suppliers with Paging.
     *
     * @param userId the id of current logged-in user.
     * @return list of Suppliers
     */
    @GetMapping("/allPaging")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllSupplierPaging(@RequestParam(name = "userId") Long userId,
                                                    @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                    @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {

        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("supplierId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("supplierId").descending();
        }
        // Get all customers of the current User with Paging
        Page<Supplier> supplierPage = supplierRepository.findAllByUserId(userId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(supplierPage, HttpStatus.OK);
    }
}
