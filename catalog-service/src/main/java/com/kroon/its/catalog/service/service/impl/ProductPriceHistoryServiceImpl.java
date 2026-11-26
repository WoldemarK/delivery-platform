package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import com.kroon.its.catalog.service.exception.ProductNotFoundException;
import com.kroon.its.catalog.service.mapper.ProductMapper;
import com.kroon.its.catalog.service.repository.ProductPriceHistoryRepository;
import com.kroon.its.catalog.service.repository.ProductRepository;
import com.kroon.its.common.dto.catalog.ProductPriceHistoryResponse;
import com.kroon.its.catalog.service.service.ProductPriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService {

    private final ProductRepository productRepository;
    private final ProductPriceHistoryRepository priceHistoryRepository;

    @Override
    public Page<ProductPriceHistoryResponse> getPriceHistory(UUID productId, Pageable pageable
    ) {

        if (!productRepository.existsById(productId)
        ) {
            throw new ProductNotFoundException(productId);
        }
        return priceHistoryRepository.findAllByProductIdOrderByChangedAtDescIdDesc(
                productId,
                pageable
        ).map(ProductMapper::toProductPriceHistoryResponse);
    }

}
