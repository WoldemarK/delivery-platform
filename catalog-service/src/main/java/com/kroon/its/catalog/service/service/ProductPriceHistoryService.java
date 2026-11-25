package com.kroon.its.catalog.service.service;

import com.kroon.its.catalog.service.dto.ProductPriceHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductPriceHistoryService {

    Page<ProductPriceHistoryResponse> getPriceHistory(UUID productId, Pageable pageable);
}
