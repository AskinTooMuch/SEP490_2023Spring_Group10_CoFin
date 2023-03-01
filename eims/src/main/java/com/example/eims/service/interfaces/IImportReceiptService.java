package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface IImportReceiptService {
    public ResponseEntity<?> viewImportsByUser(Long userId);
    public ResponseEntity<?> viewImportsByUserPaging(Long userId, Integer page, Integer size, String sort);
    public ResponseEntity<?> viewImportsBySupplier(Long supplierId);
    public ResponseEntity<?> viewImportsBySupplierPaging(Long supplierId, Integer page, Integer size, String sort);
    public ResponseEntity<?> viewImportStatistic(Long userId);
}
