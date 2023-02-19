package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "eggLocation")
public class EggLocation {
    @Id
    private Long id;
    private Long productId;
    private Long machineId;
    private int amount;
    private boolean status;
}
