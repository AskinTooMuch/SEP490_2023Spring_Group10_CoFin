package com.example.eims.dto.report;

import lombok.Data;

@Data
public class IncomeReportItemDTO {
    private Float total;
    private Float paid;

    public IncomeReportItemDTO() {
    }

    public IncomeReportItemDTO(Float total, Float paid) {
        this.total = total;
        this.paid = paid;
    }
}
