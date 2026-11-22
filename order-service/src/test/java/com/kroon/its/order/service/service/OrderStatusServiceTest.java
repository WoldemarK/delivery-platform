package com.kroon.its.order.service.service;


import com.kroon.its.order.service.entity.Order;
import com.kroon.its.order.service.entity.OrderStatus;
import com.kroon.its.order.service.entity.OrderStatusHistory;
import com.kroon.its.order.service.exception.OrderNotFoundException;
import com.kroon.its.order.service.repository.OrderRepository;
import com.kroon.its.order.service.repository.OrderStatusHistoryRepository;
import com.kroon.its.order.service.dto.ChangeOrderStatusRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderStatusServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @InjectMocks
    private OrderStatusService orderStatusService;

    private UUID orderId;
    private UUID correlationId;
    private UUID sagaId;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        correlationId = UUID.randomUUID();
        sagaId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Should change order status and save status history")
    void shouldChangeOrderStatus(

    ) {

        var order = Order.builder()
                .id(orderId)
                .status(OrderStatus.NEW)
                .build();

        var request = ChangeOrderStatusRequest.builder()
                .orderId(orderId)
                .newStatus(OrderStatus.PROCESSING)
                .source("ORDER_SERVICE")
                .reason("Payment completed")
                .correlationId(correlationId)
                .sagaId(sagaId)
                .changedBy("system")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));


        orderStatusService.changeStatus(request);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.PROCESSING);

        verify(orderRepository).save(order);

        var historyCaptor = ArgumentCaptor.forClass(OrderStatusHistory.class);

        verify(orderStatusHistoryRepository).save(historyCaptor.capture());

        OrderStatusHistory history = historyCaptor.getValue();

        assertThat(history.getOrderId()).isEqualTo(orderId);
        assertThat(history.getPreviousStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(history.getNewStatus()).isEqualTo(OrderStatus.PROCESSING);
        assertThat(history.getSource()).isEqualTo("ORDER_SERVICE");
        assertThat(history.getReason()).isEqualTo("Payment completed");
        assertThat(history.getCorrelationId()).isEqualTo(correlationId);
        assertThat(history.getSagaId()).isEqualTo(sagaId);
        assertThat(history.getChangedBy()).isEqualTo("system");

        verify(orderRepository, times(1)).save(order);
        verify(orderStatusHistoryRepository, times(1)).save(any(OrderStatusHistory.class));
    }

    @Test
    @DisplayName("Should not save anything when status has not changed")
    void shouldNotSaveWhenStatusHasNotChanged(

    ) {
        var order = Order.builder()
                .id(orderId)
                .status(OrderStatus.NEW)
                .build();

        var request = ChangeOrderStatusRequest.builder()
                .orderId(orderId)
                .newStatus(OrderStatus.NEW)
                .source("ORDER_SERVICE")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderStatusService.changeStatus(request);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);

        verify(orderRepository, never()).save(order);
        verify(orderStatusHistoryRepository, never()).save(any(OrderStatusHistory.class));
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when order does not exist")
    void shouldThrowExceptionWhenOrderNotFound(
    ) {
        var request = ChangeOrderStatusRequest.builder()
                .orderId(orderId)
                .newStatus(OrderStatus.PROCESSING)
                .source("ORDER_SERVICE")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderStatusService.changeStatus(request))
                .isInstanceOf(OrderNotFoundException.class);

        verify(orderRepository, never()).save(any(Order.class));
        verify(orderStatusHistoryRepository, never()).save(any(OrderStatusHistory.class));
    }

    @Test
    @DisplayName("Should save history with null optional fields")
    void shouldSaveHistoryWithNullOptionalFields(

    ) {
        var order = Order.builder()
                .id(orderId)
                .status(OrderStatus.CONFIRMED)
                .build();

        var request = ChangeOrderStatusRequest.builder()
                .orderId(orderId)
                .newStatus(OrderStatus.IN_DELIVERY)
                .source("DELIVERY_SERVICE")
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderStatusService.changeStatus(request);

        var historyCaptor = ArgumentCaptor.forClass(OrderStatusHistory.class);

        verify(orderStatusHistoryRepository).save(historyCaptor.capture());

        OrderStatusHistory history = historyCaptor.getValue();

        assertThat(history.getPreviousStatus()).isEqualTo(OrderStatus.CONFIRMED);

        assertThat(history.getNewStatus()).isEqualTo(OrderStatus.IN_DELIVERY);

        assertThat(history.getReason()).isNull();
        assertThat(history.getCorrelationId()).isNull();
        assertThat(history.getSagaId()).isNull();
        assertThat(history.getChangedBy()).isNull();
    }
}