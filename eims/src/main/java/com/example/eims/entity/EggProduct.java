package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "eggProduct")
public class EggProduct {
    @Id
    @Column(name = "productId")
    private Long productId;
    @Column(name = "eggBatchId")
    private Long eggBatchId;
    @Column(name = "incubationPhaseId")
    private Long incubationPhaseId;
    @Column(name = "incubationDate")
    private Date incubationDate;
    @Column(name = "amount")
    private int amount;
    @Column(name = "curAmount")
    private int curAmount;
    @Column(name = "status")
    private boolean status;
}
