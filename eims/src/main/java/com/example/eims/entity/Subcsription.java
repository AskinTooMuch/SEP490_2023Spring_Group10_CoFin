package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "subscription")
public class Subcsription {
    @Id
    @Column(name = "subscriptionId")
    private Long subscriptionId;
    @Column(name = "cost")
    private Float cost;
    @Column(name = "duration")
    private int duration;
    @Column(name = "machineQuota")
    private int machineQuota;
    @Column(name = "status")
    private boolean status;
}
