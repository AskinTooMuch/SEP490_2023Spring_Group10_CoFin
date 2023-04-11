package com.example.eims.dto.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IncomeReportItemDTO {
    private BigDecimal total;
    private BigDecimal paid;

    public IncomeReportItemDTO() {
    }

    public IncomeReportItemDTO(BigDecimal total, BigDecimal paid) {
        this.total = total;
        this.paid = paid;
    }
}
