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
    @Column(name = "facilityId")
    private Long facilityId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "facilityName")
    private String facilityName;
    @Column(name = "facilityAddress")
    private String facilityAddress;
    @Column(name = "facilityFoundDate")
    private Date facilityFoundDate;
    @Column(name = "subscriptionExpirationDate")
    private Date subscriptionExpirationDate;
    @Column(name = "hotline")
    private String hotline;
    @Column(name = "status")
    private boolean status;
}
