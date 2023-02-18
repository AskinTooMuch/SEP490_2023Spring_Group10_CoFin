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
    @Column(name = "exportId")
    private Long exportId;
    @Column(name = "productId")
    private Long productId;
    @Column(name = "price")
    private Float price;
    @Column(name = "vaccinePrice")
    private Float vaccinePrice;
    @Column(name = "amount")
    private int amount;
}
