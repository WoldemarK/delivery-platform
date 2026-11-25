package com.kroon.its.catalog.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductPriceHistoryResponse(
        UUID id,
        BigDecimal oldPrice,
        BigDecimal newPrice,
        String currency,
        Instant changedAt,
        String changedBy
) {
}
