package com.example.eims.service.interfaces;

import com.example.eims.dto.exportReceipt.CreateExportDTO;
import org.springframework.http.ResponseEntity;

public interface IExportReceiptService {
    /**
     * Get all export bill of a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    public ResponseEntity<?> viewExportsByFacility(Long facilityId);
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
