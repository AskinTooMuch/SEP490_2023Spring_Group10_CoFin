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
    private Long machineId;
    private Long machineTypeId;
    private Long facilityId;
    private String machineName;
    private int maxCapacity;
    private int curCapacity;
    private Date addedDate;
    private boolean active;
    private boolean status;
}
