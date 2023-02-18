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
    @Column(name = "importId")
    private Long importId;
    @Column(name = "supplierId")
    private Long supplierId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "facilityId")
    private Long facilityId;
    @Column(name = "importDate")
    private Date importDate;
    @Column(name = "total")
    private Float total;
    @Column(name = "paid")
    private Float paid;
    @Column(name = "status")
    private boolean status;
}
