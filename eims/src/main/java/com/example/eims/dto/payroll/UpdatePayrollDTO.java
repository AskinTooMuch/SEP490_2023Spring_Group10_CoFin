package com.example.eims.dto.payroll;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class UpdatePayrollDTO {
    private Long payrollId;
    private String payrollItem;
    private Float payrollAmount;
    private String issueDate;
    private String note;
}
