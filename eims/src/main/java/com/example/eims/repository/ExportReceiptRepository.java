/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 02/04/2023    1.0        DuongYK          First Deploy<br>
 * 03/04/2023    1.1        DuongNH          Add getImportReceiptYear function <br>
 **/
package com.example.eims.repository;

import com.example.eims.entity.ExportReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExportReceiptRepository extends JpaRepository<ExportReceipt, Long> {
    Optional<List<ExportReceipt>> findByFacilityId(Long facilityId);
    @Query(value = "select distinct year(export_date) as YEAR from eims.export_receipt where customer_id = ?2 and user_id = ?1 order by YEAR DESC \n", nativeQuery = true)
    Optional<List<String>> getImportReceiptYear(Long userId, Long customerId);
}
