package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;
import com.kroon.its.catalog.service.dto.UpdateProductRequest;
import com.kroon.its.catalog.service.entity.Product;
import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import com.kroon.its.catalog.service.exception.ProductNotFoundException;
import com.kroon.its.catalog.service.mapper.ProductMapper;
import com.kroon.its.catalog.service.repository.ProductPriceHistoryRepository;
import com.kroon.its.catalog.service.repository.ProductRepository;
import com.kroon.its.catalog.service.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductRepository productRepository;
    private final ProductPriceHistoryRepository productPriceHistoryRepository;

    @Transactional
    public ProductResponse updateProductById(UUID id, UpdateProductRequest request
    ) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        log.info("Updating product with id {}", id);

        Product updatedProduct = Product.builder()
                .createdAt(product.getCreatedAt())
                .name(request.name())
                .description(request.description())
                .active(request.active())
                .build();

        Product saved = productRepository.save(updatedProduct);
        log.info("Product with id {} successfully updated", id);

        ProductPriceHistory history = ProductPriceHistory.builder()
                .product(saved)
                .oldPrice(product.getPrice())
                .newPrice(saved.getPrice())
                .currency(saved.getCurrency())
                .changedAt(product.getCreatedAt())
                .build();
        productPriceHistoryRepository.save(history);

        return ProductMapper.toResponse(saved);
    }

    @Transactional
    public ProductResponse updateProductPrice(UUID id, UpdateProductPriceRequest request
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        log.info("Updating price for product with id {}", id);

        Product updatedProduct = Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .price(request.price())
                .build();

        Product saved = productRepository.save(updatedProduct);

        log.info("Product with id {} successfully updated. New price: {}",
                id, saved.getPrice());
        ProductPriceHistory history = ProductPriceHistory.builder()

                .oldPrice(product.getPrice())
                .newPrice(saved.getPrice())
                .changedAt(product.getCreatedAt())
                .build();
        productPriceHistoryRepository.save(history);
        return ProductMapper.toResponse(saved);
    }
}
