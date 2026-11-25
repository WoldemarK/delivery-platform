package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.dto.ProductPriceHistoryResponse;
import com.kroon.its.catalog.service.service.ProductPriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService {


    @Override
    public Page<ProductPriceHistoryResponse> getPriceHistory(UUID productId, Pageable pageable) {
        return null;
    }
}
