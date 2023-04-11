/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 05/04/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.report.IncomeReportDTO;
import com.example.eims.dto.report.IncomeReportItemDTO;
import com.example.eims.dto.report.ReportItem;
import com.example.eims.entity.*;
import com.example.eims.repository.*;
import com.example.eims.service.interfaces.IIncomeReportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class IncomeReportServiceImpl implements IIncomeReportService {
    @Autowired
    private final FacilityRepository facilityRepository;
    @Autowired
    private final CostRepository costRepository;
    @Autowired
    private final PayrollRepository payrollRepository;
    @Autowired
    private final ImportReceiptRepository importReceiptRepository;
    @Autowired
    private final ExportReceiptRepository exportReceiptRepository;

    public IncomeReportServiceImpl(FacilityRepository facilityRepository, CostRepository costRepository,
                                   PayrollRepository payrollRepository, ImportReceiptRepository importReceiptRepository,
                                   ExportReceiptRepository exportReceiptRepository) {
        this.facilityRepository = facilityRepository;
        this.costRepository = costRepository;
        this.payrollRepository = payrollRepository;
        this.importReceiptRepository = importReceiptRepository;
        this.exportReceiptRepository = exportReceiptRepository;
    }

    /**
     * Get income report of owner for every month of a year
     *
     * @param userId owner's id
     * @param year   the year user want to get income report
     * @return
     */
    @Override
    public ResponseEntity<?> getIncomeReportInMonth(Long userId, String year) {
        Facility facility = facilityRepository.findByUserId(userId).get();
        Long facilityId = facility.getFacilityId();
        IncomeReportDTO dto = new IncomeReportDTO();
        List<IncomeReportItemDTO> costListDto = new ArrayList<>();
        List<IncomeReportItemDTO> payrollListDto = new ArrayList<>();
        List<IncomeReportItemDTO> importListDto = new ArrayList<>();
        List<IncomeReportItemDTO> exportListDto = new ArrayList<>();
        BigDecimal incomeNow = new BigDecimal(0);
        BigDecimal incomeLast = new BigDecimal(0);
        // cost
        for (int i = 0; i < 12; i++) {
            Optional<List<Cost>> costListOptional = costRepository.getAllByYearAndMonth(facilityId, year, i + 1 + "");
            if (costListOptional.isEmpty()) {
                costListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<Cost> costList = costListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (Cost cost : costList) {
                    total = total.add(cost.getCostAmount());
                    paid = paid.add(cost.getPaidAmount());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total, paid);
                incomeNow = incomeNow.subtract(paid);
                incomeLast = incomeLast.subtract(total);
                costListDto.add(item);
            }
        }

        // payroll
        for (int i = 0; i < 12; i++) {
            Optional<List<Payroll>> payrollListOptional = payrollRepository
                    .getAllByYearAndMonth(userId, year, i + 1 + "");
            if (payrollListOptional.isEmpty()) {
                payrollListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<Payroll> payrollList = payrollListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (Payroll payroll : payrollList) {
                    total.add(payroll.getPayrollAmount());
                    paid = paid.add(payroll.getPayrollAmount());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total = total, paid);
                incomeNow = incomeNow.subtract(paid);
                incomeLast = incomeLast.subtract(total);
                payrollListDto.add(item);
            }
        }

        // import
        for (int i = 0; i < 12; i++) {
            Optional<List<ImportReceipt>> importReceiptListOptional = importReceiptRepository
                    .getAllByYearAndMonth(facilityId, year, i + 1 + "");
            if (importReceiptListOptional.isEmpty()) {
                importListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<ImportReceipt> importList = importReceiptListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (ImportReceipt importReceipt : importList) {
                    total = total.add(importReceipt.getTotal());
                    paid = paid.add(importReceipt.getPaid());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total, paid);
                incomeNow = incomeNow.subtract(paid);
                incomeLast = incomeLast.subtract(total);
                importListDto.add(item);
            }
        }

        // export
        for (int i = 0; i < 12; i++) {
            Optional<List<ExportReceipt>> exportReceiptListOptional = exportReceiptRepository
                    .getAllByYearAndMonth(facilityId, year, i + 1 + "");
            if (exportReceiptListOptional.isEmpty()) {
                exportListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<ExportReceipt> exportList = exportReceiptListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (ExportReceipt exportReceipt : exportList) {
                    total = total.add(exportReceipt.getTotal());
                    paid = paid.add(exportReceipt.getPaid());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total, paid);
                incomeNow = incomeNow.add(paid);
                incomeLast = incomeLast.add(total);
                exportListDto.add(item);
            }
        }

        dto.setCostList(costListDto);
        dto.setPayrollList(payrollListDto);
        dto.setImportList(importListDto);
        dto.setExportList(exportListDto);
        dto.setIncomeNow(incomeNow);
        dto.setIncomeLast(incomeLast);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Get income report of owner for every year
     *
     * @param userId owner's id
     * @return
     */
    @Override
    public ResponseEntity<?> getIncomeReportInYear(Long userId) {
        Facility facility = facilityRepository.findByUserId(userId).get();
        Long facilityId = facility.getFacilityId();
        List<String> listYear = new ArrayList<>();
        IncomeReportDTO dto = new IncomeReportDTO();
        List<IncomeReportItemDTO> costListDto = new ArrayList<>();
        List<IncomeReportItemDTO> payrollListDto = new ArrayList<>();
        List<IncomeReportItemDTO> importListDto = new ArrayList<>();
        List<IncomeReportItemDTO> exportListDto = new ArrayList<>();
        BigDecimal incomeNow = new BigDecimal(0);
        BigDecimal incomeLast = new BigDecimal(0);

        // cost year
        List<String> listYearCost = costRepository.getAllYear(facility.getFacilityId()).get();
        listYear.addAll(listYearCost);
        // payroll year
        List<String> listYearPayroll = payrollRepository.getAllYear(userId).get();
        listYear.addAll(listYearPayroll);
        // import year
        List<String> listYearImport = importReceiptRepository.getAllYear(facility.getFacilityId()).get();
        listYear.addAll(listYearImport);
        // export year
        List<String> listYearExport = exportReceiptRepository.getAllYear(facility.getFacilityId()).get();
        listYear.addAll(listYearExport);
        // remove duplicate year
        Set inputSet = new HashSet(listYear);
        listYear = new ArrayList<>(inputSet);

        // cost
        for (String year : listYear) {
            Optional<List<Cost>> costListOptional = costRepository.getAllByYear(facilityId, year);
            if (costListOptional.isEmpty()) {
                costListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<Cost> costList = costListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (Cost cost : costList) {
                    total = total.add(cost.getCostAmount());
                    paid = paid.add(cost.getPaidAmount());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total, paid);
                incomeNow = incomeNow.subtract(paid);
                incomeLast = incomeLast.subtract(total);
                costListDto.add(item);
            }
        }

        // payroll
        for (String year : listYear) {
            Optional<List<Payroll>> payrollListOptional = payrollRepository
                    .getAllByYear(userId, year);
            if (payrollListOptional.isEmpty()) {
                payrollListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<Payroll> payrollList = payrollListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (Payroll payroll : payrollList) {
                    total = total.add(payroll.getPayrollAmount());
                    paid = paid.add(payroll.getPayrollAmount());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total, paid);
                incomeNow = incomeNow.subtract(paid);
                incomeLast = incomeLast.subtract(total);
                payrollListDto.add(item);
            }
        }

        // import
        for (String year : listYear) {
            Optional<List<ImportReceipt>> importReceiptListOptional = importReceiptRepository
                    .getAllByYear(facilityId, year);
            if (importReceiptListOptional.isEmpty()) {
                importListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<ImportReceipt> importList = importReceiptListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (ImportReceipt importReceipt : importList) {
                    total = total.add(importReceipt.getTotal());
                    paid = paid.add(importReceipt.getPaid());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total, paid);
                incomeNow = incomeNow.subtract(paid);
                incomeLast = incomeLast.subtract(total);
                importListDto.add(item);
            }
        }


        // export
        for (String year : listYear) {
            Optional<List<ExportReceipt>> exportReceiptListOptional = exportReceiptRepository
                    .getAllByYear(facilityId, year);
            if (exportReceiptListOptional.isEmpty()) {
                exportListDto.add(new IncomeReportItemDTO(new BigDecimal(0), new BigDecimal(0)));
            } else {
                List<ExportReceipt> exportList = exportReceiptListOptional.get();
                BigDecimal total = new BigDecimal(0);
                BigDecimal paid = new BigDecimal(0);
                for (ExportReceipt exportReceipt : exportList) {
                    total = total.add(exportReceipt.getTotal());
                    paid = paid.add(exportReceipt.getPaid());
                }
                IncomeReportItemDTO item = new IncomeReportItemDTO(total, paid);
                incomeNow = incomeNow.add(paid);
                incomeLast = incomeLast.add(total);
                exportListDto.add(item);
            }
        }

        dto.setYearList(listYear);
        dto.setCostList(costListDto);
        dto.setPayrollList(payrollListDto);
        dto.setImportList(importListDto);
        dto.setExportList(exportListDto);
        dto.setIncomeNow(incomeNow);
        dto.setIncomeLast(incomeLast);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
