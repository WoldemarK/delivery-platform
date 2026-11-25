package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record PriceQuoteRequest
        (
                @NotEmpty(message = "Product IDs are required")
                @Size(max = 100,
                        message = "Maximum 100 products are allowed")
                List<@NotNull(message = "Product ID must not be null") UUID> productIds
        ) {
}
