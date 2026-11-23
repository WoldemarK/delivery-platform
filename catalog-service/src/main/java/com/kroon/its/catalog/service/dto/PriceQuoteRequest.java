package com.kroon.its.catalog.service.dto;

import com.kroon.its.catalog.service.entity.Product;

import java.util.List;

public record PriceQuoteRequest
        (
                List<Product> products
        ) {
}
