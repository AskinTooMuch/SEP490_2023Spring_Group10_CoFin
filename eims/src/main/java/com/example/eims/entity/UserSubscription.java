package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "userSubscription")
public class UserSubscription {
    @Id
    @Column(name = "facilityId")
    private Long facilityId;
    @Column(name = "subscriptionId")
    private Long subscriptionId;
    @Column(name = "subscriptionDate")
    private Date subscriptionDate;
    @Column(name = "status")
    private boolean status;
}
