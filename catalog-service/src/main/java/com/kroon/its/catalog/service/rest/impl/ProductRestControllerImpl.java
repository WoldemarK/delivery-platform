package com.kroon.its.catalog.service.rest.impl;

import com.kroon.its.common.dto.catalog.CreateProductRequest;
import com.kroon.its.common.dto.catalog.ProductResponse;
import com.kroon.its.catalog.service.rest.ProductRestController;
import com.kroon.its.catalog.service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestControllerImpl implements ProductRestController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request
    ) {
        ProductResponse response = productService.createProduct(request);

        URI local = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(local)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id
    ) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@RequestParam(required = false) Boolean active,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size,
                                                             @RequestParam(defaultValue = DEFAULT_SORT) String sort
    ) {

        Pageable pageable = createPageable(page, size, sort);

        return ResponseEntity.ok(productService.getProducts(active, pageable));
    }

}
