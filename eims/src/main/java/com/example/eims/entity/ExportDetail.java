package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "exportDetail")
public class ExportDetail {
    @Id
    private Long exportId;
    private Long productId;
    private Float price;
    private Float vaccinePrice;
    private int amount;
}
