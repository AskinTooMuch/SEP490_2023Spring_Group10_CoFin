package com.example.eims.dto.employee;

import com.example.eims.entity.User;
import lombok.Data;

@Data
public class EmployeeListItemDTO {
    private Long employeeId;
    private String employeeName;
    private String employeePhone;
    private int status;

    public void getFromUser(User user){
        this.employeeId = user.getUserId();
        this.employeeName = user.getUsername();
        this.employeePhone = user.getPhone();
        this.status = user.getStatus();
    }
}
