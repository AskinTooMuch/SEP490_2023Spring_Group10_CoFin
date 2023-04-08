/*
 * Copyright (C) 2023, FPT University<br>
 * SEP490 - SEP490_G10<br>
 * EIMS<br>
 * Eggs Incubating Management System<br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 17/02/2023    1.0        DuongVV          First Deploy<br>
 * 23/02/2023    2.0        DuongVV          Add search<br>
 * 28/02/2023    3.0        DuongVV          Add paging<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<List<Supplier>> findByUserId(Long userId);
    Optional<Supplier> findBySupplierId(Long supplierId);
    boolean existsBySupplierPhone(String phoneNumber);
    boolean existsBySupplierPhoneAndUserId(String phoneNumber, Long userId);
    boolean existsBySupplierId(Long supplierId);
    @Query(value = "SELECT * FROM eims.supplier WHERE user_id = ?1 " +
            "AND supplier_phone LIKE %?2% OR supplier_name LIKE %?2% ", nativeQuery = true)
    Optional<List<Supplier>> searchByUsernameOrPhone(Long userId, String key);
    Page<Supplier> findAllByUserId(Long userId, Pageable pageable);
    boolean existsBySupplierPhoneNot(String phone);
    @Query(value = "SELECT supplier_phone FROM eims.supplier WHERE supplier_id = ?1 ", nativeQuery = true)
    String findSupplierPhoneById(Long supplierId);
    Optional<Supplier> findBySupplierPhone(String phone);
    Optional<Supplier> findBySupplierPhoneAndUserId(String phone, Long userId);
    Optional<List<Supplier>> findByUserIdAndStatus(Long userId, int status);
}
