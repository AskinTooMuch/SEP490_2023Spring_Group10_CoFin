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

package com.example.eims.dto.exportReceipt;

import com.example.eims.dto.eggProduct.EggProductCreateExportDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateExportDTO {
    private Long customerId;
    private Long userId;
    private Long facilityId;
    private LocalDateTime exportDate;
    private List<EggProductCreateExportDTO> eggProductList;
}
