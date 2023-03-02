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
import com.example.eims.service.interfaces.ISupplierService;
import org.springframework.http.ResponseEntity;

public class SupplierServiceImpl implements ISupplierService {
    /**
     * Get all of their suppliers.
     *
     * @param userId the id of current logged-in user
     * @return list of Suppliers
     */
    @Override
    public ResponseEntity<?> getAllSupplier(Long userId) {
        return null;
    }

    /**
     * Get a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> getSupplier(Long supplierId) {
        return null;
    }

    /**
     * Create a supplier of a user.
     *
     * @param createSupplierDTO contains the user's id, name, phone number and address of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> createSupplier(CreateSupplierDTO createSupplierDTO) {
        return null;
    }

    /**
     * Show form to update a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> showFormUpdate(Long supplierId) {
        return null;
    }

    /**
     * Update a supplier of a user.
     *
     * @param supplierId        the id of the supplier
     * @param updateSupplierDTO contains the user's id, new name, phone number and address of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> updateSupplier(Long supplierId, UpdateSupplierDTO updateSupplierDTO) {
        return null;
    }

    /**
     * Delete a supplier of a user.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    @Override
    public ResponseEntity<?> deleteSupplier(Long supplierId) {
        return null;
    }

    /**
     * Search supplier of the user by their name or phone number.
     *
     * @param key    the search key (name or phone number)
     * @param userId the id of current logged-in user
     * @return list of suppliers
     */
    @Override
    public ResponseEntity<?> searchSupplier(String key, Long userId) {
        return null;
    }

    /**
     * Get all import bill from supplier.
     *
     * @param supplierId the id of supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImports(Long supplierId) {
        return null;
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
        return null;
    }
}
