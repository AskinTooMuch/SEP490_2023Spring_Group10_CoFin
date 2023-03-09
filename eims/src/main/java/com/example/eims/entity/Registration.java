/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 04/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;
    private Long userId;
    private Date registerDate;
    private int status;

    public Registration() {
    }

    public Registration(Long registrationId, Long userId, Date registerDate, int status) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.registerDate = registerDate;
        this.status = status;
    }

    public Registration(Long userId, int status) {
        this.userId = userId;
        this.status = status;
    }
}
