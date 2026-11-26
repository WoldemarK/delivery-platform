package com.kroon.its.common.dto.catalog;

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
