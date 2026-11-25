package com.kroon.its.catalog.service.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PriceQuoteItemResponse
        (
                UUID productId,
                String productName,
                BigDecimal unitPrice,
                String currency
        ) {
}
