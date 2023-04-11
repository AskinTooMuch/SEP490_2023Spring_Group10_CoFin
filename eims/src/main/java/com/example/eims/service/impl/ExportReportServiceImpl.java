/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/04/2023   1.0         DuongNH      First Deploy<br>
 * 03/04/2023   1.1         DuongNH      Add new function<br>
 */

package com.example.eims.service.impl;

import com.example.eims.dto.exportReceipt.ExportReceiptStatisticDTO;
import com.example.eims.dto.report.ReportItem;
import com.example.eims.repository.CustomerRepository;
import com.example.eims.repository.ExportReceiptRepository;
import com.example.eims.service.interfaces.IExportReportService;
import com.example.eims.utils.StringDealer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class ExportReportServiceImpl implements IExportReportService {
    private final StringDealer stringDealer = new StringDealer();
    @PersistenceContext
    private final EntityManager em;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ExportReceiptRepository exportReceiptRepository;

    public ExportReportServiceImpl(EntityManager em, CustomerRepository customerRepository, ExportReceiptRepository exportReceiptRepository) {
        this.em = em;
        this.customerRepository = customerRepository;
        this.exportReceiptRepository = exportReceiptRepository;
    }

    /**
     * Return all export report of owner
     *
     * @param userId owner's id
     * @return List of export receipt static DTO
     */
    public ResponseEntity<?> getAllExportReport(Long userId) {
        Query query = em.createNamedQuery("getExportReceiptStatisticByUserId");
        query.setParameter(1,userId);
        List<ExportReceiptStatisticDTO> list = (List<ExportReceiptStatisticDTO>) query.getResultList();
        if (!list.isEmpty()) {
            System.out.println(list.toString());
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all export report by month
     *
     * @param customerId customer's id
     * @param year       year
     * @return List of ReportDetailDTO
     */
    public ResponseEntity<?> getAllExportReportByMonth(Long customerId, String year) {
        //check customer exist
        if (!customerRepository.existsById(customerId)){
            return new ResponseEntity<>("Khách hàng không tồn tại", HttpStatus.BAD_REQUEST);
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
            Query query = em.createNamedQuery("getExportReportItemByMonth");
            query.setParameter(1,customerId);
            query.setParameter(2,month);
            query.setParameter(3, year);
            List<ReportItem> reportItemList = (List<ReportItem>) query.getResultList();

            if(reportItemList.isEmpty()){
                //add blank report
                reportItem[index] = new ReportItem(month + "", new BigDecimal(0), new BigDecimal(0));
            }else{
                reportItem[index] = reportItemList.get(0);
            }
            month++;
        }
        return new ResponseEntity<>(reportItem, HttpStatus.OK);
    }

    /**
     * Get all export report by year
     *
     * @param customerId customer's id
     * @return List of ReportDetailDTO
     */
    public ResponseEntity<?> getAllExportReportByYear(Long userId, Long customerId) {
        // validate customer
        if (!customerRepository.existsById(customerId)) {
            return new ResponseEntity<>("Khách hàng không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Optional<List<String>> list = exportReceiptRepository.getImportReceiptYear(userId, customerId);
        if (!list.isPresent()) {
            return new ResponseEntity<>("Chưa có giao dịch với khách hàng", HttpStatus.BAD_REQUEST);
        }
        List<ReportItem> yearReportList;
        //get report for each year
        Query query ;
        query = em.createNamedQuery("getExportReportItemByYear");
        query.setParameter(1,customerId );
        yearReportList = (List<ReportItem>) query.getResultList();
        System.out.println(yearReportList);
        return new ResponseEntity<>(yearReportList, HttpStatus.OK);
    }

    /**
     * Get all year that owner have bought product from customer
     *
     * @param userId     owner's id
     * @param customerId customer's id
     * @return String ArrayList
     */
    public ResponseEntity<?> getExportReceiptYear(Long userId, Long customerId) {
        // validate customer
        if (!customerRepository.existsById(customerId)) {
            return new ResponseEntity<>("Khách hàng không tồn tại", HttpStatus.BAD_REQUEST);
        }
        Optional<List<String>> listOpt = exportReceiptRepository.getImportReceiptYear(userId, customerId);

        if (listOpt.isPresent()) {
            System.out.println("year list" + listOpt.get().size());
            return new ResponseEntity<>(listOpt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Chưa có giao dịch với khách hàng", HttpStatus.BAD_REQUEST);
    }
}
