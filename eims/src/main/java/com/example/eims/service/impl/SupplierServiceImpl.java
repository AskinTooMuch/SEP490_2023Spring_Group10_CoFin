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

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.service.interfaces.ISupplierService;
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
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    private final SupplierRepository supplierRepository;
    @Autowired
    private final ImportReceiptRepository importReceiptRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ImportReceiptRepository importReceiptRepository) {
        this.supplierRepository = supplierRepository;
        this.importReceiptRepository = importReceiptRepository;
    }

    /**
     * Get all of their suppliers.
     *
     * @param userId the id of current logged-in user
     * @return list of Suppliers
     */
    @Override
    public ResponseEntity<?> getAllSupplier(Long userId) {
        // Get all suppliers of the current User
        Optional<List<Supplier>> supplierList = supplierRepository.findByUserId(userId);
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
    @Override
    public ResponseEntity<?> getSupplier(Long supplierId) {
        // Get a supplier
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isPresent()) {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a supplier of a user.
     *
     * @param createSupplierDTO contains the user's id, name, phone number and address of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> createSupplier(CreateSupplierDTO createSupplierDTO) {
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
            supplier.setFacilityName(createSupplierDTO.getFacilityName());
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
    @Override
    public ResponseEntity<?> showFormUpdate(Long supplierId) {
        // Get a supplier
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isPresent()) {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a supplier of a user.
     *
     * @param updateSupplierDTO contains the user's id, new name, phone number and address of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> updateSupplier(UpdateSupplierDTO updateSupplierDTO) {
        // Retrieve supplier's new information
        Supplier supplier = supplierRepository.findBySupplierId(updateSupplierDTO.getSupplierId()).get();
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
     * Search supplier of the user by their name or phone number.
     *
     * @param key    the search key (name or phone number)
     * @param userId the id of current logged-in user
     * @return list of suppliers
     */
    @Override
    public ResponseEntity<?> searchSupplier(Long userId, String key) {
        // Trim spaces
        StringDealer stringDealer = new StringDealer();
        key = stringDealer.trimMax(key);
        // Search
        Optional<List<Supplier>> supplierList = supplierRepository.searchByUsernameOrPhone(userId, key);
        if (supplierList.isPresent()) {
            return new ResponseEntity<>(supplierList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all import bill from supplier.
     *
     * @param supplierId the id of supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImports(Long supplierId) {
        Optional<List<ImportReceipt>> importReceiptList = importReceiptRepository.findBySupplierId(supplierId);
        if (importReceiptList.isPresent()) {
            return new ResponseEntity<>(importReceiptList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all of user's Suppliers with Paging.
     *
     * @param userId the id of current logged-in user.
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of Suppliers
     */
    @Override
    public ResponseEntity<?> getAllSupplierPaging(Long userId, Integer page, Integer size, String sort) {
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
