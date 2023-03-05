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
