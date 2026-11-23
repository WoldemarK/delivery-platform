package com.kroon.its.catalog.service.mapper;

import com.kroon.its.catalog.service.dto.PriceQuoteItemResponse;
import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.entity.Product;

import java.util.List;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product
    ) {
        if (product == null) {
            return null;
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .version(product.getVersion())
                .build();
    }

    public static List<ProductResponse> toResponseList(List<Product> products
    ) {
        if (products == null) {
            return List.of();
        }
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public static PriceQuoteItemResponse toPriceQuoteItemResponse(Product product
    ) {
        if (product == null) {
            return null;
        }
        return PriceQuoteItemResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .unitPrice(product.getPrice())
                .currency(product.getCurrency())
                .build();
    }

    public static List<PriceQuoteItemResponse> toPriceQuoteItemResponseList(List<Product> products
    ) {
        if (products == null) {
            return List.of();
        }
        return products.stream()
                .map(ProductMapper::toPriceQuoteItemResponse)
                .toList();
    }
}
