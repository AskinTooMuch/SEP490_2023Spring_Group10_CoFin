/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 15/03/2023    1.0        DuongVV          First Deploy<br>
 * 28/03/2023    1.1        DuongVV          Update attributes<br>
 */

package com.example.eims.dto.eggBatch;

import com.example.eims.dto.eggLocation.EggLocationUpdateEggBatchDTO;
import lombok.Data;

import java.util.List;

@Data
public class UpdateEggBatchDTO {
    private Long eggBatchId;
    private int phaseNumber;
    private int eggWasted;
    private int amount;
    private List<EggLocationUpdateEggBatchDTO> eggLocationUpdateEggBatchDTOS;
    private int needAction;
}
