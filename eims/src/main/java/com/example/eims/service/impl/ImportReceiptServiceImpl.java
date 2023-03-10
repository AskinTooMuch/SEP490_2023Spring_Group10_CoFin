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

import com.example.eims.dto.importReceipt.ImportReceiptStatisticDTO;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.service.interfaces.IImportReceiptService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
public class ImportReceiptServiceImpl implements IImportReceiptService {

    @Autowired
    private final ImportReceiptRepository importReceiptRepository;
    @PersistenceContext
    private final EntityManager em;

    public ImportReceiptServiceImpl(ImportReceiptRepository importReceiptRepository, EntityManager em) {
        this.importReceiptRepository = importReceiptRepository;
        this.em = em;
    }

    /**
     * Get all import bill from suppliers of an Owner.
     *
     * @param userId the id of supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsByUser(Long userId) {
        Optional<List<ImportReceipt>> importReceiptList = importReceiptRepository.findByUserId(userId);
        if (importReceiptList.isPresent()){
            return new ResponseEntity<>(importReceiptList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("importId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("importId").descending();
        }
        // Get all customers of the current User with Paging
        Page<ImportReceipt> importReceiptPage = importReceiptRepository.findAllByUserId(userId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(importReceiptPage, HttpStatus.OK);
    }

    /**
     * Get all import bill from a supplier of an owner.
     *
     * @param supplierId the id of the supplier
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportsBySupplier(Long supplierId) {
        Optional<List<ImportReceipt>> importReceiptList = importReceiptRepository.findBySupplierId(supplierId);
        if (importReceiptList.isPresent()){
            return new ResponseEntity<>(importReceiptList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
        // Get sorting type
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("importId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("importId").descending();
        }
        // Get all customers of the current User with Paging
        Page<ImportReceipt> importReceiptPage = importReceiptRepository.findAllBySupplierId(supplierId, PageRequest.of(page, size, sortable));
        return new ResponseEntity<>(importReceiptPage, HttpStatus.OK);
    }

    /**
     * Get imports statistics of an owner.
     *
     * @param userId the id of the owner
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewImportStatistic(Long userId) {
        Query query = em.createNamedQuery("getImportReceiptStatisticByUserId");
        query.setParameter(1, userId);
        List<ImportReceiptStatisticDTO> statistics = query.getResultList();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
