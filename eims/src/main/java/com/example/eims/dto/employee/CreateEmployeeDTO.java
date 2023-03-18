/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 15/03/2023   1.0         DuongVV     First Deploy<br>
 */
package com.example.eims.dto.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
public class CreateEmployeeDTO {
    private String employeeName;
    private String employeeDob;
    private String employeePhone;
    private String employeePassword;
    private String employeeAddress;
    private String email;
    private Float salary;
    private Long facilityId;
}
