package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String address;
    private String mail;
    private boolean status;
}
