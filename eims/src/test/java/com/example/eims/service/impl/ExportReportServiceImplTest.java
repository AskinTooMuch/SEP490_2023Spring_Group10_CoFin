package com.example.eims.service.impl;

import com.example.eims.dto.importReceipt.ImportReceiptStatisticDTO;
import com.example.eims.dto.report.ReportItem;
import com.example.eims.repository.CustomerRepository;
import com.example.eims.repository.ExportReceiptRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportReportServiceImplTest {

    @Mock
    private EntityManager em;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    ExportReceiptRepository exportReceiptRepository;
    @InjectMocks
    ExportReportServiceImpl exportReportService;

    @Test
    @DisplayName("getAllExportReportUTCID01")
    void getAllExportReportUTCID01() {
        //Set up
        Long ownerId = 1L;
        Query q = mock(Query.class);

        ImportReceiptStatisticDTO reportDTO1 = new ImportReceiptStatisticDTO();
        ImportReceiptStatisticDTO reportDTO2 = new ImportReceiptStatisticDTO();
        List<ImportReceiptStatisticDTO> list = new ArrayList<>();
        list.add(reportDTO1);
        list.add(reportDTO2);

        // Define behaviour of repository
        when(em.createNamedQuery("getExportReceiptStatisticByUserId")).thenReturn(q);
        when(q.getResultList()).thenReturn(list);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReport(ownerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(list, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportUTCID02")
    void getAllExportReportUTCID02() {
        //Set up
        Long ownerId = 5L;
        Query q = mock(Query.class);

        List<ImportReceiptStatisticDTO> list = new ArrayList<>();

        // Define behaviour of repository
        when(em.createNamedQuery("getExportReceiptStatisticByUserId")).thenReturn(q);
        when(q.getResultList()).thenReturn(list);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReport(ownerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportByMonthUTCID01")
    void getAllExportReportByMonthUTCID01() {
        //Set up
        Long customerId = 1L;
        String year = "2023";
        Query q = mock(Query.class);

        ReportItem dto = new ReportItem();
        dto.setTotal(100F);
        dto.setPaid(100F);

        List<ReportItem> list = new ArrayList<>();
        list.add(dto);

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(em.createNamedQuery("getExportReportItemByMonth")).thenReturn(q);
        when(q.getResultList()).thenReturn(list);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReportByMonth(customerId,year);
        System.out.println(responseEntity.toString());
        // Assert
        assertNotEquals(null, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportByMonthUTCID02")
    void getAllExportReportByMonthUTCID02() {
        //Set up
        Long customerId = -1L;
        String year = "2023";

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReportByMonth(customerId,year);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khách hàng không tồn tại", responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportByMonthUTCID03")
    void getAllExportReportByMonthUTCID03() {
        //Set up
        Long customerId = 1L;
        String year = "";

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReportByMonth(customerId,year);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhập năm muốn xem", responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportByMonthUTCID04")
    void getAllExportReportByMonthUTCID04() {
        //Set up
        Long customerId = 1L;
        String year = null;

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(true);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReportByMonth(customerId,year);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Nhập năm muốn xem", responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportByYearUTCID01")
    void getAllExportReportByYearUTCID01() {
        //Set up
        Long customerId = 1L;
        Long ownerId = 1L;

        Query q = mock(Query.class);

        List<String> yearList = new ArrayList<>();
        yearList.add("2023");

        ReportItem dto = new ReportItem();
        dto.setReportName("2023");
        dto.setTotal(100F);
        dto.setPaid(100F);

        List<ReportItem> list = new ArrayList<>();
        list.add(dto);

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(exportReceiptRepository.getImportReceiptYear(ownerId, customerId)).thenReturn(Optional.of(yearList));
        when(em.createNamedQuery("getExportReportItemByYear")).thenReturn(q);
        when(q.getResultList()).thenReturn(list);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReportByYear(ownerId,customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(list, responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportByYearUTCID02")
    void getAllExportReportByYearUTCID02() {
        //Set up
        Long customerId = 5L;
        Long ownerId = 1L;

        Query q = mock(Query.class);

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(exportReceiptRepository.getImportReceiptYear(ownerId, customerId)).thenReturn(Optional.empty());
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReportByYear(ownerId,customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Chưa có giao dịch với khách hàng", responseEntity.getBody());
    }

    @Test
    @DisplayName("getAllExportReportByYearUTCID03")
    void getAllExportReportByYearUTCID03() {
        //Set up
        Long customerId = 1L;
        Long ownerId = -1L;

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getAllExportReportByYear(ownerId,customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khách hàng không tồn tại", responseEntity.getBody());
    }

    @Test
    @DisplayName("getExportReceiptYearUTCID01")
    void getExportReceiptYearUTCID01() {
        //Set up
        Long customerId = 1L;
        Long ownerId = 1L;

        List<String> yearList = new ArrayList<>();
        yearList.add("2023");

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(exportReceiptRepository.getImportReceiptYear(ownerId, customerId)).thenReturn(Optional.of(yearList));
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getExportReceiptYear(ownerId,customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals(yearList, responseEntity.getBody());
    }

    @Test
    @DisplayName("getExportReceiptYearUTCID02")
    void getExportReceiptYearUTCID02() {
        //Set up
        Long customerId = -1L;
        Long ownerId = 1L;

        // Define behaviour of repository
        when(customerRepository.existsById(customerId)).thenReturn(false);
        // Run service method
        ResponseEntity<?> responseEntity = exportReportService.getExportReceiptYear(ownerId,customerId);
        System.out.println(responseEntity.toString());
        // Assert
        assertEquals("Khách hàng không tồn tại", responseEntity.getBody());
    }
}