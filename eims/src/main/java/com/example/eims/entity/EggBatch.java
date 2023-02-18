package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "eggbatch")
public class EggBatch {
    @Id
    @Column(name = "eggBatchId")
    private Long id;
    @Column(name = "importId")
    private Long importId;
    @Column(name = "breedId")
    private Long breedId;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private Float price;
}
