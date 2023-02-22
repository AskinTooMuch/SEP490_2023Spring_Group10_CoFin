/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 19/02/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.facility;

import lombok.Data;

@Data
public class UpdateFacilityDTO {
    private Long userId;
    private String facilityName;
    private String facilityAddress;
    private String foundDate;
    private String expirationDate;
    private String hotline;
    private int status;
}
