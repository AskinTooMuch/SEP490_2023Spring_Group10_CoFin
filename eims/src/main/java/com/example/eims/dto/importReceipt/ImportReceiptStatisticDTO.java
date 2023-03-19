/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.importReceipt;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@NamedNativeQuery(name="getImportReceiptStatisticByUserId",
        query="SELECT supplier_id, SUM(total) as total, SUM(paid) as paid from eims.import_receipt " +
                "WHERE user_id = ? GROUP BY supplier_id",
        resultClass = ImportReceiptStatisticDTO.class)

@Entity
public class ImportReceiptStatisticDTO {
    @Id
    private Long supplierId;
    private Float total;
    private Float paid;
}
