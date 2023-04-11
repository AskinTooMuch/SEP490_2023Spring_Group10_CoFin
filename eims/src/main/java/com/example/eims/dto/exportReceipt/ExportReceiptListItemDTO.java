/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 03/04/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.exportReceipt;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExportReceiptListItemDTO {
    private Long exportId;
    private Long customerId;
    private String customerName;
    private LocalDateTime exportDate;
    private BigDecimal total;
    private BigDecimal paid;
    private int status;
}
