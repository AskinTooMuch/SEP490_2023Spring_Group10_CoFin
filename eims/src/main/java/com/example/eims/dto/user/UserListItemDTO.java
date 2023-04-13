/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 12/04/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.user;

import lombok.Data;

import java.sql.Date;

@Data
public class UserListItemDTO {
    private Long userId;
    private String userName;
    private Date dob;
    private String phone;
    private int status;
    private String facilityName;
    private String businessLicenseNumber;
}
