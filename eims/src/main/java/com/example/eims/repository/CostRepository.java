/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 27/03/2023    1.0        DuongNH          First Deploy<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CostRepository extends JpaRepository<Cost,Long> {

    @Query(value = "select * from eims.cost where user_id = ?1 " +
            "and cost_item like %?2%", nativeQuery = true)
    Optional<List<Cost>> searchCostByName(Long userId,String costName);

    Optional<List<Cost>> findAllByUserId(Long userId);
}
