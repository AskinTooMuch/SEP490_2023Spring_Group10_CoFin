package com.example.eims.dto.eggProduct;

import lombok.Data;

@Data
public class EggProductViewExportDetailDTO {
    private Long breedId;
    private String breedName;
    private String productName;
    private Long eggBatchId;
    private int exportAmount;
    private Float price;
    private Float vaccine;
}
