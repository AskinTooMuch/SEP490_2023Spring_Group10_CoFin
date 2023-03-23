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

package com.example.eims.dto.eggLocation;

import lombok.Data;

@Data
public class EggLocationEggBatchDetailDTO {
    private Long eggLocationId;
    private Long productId;
    private Long machineId;
    private String machineName;
    private int amount;
    private int maxCapacity;
    private int curCapacity;
}
