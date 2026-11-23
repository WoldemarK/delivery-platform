package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductPriceRequest
        (
                @NotNull(message = "Price is required")
                @DecimalMin(
                        value = "0.01",
                        message = "Price must be greater than zero"
                )
                @Digits(
                        integer = 10,
                        fraction = 2,
                        message = "Price must have at most 10 integer digits and 2 decimal places"
                )
                BigDecimal price
        ) {
}
