package com.kroon.its.catalog.service.service;


import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;

import java.util.UUID;

public interface ProductPriceService {

    ProductResponse updateProductPrice(UUID id, UpdateProductPriceRequest request);

}
