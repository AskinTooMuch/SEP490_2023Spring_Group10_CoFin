/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 02/04/2023    1.0        DuongVV          First Deploy<br>
 * 03/04/2023    1.1        DuongNH          Add getImportReceiptYear function <br>
 */

package com.example.eims.repository;

import com.example.eims.entity.ExportReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExportReceiptRepository extends JpaRepository<ExportReceipt, Long> {
    Optional<List<ExportReceipt>> findByFacilityId(Long facilityId);
    @Query(value = "select distinct year(export_date) as YEAR from eims.export_receipt where customer_id = ?2 " +
            "and user_id = ?1 order by YEAR DESC", nativeQuery = true)
    Optional<List<String>> getImportReceiptYear(Long userId, Long customerId);
    @Query(value = "select distinct(year(export_date)) from eims.export_receipt where facility_id = ?1 ", nativeQuery = true)
    Optional<List<String>> getAllYear(Long facilityId);
    @Query(value = "select * from eims.export_receipt where facility_id = ?1 and year(export_date) = ?2", nativeQuery = true)
    Optional<List<ExportReceipt>> getAllByYear(Long facilityId, String year);
    @Query(value = "select * from eims.export_receipt where facility_id = ?1 and year(export_date) = ?2 and " +
            "month(export_date) = ?3", nativeQuery = true)
    Optional<List<ExportReceipt>> getAllByYearAndMonth(Long facilityId, String year, String month);
}
