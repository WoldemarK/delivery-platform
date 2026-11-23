package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateProductRequest
        (

                @NotBlank(message = "Name is required")
                @Size(max = 200,
                        message = "Name must not exceed 200 characters")
                String name,

                @Size(max = 2000,
                        message = "Description must not exceed 2000 characters")
                String description,

                @NotNull(message = "Price is required")
                @DecimalMin(value = "0.00",
                        message = "Price must be greater than or equal to zero")
                @Digits(integer = 10,
                        fraction = 2)
                BigDecimal price,

                @NotBlank(message = "Currency is required")
                @Pattern(regexp = "^[A-Z]{3}$",
                        message = "Currency must be a 3-letter uppercase ISO code")
                String currency
        ) {
}
