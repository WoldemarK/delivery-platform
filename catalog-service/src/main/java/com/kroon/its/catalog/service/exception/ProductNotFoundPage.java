package com.kroon.its.catalog.service.exception;

import org.springframework.data.domain.Pageable;

public class ProductNotFoundPage extends RuntimeException {

    public ProductNotFoundPage(Boolean active, Pageable pageable) {
        super(String.format(
                "Products with active: %s, pageable: %s not found",
                active,
                pageable
        ));
    }
}
