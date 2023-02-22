/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 16/02/2023    1.0        DuongVV          First Deploy<br>
 * 19/02/2023    2.0        DuongVV          Fix notation, id filed
 */

package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "exportReceipt")
public class ExportReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exportId;
    private Long customerId;
    private Long userId;
    private Long facilityId;
    private Date exportDate;
    private Float total;
    private Float paid;
    private boolean status;
}
