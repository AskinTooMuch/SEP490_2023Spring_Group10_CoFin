/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/01/2023    1.0        ChucNV           First Deploy<br>
 * 02/03/2023    2.0        ChucNV           Change attributes
 */

package com.example.eims.dto.auth;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpDTO {
    private String username;
    private String userDob;
    private String userPhone;
    private String userEmail;
    private String userAddress;
    private String userPassword;
    private String facilityName;
    private String facilityFoundDate;
    private String facilityHotline;
    private String facilityAddress;
    private String businessLicenseNumber;
}
