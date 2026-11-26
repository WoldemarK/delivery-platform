package com.kroon.its.common.dto.catalog;

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
