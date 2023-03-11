/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 03/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.auth;

import lombok.Data;
@Data
public class SessionDTO {
    private Long userId;
    private Long facilityId;
    private Long roleId;
}
