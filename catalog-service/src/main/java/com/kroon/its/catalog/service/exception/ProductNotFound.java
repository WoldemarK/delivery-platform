package com.kroon.its.catalog.service.exception;

import java.util.UUID;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(UUID id){
        super("Product with id: "+id+" not found");
    }
}
