package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.BitSet;

@Data
@Entity
@Table(name = "breed")
public class Breed {
    @Id
    private Long id;
    private Long specieId;
    private Long userId;
    private String name;
    private Float aveWeight;
    private Long commonDisease;
    private String growthTime;
    private String image;
    private boolean status;
}
