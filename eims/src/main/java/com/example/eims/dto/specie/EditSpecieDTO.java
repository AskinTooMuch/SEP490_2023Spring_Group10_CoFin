package com.example.eims.dto.specie;

import lombok.Data;

@Data
public class EditSpecieDTO {
    private Long specieId;
    private String specieName;
    private int incubationPeriod;
}
