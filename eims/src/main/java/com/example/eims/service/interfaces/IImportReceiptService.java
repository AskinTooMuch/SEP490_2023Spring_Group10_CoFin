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

import org.springframework.http.ResponseEntity;

public interface IImportReceiptService {
    /**
     * Get all import bill from suppliers of an Owner.
     *
     * @param userId the id of supplier
     * @return list of import receipts
     */
    public ResponseEntity<?> viewImportsByUser(Long userId);

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param userId the id of current logged-in user.
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return list of import receipts
     */
    public ResponseEntity<?> viewImportsByUserPaging(Long userId, Integer page, Integer size, String sort);

    /**
     * Get all import bill from a supplier of an owner.
     *
     * @param supplierId the id of the supplier
     * @return list of import receipts
     */
    public ResponseEntity<?> viewImportsBySupplier(Long supplierId);

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param supplierId the id of current logged-in user.
     * @param page the page number
     * @param size the size of page
     * @param sort sorting type
     * @return list of import receipts
     */
    public ResponseEntity<?> viewImportsBySupplierPaging(Long supplierId, Integer page, Integer size, String sort);

    /**
     * Get imports statistics of an owner.
     *
     * @param userId the id of the owner
     * @return list of import receipts
     */
    public ResponseEntity<?> viewImportStatistic(Long userId);
}
