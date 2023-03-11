/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 19/02/2023    1.0        DuongVV          First Deploy<br>
 * 06/03/2023    1.1        DuongVV          Add function<br>
 */

package com.example.eims.dto.machine;

import com.example.eims.entity.Machine;
import lombok.Data;

@Data
public class UpdateMachineDTO {
    private Long machineId;
    private String machineName;
    private int status;
    public void getFromEntity(Machine machine) {
        this.machineId = machine.getMachineId();
        this.machineName = machine.getMachineName();
        this.status = machine.getStatus();
    }
}
