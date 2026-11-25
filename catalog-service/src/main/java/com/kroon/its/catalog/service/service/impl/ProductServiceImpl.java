package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.dto.CreateProductRequest;
import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductRequest;
import com.kroon.its.catalog.service.entity.Product;
import com.kroon.its.catalog.service.exception.ProductNotFoundException;
import com.kroon.its.catalog.service.mapper.ProductMapper;
import com.kroon.its.catalog.service.repository.ProductRepository;
import com.kroon.its.catalog.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request
    ) {
        Objects.requireNonNull(request,
                "CreateProductRequest must not be null"
        );

        log.info("Creating product with name: {}",
                request.name()
        );

        Product product = ProductMapper.toProduct(request);
        Product saved = productRepository.save(product);
        log.info("Product successfully created with id: {}", saved.getId());

        return ProductMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id
    ) {
        log.info("Fetching product by id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        log.debug("Product successfully retrieved with id: {}", id);

        return ProductMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(Boolean active,
                                             Pageable pageable
    ) {
        log.info(
                "Fetching products with active filter: {}, page: {}, size: {}",
                active,
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Product> products = active == null
                ? productRepository.findAll(pageable)
                : productRepository.findAllByActive(active, pageable);

        log.info(
                "Successfully retrieved {} products. Total elements: {}, total pages: {}",
                products.getNumberOfElements(),
                products.getTotalElements(),
                products.getTotalPages()
        );
        return products.map(ProductMapper::toResponse);

    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, UpdateProductRequest request
    ) {
        Objects.requireNonNull(request, "UpdateProductRequest must not be null");

        if (request.getName() == null && !request.isDescriptionProvided() && request.getActive() == null
        ) {
            throw new IllegalArgumentException("At least one product field must be provided");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.updateDetails(request.getName(),
                request.getDescription(),
                request.isDescriptionProvided(),
                request.getActive()
        );

        Product saved = productRepository.saveAndFlush(product);

        log.info("Product successfully updated with id: {}", id);

        return ProductMapper.toResponse(saved);

    }
}
