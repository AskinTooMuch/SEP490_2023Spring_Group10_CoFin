/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/04/2023   1.0         DuongNH      First Deploy<br>
 */
package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.sql.Date;

public interface IImportReportService {
    /**
     * Return all import report of owner
     * @param userId owner's id
     * @return List of import receipt static DTO
     */
    public ResponseEntity<?> getAllImportReport(Long userId);

    /**
     * Get all import report by month
     * @param supplierId supplier's id
     * @param year year
     * @return List of ReportDetailDTO
     */
    public ResponseEntity<?> getAllImportReportByMonth(Long supplierId,String year);

    /**
     * Get all import report by year
     * @param supplierId supplier's id
     * @return List of ReportDetailDTO
     */
    public ResponseEntity<?> getAllImportReportByYear(Long userId, Long supplierId);

    /**
     * Get all year that owner have bought product from supplier
     * @param userId owner's id
     * @param supplierId supplier's id
     * @return String ArrayList
     */
    public ResponseEntity<?> getImportReceiptYear(Long userId, Long supplierId);
}
