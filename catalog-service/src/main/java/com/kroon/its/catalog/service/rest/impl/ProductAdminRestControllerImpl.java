package com.kroon.its.catalog.service.rest.impl;

import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;
import com.kroon.its.catalog.service.dto.UpdateProductRequest;
import com.kroon.its.catalog.service.rest.ProductAdminRestController;
import com.kroon.its.catalog.service.service.ProductPriceService;
import com.kroon.its.catalog.service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/v1/products")
public class ProductAdminRestControllerImpl implements ProductAdminRestController {

    private final ProductService productService;
    private final ProductPriceService productPriceService;

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
                                                         @Valid @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, request)
        );
    }

    @Override
    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponse> updateProductPrice(@PathVariable UUID id,
                                                              @Valid @RequestBody UpdateProductPriceRequest request
    ) {
        return ResponseEntity.ok(productPriceService.updateProductPrice(id, request)
        );
    }
}
