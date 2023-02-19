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
    private Long machineTypeId;
    private String machineTypeName;
    private String description;
    private boolean status;
}
