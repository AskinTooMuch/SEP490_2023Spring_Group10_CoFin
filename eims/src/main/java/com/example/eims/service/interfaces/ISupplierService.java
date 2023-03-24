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

package com.example.eims.service.interfaces;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import org.springframework.http.ResponseEntity;

public interface ISupplierService {

    /**
     * Get all of their suppliers.
     *
     * @param facilityId the id of facility
     * @return list of Suppliers
     */
    public ResponseEntity<?> getAllSupplier(Long facilityId);

    /**
     * Get all of their active suppliers.
     *
     * @param facilityId the id of facility
     * @return list of Suppliers
     */
    public ResponseEntity<?> getActiveSupplier(Long facilityId);

    /**
     * Get a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    public ResponseEntity<?> getSupplier(Long supplierId);

    /**
     * Create a supplier of a user.
     *
     * @param createSupplierDTO contains the user's id, name, phone number and address of the supplier
     * @return
     */
    public ResponseEntity<?> createSupplier(CreateSupplierDTO createSupplierDTO);

    /**
     * Show form to update a supplier.
     *
     * @param supplierId the id of the supplier
     * @return
     */
    public ResponseEntity<?> showFormUpdate(Long supplierId);

    /**
     * Update a supplier of a user.
     *
     * @param updateSupplierDTO contains the user's id, new name, phone number and address of the supplier
     * @return
     */
    public ResponseEntity<?> updateSupplier(UpdateSupplierDTO updateSupplierDTO);

    /**
     * Search supplier of the user by their name or phone number.
     *
     * @param facilityId the id of facility
     * @param key the search key (name or phone number)
     * @return list of suppliers
     */
    public ResponseEntity<?> searchSupplier(Long facilityId, String key);

    /**
     * Get all import bill from supplier.
     *
     * @param supplierId the id of supplier
     * @return list of import receipts
     */
    public ResponseEntity<?> viewImports(Long supplierId);

    /**
     * Get all of user's Suppliers with Paging.
     *
     * @param facilityId the id of facility
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return list of Suppliers
     */
    public ResponseEntity<?> getAllSupplierPaging(Long facilityId, Integer page, Integer size, String sort);

}
