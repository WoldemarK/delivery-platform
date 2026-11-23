package com.kroon.its.catalog.service.dto;

public record UpdateProductRequest
        (
                String name,
                String description,
                boolean active
        ) {
}
