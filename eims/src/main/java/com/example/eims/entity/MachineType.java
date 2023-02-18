package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "machineType")
public class MachineType {
    @Id
    @Column(name = "machineTypeId")
    private Long machineTypeId;
    @Column(name = "machineTypeName")
    private String machineTypeName;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private boolean status;
}
