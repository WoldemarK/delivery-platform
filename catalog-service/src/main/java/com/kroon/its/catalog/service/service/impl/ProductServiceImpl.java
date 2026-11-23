package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.dto.CreateProductRequest;
import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.entity.Product;
import com.kroon.its.catalog.service.exception.ProductNotFoundException;
import com.kroon.its.catalog.service.mapper.ProductMapper;
import com.kroon.its.catalog.service.repository.ProductRepository;
import com.kroon.its.catalog.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductResponse createProduct(CreateProductRequest request
    ) {
        Product product = ProductMapper.toProduct(request);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    @Override
    public ProductResponse getProductById(UUID id
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(id));

        return ProductMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> getProducts(Boolean active,
                                             Pageable pageable
    ) {
        Page<Product> products = active == null
                ? productRepository.findAll(pageable)
                : productRepository.findAllByActive(active, pageable);

        return products.map(ProductMapper::toResponse);

    }
}
