package com.example.eims.service.impl;

import com.example.eims.dto.exportReceipt.CreateExportDTO;
import com.example.eims.service.interfaces.IExportReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExportReceiptServiceImpl implements IExportReceiptService {
    /**
     * Get all export bill of a facility.
     *
     * @param facilityId the id of facility
     * @return list of import receipts
     */
    @Override
    public ResponseEntity<?> viewExportsByFacility(Long facilityId) {
        return null;
    }

    /**
     * Create an export.
     *
     * @param createExportDTO contains customer's id, user's id who create the export, facility's id, export date
     * @return
     */
    @Override
    public ResponseEntity<?> createExport(CreateExportDTO createExportDTO) {
        return null;
    }

    /**
     * View detail of an export.
     *
     * @param exportId the id of export receipt.
     * @return
     */
    @Override
    public ResponseEntity<?> getExport(Long exportId) {
        return null;
    }

    /**
     * Update paid amount of export receipt.
     *
     * @param exportId the id of export receipt.
     * @param paid
     * @return
     */
    @Override
    public ResponseEntity<?> updatePaidOfExport(Long exportId, Float paid) {
        return null;
    }
}
