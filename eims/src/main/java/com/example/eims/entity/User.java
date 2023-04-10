/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 16/02/2023    1.0        DuongVV          First Deploy<br>
 * 19/02/2023    2.0        DuongVV          Fix notation, id filed<br>
 * 23/03/2023    3.0        ChucNV           Added roles list, change user - role relation to many-many<br>
 */

package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dob;
    private String phone;
    private String email;
    private BigDecimal salary;
    private String password;
    private String address;
    private int status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<Role>();

    public User() {
    }

    public User(Long userId, String username, Date dob, String phone, String email, BigDecimal salary,
                String password, String address, int status) {
        this.userId = userId;
        this.username = username;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.password = password;
        this.address = address;
        this.status = status;
    }

    public User(Long userId, String phone, String password, int status) {
        this.userId = userId;
        this.phone = phone;
        this.password = password;
        this.status = status;
    }
}
