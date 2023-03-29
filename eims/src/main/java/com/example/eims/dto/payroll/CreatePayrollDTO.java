package com.example.eims.dto.payroll;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@Data
public class CreatePayrollDTO {
    private Long payrollId;
    private Long ownerId;
    private Long employeeId;
    private String phone;
    private String payrollItem;
    private Float payrollAmount;
    private String issueDate;
    private String note;
    private boolean status;
}
