package com.example.eims.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Entity
@Data
public class SalaryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryHistoryId;
    private Long userId;
    private BigDecimal salary;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date issueDate;
}
