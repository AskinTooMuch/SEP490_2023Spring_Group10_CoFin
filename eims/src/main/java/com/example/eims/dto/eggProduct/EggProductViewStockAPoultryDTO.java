/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 28/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.dto.eggProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@NamedNativeQuery(name="viewStockAPoultry",
        query="SELECT ep.product_id, ep.egg_batch_id, ir.import_id, b.breed_id, b.breed_name, " +
                " ip.phase_number, ip.phase_description, ep.cur_amount, ep.incubation_date  " +
                "FROM eims.import_receipt ir " +
                "JOIN eims.egg_batch eb ON ir.import_id = eb.import_id " +
                "JOIN eims.egg_product ep ON eb.egg_batch_id = ep.egg_batch_id " +
                "JOIN eims.incubation_phase ip ON ep.incubation_phase_id = ip.incubation_phase_id " +
                "JOIN eims.breed b ON eb.breed_id = b.breed_id " +
                "WHERE ir.facility_id = ? " +
                "AND eb.status = 0 " +
                "AND ip.phase_number = ? " +
                "AND ep.cur_amount > 0 " +
                "ORDER BY ep.incubation_date ",
        resultClass = EggProductViewStockAPoultryDTO.class)

@Entity
public class EggProductViewStockAPoultryDTO {
    @Id
    private Long productId;
    private Long eggBatchId;
    private Long importId;
    private Long breedId;
    private String breedName;
    private int phaseNumber;
    private String phaseDescription;
    private int curAmount;
    private LocalDateTime incubationDate;
}
