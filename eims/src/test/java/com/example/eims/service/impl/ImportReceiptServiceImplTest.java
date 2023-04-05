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

import com.example.eims.dto.importReceipt.ImportReceiptStatisticDTO;
import com.example.eims.entity.ImportReceipt;
import com.example.eims.repository.ImportReceiptRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImportReceiptServiceImplTest {
    @Mock
    ImportReceiptRepository importReceiptRepository;
    @Mock
    EntityManager em;
    @Mock
    Page<ImportReceipt> importsPage;
    @InjectMocks
    ImportReceiptServiceImpl importReceiptService;

    @Test
    void viewImportsByUser() {
        // Set up
        ImportReceipt importReceipt1 = new ImportReceipt();
        ImportReceipt importReceipt2 = new ImportReceipt();
        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt1);
        importReceiptList.add(importReceipt2);

        // Define behaviour of repository
        when(importReceiptRepository.findByUserId(1L)).thenReturn(Optional.of(importReceiptList));
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.getImport(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(importReceiptList, responseEntity.getBody());
    }

    @Test
    void viewImportsByUserPaging() {
        // Set up
        ImportReceipt importReceipt1 = new ImportReceipt();
        ImportReceipt importReceipt2 = new ImportReceipt();
        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt1);
        importReceiptList.add(importReceipt2);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("importId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(importReceiptRepository.findAllByUserId(1L, pageable)).thenReturn(importsPage);
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.viewImportsByOwnerPaging(1L, page, size, sort);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(importsPage, responseEntity.getBody());
    }

    @Test
    void viewImportsBySupplier() {
        // Set up
        ImportReceipt importReceipt1 = new ImportReceipt();
        ImportReceipt importReceipt2 = new ImportReceipt();
        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt1);
        importReceiptList.add(importReceipt2);

        // Define behaviour of repository
        when(importReceiptRepository.findBySupplierId(1L)).thenReturn(Optional.of(importReceiptList));
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.viewImportsBySupplier(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(importReceiptList, responseEntity.getBody());
    }

    @Test
    void viewImportsBySupplierPaging() {
        // Set up
        ImportReceipt importReceipt1 = new ImportReceipt();
        ImportReceipt importReceipt2 = new ImportReceipt();
        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt1);
        importReceiptList.add(importReceipt2);
        int page = 1;
        int size = 2;
        String sort = "ASC";
        Sort sortable = Sort.by("importId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        // Define behaviour of repository
        when(importReceiptRepository.findAllBySupplierId(1L, pageable)).thenReturn(importsPage);
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.viewImportsBySupplierPaging(1L, page, size, sort);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(importsPage, responseEntity.getBody());
    }

    @Test
    void viewImportStatistic() {
        // Set up
        ImportReceiptStatisticDTO dto1 = new ImportReceiptStatisticDTO();
        ImportReceiptStatisticDTO dto2 = new ImportReceiptStatisticDTO();
        List<ImportReceiptStatisticDTO> listDto = new ArrayList<>();
        listDto.add(dto1);
        listDto.add(dto2);
        Query q = mock(Query.class);
        // Define behaviour of repository
        when(em.createNamedQuery("getImportReceiptStatisticByUserId")).thenReturn(q);
        when(q.getResultList()).thenReturn(listDto);
        // Run service method
        ResponseEntity<?> responseEntity = importReceiptService.viewImportStatistic(1L);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(listDto,responseEntity.getBody());
    }
}