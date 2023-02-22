package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "specie")
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long specieId = 100L;
    private Long userId;
    private String specieName;
    private int incubationPeriod;
    private boolean status;
}
