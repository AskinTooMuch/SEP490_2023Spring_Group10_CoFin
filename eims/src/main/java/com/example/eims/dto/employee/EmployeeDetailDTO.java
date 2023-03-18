package com.example.eims.dto.employee;

import com.example.eims.entity.User;
import lombok.Data;
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
    private Float salary;
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
