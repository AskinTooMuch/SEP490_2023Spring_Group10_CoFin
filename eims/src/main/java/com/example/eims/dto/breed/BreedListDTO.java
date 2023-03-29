/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 29/03/2023    1.0        ChucNV           First deploy
 */
package com.example.eims.dto.breed;

import com.example.eims.dto.user.UserDetailDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;


@NamedNativeQuery(name="GetListBreedBySpecie",
        query="SELECT B.breed_id, S.specie_id, B.breed_name, S.specie_name, B.average_weight_male, B.average_weight_female, B.growth_time, B.status\n" +
                "    FROM breed B INNER JOIN specie S ON B.specie_id = S.specie_id\n" +
                "    WHERE B.specie_id = ?;",
        resultClass = BreedListDTO.class)
@NamedNativeQuery(name="GetListBreedByUser",
        query="SELECT B.breed_id, S.specie_id, B.breed_name, S.specie_name, B.average_weight_male, B.average_weight_female, B.growth_time, B.status\n" +
                "    FROM breed B INNER JOIN specie S ON B.specie_id = S.specie_id\n" +
                "    WHERE B.user_id = ?;",
        resultClass = BreedListDTO.class)
@SqlResultSetMapping(
        name = "BreedListMapping",
        classes = @ConstructorResult(
                targetClass = BreedListDTO.class,
                columns = {@ColumnResult(name = "breedId", type = Long.class),
                        @ColumnResult(name = "breedName", type = String.class),
                        @ColumnResult(name = "specieName", type = String.class),
                        @ColumnResult(name = "averageWeightMale", type = Float.class),
                        @ColumnResult(name = "averageWeightFemale", type = Float.class),
                        @ColumnResult(name = "growthTime", type = Integer.class),
                        @ColumnResult(name = "status", type = Boolean.class)
                }))
@Data
@Entity
public class BreedListDTO {
    @Id
    @Column(name = "breed_id")
    private Long breedId;
    @Column(name = "specie_id")
    private Long specieId;
    @Column(name = "breed_name")
    private String breedName;
    @Column(name = "specie_name")
    private String specieName;
    @Column(name = "average_weight_male")
    private Float averageWeightMale;
    @Column(name = "average_weight_female")
    private Float averageWeightFemale;
    @Column(name = "growth_time")
    private int growthTime;
    @Column(name = "status")
    private boolean status;
}
