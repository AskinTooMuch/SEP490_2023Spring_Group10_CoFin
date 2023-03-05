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
