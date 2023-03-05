/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 05/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.eggLocation;

import com.example.eims.entity.EggLocation;
import lombok.Data;

@Data
public class EggLocationMachineDetailDTO {
    private Long eggLocationId;
    private Long eggBatchId;
    private Long machineId;
    private int amount;
    private int status;

    public void getFromEntity(EggLocation eggLocation) {
        this.eggLocationId = eggLocation.getEggLocationId();
        this.machineId = eggLocation.getMachineId();
        this.amount = eggLocation.getAmount();
        this.status = eggLocation.getStatus();
    }
}
