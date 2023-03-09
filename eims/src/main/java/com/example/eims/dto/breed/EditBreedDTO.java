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
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditBreedDTO {
    private Long breedId;
    private Long specieId;
    private String breedName;
    private Float averageWeightMale;
    private Float averageWeightFemale;
    private String commonDisease;
    private int growthTime;
    private MultipartFile image;
    private boolean status;
}

