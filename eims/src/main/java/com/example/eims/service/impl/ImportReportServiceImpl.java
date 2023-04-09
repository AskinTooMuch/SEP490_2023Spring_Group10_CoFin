/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/04/2023   1.0         DuongNH      First Deploy<br>
 */
package com.example.eims.service.impl;

import com.example.eims.dto.importReceipt.ImportReceiptStatisticDTO;
import com.example.eims.dto.report.ReportItem;
import com.example.eims.repository.ImportReceiptRepository;
import com.example.eims.repository.SupplierRepository;
import com.example.eims.service.interfaces.IImportReportService;
import com.example.eims.utils.StringDealer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImportReportServiceImpl implements IImportReportService {
    private final StringDealer stringDealer = new StringDealer();
    @PersistenceContext
    private final EntityManager em;
    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ImportReceiptRepository importReceiptRepository;

    public ImportReportServiceImpl(EntityManager em,SupplierRepository supplierRepository,ImportReceiptRepository importReceiptRepository) {
        this.em = em;
        this.supplierRepository = supplierRepository;
        this.importReceiptRepository = importReceiptRepository;
    }

    /**
     * Return all import report of owner
     *
     * @param userId owner's id
     * @return List of import receipt static DTO
     */
    @Override
    public ResponseEntity<?> getAllImportReport(Long userId) {
        Query query = em.createNamedQuery("getImportReceiptStatisticByUserId");
        query.setParameter(1, userId);
        List<ImportReceiptStatisticDTO> list = (List<ImportReceiptStatisticDTO>) query.getResultList();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all import report by month
     *
     * @param supplierId supplier's id
     * @param year       year
     * @return List of ReportDetailDTO
     */
    @Override
    public ResponseEntity<?> getAllImportReportByMonth(Long supplierId, String year) {
        //check supplier exist
        if (!supplierRepository.existsBySupplierId(supplierId)) {
            return new ResponseEntity<>("Nhà cung cấp không tồn tại", HttpStatus.BAD_REQUEST);
        }
        year = stringDealer.trimMax(year);
        //validate year
        if (year == null || year.equals("")) {
            return new ResponseEntity<>("Nhập năm muốn xem", HttpStatus.BAD_REQUEST);
        }
        //array for 12 monthly record
        ReportItem reportItem[] = new ReportItem[12];
        int month = 1;
        for (int index = 0; index < reportItem.length; index++) {
            //get import report for each month
            Query query = em.createNamedQuery("getImportReportItemByMonth");
            query.setParameter(1,supplierId);
            query.setParameter(2,month);
            query.setParameter(3, year);
            List<ReportItem> reportItemList = (List<ReportItem>) query.getResultList();

            if(reportItemList.isEmpty()){
                //add blank report
                reportItem[index] = new ReportItem(month + "", 0F, 0F);
            }else{
                reportItem[index] = reportItemList.get(0);
            }
            month++;
        }
        return new ResponseEntity<>(reportItem, HttpStatus.OK);
    }

    /**
     * Get all import report by year
     *
     * @param supplierId supplier's id
     * @return List of ReportDetailDTO
     */
    @Override
    public ResponseEntity<?> getAllImportReportByYear(Long userId, Long supplierId) {
        // validate supplier
        if (!supplierRepository.existsBySupplierId(supplierId)) {
            return new ResponseEntity<>("Nhà cung cấp không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Optional<List<String>> list = importReceiptRepository.getImportReceiptYear(userId, supplierId);
        if (!list.isPresent()) {
            return new ResponseEntity<>("Chưa có giao dịch với nhà cung cấp", HttpStatus.BAD_REQUEST);
        }
        List<ReportItem> yearReportList;
        //get report for each year
        Query query ;
        query = em.createNamedQuery("getImportReportItemByYear");
        query.setParameter(1,supplierId );
        yearReportList = (List<ReportItem>) query.getResultList();
        System.out.println(yearReportList);
        return new ResponseEntity<>(yearReportList, HttpStatus.OK);
    }

    /**
     * Get all year that owner have bought product from supplier
     *
     * @param userId     owner's id
     * @param supplierId supplier's id
     * @return String ArrayList
     */
    @Override
    public ResponseEntity<?> getImportReceiptYear(Long userId, Long supplierId) {
        if (!supplierRepository.existsBySupplierId(supplierId)) {
            return new ResponseEntity<>("Nhà cung cấp không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Optional<List<String>> list = importReceiptRepository.getImportReceiptYear(userId, supplierId);
        if (list.isPresent()) {
            System.out.println("year list" + list.get().size());
            return new ResponseEntity<>(list.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Chưa có giao dịch với nhà cung cấp", HttpStatus.BAD_REQUEST);
    }
}
