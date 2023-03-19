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

package com.example.eims.dto.importReceipt;

import com.example.eims.entity.EggBatch;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ViewImportDTO {
    private Long supplierId;
    private String supplierName;
    private LocalDateTime importDate;
    private Long importId;
    private List<EggBatch> eggBatchList;
}
