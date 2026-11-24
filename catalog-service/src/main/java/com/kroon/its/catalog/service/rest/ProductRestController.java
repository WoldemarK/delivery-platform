package com.kroon.its.catalog.service.rest;

import com.kroon.its.catalog.service.dto.CreateProductRequest;
import com.kroon.its.catalog.service.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Set;
import java.util.UUID;


public interface ProductRestController {

    String DEFAULT_SORT = "createdAt,desc";

    Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "name",
            "price",
            "createdAt",
            "updatedAt"
    );

    ResponseEntity<ProductResponse> createProduct(CreateProductRequest request);

    ResponseEntity<ProductResponse> getProductById(UUID id);

    ResponseEntity<Page<ProductResponse>> getProducts(Boolean active, int page, int size, String sort);

    default Pageable createPageable(int page, int size, String sort) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be greater than or equal to 0");
        }

        if (size <= 0 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
        String[] sortParams = sort.split(",", -1);
        if (sortParams.length > 2 || sortParams[0].isBlank()) {
            throw new IllegalArgumentException("Invalid sort parameter");
        }

        String property = sortParams[0].trim();

        if (!ALLOWED_SORT_FIELDS.contains(property)) {
            throw new IllegalArgumentException("Sorting by field '" + property + "' is not allowed");
        }

        Sort.Direction direction = Sort.Direction.ASC;

        if (sortParams.length == 2) {
            try {
                direction = Sort.Direction.fromString(sortParams[1].trim());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Invalid sort direction: " + sortParams[1]);
            }
        }

        return PageRequest.of(page, size, Sort.by(direction, property));
    }
}
