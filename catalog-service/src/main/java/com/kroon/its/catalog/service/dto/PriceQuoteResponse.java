package com.kroon.its.catalog.service.dto;

import java.util.List;

public record PriceQuoteResponse
        (
                List<PriceQuoteItemResponse> items
        ) {
}
