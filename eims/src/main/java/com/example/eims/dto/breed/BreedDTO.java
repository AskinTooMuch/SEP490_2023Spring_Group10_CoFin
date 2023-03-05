/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 05/03/2023    1.0        ChucNV           First deploy
 */
package com.example.eims.dto.breed;

import lombok.Data;

@Data
public class BreedDTO {
    private Long breedId;
    private Long specieId;
    private Long userId;
    private String breedName;
    private Float averageWeightMale;
    private Float averageWeightFemale;
    private String commonDisease;
    private int growthTime;
    private String imageSrc;
    private boolean status;
}
