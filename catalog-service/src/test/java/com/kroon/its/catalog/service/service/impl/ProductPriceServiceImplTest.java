package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.dto.ProductResponse;
import com.kroon.its.catalog.service.dto.UpdateProductPriceRequest;
import com.kroon.its.catalog.service.entity.Product;
import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import com.kroon.its.catalog.service.exception.ProductNotFoundException;
import com.kroon.its.catalog.service.mapper.ProductMapper;
import com.kroon.its.catalog.service.repository.ProductPriceHistoryRepository;
import com.kroon.its.catalog.service.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductPriceHistoryRepository productPriceHistoryRepository;

    @InjectMocks
    private ProductPriceServiceImpl productPriceService;

    private MockedStatic<ProductMapper> productMapper;

    private final UUID productId = UUID.randomUUID();

    private Product product;
    private ProductResponse productResponse;

    private final BigDecimal oldPrice = new BigDecimal("100.00");
    private final BigDecimal newPrice = new BigDecimal("150.00");

    @BeforeEach
    void setUp() {
        product = mock(Product.class);

        productResponse = ProductResponse.builder()
                .id(productId)
                .name("iPhone")
                .description("Smartphone")
                .price(newPrice)
                .currency("USD")
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .version(1L)
                .build();

        productMapper = mockStatic(ProductMapper.class);
    }

    @AfterEach
    void tearDown() {
        productMapper.close();
    }

    @Test
    @DisplayName("Should update product price and save price history")
    void shouldUpdateProductPriceAndSaveHistory(

    ) {
        var request = new UpdateProductPriceRequest(newPrice);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(product.getPrice()).thenReturn(oldPrice);
        when(product.getCurrency()).thenReturn("USD");
        when(productRepository.saveAndFlush(product)).thenReturn(product);

        productMapper.when(() -> ProductMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productPriceService.updateProductPrice(productId, request);
        assertNotNull(result);
        assertEquals(productResponse, result);

        verify(productRepository).findById(productId);
        verify(product).updatePrice(newPrice);
        verify(productRepository).saveAndFlush(product);

        ArgumentCaptor<ProductPriceHistory> historyCaptor = ArgumentCaptor.forClass(ProductPriceHistory.class);

        verify(productPriceHistoryRepository).save(historyCaptor.capture());

        ProductPriceHistory history = historyCaptor.getValue();

        assertEquals(product, history.getProduct());
        assertEquals(oldPrice, history.getOldPrice());
        assertEquals(newPrice, history.getNewPrice());
        assertEquals("USD", history.getCurrency());

        productMapper.verify(() -> ProductMapper.toResponse(product));
    }

    @Test
    @DisplayName("Should not save history when price has not changed")
    void shouldNotSaveHistoryWhenPriceHasNotChanged(

    ) {

        var request = new UpdateProductPriceRequest(oldPrice);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(product.getPrice()).thenReturn(oldPrice);

        var unchangedResponse = ProductResponse.builder()
                .id(productId)
                .name("iPhone")
                .price(oldPrice)
                .currency("USD")
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .version(1L)
                .build();

        productMapper.when(() -> ProductMapper.toResponse(product)).thenReturn(unchangedResponse);


        var result = productPriceService.updateProductPrice(productId, request);


        assertNotNull(result);
        assertEquals(unchangedResponse, result);

        verify(productRepository).findById(productId);
        verify(product, never()).updatePrice(any());
        verify(productRepository, never()).saveAndFlush(any());
        verify(productPriceHistoryRepository, never()).save(any());

        productMapper.verify(() -> ProductMapper.toResponse(product));
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when product does not exist")
    void shouldThrowExceptionWhenProductNotFound(

    ) {
        var request = new UpdateProductPriceRequest(newPrice);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productPriceService.updateProductPrice(productId, request));

        verify(productRepository).findById(productId);
        verify(productRepository, never()).saveAndFlush(any());
        verify(productPriceHistoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw NullPointerException when request is null")
    void shouldThrowExceptionWhenRequestIsNull() {

        var exception = assertThrows(NullPointerException.class,
                () -> productPriceService.updateProductPrice(productId, null));

        assertEquals("UpdateProductPriceRequest must not be null", exception.getMessage());

        verifyNoInteractions(productRepository, productPriceHistoryRepository);
    }

    @Test
    @DisplayName("Should not save history if price update fails")
    void shouldNotSaveHistoryWhenPriceUpdateFails() {

        var request = new UpdateProductPriceRequest(newPrice);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(product.getPrice()).thenReturn(oldPrice);
        when(productRepository.saveAndFlush(product)).thenThrow(new RuntimeException("Database error"));


        assertThrows(RuntimeException.class,
                () -> productPriceService.updateProductPrice(productId, request));

        verify(product).updatePrice(newPrice);
        verify(productRepository).saveAndFlush(product);
        verifyNoInteractions(productPriceHistoryRepository);
    }

}