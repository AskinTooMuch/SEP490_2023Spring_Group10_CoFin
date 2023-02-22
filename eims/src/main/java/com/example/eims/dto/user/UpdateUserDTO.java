/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 21/02/2023    1.0        ChucNV           First Deploy<br>
 */

package com.example.eims.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserDTO {
    private String username;
    private String dob;
    private String phone;
    private String email;
    private Float salary;
    private String address;
    private int status;
}
