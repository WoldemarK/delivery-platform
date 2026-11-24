package com.kroon.its.catalog.service.repository;

import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, UUID> {

    Page<ProductPriceHistory> findAllByProductIdOrderByChangedAtDescIdDesc(UUID productId, Pageable pageable);
}
