/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/02/2023    1.0        DuongVV          First Deploy<br>
 * 02/04/2023    1.0        DuongNH          Add new named query<br>
 */

package com.example.eims.dto.importReceipt;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NamedNativeQuery(name="getImportReceiptStatisticByUserId",
        query="select IR.supplier_id, S.supplier_name, sum(total) as total, sum(paid) as paid\n" +
                "from eims.import_receipt as IR inner join eims.supplier as S \n" +
                "on IR.supplier_id = S.supplier_id\n" +
                "where IR.user_id = ? group by IR.supplier_id",
        resultClass = ImportReceiptStatisticDTO.class)
@SqlResultSetMapping(
        name="ImportReceiptStatisticMapping",
        classes = @ConstructorResult(
                targetClass = ImportReceiptStatisticDTO.class,
                columns = {
                        @ColumnResult(name="supplierId", type = Long.class),
                        @ColumnResult(name="supplierName", type = String.class),
                        @ColumnResult(name="total", type= BigDecimal.class),
                        @ColumnResult(name="paid", type = BigDecimal.class)
                }
        )
)

@Entity
public class ImportReceiptStatisticDTO {
    @Id
    @Column(name = "supplier_id")
    private Long supplierId;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "paid")
    private BigDecimal paid;
}
