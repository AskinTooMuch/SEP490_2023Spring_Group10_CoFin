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

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.SupplierRepository;
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

    /**
     * API to get all of their suppliers.
     * userId is the id of current logged-in user.
     *
     * @param userId
     * @return list of Suppliers
     */
    @GetMapping("/all")
    public List<Supplier> getAllSupplier(Long userId) {
        // Get all suppliers of the current User
        List<Supplier> supplierList = supplierRepository.findByUserId(userId);
        return supplierList;
    }

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
}
