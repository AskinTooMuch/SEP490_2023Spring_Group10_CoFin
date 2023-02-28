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
