package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.BitSet;

@Data
@Entity
@Table(name = "breed")
public class Breed {
    @Id
    @Column(name = "breedId")
    private Long id;
    @Column(name = "specieId")
    private Long specieId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "breedName")
    private String name;
    @Column(name = "averageWeight")
    private Float aveWeight;
    @Column(name = "commonDisease")
    private Long commonDisease;
    @Column(name = "growthTime")
    private String growthTime;
    @Column(name = "imageSrc")
    private String image;
    @Column(name = "status")
    private boolean status;
}
