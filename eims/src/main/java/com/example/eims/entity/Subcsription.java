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
    private Long subscriptionId;
    private Float cost;
    private int duration;
    private int machineQuota;
    private boolean status;
}
