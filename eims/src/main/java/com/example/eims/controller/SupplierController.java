package com.example.eims.controller;

import com.example.eims.dto.NewSupplierDTO;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * API to get all of their suppliers.
     * The userId is the id of current logged-in user.
     *
     * @param userId
     * @return list of Supplier
     */
    @GetMapping("/all")
    public List<Supplier> getAllCustomer(Long userId) {
        // Get all suppliers of the current User
        List<Supplier> supplierList = supplierRepository.findByUserId(userId);
        return supplierList;
    }

    /**
     * API to get a supplier.
     * id is the id of the supplier
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Supplier getCustomer(@PathVariable Long id) {
        // Get all supplier of current User
        Supplier supplier = supplierRepository.findById(id).get();
        return supplier;
    }

    /**
     * API to create a supplier of a user.
     * id is the id of the supplier
     * The DTO contains the user's id, name, phone number and address of the supplier
     *
     * @param newSupplierDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createSupplier(NewSupplierDTO newSupplierDTO) {
        // Retrieve supplier information and create new supplier
        Supplier supplier = new Supplier();
        supplier.setUserId(newSupplierDTO.getUserId());
        supplier.setSupplierName(newSupplierDTO.getName());
        supplier.setSupplierPhone(newSupplierDTO.getPhone());
        supplier.setSupplierAddress(newSupplierDTO.getAddress());
        // Save
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Supplier created!", HttpStatus.OK);
    }

    /**
     * API to update a supplier of a user.
     * id is the id of the supplier
     * The DTO contains the user's id, new name, phone number and address of the supplier
     *
     * @param newSupplierDTO, id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, NewSupplierDTO newSupplierDTO) {
        // Retrieve supplier's new information
        Supplier supplier = new Supplier();
        supplier.setUserId(newSupplierDTO.getUserId());
        supplier.setSupplierName(newSupplierDTO.getName());
        supplier.setSupplierPhone(newSupplierDTO.getPhone());
        supplier.setSupplierAddress(newSupplierDTO.getAddress());
        // Save
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Supplier updated!", HttpStatus.OK);
    }

    /**
     * API to delete a supplier of a user.
     * id is the id of the supplier
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        // Delete
        supplierRepository.deleteById(id);
        return new ResponseEntity<>("Supplier deleted!", HttpStatus.OK);
    }
}
