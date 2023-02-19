package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "eggbatch")
public class EggBatch {
    @Id
    private Long id;
    private Long importId;
    private Long breedId;
    private int amount;
    private Float price;
}
