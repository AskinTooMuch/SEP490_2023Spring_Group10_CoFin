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

import com.example.eims.dto.importReceipt.CreateImportDTO;
import org.springframework.http.ResponseEntity;

public interface IImportReceiptService {
    /**
     * Get all import bill a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    public ResponseEntity<?> viewImportsByFacility(Long facilityId);

    /**
     * Create an import.
     *
     * @param createImportDTO contains supplier's id, user's id who create the import, facility's id, import date
     * @return
     */
    public ResponseEntity<?> createImport(CreateImportDTO createImportDTO);

    /**
     * View detail of an import.
     *
     * @param importId the id of import receipt.
     * @return
     */
    public ResponseEntity<?> getImport(Long importId);

    /**
     * Update paid amount of import receipt.
     *
     * @param importId the id of import receipt.
     * @return
     */
    public ResponseEntity<?> updatePaidOfImport(Long importId, Float paid);
}
