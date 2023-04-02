package com.example.eims.dto.exportReceipt;

import com.example.eims.dto.eggBatch.EggBatchDataForExportItemDTO;
import com.example.eims.dto.eggProduct.EggProductDataForExportItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateExportDataDTO {
    private List<EggBatchDataForExportItemDTO> eggBatchList;
    private List<List<EggProductDataForExportItemDTO>> eggProductsList;
}
