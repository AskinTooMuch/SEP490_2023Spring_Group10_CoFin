package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "importReceipt")
public class ImportReceipt {
    @Id
    private Long importId;
    private Long supplierId;
    private Long userId;
    private Long facilityId;
    private Date importDate;
    private Float total;
    private Float paid;
    private boolean status;
}
