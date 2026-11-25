package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductPriceRequest
        (
                @NotNull(message = "Price is required")
                @DecimalMin(value = "0.00",
                        message = "Price must be greater than or equal to zero")
                @Digits(integer = 10, fraction = 2,
                        message = "Price must have no more than 10 integer and 2 fractional digits")
                BigDecimal price
        ) {
}
