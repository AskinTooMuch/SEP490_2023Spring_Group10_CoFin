package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "facility")
public class Facility {
    @Id
    private Long facilityId;
    private Long userId;
    private String facilityName;
    private String facilityAddress;
    private Date facilityFoundDate;
    private Date subscriptionExpirationDate;
    private String hotline;
    private boolean status;
}
