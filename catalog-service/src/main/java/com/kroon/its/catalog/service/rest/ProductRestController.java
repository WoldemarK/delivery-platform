package com.kroon.its.catalog.service.rest;

import com.kroon.its.catalog.service.dto.CreateProductRequest;
import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id
    ) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    ResponseEntity<Page<ProductResponse>> getProducts(@RequestParam Boolean active,
                                                      Pageable pageable
    ) {
        return ResponseEntity.ok(productService.getProducts(active, pageable));
    }
}
