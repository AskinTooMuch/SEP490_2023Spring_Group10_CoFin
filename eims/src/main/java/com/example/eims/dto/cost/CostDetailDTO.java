package com.example.eims.dto.cost;

import com.example.eims.entity.Cost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CostDetailDTO {
    private Long costId;
    private Long userId;
    private Long facilityId;
    private String costItem;
    private Float costAmount;
    private Float paidAmount;
    private String issueDate;
    private String note;
    private boolean status;

    public void getFromEntity(Cost cost){
        this.costId = cost.getCostId();
        this.userId = cost.getUserId();
        this.facilityId = cost.getFacilityId();
        this.costItem = cost.getCostItem();
        this.costAmount = cost.getCostAmount();
        this.paidAmount = cost.getPaidAmount();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.issueDate = sdf.format(cost.getIssueDate());
        this.note = cost.getNote();
        this.status = cost.isStatus();
    }
}
