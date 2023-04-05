package com.example.eims.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface IIncomeReportService {
    /**
     * Get income report of owner for every month of a year
     * @param userId owner's id
     * @param year the year user want to get income report
     * @return
     */
    public ResponseEntity<?> getIncomeReportInMonth(Long userId, String year);

    /**
     * Get income report of owner for every year
     * @param userId owner's id
     * @return
     */
    public ResponseEntity<?> getIncomeReportInYear(Long userId);
}
