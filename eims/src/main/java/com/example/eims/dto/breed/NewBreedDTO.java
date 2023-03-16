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

import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewBreedDTO {
    private Long specieId;
    private String breedName;
    private Float averageWeightMale;
    private Float averageWeightFemale;
    private String commonDisease;
    private int growthTime;
    @Nullable
    private MultipartFile image;
}
