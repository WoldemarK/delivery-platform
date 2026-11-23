package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ProductResponse
        (
                @NotNull
                UUID id,

                @NotBlank
                String name,

                String description,

                @NotNull
                @DecimalMin("0.00")
                @Digits(integer = 10, fraction = 2)
                BigDecimal price,

                @NotBlank
                @Pattern(regexp = "^[A-Z]{3}$")
                String currency,

                boolean active,

                @NotNull
                Instant createdAt,

                @NotNull
                Instant updatedAt,

                @NotNull
                Long version
        ) {
}
