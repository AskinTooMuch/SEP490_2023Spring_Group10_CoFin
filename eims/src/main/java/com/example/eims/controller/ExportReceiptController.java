/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 03/04/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.controller;

import com.example.eims.dto.exportReceipt.CreateExportDTO;
import com.example.eims.service.interfaces.IExportReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/export")
public class ExportReceiptController {
    @Autowired
    private IExportReceiptService exportReceiptService;

    /**
     * Get all export bill of a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    @GetMapping("/allByFacility")
    public ResponseEntity<?> viewExportsByFacility(Long facilityId) {
        return exportReceiptService.viewExportsByFacility(facilityId);
    }

    /**
     * Get all egg batch's available egg products.
     *
     * @param facilityId the id of the facility.
     * @return
     */
    @GetMapping("/getData")
    public ResponseEntity<?> getExportData(@RequestParam Long facilityId) {
        return exportReceiptService.getExportData(facilityId);
    }

    /**
     * Create an export.
     *
     * @param createExportDTO contains customer's id, user's id who create the export, facility's id, export date
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createExport(@RequestBody CreateExportDTO createExportDTO) {
        return exportReceiptService.createExport(createExportDTO);
    }

    /**
     * View detail of an export.
     *
     * @param exportId the id of export receipt.
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<?> getExport(@RequestParam Long exportId) {
        return exportReceiptService.getExport(exportId);
    }

    /**
     * Update paid amount of export receipt.
     *
     * @param exportId the id of export receipt.
     * @param paid the paid amount
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<?> updatePaidOfExport(@RequestParam Long exportId, @RequestParam BigDecimal paid) {
        return exportReceiptService.updatePaidOfExport(exportId, paid);
    }
}
