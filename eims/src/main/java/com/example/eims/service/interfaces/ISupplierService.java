package com.example.eims.service.interfaces;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ISupplierService {
    public ResponseEntity<?> getAllSupplier(Long userId);
    public ResponseEntity<?> getSupplier(Long supplierId);
    public ResponseEntity<?> createSupplier(CreateSupplierDTO createSupplierDTO);
    public ResponseEntity<?> showFormUpdate(Long supplierId);
    public ResponseEntity<?> updateSupplier(Long supplierId, UpdateSupplierDTO updateSupplierDTO);
    public ResponseEntity<?> deleteSupplier(Long supplierId);
    public ResponseEntity<?> searchSupplier(String key, Long userId);
    public ResponseEntity<?> viewImports(Long supplierId);
    public ResponseEntity<?> getAllSupplierPaging(Long userId, Integer page, Integer size, String sort);

}
