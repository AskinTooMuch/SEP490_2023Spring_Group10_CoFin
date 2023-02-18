package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "exportReceipt")
public class ExportReceipt {
    @Id
    @Column(name = "exportId")
    private Long exportId;
    @Column(name = "customerId")
    private Long customerId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "facilityId")
    private Long facilityId;
    @Column(name = "exportDate")
    private Date exportDate;
    @Column(name = "total")
    private Float total;
    @Column(name = "paid")
    private Float paid;
    @Column(name = "status")
    private boolean status;
}
