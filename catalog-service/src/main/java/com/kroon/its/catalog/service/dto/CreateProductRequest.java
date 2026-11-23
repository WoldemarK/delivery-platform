package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateProductRequest
        (
                @NotBlank(message = "Name is required")
                String name,

                String description,


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
                BigDecimal price,

                @NotBlank(message = "Currency is required")
                @Pattern(
                        regexp = "^[A-Z]{3}$",
                        message = "Currency must be a 3-letter uppercase ISO code"
                )
                String currency
        ) {
}
