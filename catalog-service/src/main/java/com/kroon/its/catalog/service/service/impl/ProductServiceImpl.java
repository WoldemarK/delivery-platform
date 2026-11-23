package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.dto.CreateProductRequest;
import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.entity.Product;
import com.kroon.its.catalog.service.exception.ProductNotFound;
import com.kroon.its.catalog.service.exception.ProductNotFoundPage;
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
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request
    ) {
        Product saved = ProductMapper.toProduct(request);
        productRepository.save(saved);
        return ProductMapper.toResponse(saved);
    }

    @Override
    public ProductResponse getProductById(UUID id
    ) {
        Product find = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound(id));
        return ProductMapper.toResponse(find);
    }

    @Override
    public Page<ProductResponse> getProducts(Boolean active,
                                             Pageable pageable
    ) {
        Page<Product> find = productRepository.findAllByActive(active, pageable);
        if (!find.stream().isParallel()) {
            throw new ProductNotFoundPage(active, pageable);
        }
        return find.map(ProductMapper::toResponse);

    }
}
