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
    private Long productId;
    private Long eggBatchId;
    private Long incubationPhaseId;
    private Date incubationDate;
    private int amount;
    private int curAmount;
    private boolean status;
}
