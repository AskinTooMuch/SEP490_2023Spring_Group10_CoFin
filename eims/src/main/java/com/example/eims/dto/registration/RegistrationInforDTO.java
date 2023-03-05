/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 04/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.dto.registration;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import lombok.Data;

import java.sql.Date;

@Data
@NamedNativeQuery(name="getRegistrationInforForUser",
        query="SELECT  R.registration_id, U.user_id, F.facility_id, U.username, U.phone, U.dob, U.email, U.address, "+
                "F.facility_name, F.facility_found_date, F.business_license_number, F.facility_address, F.hotline, " +
                "R.register_date, R.status " +
                "FROM eims.registration R " +
                "JOIN eims.user U ON R.user_id = U.user_id " +
                "JOIN eims.facility F ON F.user_id = U.user_id " +
                "WHERE U.user_id = ? ",
        resultClass = RegistrationInforDTO.class)
@Entity
public class RegistrationInforDTO {
    @Id
    private Long registrationId;
    private Long userId;
    private Long facilityId;
    private String username;
    private String phone;
    private Date dob;
    private String email;
    private String address;
    private String facilityName;
    private Date facilityFoundDate;
    private String businessLicenseNumber;
    private String facilityAddress;
    private String hotline;
    private Date registerDate;
    private int status;
}
