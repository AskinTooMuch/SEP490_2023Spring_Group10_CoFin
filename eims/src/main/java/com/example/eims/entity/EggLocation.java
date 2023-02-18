package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "eggLocation")
public class EggLocation {
    @Id
    @Column(name = "eggId")
    private Long id;
    @Column(name = "productId")
    private Long productId;
    @Column(name = "machineId")
    private Long machineId;
    @Column(name = "amount")
    private int amount;
    @Column(name = "status")
    private boolean status;
}
