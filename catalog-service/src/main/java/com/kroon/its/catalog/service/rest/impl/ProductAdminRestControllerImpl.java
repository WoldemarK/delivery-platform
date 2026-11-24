package com.kroon.its.catalog.service.rest.impl;

import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;
import com.kroon.its.catalog.service.dto.UpdateProductRequest;
import com.kroon.its.catalog.service.rest.ProductAdminRestController;
import com.kroon.its.catalog.service.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/v1/products")
public class ProductAdminRestControllerImpl implements ProductAdminRestController {

    private final ProductPriceService productPriceService;


    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
                                                         @RequestBody UpdateProductRequest request) {
        return new ResponseEntity<>(productPriceService.updateProductById(id, request), HttpStatus.OK);
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponse> updateProductPrice(@PathVariable UUID id,
                                                              @RequestBody UpdateProductPriceRequest request) {
        return new ResponseEntity<>(productPriceService.updateProductPrice(id, request), HttpStatus.OK);
    }
}
