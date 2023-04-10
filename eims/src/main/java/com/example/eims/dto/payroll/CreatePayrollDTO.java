/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 28/03/2023   1.0         DuongNH     First Deploy<br>
 * 28/03/2023   2.0         DuongNH     Add function<br>
 */
package com.example.eims.dto.payroll;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class CreatePayrollDTO {
    private Long payrollId;
    private Long ownerId;
    private Long employeeId;
    private String phone;
    private String payrollItem;
    private BigDecimal payrollAmount;
    private String issueDate;
    private String note;
    private boolean status;
}
