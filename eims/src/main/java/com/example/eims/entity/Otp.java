package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "otp")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;
    private String otp;
    private String phoneNumber;
    private boolean status;
}
