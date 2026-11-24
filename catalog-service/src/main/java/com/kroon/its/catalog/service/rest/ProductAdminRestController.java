package com.kroon.its.catalog.service.rest;

import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;
import com.kroon.its.catalog.service.dto.UpdateProductRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ProductAdminRestController {

    ResponseEntity<ProductResponse> updateProduct(UUID id, UpdateProductRequest request);

    ResponseEntity<ProductResponse> updateProductPrice(UUID id, UpdateProductPriceRequest request);
}
