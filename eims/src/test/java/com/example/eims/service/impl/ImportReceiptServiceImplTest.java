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



}