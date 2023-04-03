/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 02/04/2023    1.0        DuongNH          First Deploy<br>
 */
package com.example.eims.dto.report;

import lombok.Data;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@NamedNativeQuery( name = "getImportReportItemByMonth",
query = "select supplier_id as id,month(import_date) as report_name,sum(total) as total, sum(paid) as paid\n" +
        "from eims.import_receipt where supplier_id = ?1 and month(import_date) = ?2 and year(import_date) = ?3 \n" +
        "group by month(import_date)",
resultClass = ReportItem.class)

@NamedNativeQuery( name = "getImportReportItemByYear",
        query = "select supplier_id as id ,year(import_date) as report_name,sum(total) as total, sum(paid) as paid " +
                "from eims.import_receipt where supplier_id = ?1 " +
                "group by year(import_date) order by report_name DESC ",
        resultClass = ReportItem.class)

@Entity
public class ReportItem {

    private Long id;
    @Id
    private String reportName;
    private Float total;
    private Float paid;

    public ReportItem(String reportName, Float total, Float paid) {
        this.reportName = reportName;
        this.total = total;
        this.paid = paid;
    }

    public ReportItem() {

    }
}
