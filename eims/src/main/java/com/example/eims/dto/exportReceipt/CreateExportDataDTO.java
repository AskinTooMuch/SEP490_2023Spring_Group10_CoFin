package com.example.eims.dto.exportReceipt;

import com.example.eims.entity.EggBatch;
import com.example.eims.entity.EggProduct;
import lombok.Data;

import java.util.List;

@Data
public class CreateExportDataDTO {
    private List<EggBatch> eggBatchList;
    private List<List<EggProduct>> eggProductsList;
}
