package com.kroon.its.catalog.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PriceQuoteItemResponse
        (
                UUID productId,
                String productName,
                BigDecimal unitPrice,
                String currency
        ) {
}
