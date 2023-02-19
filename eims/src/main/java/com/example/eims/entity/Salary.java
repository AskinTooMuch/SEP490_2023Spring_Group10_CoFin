package com.example.eims.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "salary")
public class Salary {
    @Id
    private Long salaryId;
    private Long userId;
    private Float baseSalary;
    private Float bonus;
    private Float fine;
    private Date issueDate;
    private String note;
    private boolean status;
}
