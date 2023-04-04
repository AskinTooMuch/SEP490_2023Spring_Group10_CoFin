/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 02/04/2023    1.0        DuongNH          First Deploy<br>
 * 02/04/2023    1.1        DuongNH          Add new named query<br>
 */
package com.example.eims.dto.exportReceipt;

import jakarta.persistence.*;
import lombok.Data;

@Data
@NamedNativeQuery(name="getExportReceiptStatisticByUserId",
        query="select IR.customer_id, S.customer_name, sum(total) as total, sum(paid) as paid\n" +
                "from eims.export_receipt as IR inner join eims.customer as S \n" +
                "on IR.customer_id = S.customer_id\n" +
                "where IR.user_id = ?1 \n" +
                "group by IR.customer_id",
        resultClass = ExportReceiptStatisticDTO.class)
@SqlResultSetMapping(
        name="ExportReceiptStatisticMapping",
        classes = @ConstructorResult(
                targetClass = ExportReceiptStatisticDTO.class,
                columns = {
                        @ColumnResult(name="customerId", type = Long.class),
                        @ColumnResult(name="customerName", type = String.class),
                        @ColumnResult(name="total", type=Float.class),
                        @ColumnResult(name="paid", type = Float.class)
                }
        )
)
@Entity
public class ExportReceiptStatisticDTO {
    @Id
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "total")
    private Float total;
    @Column(name = "paid")
    private Float paid;
}
