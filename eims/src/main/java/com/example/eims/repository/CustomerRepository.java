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

import com.example.eims.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<List<Customer>> findByFacilityId(Long facilityId);
    Optional<Customer> findByCustomerId(Long customerId);
    boolean existsByCustomerPhone(String phoneNumber);
    boolean existsByCustomerPhoneAndFacilityId(String phoneNumber, Long facilityId);
    @Query(value = "SELECT * FROM eims.customer WHERE facility_id = ?1 " +
            "AND customer_name LIKE %?2% OR customer_phone LIKE %?2%", nativeQuery = true)
    Optional<List<Customer>> searchByUsernameOrPhone(Long facilityId, String key);
    Page<Customer> findAllByFacilityId(Long facilityId, Pageable pageable);
    boolean existsByCustomerPhoneNot(String phone);
    @Query(value = "SELECT customer_phone FROM eims.customer WHERE customer_id = ?1 ", nativeQuery = true)
    String findCustomerPhoneById(Long customerId);
    Optional<Customer> findByCustomerPhone(String phone);
    Optional<Customer> findByCustomerPhoneAndFacilityId(String phone, Long facilityId);
}
