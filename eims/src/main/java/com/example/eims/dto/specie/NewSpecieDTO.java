package com.example.eims.dto.specie;

import lombok.Data;

@Data
public class NewSpecieDTO {
    private Long userId;
    private String specieName;
    private int incubationPeriod;
}
