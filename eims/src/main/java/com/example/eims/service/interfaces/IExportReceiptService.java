/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 02/04/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.service.interfaces;

import com.example.eims.dto.exportReceipt.CreateExportDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface IExportReceiptService {

    /**
     * Get all export bill of a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    public ResponseEntity<?> viewExportsByFacility(Long facilityId);

    /**
     * Get all egg batch's available egg products.
     *
     * @param facilityId the id of the facility.
     * @return
     */
    public ResponseEntity<?> getExportData(@RequestParam Long facilityId);

    /**
     * Create an export.
     *
     * @param createExportDTO contains customer's id, user's id who create the export, facility's id, export date
     * @return
     */
    public ResponseEntity<?> createExport(CreateExportDTO createExportDTO);

    /**
     * View detail of an export.
     *
     * @param exportId the id of export receipt.
     * @return
     */
    public ResponseEntity<?> getExport(Long exportId);

    /**
     * Update paid amount of export receipt.
     *
     * @param exportId the id of export receipt.
     * @return
     */
    public ResponseEntity<?> updatePaidOfExport(Long exportId, Float paid);
}
