package com.kroon.its.common.dto.catalog;

import java.util.List;

public record PriceQuoteResponse
        (
                List<PriceQuoteItemResponse> items
        ) {
}
