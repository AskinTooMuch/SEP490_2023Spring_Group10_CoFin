package com.example.eims.repository;

import com.example.eims.entity.EggProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EggProductRepository extends JpaRepository<EggProduct, Long> {
    Optional<EggProduct> getByProductId(Long productId);
}
