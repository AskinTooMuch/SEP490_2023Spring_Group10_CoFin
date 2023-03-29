/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 16/02/2023    1.0        DuongVV          First Deploy<br>
 * 19/02/2023    2.0        DuongVV          Fix notation, id filed<br>
 * 28/03/2023    1.0        DuongVV          Update attributes<br>
 */

package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "egg_batch")
public class EggBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eggBatchId;
    private Long importId;
    private Long breedId;
    private int amount;
    private Float price;
    private int needAction;
    private LocalDateTime dateAction;
    private int status;
}
