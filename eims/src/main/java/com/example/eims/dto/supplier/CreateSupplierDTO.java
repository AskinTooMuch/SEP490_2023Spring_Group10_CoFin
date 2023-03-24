/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 * 03/03/2023    2.0        ChucNV           Add facilityName<br>
 */

package com.example.eims.dto.supplier;

import lombok.Data;

@Data
public class CreateSupplierDTO {
    private Long facilityId;
    private String supplierName;
    private String facilityName;
    private String supplierPhone;
    private String supplierAddress;
    private String supplierMail;
}
