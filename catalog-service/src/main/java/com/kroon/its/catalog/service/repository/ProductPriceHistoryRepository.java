package com.kroon.its.catalog.service.repository;

import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, UUID> {

    Page<ProductPriceHistory> findAllByProductIdOrderByChangedAtDesc(UUID productId,
                                                                     Pageable pageable);
    List<ProductPriceHistory> findAllByProductIdOrderByChangedAtDesc(UUID productId);
}
