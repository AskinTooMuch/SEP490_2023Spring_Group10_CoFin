 /*
  * Copyright (C) 2023, FPT University <br>
  * SEP490 - SEP490_G10 <br>
  * EIMS <br>
  * Eggs Incubating Management System <br>
  *
  * Record of change:<br>
  * DATE         Version     Author      DESCRIPTION<br>
  * 08/04/2023   1.0         DuongNH     First Deploy<br>
  * 08/04/2023   1.1         DuongNH     Add all test case<br>
  */
 package com.example.eims.service.impl;

import com.example.eims.dto.importReceipt.ImportReceiptStatisticDTO;
import com.example.eims.dto.report.ReportItem;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

 @ExtendWith(MockitoExtension.class)
class ImportReportServiceImplTest {

    @Mock
    private EntityManager em;
    @Mock
    SupplierRepository supplierRepository;

    @Mock
    ImportReceiptRepository importReceiptRepository;

    @InjectMocks
    ImportReportServiceImpl importReportService;
    @Test
    @DisplayName("getAllImportReportUTCID01")
    void getAllImportReportUTCID01() {
        //Set up
        Long ownerId = 1L;
        Query q = mock(Query.class);

        ImportReceiptStatisticDTO reportDTO1 = new ImportReceiptStatisticDTO();
        ImportReceiptStatisticDTO reportDTO2 = new ImportReceiptStatisticDTO();
        List<ImportReceiptStatisticDTO> list = new ArrayList<>();
        list.add(reportDTO1);
        list.add(reportDTO2);

        // Define behaviour of repository
        when(em.createNamedQuery("getImportReceiptStatisticByUserId")).thenReturn(q);
        when(q.getResultList()).thenReturn(list);
        // Run service method
        ResponseEntity<?> responseEntity = importReportService.getAllImportReport(ownerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(list, responseEntity.getBody());
    }

     @Test
     @DisplayName("getAllImportReportUTCID02")
     void getAllImportReportUTCID02() {
         //Set up
         Long ownerId = 5L;
         Query q = mock(Query.class);

         List<ImportReceiptStatisticDTO> list = new ArrayList<>();

         // Define behaviour of repository
         when(em.createNamedQuery("getImportReceiptStatisticByUserId")).thenReturn(q);
         when(q.getResultList()).thenReturn(list);
         // Run service method
         ResponseEntity<?> responseEntity = importReportService.getAllImportReport(ownerId);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals(null, responseEntity.getBody());
     }

    @Test
    @DisplayName("getAllImportReportByMonthUTCID01")
    void getAllImportReportByMonthUTCID01() {
        //Set up
        Long supplierId = 1L;
        String year = "2023";
        Query q = mock(Query.class);

        ReportItem dto = new ReportItem();
        dto.setTotal(new BigDecimal(100));
        dto.setPaid(new BigDecimal(100));

        List<ReportItem> list = new ArrayList<>();
        list.add(dto);

        // Define behaviour of repository
        when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(true);
        when(em.createNamedQuery("getImportReportItemByMonth")).thenReturn(q);
        when(q.getResultList()).thenReturn(list);
        // Run service method
        ResponseEntity<?> responseEntity = importReportService.getAllImportReportByMonth(supplierId,year);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

     @Test
     @DisplayName("getAllImportReportByMonthUTCID02")
     void getAllImportReportByMonthUTCID02() {
         //Set up
         Long supplierId = -1L;
         String year = "2023";
         // Define behaviour of repository
         when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(false);
         // Run service method
         ResponseEntity<?> responseEntity = importReportService.getAllImportReportByMonth(supplierId,year);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Nhà cung cấp không tồn tại", responseEntity.getBody());
     }

     @Test
     @DisplayName("getAllImportReportByMonthUTCID03")
     void getAllImportReportByMonthUTCID03() {
         //Set up
         Long supplierId = 1L;
         String year = "";
         // Define behaviour of repository
         when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = importReportService.getAllImportReportByMonth(supplierId,year);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Nhập năm muốn xem", responseEntity.getBody());
     }

     @Test
     @DisplayName("getAllImportReportByMonthUTCID04")
     void getAllImportReportByMonthUTCID04() {
         //Set up
         Long supplierId = 1L;
         String year = null;
         // Define behaviour of repository
         when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(true);
         // Run service method
         ResponseEntity<?> responseEntity = importReportService.getAllImportReportByMonth(supplierId,year);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Nhập năm muốn xem", responseEntity.getBody());
     }


    @Test
    @DisplayName("getAllImportReportByYearUTCID01")
    void getAllImportReportByYearUTCID01() {
        //Set up
        Long supplierId = 1L;
        Long ownerId = 1L;

        Query q = mock(Query.class);

        List<String> yearList = new ArrayList<>();
        yearList.add("2023");

        ReportItem dto = new ReportItem();
        dto.setReportName("2023");
        dto.setTotal(new BigDecimal(100));
        dto.setPaid(new BigDecimal(100));

        List<ReportItem> list = new ArrayList<>();
        list.add(dto);

        // Define behaviour of repository
        when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(true);
        when(importReceiptRepository.getImportReceiptYear(ownerId, supplierId)).thenReturn(Optional.of(yearList));
        when(em.createNamedQuery("getImportReportItemByYear")).thenReturn(q);
        when(q.getResultList()).thenReturn(list);
        // Run service method
        ResponseEntity<?> responseEntity = importReportService.getAllImportReportByYear(ownerId,supplierId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(list, responseEntity.getBody());
    }

     @Test
     @DisplayName("getAllImportReportByYearUTCID02")
     void getAllImportReportByYearUTCID02() {
         //Set up
         Long supplierId = 5L;
         Long ownerId = 1L;

         // Define behaviour of repository
         when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(true);
         when(importReceiptRepository.getImportReceiptYear(ownerId, supplierId)).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = importReportService.getAllImportReportByYear(ownerId,supplierId);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Chưa có giao dịch với nhà cung cấp", responseEntity.getBody());
     }

     @Test
     @DisplayName("getAllImportReportByYearUTCID03")
     void getAllImportReportByYearUTCID03() {
         //Set up
         Long supplierId = -1L;
         Long ownerId = 1L;

         // Define behaviour of repository
         when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(false);
         // Run service method
         ResponseEntity<?> responseEntity = importReportService.getAllImportReportByYear(ownerId,supplierId);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Nhà cung cấp không tồn tại", responseEntity.getBody());
     }

    @Test
    @DisplayName("getImportReceiptYearUTCID01")
    void getImportReceiptYearUTCID01() {
        //Set up
        Long supplierId = 1L;
        Long ownerId = 1L;

        List<String> yearList = new ArrayList<>();
        yearList.add("2023");

        // Define behaviour of repository
        when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(true);
        when(importReceiptRepository.getImportReceiptYear(ownerId, supplierId)).thenReturn(Optional.of(yearList));
        // Run service method
        ResponseEntity<?> responseEntity = importReportService.getImportReceiptYear(ownerId,supplierId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(yearList, responseEntity.getBody());
    }

     @Test
     @DisplayName("getImportReceiptYearUTCID02")
     void getImportReceiptYearUTCID02() {
         //Set up
         Long supplierId = -1L;
         Long ownerId = 1L;

         // Define behaviour of repository
         when(supplierRepository.existsBySupplierId(supplierId)).thenReturn(true);
         when(importReceiptRepository.getImportReceiptYear(ownerId, supplierId)).thenReturn(Optional.empty());
         // Run service method
         ResponseEntity<?> responseEntity = importReportService.getImportReceiptYear(ownerId,supplierId);
         System.out.println(responseEntity.toString());
         // Assert
         assertEquals("Chưa có giao dịch với nhà cung cấp", responseEntity.getBody());
     }
}