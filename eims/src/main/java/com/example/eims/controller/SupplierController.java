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
 * 02/03/2023   5.0         DuongVV     New code structure<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.service.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    /**
     * Get all of their suppliers.
     *
     * @param userId the id of the Owner
     * @return list of Suppliers
     */
    @GetMapping("/all")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllSupplier(@RequestParam Long userId) {
        return supplierService.getAllSupplier(userId);
    }

    /**
     * Get all of their active suppliers.
     *
     * @param userId the id of the Owner
     * @return list of Suppliers
     */
    @GetMapping("/allActive")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getActiveSupplier(@RequestParam Long userId) {
        return supplierService.getActiveSupplier(userId);
    }

    /**
     * Get a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @GetMapping("get")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getSupplier(@RequestParam Long supplierId) {
        return supplierService.getSupplier(supplierId);
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
        return supplierService.createSupplier(createSupplierDTO);
    }

    /**
     * Show form to update a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @GetMapping("/update/showForm")
    public ResponseEntity<?> showFormUpdate(@RequestParam Long supplierId) {
        return supplierService.showFormUpdate(supplierId);
    }

    /**
     * Update a supplier of a user.
     *
     * @param updateSupplierDTO contains the user's id, new name, phone number and address of the supplier
     * @return
     */
    @PutMapping("/update/save")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> updateSupplier(@RequestBody UpdateSupplierDTO updateSupplierDTO) {
        return supplierService.updateSupplier(updateSupplierDTO);
    }

    /**
     * Search supplier of the user by their name or phone number.
     *
     * @param userId the id of the Owner
     * @param key        the search key (name or phone number
     * @return list of suppliers
     */
    @GetMapping("/search")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> searchSupplier(@RequestParam Long userId, @RequestParam String key) {
        return supplierService.searchSupplier(userId, key);
    }

    /**
     * Get all import bill from supplier.
     *
     * @param supplierId the id of supplier
     * @return list of import receipts
     */
    @GetMapping("/imports")
    public ResponseEntity<?> viewImports(@RequestParam Long supplierId) {
        return supplierService.viewImports(supplierId);
    }

    /**
     * Get all of user's Suppliers with Paging.
     *
     * @param userId the id of the Owner
     * @return list of Suppliers
     */
    @GetMapping("/allPaging")
    @Secured({"ROLE_OWNER"})
    public ResponseEntity<?> getAllSupplierPaging(@RequestParam(name = "userId") Long userId,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return supplierService.getAllSupplierPaging(userId, page, size, sort);
    }
}
