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
    @Column(name = "specieId")
    private Long specieId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "specieName")
    private String specieName;
    @Column(name = "incubationPeriod")
    private int incubationPeriod;
    @Column(name = "status")
    private boolean status;
}
