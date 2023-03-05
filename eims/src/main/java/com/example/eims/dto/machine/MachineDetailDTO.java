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

package com.example.eims.dto.machine;

import com.example.eims.dto.eggLocation.EggLocationMachineDetailDTO;
import com.example.eims.entity.Machine;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class MachineDetailDTO {
    private Long machineId;
    private String name;
    private String machineTypeName;
    private int curCapacity;
    private int maxCapacity;
    private Date addedDate;
    private int status;
    private List<EggLocationMachineDetailDTO> eggs;

    public void getFromEntity(Machine machine) {
        this.machineId = machine.getMachineId();
        this.name = machine.getMachineName();
        this.curCapacity = machine.getCurCapacity();
        this.maxCapacity = machine.getMaxCapacity();
        this.addedDate = machine.getAddedDate();
        this.status = machine.getStatus();
    }
}
