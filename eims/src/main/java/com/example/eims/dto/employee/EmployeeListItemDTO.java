/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 18/03/2023   1.0         DuongNH     First Deploy<br>
 */
package com.example.eims.dto.employee;

import com.example.eims.entity.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeListItemDTO {
    private Long employeeId;
    private String employeeName;
    private String employeePhone;
    private BigDecimal payrollAmount;
    private int status;

    public void getFromUser(User user){
        this.employeeId = user.getUserId();
        this.employeeName = user.getUsername();
        this.employeePhone = user.getPhone();
        this.payrollAmount = user.getSalary();
        this.status = user.getStatus();
    }
}
