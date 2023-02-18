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
    @Column(name = "supplierId")
    private Long supplierId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "supplierName")
    private String supplierName;
    @Column(name = "supplierPhone")
    private String supplierPhone;
    @Column(name = "supplierAddress")
    private String supplierAddress;
    @Column(name = "supplierMail")
    private String supplierMail;
    @Column(name = "status")
    private boolean status;
}
