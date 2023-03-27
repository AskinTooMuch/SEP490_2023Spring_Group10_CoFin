package com.example.eims.dto.cost;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
public class CreateCostDTO {
    private Long userId;
    private Long facilityId;
    private String costItem;
    private Float costAmount;
    private Float paidAmount;
    private Date issueDate;
    private String note;
    private boolean status;
}
