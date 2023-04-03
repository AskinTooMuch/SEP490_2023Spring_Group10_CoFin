/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/04/2023   1.0         DuongNH     First Deploy<br>
 * 02/04/2023   1.2         DuongNH     Add function<br>
 * 03/04/2023   1.2         DuongNH     Add function<br>
 * */
package com.example.eims.controller;

import com.example.eims.service.interfaces.IImportReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/importReport")
public class ImportReportController {
    @Autowired
    IImportReportService importReportService;

    /**
     * Return all import report of owner
     * @param userId owner's id
     * @return List of import receipt static DTO
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/all")
    public ResponseEntity<?> getAllImportReport(@RequestParam Long userId){
        return importReportService.getAllImportReport(userId);
    }

    /**
     * Get all import report by month
     * @param supplierId supplier's id
     * @param year year
     * @return List of ReportDetailDTO
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/getByMonth")
    public ResponseEntity<?> getAllImportReportByMonth(@RequestParam Long supplierId, @RequestParam String year){
        return importReportService.getAllImportReportByMonth(supplierId, year);
    }

    /**
     * Get all import report by year
     * @param supplierId supplier's id
     * @return List of ReportDetailDTO
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/getByYear")
    public ResponseEntity<?> getAllImportReportByYear(@RequestParam Long userId, @RequestParam Long supplierId){
        return importReportService.getAllImportReportByYear(userId,supplierId);
    }

    /**
     * Get all year that owner have bought product from supplier
     * @param userId owner's id
     * @param supplierId supplier's id
     * @return String ArrayList
     */
    @Secured({"ROLE_OWNER"})
    @GetMapping("/getImportReceiptYear")
    public ResponseEntity<?> getImportReceiptYear(@RequestParam Long userId, @RequestParam Long supplierId){
        return importReportService.getImportReceiptYear(userId,supplierId);
    }
}
