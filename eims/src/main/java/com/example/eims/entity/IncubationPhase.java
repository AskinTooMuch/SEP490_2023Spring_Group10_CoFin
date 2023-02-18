package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "incubationPhase")
public class IncubationPhase {
    @Id
    @Column(name = "incubationPhaseId")
    private Long incubationPhaseId;
    @Column(name = "specieId")
    private Long specieId;
    @Column(name = "phaseNumber")
    private int phaseNumber;
    @Column(name = "phasePeriod")
    private int phasePeriod;
    @Column(name = "phaseDescription")
    private String phaseDescription;
    @Column(name = "status")
    private boolean status;
}
