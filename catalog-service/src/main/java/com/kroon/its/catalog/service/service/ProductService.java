package com.kroon.its.catalog.service.service;

import com.kroon.its.catalog.service.dto.CreateProductRequest;
import com.kroon.its.catalog.service.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProductById(UUID id);

    Page<ProductResponse> getProducts(Boolean active,
                                      Pageable pageable);
}
