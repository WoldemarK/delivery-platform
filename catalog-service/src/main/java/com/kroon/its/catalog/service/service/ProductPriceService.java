package com.kroon.its.catalog.service.service;


import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;
import com.kroon.its.catalog.service.dto.UpdateProductRequest;

import java.util.UUID;

public interface ProductPriceService {

    ProductResponse updateProductById(UUID id, UpdateProductRequest request);

    ProductResponse updateProductPrice(UUID id, UpdateProductPriceRequest request);
}
