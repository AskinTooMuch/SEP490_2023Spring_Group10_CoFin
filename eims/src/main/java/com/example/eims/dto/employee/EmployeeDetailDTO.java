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
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class EmployeeDetailDTO {
    private Long employeeId;
    private String employeeName;
    private Date employeeDob;
    private String employeePhone;
    private String employeeAddress;
    private String email;
    private BigDecimal salary;
    private int status;

    public void getFromEntity(User user){
        this.employeeId = user.getUserId();
        this.employeeName = user.getUsername();
        this.employeeDob = user.getDob();
        this.employeePhone = user.getPhone();
        this.employeeAddress = user.getAddress();
        this.email = user.getEmail();
        this.salary = user.getSalary();
        this.status = user.getStatus();
    }
}
