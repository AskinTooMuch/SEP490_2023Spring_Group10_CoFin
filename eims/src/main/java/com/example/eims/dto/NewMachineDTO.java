package com.example.eims.dto;

import lombok.Data;

@Data
public class NewMachineDTO {
    private Long machineTypeId;
    private Long facilityId;
    private String name;
    private int maxCapacity;
    private int curCapacity;
    private boolean active;
}
