/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 15/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.eggBatch;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EggBatchViewImportDTO {
    private Long eggBatchId;
    private Long breedId;
    private String breedName;
    private int amount;
    private BigDecimal price;
}
