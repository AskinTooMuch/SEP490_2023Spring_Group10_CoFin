/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author      DESCRIPTION<br>
 * 06/04/2023    1.0        ChucNV      First Deploy<br>
 */
package com.example.eims.dto.payment;

import lombok.Data;

@Data
public class ChargeRequirementDTO {
    private Long subscriptionId;
    private float cost;
    private int machineQuota;
    private float discount;
    private int machineRunning;
}
