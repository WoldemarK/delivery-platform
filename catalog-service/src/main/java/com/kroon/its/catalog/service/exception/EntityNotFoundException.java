package com.kroon.its.catalog.service.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(UUID id) {
        super("Active product not found: " + id);
    }
}
