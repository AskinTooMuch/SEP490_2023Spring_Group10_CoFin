package com.example.eims.dto.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class IncomeReportDTO {
    private List<String> yearList;
    private List<IncomeReportItemDTO> costList;
    private List<IncomeReportItemDTO> payrollList;
    private List<IncomeReportItemDTO> importList;
    private List<IncomeReportItemDTO> exportList;
    private BigDecimal incomeNow;
    private BigDecimal incomeLast;
}
