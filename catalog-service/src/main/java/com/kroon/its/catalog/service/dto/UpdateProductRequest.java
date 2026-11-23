package com.kroon.its.catalog.service.dto;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest
        (
                @Size(min = 1, max = 200,
                        message = "Name must be between 1 and 200 characters")
                @Pattern(regexp = ".*\\S.*",
                        message = "Name must not be blank")
                String name,

                @Size(max = 2000,
                        message = "Description must not exceed 2000 characters")
                String description,

                Boolean active
        ) {
}
