package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "machine")
public class Machine {
    @Id
    @Column(name = "machineId")
    private Long machineId;
    @Column(name = "machineTypeId")
    private Long machineTypeId;
    @Column(name = "facilityId")
    private Long facilityId;
    @Column(name = "machineName")
    private String machineName;
    @Column(name = "maxCapacity")
    private int maxCapacity;
    @Column(name = "curCapacity")
    private int curCapacity;
    @Column(name = "addedDate")
    private Date addedDate;
    @Column(name = "active")
    private boolean active;
    @Column(name = "status")
    private boolean status;
}
