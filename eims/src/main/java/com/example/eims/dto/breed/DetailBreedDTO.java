/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 08/03/2023    1.0        ChucNV           First deploy
 * 09/03/2023    1.1        ChucNV           Add constructor
 * 11/03/2023    1.2        ChucNV           Add specie name
 */
package com.example.eims.dto.breed;

import com.example.eims.entity.Breed;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
@Data
public class DetailBreedDTO {
    private Long breedId;
    private Long specieId;
    private Long userId;
    private String breedName;
    private String specieName;
    private Float averageWeightMale;
    private Float averageWeightFemale;
    private String commonDisease;
    private int growthTime;
    private Resource image;
    private boolean status;

    public DetailBreedDTO(Breed breed) {
        this.breedId = breed.getBreedId();
        this.specieId = breed.getSpecieId();
        this.userId = breed.getUserId();
        this.breedName = breed.getBreedName();
        this.averageWeightMale = breed.getAverageWeightMale();
        this.averageWeightFemale = breed.getAverageWeightFemale();
        this.commonDisease = breed.getCommonDisease();
        this.growthTime = breed.getGrowthTime();
        this.status = breed.isStatus();
    }
}
