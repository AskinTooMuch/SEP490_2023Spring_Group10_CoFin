package com.example.eims.repository;

import com.example.eims.entity.ExportDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExportDetailRepository extends JpaRepository<ExportDetail, Long> {
    Optional<List<ExportDetail>> findByExportId(Long exportId);
}
