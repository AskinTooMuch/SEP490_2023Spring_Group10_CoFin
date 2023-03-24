/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.supplier.CreateSupplierDTO;
import com.example.eims.dto.supplier.SupplierDetailDTO;
import com.example.eims.dto.supplier.UpdateSupplierDTO;
import com.example.eims.entity.Customer;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.entity.Supplier;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    SupplierRepository supplierRepository;
    @Mock
    ImportReceiptRepository importReceiptRepository;
    @Mock
    Page supplierPage;
    @InjectMocks
    SupplierServiceImpl supplierService;
    @Test
    void getAllSupplier() {
        // Set up
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);
        // Define behaviour of repository
        when(supplierRepository.findByFacilityId(1L)).thenReturn(Optional.of(supplierList));

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getAllSupplier(1L);
        System.out.println(responseEntity.toString());
        List<Supplier> resultList = (List<Supplier>) responseEntity.getBody();
        // Assert
        assertEquals(2, resultList.size());
    }

    @Test
    void getSupplier() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        SupplierDetailDTO dto = new SupplierDetailDTO();
        dto.getFromEntity(supplier);
        dto.setFertilizedRate(9.0F);
        dto.setMaleRate(6.0F);
        // Define behaviour of repository
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getSupplier(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    void createSupplier() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        CreateSupplierDTO dto = new CreateSupplierDTO();
        dto.setFacilityId(1L);
        dto.setSupplierName("name");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("address");
        dto.setSupplierMail("a@a.com");
        dto.setFacilityName("F name");

        // Define behaviour of repository
        when(userRepository.getStatusByUserId(1L)).thenReturn(true);
        when(supplierRepository.existsBySupplierPhoneAndFacilityId("0987654321", dto.getFacilityId()))
                .thenReturn(false);

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.createSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Thêm nhà cung cấp thành công", responseEntity.getBody());
    }

    @Test
    void showFormUpdate() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);

        // Define behaviour of repository
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.showFormUpdate(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(supplier, responseEntity.getBody());
    }

    @Test
    void updateSupplier() {
        // Set up
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        UpdateSupplierDTO dto = new UpdateSupplierDTO();
        dto.setFacilityId(1L);
        dto.setSupplierId(1L);
        dto.setSupplierName("name");
        dto.setSupplierPhone("0987654321");
        dto.setSupplierAddress("address");
        dto.setSupplierMail("a@a.com");
        dto.setFacilityName("F name");
        dto.setStatus(1);
        String oldPhone = "0987654320";
        // Define behaviour of repository
        when(supplierRepository.findSupplierPhoneById(dto.getSupplierId())).thenReturn(oldPhone);
        when(supplierRepository.existsBySupplierPhoneAndFacilityId(dto.getSupplierPhone(), dto.getFacilityId()))
                .thenReturn(false);
        when(supplierRepository.findBySupplierId(1L)).thenReturn(Optional.of(supplier));
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.updateSupplier(dto);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Cập nhật thông tin nhà cung cấp thành công", responseEntity.getBody());
    }

    @Test
    void searchSupplier() {
        // Set up
        String key1 = "uye";
        String key2 = "0987";
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);
        // Define behaviour of repository
        when(supplierRepository.searchByUsernameOrPhone(1L, key1)).thenReturn(Optional.of(supplierList));
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.searchSupplier(1L, key1);
        List<Customer> resultList = (List<Customer>) responseEntity.getBody();
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(supplierList, responseEntity.getBody());
    }

    @Test
    void viewImports() {
        // Set up
        ImportReceipt importReceipt1 = new ImportReceipt();
        ImportReceipt importReceipt2 = new ImportReceipt();
        List<ImportReceipt> importList = new ArrayList<>();
        importList.add(importReceipt1);
        importList.add(importReceipt2);
        // Define behaviour of repository
        when(importReceiptRepository.findBySupplierId(1L)).thenReturn(Optional.of(importList));

        // Run service method
        ResponseEntity<?> responseEntity = supplierService.viewImports(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(importList, responseEntity.getBody());
    }

    @Test
    void getAllSupplierPaging() {
        // Set up
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("supplierId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(supplierRepository.findAllByFacilityId(1L, pageable)).thenReturn(supplierPage);
        // Run service method
        ResponseEntity<?> responseEntity = supplierService.getAllSupplierPaging(1L, 1, 2, "ASC");
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(supplierPage, responseEntity.getBody());
    }
}