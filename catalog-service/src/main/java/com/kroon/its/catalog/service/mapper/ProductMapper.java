package com.kroon.its.catalog.service.mapper;

import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import com.kroon.its.common.dto.catalog.CreateProductRequest;
import com.kroon.its.common.dto.catalog.PriceQuoteItemResponse;
import com.kroon.its.common.dto.catalog.ProductPriceHistoryResponse;
import com.kroon.its.common.dto.catalog.ProductResponse;
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

    public static Product toProduct(CreateProductRequest request) {
        if (request == null) {
            return null;
        }
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .currency(request.currency())
                .build();
    }

    public static ProductPriceHistoryResponse toProductPriceHistoryResponse(
            ProductPriceHistory history
    ) {
        return new ProductPriceHistoryResponse(
                history.getId(),
                history.getOldPrice(),
                history.getNewPrice(),
                history.getCurrency(),
                history.getChangedAt(),
                history.getChangedBy()
        );
    }
}