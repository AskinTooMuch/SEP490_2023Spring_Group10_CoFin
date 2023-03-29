/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 18/01/2023    1.0        ChucNV           First Deploy<br>
 * 27/02/2023    1.1        ChucNV           Add findByUserId<br>
 * 23/03/2023    1.2        ChucNV           Update code according to new relations<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByPhoneAndPassword(String phone, String password);
    boolean existsByPhone(String phone);
    Optional<User> findByUserId(Long userId);
    @Query(value = "SELECT user_id FROM eims.user where phone = ?1", nativeQuery = true)
    Long getUserIdByPhone(String phone);
    Page<User> findAll(Pageable pageable);
//    Optional<List<User>> findAllByRoleId(Long roleId);
//    Optional<List<User>> findAllByRoleIdAndStatus(Long roleId, int status);
    @Query(value = "SELECT status FROM eims.user WHERE user_id = ?1", nativeQuery = true)
    boolean getStatusByUserId(Long userId);

    @Query(value = "select * from eims.user\n" +
            "where user_id in (select user_id from eims.work_in where facility_id = ?1)\n" +
            "and (username like %?2% or phone like %?2%)", nativeQuery = true)
    Optional<List<User>> searchEmployeeByPhoneOrName(Long facilityId,String searchKey);
}
