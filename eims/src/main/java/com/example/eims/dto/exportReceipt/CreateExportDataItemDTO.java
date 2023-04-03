package com.example.eims.dto.exportReceipt;

import com.example.eims.dto.eggBatch.EggBatchDataForExportItemDTO;
import com.example.eims.dto.eggProduct.EggProductDataForExportItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateExportDataItemDTO {
    private EggBatchDataForExportItemDTO eggBatch;
    private List<EggProductDataForExportItemDTO> eggProductList;
}
