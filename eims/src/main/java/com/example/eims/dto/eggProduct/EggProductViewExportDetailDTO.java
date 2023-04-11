/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 03/04/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.eggProduct;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EggProductViewExportDetailDTO {
    private Long breedId;
    private String breedName;
    private String productName;
    private Long eggBatchId;
    private int exportAmount;
    private BigDecimal price;
    private BigDecimal vaccine;
}
