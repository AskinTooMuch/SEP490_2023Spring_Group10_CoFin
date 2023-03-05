package com.example.eims.dto.specie;

import lombok.Data;

@Data
public class NewSpecieDTO {
    private Long userId;
    private String specieName;
    private int incubationPeriod;
    private int embryolessDate;
    private int diedEmbryoDate;
    private int hatchingDate;
}
