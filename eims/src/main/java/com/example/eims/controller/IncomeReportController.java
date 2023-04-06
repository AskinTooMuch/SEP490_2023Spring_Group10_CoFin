package com.example.eims.controller;

import com.example.eims.service.interfaces.IIncomeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/incomeReport")
public class IncomeReportController {
    @Autowired
    private IIncomeReportService incomeReportService;

    /**
     * Get income report of owner for every month of a year
     * @param userId owner's id
     * @param year the year user want to get income report
     * @return
     */
    @GetMapping("/inMonth")
    public ResponseEntity<?> getIncomeReportInMonth(@RequestParam Long userId, @RequestParam String year) {
        return incomeReportService.getIncomeReportInMonth(userId, year);
    }

    /**
     * Get income report of owner for every year
     * @param userId owner's id
     * @return
     */
    @GetMapping("/inYear")
    public ResponseEntity<?> getIncomeReportInYear(@RequestParam Long userId){
        return incomeReportService.getIncomeReportInYear(userId);
    }
}
