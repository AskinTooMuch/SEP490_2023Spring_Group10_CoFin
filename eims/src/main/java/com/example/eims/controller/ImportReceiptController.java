/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 22/02/2023    1.0        DuongVV          First Deploy<br>
 * 28/02/2023    1.1        DuongVV          Update function<br>
 */
package com.example.eims.controller;

import com.example.eims.dto.importReceipt.ImportReceiptStatisticDTO;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.repository.ImportReceiptRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/import")
public class ImportReceiptController {
    @Autowired
    private ImportReceiptRepository importReceiptRepository;

    @PersistenceContext
    private EntityManager em;


    /**
     * API to get all import bill from suppliers of an owner.
     * userId is the id of supplier
     *
     * @param userId
     * @return list of import receipts
     */
    @GetMapping("/imports/all")
    public ResponseEntity<?> viewImports(@RequestParam Long userId) {
        List<ImportReceipt> importReceiptList = importReceiptRepository.findByUserId(userId);
        return new ResponseEntity<>(importReceiptList, HttpStatus.OK);
    }

    /**
     * API to get imports statistics of an owner.
     * userId is the id of the owner
     *
     * @param userId
     * @return list of import receipts
     */
    @GetMapping("/imports/statistic")
    public ResponseEntity<?> viewImportStatistic(@RequestParam Long userId) {
        List<ImportReceiptStatisticDTO> statistics = em.createNamedQuery("getImportReceiptStatisticByUserId")
                .setParameter(1, userId)
                .getResultList();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    /**
     * API to get all import bill from a supplier of an owner.
     * supplierId is the id of the supplier
     *
     * @param supplierId
     * @return list of import receipts
     */
    @GetMapping("/imports/supplier")
    public ResponseEntity<?> viewImportsBySupplier(@RequestParam Long supplierId) {
        List<ImportReceipt> importReceiptList = importReceiptRepository.findBySupplierId(supplierId);
        return new ResponseEntity<>(importReceiptList, HttpStatus.OK);
    }
}
