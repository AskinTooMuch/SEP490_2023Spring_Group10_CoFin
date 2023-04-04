/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/04/2023   1.0         ChucNV     First Deploy<br>
 * 04/04/2023   2.0         ChucNV      Modify attributes<br>
 */
package com.example.eims.dto.payment;

import lombok.Data;

@Data
public class ChargeDTO {
    private Long subscriptionId;
    private Long facilityId;
    private Long amount;
    private String currency;
    private String method;
}
