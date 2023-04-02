package com.example.eims.dto.eggProduct;

import lombok.Data;

@Data
public class EggProductCreateExportDTO {
    private Long eggProductId;
    private int curAmount;
    private int exportAmount;
    private Float price;
    private Float vaccine;
}
