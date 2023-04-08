/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 22/02/2023    1.0        DuongVV          First Deploy<br>
 * 28/02/2023    2.0        DuongVV          Update function, add Paging<br>
 * 01/03/2023    2.1        ChucNV           (Line 64, method viewImportsByUserPaging) @GetMapping("/allByUserPaging") -> @GetMapping("/allImportByUserPaging")<br>
 * 02/03/2023    3.0        DuongVV          New code structure<br>
 */

package com.example.eims.controller;
import com.example.eims.dto.importReceipt.CreateImportDTO;
import com.example.eims.entity.EggBatch;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.service.interfaces.IImportReceiptService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/import")
public class ImportReceiptController {
    @Autowired
    private IImportReceiptService importReceiptService;

    /**
     * Get all import bill a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/allByFacility")
    public ResponseEntity<?> viewImportsByFacility(@RequestParam Long facilityId) {
        return importReceiptService.viewImportsByFacility(facilityId);
    }

    /**
     * Create an import.
     *
     * @param createImportDTO contains supplier's id, user's id who create the import, facility's id, import date,
     *                        total, paid, status, list of import items.
     * @return
     */
    @Secured({"ROLE_OWNER","ROLE_EMPLOYEE"})
    @PostMapping("/create")
    public ResponseEntity<?> createImport(@RequestBody CreateImportDTO createImportDTO) {
        return importReceiptService.createImport(createImportDTO);
    }

    /**
     * View detail of an import.
     *
     * @param importId the id of import receipt.
     * @return
     */
    @Secured({"ROLE_OWNER","ROLE_EMPLOYEE"})
    @GetMapping("/get")
    public ResponseEntity<?> getImport(@RequestParam Long importId) {
        return importReceiptService.getImport(importId);
    }

    /**
     * Update paid amount of import receipt.
     *
     * @param importId the id of import receipt.
     * @param paid
     * @return
     */
    @Secured({"ROLE_OWNER","ROLE_EMPLOYEE"})
    @PutMapping("/update")
    public ResponseEntity<?> updatePaidOfImport(@RequestParam Long importId, @RequestParam Float paid) {
        return  importReceiptService.updatePaidOfImport(importId, paid);
    }
}
