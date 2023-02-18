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
    @Column(name = "salaryId")
    private Long salaryId;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "baseSalary")
    private Float baseSalary;
    @Column(name = "bonus")
    private Float bonus;
    @Column(name = "fine")
    private Float fine;
    @Column(name = "issueDate")
    private Date issueDate;
    @Column(name = "note")
    private String note;
    @Column(name = "status")
    private boolean status;
}
