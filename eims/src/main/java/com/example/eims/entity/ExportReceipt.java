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
    private Long exportId;
    private Long customerId;
    private Long userId;
    private Long facilityId;
    private Date exportDate;
    private Float total;
    private Float paid;
    private boolean status;
}
