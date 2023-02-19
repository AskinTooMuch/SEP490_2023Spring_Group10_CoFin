package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "cost")
public class Cost {
    @Id
    private Long id;
    private Long userId;
    private Long facilityId;
    private String costItem;
    private Float costAmount;
    private Date issueDate;
    private String note;
    private boolean status;
}
