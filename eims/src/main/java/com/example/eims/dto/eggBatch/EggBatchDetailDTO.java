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

import com.example.eims.dto.eggLocation.EggLocationEggBatchDetailDTO;
import com.example.eims.dto.machine.MachineListItemDTO;
import com.example.eims.entity.EggProduct;
import com.example.eims.entity.IncubationPhase;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EggBatchDetailDTO {
    private Long eggBatchId;
    private Long importId;
    private Long supplierId;
    private String supplierName;
    private LocalDateTime importDate;
    private Long specieId;
    private String specieName;
    private Long breedId;
    private String breedName;
    private LocalDateTime startDate;
    private Long progressInDays;
    private int incubationPeriod;
    private int amount;
    private int progress;
    private String phase;
    private int needAction;
    private LocalDateTime dateAction;
    private int status;
    private List<EggProduct> eggProductList;
    private List<EggLocationEggBatchDetailDTO> machineList;
    private List<MachineListItemDTO> machineNotFullList;
    private List<IncubationPhase> phaseUpdateList;
}
