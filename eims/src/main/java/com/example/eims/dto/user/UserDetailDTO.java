package com.example.eims.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailDTO {
    // User account's detail
    private Long userId;
    private String userRoleName;
    private String username;
    private Date userDob;
    private String userEmail;
    private Float userSalary;
    private String userAddress;
    private boolean userStatus;

    // Facility details
    private Long facilityId;
    private String facilityName;
    private String facilityAddress;
    private Date facilityFoundDate;
    private Date subscriptionExpirationDate;
    private String hotline;
    private boolean facilityStatus;
    private Long subscriptionId;
}
