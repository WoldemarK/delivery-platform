package com.kroon.its.catalog.service.rest;

import com.kroon.its.catalog.service.dto.CreateProductRequest;
import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;
    private static final String DEFAULT_SORT = "createdAt,desc";

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "name",
            "price",
            "createdAt"
    );

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request
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
    ResponseEntity<Page<ProductResponse>> getProducts(@RequestParam(required = false) Boolean active,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size,
                                                      @RequestParam(defaultValue = DEFAULT_SORT) String sort
    ) {

        Pageable pageable = createPageable(page, size, sort);

        return ResponseEntity.ok(productService.getProducts(active, pageable));
    }

    private Pageable createPageable(int page, int size, String sort) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be greater than or equal to 0");
        }

        if (size <= 0 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
        String[] sortParams = sort.split(",");
        if (sortParams.length > 2 || sortParams[0].isBlank()) {
            throw new IllegalArgumentException("Invalid sort parameter");
        }

        String property = sortParams[0].trim();

        if (!ALLOWED_SORT_FIELDS.contains(property)) {
            throw new IllegalArgumentException(
                    "Sorting by field '" + property + "' is not allowed"
            );
        }

        Sort.Direction direction = Sort.Direction.ASC;

        if (sortParams.length == 2) {
            try {
                direction = Sort.Direction.fromString(
                        sortParams[1].trim()
                );
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(
                        "Invalid sort direction: " + sortParams[1]
                );
            }
        }

        return PageRequest.of(
                page,
                size,
                Sort.by(direction, property)
        );
    }
}
