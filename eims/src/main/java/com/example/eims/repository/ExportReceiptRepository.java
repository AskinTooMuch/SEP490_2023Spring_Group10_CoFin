package com.example.eims.repository;

import com.example.eims.entity.ExportReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExportReceiptRepository extends JpaRepository<ExportReceipt, Long> {
    Optional<List<ExportReceipt>> findByFacilityId(Long facilityId);
}
