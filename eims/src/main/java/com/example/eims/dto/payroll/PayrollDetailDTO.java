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

import com.example.eims.entity.Payroll;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
public class PayrollDetailDTO {
    private Long payrollId;
    private Long ownerId;
    private Long userId;
    private String employeeName;
    private String employeePhone;
    private String payrollItem;
    private Float payrollAmount;
    private String issueDate;
    private String note;
    private int status;

    public void getFromEntity(Payroll payroll, String employeeName){
        this.payrollId = payroll.getPayrollId();
        this.ownerId = payroll.getOwnerId();
        this.userId = payroll.getEmployeeId();
        this.employeeName = employeeName;
        this.employeePhone = payroll.getPhone();
        this.payrollItem = payroll.getPayrollItem();
        this.payrollAmount = payroll.getPayrollAmount();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.issueDate = sdf.format(payroll.getIssueDate());
        this.note = payroll.getNote();
        if (payroll.isStatus()) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }
}
