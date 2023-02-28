/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.user;

import com.example.eims.entity.User;
import lombok.Data;

import java.sql.Date;

@Data
public class UpdateUserDTO {
    public void getFromEntity(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.dob = user.getDob().toString();
        this.email = user.getEmail();
        //this.salary = user.getSalary();
        this.address = user.getAddress();
        //this.status = user.getStatus();
    }
    private Long userId;
    private String username;
    private String dob;
    private String email;
    //private Float salary;
    private String address;
    //private int status;
}
