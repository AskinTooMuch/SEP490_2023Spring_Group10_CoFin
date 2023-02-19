package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "specie")
public class Specie {
    @Id
    private Long specieId;
    private Long userId;
    private String specieName;
    private int incubationPeriod;
    private boolean status;
}
