package com.kroon.its.catalog.service.service;


import com.kroon.its.common.dto.catalog.ProductResponse;
import com.kroon.its.common.dto.catalog.UpdateProductPriceRequest;

import java.util.UUID;

public interface ProductPriceService {

    ProductResponse updateProductPrice(UUID id, UpdateProductPriceRequest request);

}
