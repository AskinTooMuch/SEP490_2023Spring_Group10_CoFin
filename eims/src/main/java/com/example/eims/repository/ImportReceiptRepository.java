package com.example.eims.repository;

import com.example.eims.entity.ImportReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ImportReceiptRepository extends JpaRepository<ImportReceipt, Long> {
    List<ImportReceipt> findByFacilityId(Long facilityId);
    List<ImportReceipt> findByUserId(Long userId);
    List<ImportReceipt> findBySupplierId(Long supplierId);
    Optional<ImportReceipt> findByImportId(Long importId);

    @Query(value = "SELECT supplier_id, SUM(total) as total, SUM(paid) as paid from eims.import_receipt " +
            "WHERE user_id = ?1 GROUP BY supplier_id", nativeQuery = true)
    List<Objects> importStatistic(Long userId);
    Page<ImportReceipt> findAllByUserId(Long userId, Pageable pageable);
    Page<ImportReceipt> findAllBySupplierId(Long suppplierId, Pageable pageable);
}
