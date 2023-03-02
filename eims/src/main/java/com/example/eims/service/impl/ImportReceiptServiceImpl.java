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

import com.example.eims.service.interfaces.IImportReceiptService;
import org.springframework.http.ResponseEntity;

public class ImportReceiptServiceImpl implements IImportReceiptService {
    /**
     * Get all import bill from suppliers of an Owner.
     *
     * @param userId the id of supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsByUser(Long userId) {
        return null;
    }

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param userId the id of current logged-in user.
     * @param page   the page number
     * @param size   the size of page
     * @param sort   sorting type
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsByUserPaging(Long userId, Integer page, Integer size, String sort) {
        return null;
    }

    /**
     * Get all import bill from a supplier of an owner.
     *
     * @param supplierId the id of the supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsBySupplier(Long supplierId) {
        return null;
    }

    /**
     * Get all import bill from suppliers of an Owner with paging.
     *
     * @param supplierId the id of current logged-in user.
     * @param page       the page number
     * @param size       the size of page
     * @param sort       sorting type
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsBySupplierPaging(Long supplierId, Integer page, Integer size, String sort) {
        return null;
    }

    /**
     * Get imports statistics of an owner.
     *
     * @param userId the id of the owner
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportStatistic(Long userId) {
        return null;
    }
}
