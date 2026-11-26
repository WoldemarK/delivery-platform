package com.kroon.its.catalog.service.service.impl;

import com.kroon.its.catalog.service.entity.ProductPriceHistory;
import com.kroon.its.catalog.service.exception.ProductNotFoundException;
import com.kroon.its.catalog.service.repository.ProductPriceHistoryRepository;
import com.kroon.its.catalog.service.repository.ProductRepository;
import com.kroon.its.common.dto.catalog.ProductPriceHistoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceHistoryServiceImplTest {

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private  ProductPriceHistoryRepository priceHistoryRepository;

    @InjectMocks
    private ProductPriceHistoryServiceImpl service;

    @Test
    void getPriceHistory_shouldReturnPriceHistory_whenProductExists() {

        var productId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        var history = new ProductPriceHistory();
        Page<ProductPriceHistory> historyPage = new PageImpl<>(List.of(history), pageable, 1);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(priceHistoryRepository.findAllByProductIdOrderByChangedAtDescIdDesc(productId, pageable))
                .thenReturn(historyPage);

        Page<ProductPriceHistoryResponse> result = service.getPriceHistory(productId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        verify(productRepository).existsById(productId);
        verify(priceHistoryRepository).findAllByProductIdOrderByChangedAtDescIdDesc(productId, pageable);
    }

    @Test
    void getPriceHistory_shouldReturnEmptyPage_whenHistoryDoesNotExist() {

        var productId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(priceHistoryRepository.findAllByProductIdOrderByChangedAtDescIdDesc(productId, pageable))
                .thenReturn(Page.empty(pageable));

        Page<ProductPriceHistoryResponse> result = service.getPriceHistory(productId, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(productRepository).existsById(productId);
        verify(priceHistoryRepository).findAllByProductIdOrderByChangedAtDescIdDesc(productId, pageable);
    }

    @Test
    void getPriceHistory_shouldThrowProductNotFoundException_whenProductDoesNotExist() {

        var productId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> service.getPriceHistory(productId, pageable));

        verify(productRepository).existsById(productId);
        verifyNoInteractions(priceHistoryRepository);
    }

    @Test
    void getPriceHistory_shouldPassCorrectPageableToRepository() {

        UUID productId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(2, 5);

        when(productRepository.existsById(productId)).thenReturn(true);

        when(priceHistoryRepository.findAllByProductIdOrderByChangedAtDescIdDesc(productId, pageable))
                .thenReturn(Page.empty(pageable));

        service.getPriceHistory(productId, pageable);

        verify(priceHistoryRepository).findAllByProductIdOrderByChangedAtDescIdDesc(eq(productId), eq(pageable));
    }
}