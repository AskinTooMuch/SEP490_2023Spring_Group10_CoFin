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

package com.example.eims.dto.exportReceipt;

import com.example.eims.dto.eggBatch.EggBatchDataForExportItemDTO;
import com.example.eims.dto.eggProduct.EggProductDataForExportItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateExportDataItemDTO {
    private EggBatchDataForExportItemDTO eggBatch;
    private List<EggProductDataForExportItemDTO> eggProductList;
}
