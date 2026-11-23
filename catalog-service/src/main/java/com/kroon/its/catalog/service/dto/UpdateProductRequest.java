package com.kroon.its.catalog.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest
        (
                @NotBlank(message = "Name is required")
                @Size(min = 1, max = 20,
                        message = "Name must be between 1 and 200 characters")
                String name,
                String description,
                Boolean active
        ) {
}
