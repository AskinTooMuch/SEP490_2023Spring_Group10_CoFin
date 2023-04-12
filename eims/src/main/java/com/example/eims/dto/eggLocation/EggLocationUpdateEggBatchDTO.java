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
public class EggLocationUpdateEggBatchDTO {
    private Long eggLocationId;
    private Long machineId;
    private String machineName;
    private Long machineTypeId;
    private int amountCurrent;
    private int capacity;
    private int amountUpdate;
    private int oldAmount;
}
