/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 05/03/2023    1.0        DuongVV          First Deploy<br>
 */

package com.example.eims.repository;

import com.example.eims.entity.EggProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EggProductRepository extends JpaRepository<EggProduct, Long> {
    Optional<EggProduct> getByProductId(Long productId);
}
