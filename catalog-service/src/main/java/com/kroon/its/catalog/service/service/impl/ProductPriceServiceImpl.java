package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;
import com.kroon.its.catalog.service.entity.Product;
import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import com.kroon.its.catalog.service.exception.ProductNotFoundException;
import com.kroon.its.catalog.service.mapper.ProductMapper;
import com.kroon.its.catalog.service.repository.ProductPriceHistoryRepository;
import com.kroon.its.catalog.service.repository.ProductRepository;
import com.kroon.its.catalog.service.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductRepository productRepository;
    private final ProductPriceHistoryRepository productPriceHistoryRepository;

    @Override
    @Transactional
    public ProductResponse updateProductPrice(UUID id, UpdateProductPriceRequest request
    ) {
        Objects.requireNonNull(request,
                "UpdateProductPriceRequest must not be null");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        BigDecimal oldPrice = product.getPrice();
        BigDecimal newPrice = request.price();

        if (oldPrice.compareTo(newPrice) == 0) {
            log.info("Product {} price has not changed", id);
            return ProductMapper.toResponse(product);
        }

        product.updatePrice(newPrice);

        Product saved = productRepository.saveAndFlush(product);

        ProductPriceHistory history = ProductPriceHistory.builder()
                .product(saved)
                .oldPrice(oldPrice)
                .newPrice(newPrice)
                .currency(saved.getCurrency())
                .build();

        productPriceHistoryRepository.save(history);

        log.info(
                "Product {} price changed from {} to {} {}",
                id,
                oldPrice,
                newPrice,
                saved.getCurrency()
        );

        return ProductMapper.toResponse(saved);
    }
}
