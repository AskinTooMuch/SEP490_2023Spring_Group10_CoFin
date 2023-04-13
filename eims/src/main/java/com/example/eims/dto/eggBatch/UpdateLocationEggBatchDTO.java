/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 13/04/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.eggBatch;

import com.example.eims.dto.eggLocation.EggLocationEggBatchDetailDTO;
import com.example.eims.dto.eggLocation.EggLocationUpdateEggBatchDTO;
import lombok.Data;

import java.util.List;
@Data
public class UpdateLocationEggBatchDTO {
    private Long eggBatchId;
    private int eggWastedIncubating;
    private int eggWastedHatching;
    private List<EggLocationEggBatchDetailDTO> locationsOld;
    private List<EggLocationUpdateEggBatchDTO> locationsNew;
}
