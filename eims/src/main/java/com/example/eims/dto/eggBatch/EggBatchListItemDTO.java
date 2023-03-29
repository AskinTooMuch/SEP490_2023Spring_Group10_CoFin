/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 15/03/2023    1.0        DuongVV          First Deploy<br>
 * 28/03/2023    1.1        DuongVV          Update attributes<br>
 */

package com.example.eims.dto.eggBatch;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class EggBatchListItemDTO {
    private Long eggBatchId;
    private Long importId;
    private LocalDateTime importDate;
    private Long breedId;
    private String breedName;
    private Long supplierId;
    private String supplierName;
    private int amount;
    private int progress;
    private Long progressInDays;
    private int incubationPeriod;
    private String phase;
    private int needAction;
    private LocalDateTime dateAction;
    private int status;
}
