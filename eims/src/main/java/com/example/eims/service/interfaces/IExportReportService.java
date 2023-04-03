/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 03/04/2023   1.0         DuongNH      First Deploy<br>
 */
package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface IExportReportService {
    /**
     * Return all export report of owner
     * @param userId owner's id
     * @return List of export receipt static DTO
     */
    public ResponseEntity<?> getAllExportReport(Long userId);

    /**
     * Get all export report by month
     * @param customerId customer's id
     * @param year year
     * @return List of ReportDetailDTO
     */
    public ResponseEntity<?> getAllExportReportByMonth(Long customerId,String year);

    /**
     * Get all export report by year
     * @param customerId customer's id
     * @return List of ReportDetailDTO
     */
    public ResponseEntity<?> getAllExportReportByYear(Long userId, Long customerId);

    /**
     * Get all year that owner have bought product from customer
     * @param userId owner's id
     * @param customerId customer's id
     * @return String ArrayList
     */
    public ResponseEntity<?> getExportReceiptYear(Long userId, Long customerId);
}
