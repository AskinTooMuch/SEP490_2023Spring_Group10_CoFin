/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 10/04/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.report.IncomeReportDTO;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncomeReportServiceImplTest {
    @Mock
    FacilityRepository facilityRepository;
    @Mock
    CostRepository costRepository;
    @Mock
    PayrollRepository payrollRepository;
    @Mock
    ImportReceiptRepository importReceiptRepository;
    @Mock
    ExportReceiptRepository exportReceiptRepository;
    @InjectMocks
    IncomeReportServiceImpl incomeReportService;
    @Test
    @DisplayName("getIncomeReportInMonthUTCID01")
    void getIncomeReportInMonthUTCID01() {
        // Set up
        Long userId = 1L;
        String year = "2023";

        Facility facility = new Facility();
        facility.setFacilityId(1L);

        Cost cost = new Cost();
        cost.setCostAmount(1000F);
        cost.setPaidAmount(1000F);
        List<Cost> costList = new ArrayList<>();
        costList.add(cost);

        Payroll payroll = new Payroll();
        payroll.setPayrollAmount(1000F);
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll);

        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setTotal(1000F);
        importReceipt.setPaid(1000F);
        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt);

        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setTotal(1000F);
        exportReceipt.setPaid(1000F);
        List<ExportReceipt> exportReceiptList = new ArrayList<>();
        exportReceiptList.add(exportReceipt);

        // Define behaviour of repository
        when(facilityRepository.findByUserId(userId)).thenReturn(Optional.of(facility));
        when(costRepository.getAllByYearAndMonth(facility.getFacilityId(), year, "1")).thenReturn(Optional.of(costList));
        when(payrollRepository.getAllByYearAndMonth(userId, year,  "1")).thenReturn(Optional.of(payrollList));
        when(importReceiptRepository.getAllByYearAndMonth(facility.getFacilityId(), year, "1")).thenReturn(Optional.of(importReceiptList));
        when(exportReceiptRepository.getAllByYearAndMonth(facility.getFacilityId(), year,  "1")).thenReturn(Optional.of(exportReceiptList));

        // Run service method
        ResponseEntity<?> responseEntity = incomeReportService.getIncomeReportInMonth(userId, year);
        IncomeReportDTO result = (IncomeReportDTO) responseEntity.getBody();
        // Assert
        assertEquals(-2000, result.getIncomeNow());
    }
    @Test
    @DisplayName("getIncomeReportInMonthUTCID02")
    void getIncomeReportInMonthUTCID02() {
        // Set up
        Long userId = 1L;
        String year = "-1";

        Facility facility = new Facility();
        facility.setFacilityId(1L);

        List<Cost> costList = new ArrayList<>();
        List<Payroll> payrollList = new ArrayList<>();
        List<ImportReceipt> importReceiptList = new ArrayList<>();
        List<ExportReceipt> exportReceiptList = new ArrayList<>();

        // Define behaviour of repository
        when(facilityRepository.findByUserId(userId)).thenReturn(Optional.of(facility));
        when(costRepository.getAllByYearAndMonth(facility.getFacilityId(), year, "1")).thenReturn(Optional.of(costList));
        when(payrollRepository.getAllByYearAndMonth(userId, year,  "1")).thenReturn(Optional.of(payrollList));
        when(importReceiptRepository.getAllByYearAndMonth(facility.getFacilityId(), year, "1")).thenReturn(Optional.of(importReceiptList));
        when(exportReceiptRepository.getAllByYearAndMonth(facility.getFacilityId(), year,  "1")).thenReturn(Optional.of(exportReceiptList));

        // Run service method
        ResponseEntity<?> responseEntity = incomeReportService.getIncomeReportInMonth(userId, year);
        IncomeReportDTO result = (IncomeReportDTO) responseEntity.getBody();
        // Assert
        assertEquals(0, result.getIncomeNow());
    }
    @Test
    @DisplayName("getIncomeReportInYearUTCID01")
    void getIncomeReportInYearUTCID01() {
        // Set up
        Long userId = 1L;
        List<String> yearList = List.of("2023");

        Facility facility = new Facility();
        facility.setFacilityId(1L);

        Cost cost = new Cost();
        cost.setCostAmount(1000F);
        cost.setPaidAmount(1000F);
        List<Cost> costList = new ArrayList<>();
        costList.add(cost);

        Payroll payroll = new Payroll();
        payroll.setPayrollAmount(1000F);
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll);

        ImportReceipt importReceipt = new ImportReceipt();
        importReceipt.setTotal(1000F);
        importReceipt.setPaid(1000F);
        List<ImportReceipt> importReceiptList = new ArrayList<>();
        importReceiptList.add(importReceipt);

        ExportReceipt exportReceipt = new ExportReceipt();
        exportReceipt.setTotal(1000F);
        exportReceipt.setPaid(1000F);
        List<ExportReceipt> exportReceiptList = new ArrayList<>();
        exportReceiptList.add(exportReceipt);

        // Define behaviour of repository
        when(facilityRepository.findByUserId(userId)).thenReturn(Optional.of(facility));
        when(costRepository.getAllYear(facility.getFacilityId())).thenReturn(Optional.of(yearList));
        when(payrollRepository.getAllYear(userId)).thenReturn(Optional.of(yearList));
        when(importReceiptRepository.getAllYear(facility.getFacilityId())).thenReturn(Optional.of(yearList));
        when(exportReceiptRepository.getAllYear(facility.getFacilityId())).thenReturn(Optional.of(yearList));

        when(costRepository.getAllByYear(facility.getFacilityId(), "2023")).thenReturn(Optional.of(costList));
        when(payrollRepository.getAllByYear(userId, "2023")).thenReturn(Optional.of(payrollList));
        when(importReceiptRepository.getAllByYear(facility.getFacilityId(), "2023")).thenReturn(Optional.of(importReceiptList));
        when(exportReceiptRepository.getAllByYear(facility.getFacilityId(), "2023")).thenReturn(Optional.of(exportReceiptList));

        // Run service method
        ResponseEntity<?> responseEntity = incomeReportService.getIncomeReportInYear(userId);
        IncomeReportDTO result = (IncomeReportDTO) responseEntity.getBody();
        // Assert
        assertEquals(-2000, result.getIncomeNow());
    }

    @Test
    @DisplayName("getIncomeReportInYearUTCID02")
    void getIncomeReportInYearUTCID02() {
        // Set up
        Long userId = 1L;
        List<String> yearList = new ArrayList<>();

        Facility facility = new Facility();
        facility.setFacilityId(1L);

        // Define behaviour of repository
        when(facilityRepository.findByUserId(userId)).thenReturn(Optional.of(facility));
        when(facilityRepository.findByUserId(userId)).thenReturn(Optional.of(facility));
        when(costRepository.getAllYear(facility.getFacilityId())).thenReturn(Optional.of(yearList));
        when(payrollRepository.getAllYear(userId)).thenReturn(Optional.of(yearList));
        when(importReceiptRepository.getAllYear(facility.getFacilityId())).thenReturn(Optional.of(yearList));
        when(exportReceiptRepository.getAllYear(facility.getFacilityId())).thenReturn(Optional.of(yearList));

        // Run service method
        ResponseEntity<?> responseEntity = incomeReportService.getIncomeReportInYear(userId);
        IncomeReportDTO result = (IncomeReportDTO) responseEntity.getBody();
        // Assert
        assertEquals(0, result.getIncomeNow());
    }
}