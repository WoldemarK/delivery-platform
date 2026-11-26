package com.kroon.its.catalog.service.service;

import com.kroon.its.common.dto.catalog.CreateProductRequest;
import com.kroon.its.common.dto.catalog.ProductResponse;
import com.kroon.its.common.dto.catalog.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProductById(UUID id);

    Page<ProductResponse> getProducts(Boolean active, Pageable pageable);

    ProductResponse updateProduct(UUID id, UpdateProductRequest request);

}
