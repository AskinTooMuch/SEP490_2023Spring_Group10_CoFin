package com.example.eims.dto.eggProduct;

import lombok.Data;

@Data
public class EggProductDataForExportItemDTO {
    private Long eggProductId;
    private String productName;
    private int curAmount;
}
