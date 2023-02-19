package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    private Long supplierId;
    private Long userId;
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private String supplierMail;
    private boolean status;
}
