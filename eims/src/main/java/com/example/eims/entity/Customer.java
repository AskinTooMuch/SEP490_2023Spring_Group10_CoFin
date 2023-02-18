package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customerId")
    private Long id;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "customerName")
    private String name;
    @Column(name = "customerPhone")
    private String phone;
    @Column(name = "customerAddress")
    private String address;
    @Column(name = "customerMail")
    private String mail;
    @Column(name = "status")
    private boolean status;
}
