package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record PriceQuoteResponse
        (
                @NotEmpty
                @Size(max = 100)
                List<@NotNull UUID> productIds
        ) {
}
