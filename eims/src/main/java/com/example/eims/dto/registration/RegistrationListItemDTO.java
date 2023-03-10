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
@NamedNativeQuery(name="getRegistrationListByStatus",
        query="SELECT R.registration_id, U.user_id, F.facility_id, U.username, U.phone, F.facility_name, " +
                "F.facility_found_date, F.business_license_number, R.register_date, R.status " +
                "FROM eims.registration R " +
                "JOIN eims.user U ON R.user_id = U.user_id " +
                "JOIN eims.facility F ON F.user_id = U.user_id " +
                "WHERE R.status = ? ",
        resultClass = RegistrationListItemDTO.class)
@Entity
public class RegistrationListItemDTO {
    @Id
    private Long registrationId;
    private Long userId;
    private Long facilityId;
    private String username;
    private String phone;
    private String facilityName;
    private Date facilityFoundDate;
    private String businessLicenseNumber;
    private Date registerDate;
    private int status;
}
