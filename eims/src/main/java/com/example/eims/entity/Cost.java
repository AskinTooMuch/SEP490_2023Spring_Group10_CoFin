package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "cost")
public class Cost {
    @Id
    @Column(name = "costId")
    private Long id;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "facilityId")
    private Long facilityId;
    @Column(name = "costItem")
    private String costItem;
    @Column(name = "costAmount")
    private Float costAmount;
    @Column(name = "issueDate")
    private Date issueDate;
    @Column(name = "note")
    private String note;
    @Column(name = "status")
    private boolean status;
}
